package platform;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import users.User;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;


public interface ActionsSolverStrategy {
    /**
     * Execute actions
     *
     * @param actionInputData information about the action
     * @param resultArray JSONArray with result message
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actors ArrayList with all the actors
     * @param writer used for transforming the output in a JSONObject
     */
    void execute(ActionInputData actionInputData, JSONArray resultArray, ArrayList<User> users,
                 ArrayList<Movie> movies, ArrayList<Serial> serials, ArrayList<Actor> actors,
                 Writer writer) throws IOException;
}
