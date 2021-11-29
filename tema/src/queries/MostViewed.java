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

public class MostViewed extends VideoQuery{
    public ArrayList<Show> getVideosByViews(ArrayList<User> users, ArrayList<Movie> movies,
                                            ArrayList<Serial> serials, ActionInputData actionInputData) {
        ArrayList<Show> videosByViews = new ArrayList<Show>();
        if (actionInputData.getObjectType().equals("movies"))
            for (Show show : movies)
                if (show.hasCriteria(actionInputData))
                    videosByViews.add(show);
        if (actionInputData.getObjectType().equals("shows"))
            for (Show show : serials)
                if (show.hasCriteria(actionInputData))
                    videosByViews.add(show);
        sortByNoOfViews(videosByViews, actionInputData.getSortType(), users);
        videosByViews.removeIf((show) -> show.getNoOfViews(users) == 0);
        ArrayList<Show> views = new ArrayList<>();
        if (actionInputData.getNumber() < videosByViews.size() + 1){
            for (int i = 0; i < actionInputData.getNumber(); i++)
                views.add(videosByViews.get(i));
            return views;
        }
        else return videosByViews;
    }

    static public JSONObject MostViewedQuery(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                          ActionInputData actionInputData, Writer writer) throws IOException {
        MostViewed mostViewed = new MostViewed();
        ArrayList<Show> videosByViews = mostViewed.getVideosByViews(users, movies, serials, actionInputData);
        String message = "Query result: [";
        for (Show show : videosByViews)
            message = message + show.getTitle() + ", ";
        if (videosByViews.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
