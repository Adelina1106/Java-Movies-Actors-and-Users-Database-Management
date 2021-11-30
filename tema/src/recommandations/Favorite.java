package recommandations;

import commands.Favourite;
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

public class Favorite {
    public Show getFavorite(User user, ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials) {
        ArrayList<Show> favoriteVideos = new ArrayList<Show>();
        for (Show show : movies)
            favoriteVideos.add(show);
        for (Show show : serials)
            favoriteVideos.add(show);
        favoriteVideos.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                return Integer.compare(show2.getTotalNoOfFavorites(users), show1.getTotalNoOfFavorites(users));
            }
        });
        favoriteVideos.removeIf((show) -> show.getNoOfViews(users) == 0);
        favoriteVideos.removeIf((show) -> user.getHistory().containsKey(show.getTitle()));
        if (favoriteVideos.size() != 0)
            return favoriteVideos.get(0);
        return null;
    }

    static public JSONObject favoriteRecommendation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                                    ActionInputData actionInputData, Writer writer) throws IOException {
        Favorite favorite = new Favorite();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message = null;
        if (favorite.getFavorite(user, users, movies, serials) != null)
            message = "FavoriteRecommendation result: " + favorite.getFavorite(user, users, movies, serials).getTitle();
        else
            message = "FavoriteRecommendation cannot be applied!";
            org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }


}
