package platform;

import actors.Actor;
import commands.Favorite;
import commands.Rating;
import commands.View;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import users.User;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")

public class ConcreteStrategyCommand implements ActionsSolverStrategy {
    /**
     * Executes the given command
     *
     * @param actionInputData information about the action
     * @param resultArray JSONArray with the result message
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actors ArrayList with all the actors
     * @param writer used for transforming the output in a JSONObject
     */
    public void execute(final ActionInputData actionInputData, final JSONArray resultArray,
                        final ArrayList<User> users, final ArrayList<Movie> movies,
                        final ArrayList<Serial> serials, final ArrayList<Actor> actors,
                        final Writer writer) throws IOException {
        switch (actionInputData.getType()) {
            case "favorite" -> resultArray.add(Favorite.favoriteCommand(users, movies,
                    serials, actionInputData, writer));
            case "view" -> resultArray.add(View.viewCommand(users, movies,
                    serials, actionInputData, writer));
            case "rating" -> resultArray.add(Rating.ratingCommand(users, movies,
                    serials, actionInputData, writer));
            default -> System.out.println("Invalid command");
        }
    }
}
