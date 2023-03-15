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

public final class BestUnseen {
    /**
     * Gets return the best video unseen by that user.
     *
     * @param user the user who has not seen the video
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @return the best unseen show
     */
    public Show getBestUnseen(final User user, final ArrayList<Movie> movies,
                              final ArrayList<Serial> serials) {
        ArrayList<Show> ratingVideos = new ArrayList<>();
        movies.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .forEach(ratingVideos::add);
        serials.stream()
                .filter(show -> !user.getHistory().containsKey(show.getTitle()))
                .forEach(ratingVideos::add);
        ratingVideos.sort((show1, show2) -> Double.compare(show2.getRating(), show1.getRating()));
        if (ratingVideos.size() > 0) {
            return ratingVideos.get(0);
        } else {
            return null;
        }
    }

    /**
     * Executes the best unseen recommendation
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject bestUnseenRecommendation(final ArrayList<User> users,
                                                      final ArrayList<Movie> movies,
                                                      final ArrayList<Serial> serials,
                                                      final ActionInputData actionInputData,
                                                      final Writer writer) throws IOException {
        BestUnseen bestUnseen = new BestUnseen();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message;
        if (bestUnseen.getBestUnseen(user, movies, serials) != null) {
            message = "BestRatedUnseenRecommendation result: "
                    + Objects.requireNonNull(bestUnseen.getBestUnseen(user, movies, serials)).
                    getTitle();
        } else {
            message = "BestRatedUnseenRecommendation cannot be applied!";
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
