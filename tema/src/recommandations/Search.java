package recommandations;

import actor.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import queries.VideoQuery;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;

public class Search {
    public ArrayList<Show> searchUnseen(User user, ArrayList<Movie> movies, ArrayList<Serial> serials, ActionInputData actionInputData) {
        ArrayList<Show> searchUnseen = new ArrayList<>();
        for (var movie : movies)
            if (!user.getHistory().containsKey(movie.getTitle()))
                searchUnseen.add(movie);
        for (var serial : serials)
            if (!user.getHistory().containsKey(serial.getTitle()))
                searchUnseen.add(serial);
        VideoQuery.sortByRating(searchUnseen);
        searchUnseen.removeIf((show) -> !show.getGenres().contains(actionInputData.getGenre()));
        return searchUnseen;
    }

    static public JSONObject searchRecommendation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                                  ActionInputData actionInputData, Writer writer) throws IOException {
        Search search = new Search();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message = null;
        if (!user.getSubscriptionType().equals("PREMIUM") || search.searchUnseen(user, movies, serials, actionInputData).size() == 0)
            message = "SearchRecommendation cannot be applied!";
        else {
            message = "SearchRecommendation result: [";
            for (var show : search.searchUnseen(user, movies, serials, actionInputData))
                message = message + show.getTitle() + ", ";
            if (search.searchUnseen(user, movies, serials, actionInputData).size() > 0)
                message = message.substring(0, message.length() - 2);
            message = message + "]";
        }
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
