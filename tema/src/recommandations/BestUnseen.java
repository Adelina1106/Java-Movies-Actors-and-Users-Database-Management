package recommandations;

import queries.VideoQuery;
import commands.Rating;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class BestUnseen extends RecommendationForAllUsers {
    public Show getBestUnseen(User user, ArrayList<Movie> movies,
                              ArrayList<Serial> serials) {
        ArrayList<Show> ratingVideos = new ArrayList<Show>();
        for (Show show : movies)
            if (!user.getHistory().containsKey(show.getTitle()))
                ratingVideos.add(show);
        for (Show show : serials)
            if (!user.getHistory().containsKey(show.getTitle()))
                ratingVideos.add(show);
        ratingVideos.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                return Double.compare(show2.getRating(), show1.getRating());
            }
        });
        if (ratingVideos.size() > 0)
            return ratingVideos.get(0);
        else return null;
    }

    static public JSONObject BestUnseenRecommendation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                                      ActionInputData actionInputData, Writer writer) throws IOException {
        BestUnseen bestUnseen = new BestUnseen();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message = null;
        if (bestUnseen.getBestUnseen(user, movies, serials) != null)
            message = "BestRatedUnseenRecommendation result: " + bestUnseen.getBestUnseen(user, movies, serials).getTitle();
        else message = "BestRatedUnseenRecommendation cannot be applied!";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }


}
