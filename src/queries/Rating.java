package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rating extends ShowQuery {
    /**
     * Gets the first N videos sorted by rating
     *
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return List with N videos
     */
    public List<Show> getVideosByRating(final ArrayList<Movie> movies,
                                        final ArrayList<Serial> serials,
                                        final ActionInputData actionInputData) {
        List<Show> ratingShows = getShowsList(movies, serials, actionInputData);
        sortByRating(ratingShows, actionInputData);
        ratingShows.removeIf((show) -> show.getRating() < 1);
        return getShows(actionInputData, ratingShows);
    }

    /**
     * Executes the rating query
     *
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject ratingQuery(final ArrayList<Movie> movies,
                                         final ArrayList<Serial> serials,
                                         final ActionInputData actionInputData,
                                         final Writer writer) throws IOException {
        Rating rating = new Rating();
        List<Show> showsByRating = rating.getVideosByRating(movies, serials, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                rating.getMessage(showsByRating));
    }
}
