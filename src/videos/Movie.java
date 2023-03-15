package videos;

import fileio.ActionInputData;
import users.User;

import java.util.ArrayList;

public final class Movie extends Show {
    /**
     * Movie duration
     */
    private int duration;

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * Method used for rating the current movie
     *
     * @param user user for whom the command applies
     * @param grade the rating to be given
     * @param actionInputData information about the action
     */
    public void ratingShow(final User user, final double grade,
                           final ActionInputData actionInputData) {
        if (user.getHistory().containsKey(this.getTitle())
                && !user.getRatings().containsKey(this.getTitle())) {
            user.getRatings().put(this.getTitle(), grade);
        }
        this.getRatings().add(grade);
        //calculates and sets the new rating
        if (this.getRating() != null) {
            this.setRating((this.getRating() + grade) / this.getRatings().size());
        } else {
            this.setRating(grade / this.getRatings().size());
        }
    }

}
