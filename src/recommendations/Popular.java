package recommendations;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class Popular {
    /**
     * Gets the popularity of a given genre(total number of views of videos of that genre)
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param genre the given genre
     * @return int representing popularity of genre
     */
    public int getPopularityOfGenre(final ArrayList<User> users,
                                    final ArrayList<Movie> movies,
                                    final ArrayList<Serial> serials,
                                    final Genre genre) {
        Show show;
        int popularity = 0;
        for (var user : users) {
            for (var entry : user.getHistory().entrySet()) {
                show = Utils.findShow(movies, serials, entry.getKey());
                assert show != null;
                for (var genreOfShow : show.getGenres()) {
                    if (Utils.stringToGenre(genreOfShow).equals(genre)) {
                        popularity = popularity + user.getHistory().get(show.getTitle());
                    }
                }
            }
        }
        return popularity;
    }

    /**
     * Gets list of genres sorted by popularity
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @return list with sorted genres
     */
    public List<Genre> getListOfGenres(final ArrayList<User> users,
                                       final ArrayList<Movie> movies,
                                       final ArrayList<Serial> serials) {
        List<Genre> genreList = Arrays.asList(Genre.values());
        genreList.sort((genre1, genre2) -> Integer.compare(getPopularityOfGenre(users,
                movies, serials, genre2), getPopularityOfGenre(users, movies, serials, genre1)));
        return genreList;
    }

    /**
     * Gets the first unseen show from the most popular genre
     *
     * @param user user for whom the recommendation applies
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @return first unseen show
     */
    public Show getPopular(final User user, final ArrayList<User> users,
                           final ArrayList<Movie> movies,
                           final ArrayList<Serial> serials) {
        List<Genre> genreList = getListOfGenres(users, movies, serials);
        for (var genre : genreList) {
            for (Show show : movies) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    for (var genreOfShow : show.getGenres()) {
                        if (Utils.stringToGenre(genreOfShow).equals(genre)) {
                            return show;
                        }
                    }
                }
            }
            for (Show show : serials) {
                if (!user.getHistory().containsKey(show.getTitle())) {
                    for (var genreOfShow : show.getGenres()) {
                        if (Utils.stringToGenre(genreOfShow).equals(genre)) {
                            return show;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Executes the popular recommendation
     *
     * @param users ArrayList with all the users
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject popularRecommendation(final ArrayList<User> users,
                                                   final ArrayList<Movie> movies,
                                                   final ArrayList<Serial> serials,
                                                   final ActionInputData actionInputData,
                                                   final Writer writer) throws IOException {
        Popular popular = new Popular();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show popularShow = popular.getPopular(user, users, movies, serials);
        String message = null;
        if (user != null) {
            if (!user.getSubscriptionType().equals("PREMIUM")
                    || popular.getPopular(user, users, movies, serials) == null) {
                message = "PopularRecommendation cannot be applied!";
            } else if (popularShow != null) {
                message = "PopularRecommendation result: " + popularShow.getTitle();
            }
        }
        return writer.writeFile(actionInputData.getActionId(), null,
                message);
    }
}
