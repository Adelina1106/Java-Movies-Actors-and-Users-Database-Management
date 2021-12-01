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

public final class Longest extends ShowQuery {
    /**
     * Gets the first N videos sorted by their duration
     *
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return List with N videos
     */
    public List<Show> getVideosByDuration(final ArrayList<Movie> movies,
                                          final ArrayList<Serial> serials,
                                          final ActionInputData actionInputData) {
        List<Show> showsByDuration = getShowsList(movies, serials, actionInputData);
        sortByDuration(showsByDuration, actionInputData);
        showsByDuration.removeIf((show) -> show.getDuration() == 0);
        return getShows(actionInputData, showsByDuration);
    }

    /**
     * Executes longest query
     *
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject longestQuery(final ArrayList<Movie> movies,
                                          final ArrayList<Serial> serials,
                                          final ActionInputData actionInputData,
                                          final Writer writer) throws IOException {
        Longest longest = new Longest();
        List<Show> videosByDuration = longest.getVideosByDuration(movies, serials, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                longest.getMessage(videosByDuration));
    }
}
