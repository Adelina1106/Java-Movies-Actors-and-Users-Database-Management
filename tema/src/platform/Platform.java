package platform;

import queries.*;
import actor.Actor;
import commands.Favourite;
import commands.Rating;
import commands.View;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import users.User;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Platform {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Serial> serials = new ArrayList<>();
    private ArrayList<Actor> actors = new ArrayList<>();

    private static Platform instance = null;

    private Platform() {
    }

    ;

    public static Platform getPlatform() {
        if (instance == null) ;
        instance = new Platform();
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(ArrayList<Serial> serials) {
        this.serials = serials;
    }

    public void myClasses(Input input) {
        for (var userInputData : input.getUsers())
            users.add(new User(userInputData.getUsername(), userInputData.getFavoriteMovies(),
                    userInputData.getHistory(), userInputData.getSubscriptionType()));
        for (var ActorInputData : input.getActors())
            actors.add(new Actor(ActorInputData.getName(), ActorInputData.getCareerDescription(),
                    ActorInputData.getFilmography(), ActorInputData.getAwards()));
        for (var SerialInputData : input.getSerials())
            serials.add(new Serial(SerialInputData.getTitle(),
                    SerialInputData.getCast(), SerialInputData.getGenres(), SerialInputData.getNumberSeason(),
                    SerialInputData.getSeasons(), SerialInputData.getYear()));
        for (var MovieInputData : input.getMovies())
            movies.add(new Movie(MovieInputData.getTitle(),
                    MovieInputData.getCast(), MovieInputData.getGenres(), MovieInputData.getYear(),
                    MovieInputData.getDuration()));
    }

    public void start(Input input, JSONArray resultArray, Writer writer) throws IOException {
        myClasses(input);
        for (ActionInputData actionInputData : input.getCommands())
            if (actionInputData.getActionType().equals("command"))
                switch (actionInputData.getType()) {
                    case "favorite":
                        resultArray.add(Favourite.FavoriteCommand(users, movies, serials, actionInputData, writer));
                        break;
                    case "view":
                        resultArray.add(View.viewCommand(users, movies, serials, actionInputData, writer));
                        break;
                    case "rating":
                        resultArray.add(Rating.ratingCommand(users, movies, serials, actionInputData, writer));
                        break;
                    default:
                        // code blck
                }
            else if (actionInputData.getActionType().equals("query")) {
                if (actionInputData.getObjectType().equals("actors"))
                    switch (actionInputData.getCriteria()) {
                        case "average":
                            resultArray.add(Average.AverageQuery(actors, movies, serials, actionInputData, writer));
                            break;
                        case "awards":
                            resultArray.add(Award.AwardQuery(actors, movies, serials, actionInputData, writer));
                            break;
                        case "filter_description":
                            resultArray.add(FilterDescription.filterQuery(actors, movies, serials, actionInputData, writer));
                            break;
                        default:
                            break;
                    }
                else if (actionInputData.getObjectType().equals("movies") || actionInputData.getObjectType().equals("shows"))
                    switch (actionInputData.getCriteria()) {
                        case "ratings":
                            resultArray.add(queries.Rating.RatingQuery(movies, serials, actionInputData, writer));
                            break;
                        case "favorite":
                            resultArray.add(queries.Favourite.FavouriteQuery(users, movies, serials, actionInputData, writer));
                            break;
                        case "longest":
                            resultArray.add(queries.Longest.LongestQuery(movies, serials, actionInputData, writer));
                            break;
                        case "most_viewed":
                            resultArray.add(queries.MostViewed.MostViewedQuery(users, movies, serials, actionInputData, writer));
                        default:
                            break;

                    }
                else if (actionInputData.getObjectType().equals("users"))
                    switch (actionInputData.getCriteria()) {
                        case "num_ratings":
                            resultArray.add(NumberOfRatings.NumberOfRatingsQuery(users, actionInputData, writer));
                            break;
                        default:
                            break;
                    }
            }

    }
}
