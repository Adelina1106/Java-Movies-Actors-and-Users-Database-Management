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

public class View{

    public void addVisualised(User user, Show show) {
        if(user.getHistory().containsKey(show.getTitle()))
            user.getHistory().put(show.getTitle(), user.getHistory().get(show.getTitle()) + 1);
        else user.getHistory().put(show.getTitle(), 1);
    }

    static public JSONObject viewCommand(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                         ActionInputData actionInputData, Writer writer) throws IOException
    {
        View view = new View();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies,serials, actionInputData.getTitle());
        view.addVisualised(user, show);
        String message = "success -> " + actionInputData.getTitle()+" was viewed with total views of "
                + user.getHistory().get(show.getTitle());
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
