package recommendations;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public final class Search {
    /**
     * Gets all shows unseen by the user of a particular genre
     *
     * @param user user for whom the recommendation applies
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return ArrayList with the shows
     */
    public ArrayList<Show> searchUnseen(final User user, final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials,
                                        final ActionInputData actionInputData) {
        ArrayList<Show> searchUnseen = new ArrayList<>();
        for (var movie : movies) {
                if (!user.getHistory().containsKey(movie.getTitle())) {
                    searchUnseen.add(movie);
                }
        }
        for (var serial : serials) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    searchUnseen.add(serial);
                }
        }
        searchUnseen.sort((show1, show2) -> {
            if (!Objects.equals(show1.getRating(), show2.getRating())) {
                return Double.compare(show1.getRating(), show2.getRating());
            } else {
                return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        searchUnseen.removeIf((show) ->
                !show.getGenres().contains(actionInputData.getGenre()));
        return searchUnseen;
    }

    /**
     * Executes the search recommendation
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject searchRecommendation(final ArrayList<User> users,
                                                  final ArrayList<Movie> movies,
                                                  final ArrayList<Serial> serials,
                                                  final ActionInputData actionInputData,
                                                  final Writer writer) throws IOException {
        Search search = new Search();
        User user = Utils.findUser(users, actionInputData.getUsername());
        StringBuilder message = new StringBuilder();
        if (user != null) {
            if (!user.getSubscriptionType().equals("PREMIUM")
                    || search.searchUnseen(user, movies, serials, actionInputData).size() == 0) {
                message.append("SearchRecommendation cannot be applied!");
            } else {
                message.append("SearchRecommendation result: [");
                for (var show : search.searchUnseen(user, movies, serials, actionInputData)) {
                    message.append(show.getTitle());
                    message.append(", ");
                }
                if (search.searchUnseen(user, movies, serials, actionInputData).size() > 0) {
                    message.deleteCharAt(message.length() - 1);
                    message.deleteCharAt(message.length() - 1);
                }
                message.append("]");
            }
        }
        String finalMessage = new String(message);
        return writer.writeFile(actionInputData.getActionId(), null,
                finalMessage);
    }
}
