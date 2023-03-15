package database;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import queries.Average;
import queries.Award;
import queries.FilterDescription;
import queries.Longest;
import queries.Rating;
import queries.Favorite;
import queries.MostViewed;
import queries.NumberOfRatings;
import users.User;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")

public final class ConcreteStrategyQuery implements ActionsSolverStrategy {
    /**
     * Implements execute method for queries
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
        switch (actionInputData.getObjectType()) {
            case "actors":
                switch (actionInputData.getCriteria()) {
                    case "average" -> resultArray.add(Average.averageQuery(actors, movies,
                            serials, actionInputData, writer));
                    case "awards" -> resultArray.add(Award.awardQuery(actors, actionInputData,
                            writer));
                    case "filter_description" -> resultArray.add(FilterDescription.filterQuery(
                            actors, actionInputData, writer));
                    default -> System.out.println("Invalid query");
                }
                break;
            case "movies":
            case "shows":
                switch (actionInputData.getCriteria()) {
                    case "ratings" -> resultArray.add(Rating.ratingQuery(movies, serials,
                            actionInputData, writer));
                    case "favorite" -> resultArray.add(Favorite.favoriteQuery(users, movies,
                            serials, actionInputData, writer));
                    case "longest" -> resultArray.add(Longest.longestQuery(movies, serials,
                            actionInputData, writer));
                    case "most_viewed" -> resultArray.add(MostViewed.mostViewedQuery(users,
                            movies, serials, actionInputData, writer));
                    default -> System.out.println("Invalid criteria");
                }
                break;
            case "users":
                if ("num_ratings".equals(actionInputData.getCriteria())) {
                    resultArray.add(NumberOfRatings.numberOfRatingsQuery(users,
                            actionInputData, writer));
                }
                break;
            default:
                System.out.println("Invalid ObjectType");
        }
    }
}
