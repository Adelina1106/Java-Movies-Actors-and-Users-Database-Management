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

public class Favourite {

    public void addFavourite(User user, Show show) {
        user.getFavourites().add(show.getTitle());
    }

    static public JSONObject FavoriteCommand(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                             ActionInputData actionInputData, Writer writer) throws IOException {
        Favourite favourite = new Favourite();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies, serials, actionInputData.getTitle());
        String message;
        if (user.getFavourites().contains(show.getTitle()))
            message = "error -> " + actionInputData.getTitle() + " is already in favourite list";
        else if (!user.getHistory().containsKey(show.getTitle()))
            message = "error -> " + show.getTitle() + " is not seen";
        else {
            favourite.addFavourite(user, show);
            message = "success -> " + actionInputData.getTitle() + " was added as favourite";
        }
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
