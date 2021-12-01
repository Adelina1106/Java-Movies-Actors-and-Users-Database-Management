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

public class View {

    /**
     * Adds a show to the history of the user
     *
     * @param user the user that viewed the show
     * @param show the show that was viewed
     */
    public void addVisualised(final User user, final Show show) {
        if (user.getHistory().containsKey(show.getTitle())) {
            //increase the number of views by 1 if the video has already been seen
            user.getHistory().put(show.getTitle(), user.getHistory().get(show.getTitle()) + 1);
        } else {
            user.getHistory().put(show.getTitle(), 1);
        }
    }

    /**
     * Executes the view command
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the message with the result of the command
     */
    public static JSONObject viewCommand(final ArrayList<User> users,
                                         final ArrayList<Movie> movies,
                                         final ArrayList<Serial> serials,
                                         final ActionInputData actionInputData,
                                         final Writer writer) throws IOException {
        View view = new View();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies, serials, actionInputData.getTitle());
        String message = null;
        if (user != null && show != null) {
            view.addVisualised(user, show);
            message = "success -> " + actionInputData.getTitle()
                    + " was viewed with total views of "
                    + user.getHistory().get(show.getTitle());
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
