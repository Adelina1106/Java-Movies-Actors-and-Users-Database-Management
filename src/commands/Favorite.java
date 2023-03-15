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

public class Favorite {

    /**
     * Add a show to the list of favourites of the user
     *
     * @param user the user in whose list of favorites is added the show
     * @param show the show that is added
     */
    public void addFavourite(final User user, final Show show) {
        ArrayList<String> favorites = new ArrayList<>(user.getFavourites());
        favorites.add(show.getTitle());
        user.setFavourites(favorites);
    }

    /**
     * Execute the favorite command
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the message with the result of the command
     */
    public static JSONObject favoriteCommand(final ArrayList<User> users,
                                             final ArrayList<Movie> movies,
                                             final ArrayList<Serial> serials,
                                             final ActionInputData actionInputData,
                                             final Writer writer) throws IOException {
        Favorite favourite = new Favorite();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies, serials, actionInputData.getTitle());
        String message = null;
        if (user != null && show != null) {
            if (user.getFavourites().contains(show.getTitle())) {
                message = "error -> " + actionInputData.getTitle()
                        + " is already in favourite list";
            } else if (!user.getHistory().containsKey(show.getTitle())) {
                message = "error -> " + show.getTitle() + " is not seen";
            } else {
                favourite.addFavourite(user, show);
                message = "success -> " + actionInputData.getTitle() + " was added as favourite";
            }
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
