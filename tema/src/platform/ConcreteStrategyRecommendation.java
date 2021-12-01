package platform;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import recommendations.Standard;
import recommendations.BestUnseen;
import recommendations.Search;
import recommendations.Popular;
import recommendations.Favorite;
import users.User;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")

public final class ConcreteStrategyRecommendation implements ActionsSolverStrategy {
    /**
     * Implements execute method for recommendations
     *
     * @param actionInputData information about the action
     * @param resultArray JSONArray with result message
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
            case "standard" -> resultArray.add(Standard.standardRecommendation(users,
                    movies, serials, actionInputData, writer));
            case "best_unseen" -> resultArray.add(BestUnseen.bestUnseenRecommendation(users,
                    movies, serials, actionInputData, writer));
            case "popular" -> resultArray.add(Popular.popularRecommendation(users,
                    movies, serials, actionInputData, writer));
            case "favorite" -> resultArray.add(Favorite.favoriteRecommendation(users,
                    movies, serials, actionInputData, writer));
            case "search" -> resultArray.add(Search.searchRecommendation(users,
                    movies, serials, actionInputData, writer));
            default -> System.out.println("Invalid type");
        }
    }
}
