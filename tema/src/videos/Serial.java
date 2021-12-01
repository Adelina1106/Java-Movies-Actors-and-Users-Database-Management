package videos;

import entertainment.Season;
import fileio.ActionInputData;
import users.User;

import java.util.ArrayList;
import java.util.List;

public final class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    /**
     * Calculates total duration of the serial
     *
     * @return duration of the current serial
     */
    public int getDuration() {
        int duration = 0;
        for (var season : seasons) {
            duration = duration + season.getDuration();
        }
        return duration;

    }

    /**
     * Finds and sets the rating of the current serial
     *
     * @param serial the series whose rating will be set
     */
    public void setSerialRating(final Serial serial) {
        double ratingValue = 0d;
        double ratingSeason;
        for (var season : serial.getSeasons()) {
            ratingSeason = 0d;
            for (var rating : season.getRatings()) {
                if (!rating.isNaN()) {
                    ratingValue = (rating + ratingSeason) / season.getRatings().size()
                            + ratingValue;
                }
            }
        }
        serial.setRating(ratingValue / serial.getNumberOfSeasons());
    }

    /**
     * Method used for rating of the current serial
     *
     * @param user user for whom the command applies
     * @param grade the rating to be given
     * @param actionInputData information about the action
     */
    public void ratingShow(final User user, final double grade,
                           final ActionInputData actionInputData) {
        int season = actionInputData.getSeasonNumber();
        if (season <= this.getSeasons().size()) {
            if (user.getHistory().containsKey(this.getTitle())
                    && !user.getRatings().containsKey(this.getTitle() + season)) {
                this.getSeasons().get(season - 1).getRatings().add(grade);
                //the rated season is also added at the final of the name in the ratings map
                user.getRatings().put(this.getTitle() + season, grade);
            }
            this.setSerialRating(this);
        }
    }
}
