package recommandations;

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

public class Standard extends RecommendationForAllUsers {
    public Show getFirstUnseen(User user, ArrayList<Movie> movies, ArrayList<Serial> serials) {
        for (var movie : movies)
            if (!user.getHistory().containsKey(movie.getTitle()))
                return movie;
        for (var serial : serials)
            if (!user.getHistory().containsKey(serial.getTitle()))
                return serial;
        return null;
    }

    static public JSONObject standardRecommendation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                                    ActionInputData actionInputData, Writer writer) throws IOException {
        Standard standard = new Standard();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message = null;
        if (standard.getFirstUnseen(user, movies, serials) != null)
            message = "StandardRecommendation result: " + standard.getFirstUnseen(user, movies, serials).getTitle();
        else message = "StandardRecommendation cannot be applied!";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
