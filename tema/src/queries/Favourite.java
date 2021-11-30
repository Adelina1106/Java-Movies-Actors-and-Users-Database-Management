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

public class Favourite extends VideoQuery {

    public ArrayList<Show> getFavorite(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                       ActionInputData actionInputData) {
        ArrayList<Show> favoriteShows = new ArrayList<Show>();
        if (actionInputData.getObjectType().equals("movies"))
            for (Show show : movies)
                if (show.hasCriteria(actionInputData))
                    favoriteShows.add(show);
        if (actionInputData.getObjectType().equals("shows"))
            for (Show show : serials)
                if (show.hasCriteria(actionInputData))
                    favoriteShows.add(show);
        sortByNoOfFavourites(favoriteShows, users, actionInputData.getSortType());
        favoriteShows.removeIf((show) -> show.getTotalNoOfFavorites(users) == 0);
        ArrayList<Show> favorite = new ArrayList<>();
        if (actionInputData.getNumber() < favoriteShows.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++)
                favorite.add(favoriteShows.get(i));
            return favorite;
        } else return favoriteShows;
    }

    static public JSONObject FavouriteQuery(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                            ActionInputData actionInputData, Writer writer) throws IOException {
        Favourite favourite = new Favourite();
        ArrayList<Show> favouriteShows = favourite.getFavorite(users, movies, serials, actionInputData);
        String message = "Query result: [";
        for (Show show : favouriteShows)
            message = message + show.getTitle() + ", ";
        if (favouriteShows.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
