package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;

public class Longest extends VideoQuery {
    public ArrayList<Show> getVideosByDuration(ArrayList<Movie> movies,
                                              ArrayList<Serial> serials, ActionInputData actionInputData) {
        ArrayList<Show> videosByDuration = new ArrayList<Show>();
        if (actionInputData.getObjectType().equals("movies"))
            for (Show show : movies)
                if (show.hasCriteria(actionInputData))
                videosByDuration.add(show);
        if (actionInputData.getObjectType().equals("shows"))
            for (Show show : serials)
                if (show.hasCriteria(actionInputData))
                videosByDuration.add(show);
        sortByDuration(videosByDuration, actionInputData.getSortType());
        videosByDuration.removeIf((show) -> show.getDuration() == 0);
        ArrayList<Show> ratings = new ArrayList<>();
        if (actionInputData.getNumber() < videosByDuration.size() + 1){
            for (int i = 0; i < actionInputData.getNumber(); i++)
                ratings.add(videosByDuration.get(i));
            return ratings;
            }
        else return videosByDuration;
    }

    static public JSONObject LongestQuery(ArrayList<Movie> movies, ArrayList<Serial> serials,
                                          ActionInputData actionInputData, Writer writer) throws IOException {
        Longest longest = new Longest();
        ArrayList<Show> videosByDuration = longest.getVideosByDuration(movies, serials, actionInputData);
        String message = "Query result: [";
        for (Show show : videosByDuration)
            message = message + show.getTitle() + ", ";
        if (videosByDuration.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
