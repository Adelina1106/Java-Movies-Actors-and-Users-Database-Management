package commands;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Show;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

public class Rating {

    /**
     * Method used for rating a show
     *
     * @param show the show that is rated
     * @param user user that rates the show
     * @param actionInputData information about the action
     */
    public void rateShow(final Show show, final User user, final ActionInputData actionInputData) {
        show.ratingShow(user, actionInputData.getGrade(), actionInputData);
    }

    /**
     * Executes the rating command
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the message with the result of the command
     */
    public static JSONObject ratingCommand(final ArrayList<User> users,
                                           final ArrayList<Movie> movies,
                                           final ArrayList<Serial> serials,
                                           final ActionInputData actionInputData,
                                           final Writer writer) throws IOException {
        Rating rating = new Rating();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies, serials, actionInputData.getTitle());
        String message = null;
        if (show != null && user != null) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                message = "error -> " + show.getTitle() + " is not seen";
            } else if (!user.getRatings().containsKey(show.getTitle())
                    && !user.getRatings().containsKey(show.getTitle()
                    + actionInputData.getSeasonNumber())) {
                rating.rateShow(show, user, actionInputData);
                message = "success -> " + actionInputData.getTitle() + " was rated with "
                        + actionInputData.getGrade() + " by " + actionInputData.getUsername();
            } else {
                message = "error -> " + show.getTitle() + " has been already rated";
            }
        }

        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}

