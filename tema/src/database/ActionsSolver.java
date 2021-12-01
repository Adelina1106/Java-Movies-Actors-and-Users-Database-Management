package database;

import actors.Actor;
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

public final class ActionsSolver {
    /**
     * ArrayList with all users from database
     */
    private ArrayList<User> users = new ArrayList<>();
    /**
     * ArrayList with all movies from database
     */
    private ArrayList<Movie> movies = new ArrayList<>();
    /**
     * ArrayList with all serials from database
     */
    private ArrayList<Serial> serials = new ArrayList<>();
    /**
     * ArrayList with all actors from database
     */
    private final ArrayList<Actor> actors = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(final ArrayList<Serial> serials) {
        this.serials = serials;
    }

    /**
     * Creates new ArrayLists with users, movies, serials and actors
     *
     * @param input contains information about input
     */
    public void getMyClasses(final Input input) {
        input.getUsers()
                .forEach((userInputData) -> users.add(new User(userInputData.getUsername(),
                        userInputData.getFavoriteMovies(), userInputData.getHistory(),
                        userInputData.getSubscriptionType())));
        input.getActors()
                .forEach((actorInputData -> actors.add(new Actor(actorInputData.getName(),
                        actorInputData.getCareerDescription(), actorInputData.getFilmography(),
                        actorInputData.getAwards()))));
        input.getSerials()
                .forEach((serialInputData) -> serials.add(new Serial(serialInputData.getTitle(),
                        serialInputData.getCast(), serialInputData.getGenres(),
                        serialInputData.getNumberSeason(), serialInputData.getSeasons(),
                        serialInputData.getYear())));
        input.getMovies()
                .forEach((movieInputData) -> movies.add(new Movie(movieInputData.getTitle(),
                        movieInputData.getCast(), movieInputData.getGenres(),
                        movieInputData.getYear(), movieInputData.getDuration())));
    }

    /**
     * Entry point to the implementation
     *
     * @param input contains information about input
     * @param resultArray JSONArray with result message
     * @param writer used for transforming the output in a JSONObject
     */
    public void start(final Input input, final JSONArray resultArray,
                      final Writer writer) throws IOException {
        getMyClasses(input);
        ActionsSolverStrategy commandsSolverStrategy;
        for (ActionInputData actionInputData : input.getCommands()) {
            if (actionInputData.getActionType().equals("command")) {
                commandsSolverStrategy = new ConcreteStrategyCommand();
            } else if (actionInputData.getActionType().equals("query")) {
                commandsSolverStrategy = new ConcreteStrategyQuery();
            } else {
                commandsSolverStrategy = new ConcreteStrategyRecommendation();
            }
            commandsSolverStrategy.execute(actionInputData, resultArray, users, movies,
                    serials, actors, writer);
        }
    }
}
