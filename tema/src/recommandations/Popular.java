package recommandations;

import entertainment.Genre;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.io.IOException;
import java.util.*;

public class Popular {
    public int getPopularityOfGenre(ArrayList<User> users, ArrayList<Movie> movies,
                                    ArrayList<Serial> serials, Genre genre) {
        Show show;
        int popularity = 0;
        for (var user : users)
            for (var entry : user.getHistory().entrySet()) {
                show = Utils.findShow(movies, serials, entry.getKey());
                for (var genreOfShow : show.getGenres())
                    if (Utils.stringToGenre(genreOfShow).equals(genre))
                        popularity = popularity + user.getHistory().get(show.getTitle());
            }
        return popularity;
    }

    public List<Genre> getListOfGenres(ArrayList<User> users, ArrayList<Movie> movies,
                                       ArrayList<Serial> serials) {
        List<Genre> genreList = Arrays.asList(Genre.values());
        genreList.sort(new Comparator<>() {
            @Override
            public int compare(Genre genre1, Genre genre2) {
                return Integer.compare(getPopularityOfGenre(users, movies, serials, genre2), getPopularityOfGenre(users, movies, serials, genre1));
            }
        });
        return genreList;
    }

    public Show getPopular(User user, ArrayList<User> users, ArrayList<Movie> movies,
                           ArrayList<Serial> serials) {
        List<Genre> genreList = getListOfGenres(users, movies, serials);
        for (var genre : genreList) {
            for (Show show : movies) {
                if (!user.getHistory().containsKey(show.getTitle()))
                    for (var genreOfShow : show.getGenres())
                        if (Utils.stringToGenre(genreOfShow).equals(genre))
                            return show;
            }
            for (Show show : serials) {
                if (!user.getHistory().containsKey(show.getTitle()))
                    for (var genreOfShow : show.getGenres())
                        if (Utils.stringToGenre(genreOfShow).equals(genre))
                            return show;
            }
        }
        return null;
    }

    static public JSONObject PopularRecommendation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                                   ActionInputData actionInputData, Writer writer) throws IOException {
        Popular popular = new Popular();
        User user = Utils.findUser(users, actionInputData.getUsername());
        String message = null;
        if (!user.getSubscriptionType().equals("PREMIUM") || popular.getPopular(user, users, movies, serials) == null)
            message = "PopularRecommendation cannot be applied!";
        else
            message = "PopularRecommendation result: " + popular.getPopular(user, users, movies, serials).getTitle();
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
