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

public final class MostViewed extends ShowQuery {
    /**
     * Gets the first N videos sorted by number of views
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return List with videos
     */
    public List<Show> getVideosByViews(final ArrayList<User> users,
                                       final ArrayList<Movie> movies,
                                       final ArrayList<Serial> serials,
                                       final ActionInputData actionInputData) {
        List<Show> showsByViews = getShowsList(movies, serials, actionInputData);
        sortByNoOfViews(showsByViews, actionInputData, users);
        showsByViews.removeIf((show) -> show.getNoOfViews(users) == 0);
        return getShows(actionInputData, showsByViews);
    }

    /**
     * Executes most viewed query
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject mostViewedQuery(final ArrayList<User> users,
                                             final ArrayList<Movie> movies,
                                             final ArrayList<Serial> serials,
                                             final ActionInputData actionInputData,
                                             final Writer writer) throws IOException {
        MostViewed mostViewed = new MostViewed();
        List<Show> showsByViews = mostViewed.getVideosByViews(users, movies,
                serials, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                mostViewed.getMessage(showsByViews));
    }
}
