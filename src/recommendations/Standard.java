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

public final class Standard {
    /**
     * Gets first unseen show from the database
     *
     * @param user user for whom the recommendation applies
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @return the show
     */
    public Show getFirstUnseen(final User user, final ArrayList<Movie> movies,
                               final ArrayList<Serial> serials) {
        for (var movie : movies) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                return movie;
            }
        }
        for (var serial : serials) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                return serial;
            }
        }
        return null;
    }

    /**
     * Executes the standard recommendation
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject standardRecommendation(final ArrayList<User> users,
                                                    final ArrayList<Movie> movies,
                                                    final ArrayList<Serial> serials,
                                                    final ActionInputData actionInputData,
                                                    final Writer writer) throws IOException {
        Standard standard = new Standard();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show firstUnseen = standard.getFirstUnseen(user, movies, serials);
        String message;
        if (firstUnseen != null) {
            message = "StandardRecommendation result: "
                    + firstUnseen.getTitle();
        } else {
            message = "StandardRecommendation cannot be applied!";
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
