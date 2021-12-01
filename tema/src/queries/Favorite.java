package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Favorite extends ShowQuery {

    /**
     * Gets the first N videos sorted by number of appearances in users'
     * favorite video lists
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return List with first N videos sorted by number of appearances
     */
    public List<Show> getFavorite(final ArrayList<User> users,
                                  final ArrayList<Movie> movies,
                                  final ArrayList<Serial> serials,
                                  final ActionInputData actionInputData) {
        List<Show> favoriteShows = getShowsList(movies, serials, actionInputData);
        sortByNoOfFavourites(favoriteShows, users, actionInputData);
        favoriteShows.removeIf((show) -> show.getTotalNoOfFavorites(users) == 0);
        return getShows(actionInputData, favoriteShows);
    }

    /**
     * Executes the favorite query
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject favoriteQuery(final ArrayList<User> users,
                                           final ArrayList<Movie> movies,
                                           final ArrayList<Serial> serials,
                                           final ActionInputData actionInputData,
                                           final Writer writer) throws IOException {
        Favorite favorite = new Favorite();
        List<Show> favouriteShows = favorite.getFavorite(users, movies,
                serials, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                favorite.getMessage(favouriteShows));
    }
}
