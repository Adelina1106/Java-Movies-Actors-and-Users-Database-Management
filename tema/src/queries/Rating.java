package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Rating extends VideoQuery {

    public ArrayList<Show> getVideosByRating(ArrayList<Movie> movies,
                                             ArrayList<Serial> serials, ActionInputData actionInputData) {
        ArrayList<Show> ratingVideos = new ArrayList<Show>();
        if (actionInputData.getObjectType().equals("movies"))
            for (Show show : movies)
                if (show.hasCriteria(actionInputData))
                    ratingVideos.add(show);
        if (actionInputData.getObjectType().equals("shows"))
            for (Show show : serials)
                if (show.hasCriteria(actionInputData))
                    ratingVideos.add(show);
        sortByRating(ratingVideos);
        if (actionInputData.getSortType().equals("desc"))
            Collections.reverse(ratingVideos);
        ratingVideos.removeIf((show) -> show.getRating().isNaN() || show.getRating() < 1d);
        ArrayList<Show> ratings = new ArrayList<>();
        if (actionInputData.getNumber() < ratingVideos.size() + 1)
            for (int i = 0; i < actionInputData.getNumber(); i++)
                ratings.add(ratingVideos.get(i));
        return ratings;
    }

    static public JSONObject RatingQuery(ArrayList<Movie> movies, ArrayList<Serial> serials,
                                         ActionInputData actionInputData, Writer writer) throws IOException {
        Rating rating = new Rating();
        ArrayList<Show> videosByRating = rating.getVideosByRating(movies, serials, actionInputData);
        String message = "Query result: [";
        for (Show show : videosByRating)
            message = message + show.getTitle() + ", ";
        if (videosByRating.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
