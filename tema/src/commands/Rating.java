package commands;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Show;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

public class Rating {

    public void ratingShow(User user, Serial serial, Double grade, int season) {
        if (season <= serial.getSeasons().size()) {
            //a doua rel verifica ca sezonul pt care se da rating sa nu fie deja unul cu rating dat
            if (user.getHistory().containsKey(serial.getTitle()) && !user.getRatings().containsKey(serial.getTitle() + season)) {
                serial.getSeasons().get(season - 1).getRatings().add(grade);
                user.getRatings().put(serial.getTitle() + season, grade);//adaug numele sez la finalul numelui
            }
            setSerialRating(serial);
        }
    }

    public void ratingShow(User user, Movie movie, Double grade) {
        if (user.getHistory().containsKey(movie.getTitle()) && !user.getRatings().containsKey(movie.getTitle())) {
            user.getRatings().put(movie.getTitle(), grade);
        }
        movie.getRatings().add(grade);

        if (movie.getRating() != null)
            movie.setRating((movie.getRating() + grade) / movie.getRatings().size());
        else movie.setRating(grade / movie.getRatings().size());
    }

    public void setSerialRating(Serial serial) {
        Double ratingValue = 0d;
        Double ratingSeason;
        for (var season : serial.getSeasons()) {
            ratingSeason = 0d;
            for (var rating : season.getRatings())
                if (!rating.isNaN()) {
                    ratingSeason = rating + ratingSeason;
                    ratingSeason = ratingSeason / season.getRatings().size();
                    ratingValue = ratingSeason + ratingValue;
                }
        }

        serial.setRating(ratingValue / serial.getNumberOfSeasons());
    }

    static public JSONObject ratingCommand(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                           ActionInputData actionInputData, Writer writer) throws IOException {
        Rating rating = new Rating();
        User user = Utils.findUser(users, actionInputData.getUsername());
        Show show = Utils.findShow(movies, serials, actionInputData.getTitle());
        String message;
        if (!user.getHistory().containsKey(show.getTitle()))
            message = "error -> " + show.getTitle() + " is not seen";
        else if (!user.getRatings().containsKey(show.getTitle()) && !user.getRatings().containsKey(show.getTitle() + actionInputData.getSeasonNumber())) {
            if (movies.contains(show))
                rating.ratingShow(user, (Movie) show, actionInputData.getGrade());
            else rating.ratingShow(user, (Serial) show, actionInputData.getGrade(), actionInputData.getSeasonNumber());
            message = "success -> " + actionInputData.getTitle() + " was rated with "
                    + actionInputData.getGrade() + " by " + actionInputData.getUsername();
        } else message = "error -> " + show.getTitle() + " has been already rated";

        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}

