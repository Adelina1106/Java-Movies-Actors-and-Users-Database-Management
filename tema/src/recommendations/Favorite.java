package recommendations;

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
import java.util.Objects;

public final class Favorite {
    /**
     * Gets video that is most often found in the list of favorites
     * of all users (not seen by the current user)
     *
     * @param user user for whom the recommendation applies
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @return video that is most often found in the list of favorites
     */
    public Show getFavorite(final User user, final ArrayList<User> users,
                            final ArrayList<Movie> movies,
                            final ArrayList<Serial> serials) {
        ArrayList<Show> favoriteVideos = new ArrayList<>(movies);
        favoriteVideos.addAll(serials);
        favoriteVideos.sort((show1, show2) -> Integer.compare(show2.getTotalNoOfFavorites(users),
                show1.getTotalNoOfFavorites(users)));
        favoriteVideos.removeIf((show) -> show.getNoOfViews(users) == 0);
        favoriteVideos.removeIf((show) -> user.getHistory().containsKey(show.getTitle()));
        if (favoriteVideos.size() != 0) {
            return favoriteVideos.get(0);
        }
        return null;
    }

    /**
     * Executes the favorite recommendation
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject favoriteRecommendation(final ArrayList<User> users,
                                                    final ArrayList<Movie> movies,
                                                    final ArrayList<Serial> serials,
                                                    final ActionInputData actionInputData,
                                                    final Writer writer) throws IOException {
        Favorite favorite = new Favorite();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message;
        if (favorite.getFavorite(user, users, movies, serials) != null) {
            message = "FavoriteRecommendation result: "
                    + Objects.requireNonNull(favorite.getFavorite(user, users, movies, serials)).
                    getTitle();
        } else {
            message = "FavoriteRecommendation cannot be applied!";
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
