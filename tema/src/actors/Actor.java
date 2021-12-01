package actors;

import fileio.ActionInputData;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    /**
     * actor name
     */
    private final String name;

    /**
     * description of the actor's career
     */
    private final String careerDescription;

    /**
     * videos starring actor
     */
    private final ArrayList<String> filmography;

    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    /**
     * @return current name
     */
    public String getName() {
        return name;
    }

    /**
     * @return current career description
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * @return filmography of the current actor
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     * @return awards of the current actor
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * The method finds the average rating of the films and series in which the actor played
     *
     * @param movies  arrayList of movies
     * @param serials arrayList of serials
     * @return the average rating (Double)
     */
    public Double getAverageGrade(final ArrayList<Movie> movies, final ArrayList<Serial> serials) {
        Double averageGrade = 0d;
        int videosWithoutRating = 0;
        for (String nameOfShow : this.getFilmography()) {
            Show show = Utils.findShow(movies, serials, nameOfShow);
            if (show != null) {
                if (show.getRating() == null || Double.compare(show.getRating(), 0d) == 0) {
                    videosWithoutRating++;
                }
                if (show.getRating() != null) {
                    averageGrade = show.getRating() + averageGrade;
                }
            } else {
                videosWithoutRating++;
            }
        }
        //calculates the rating taking into account only the videos that have ratings
        averageGrade = averageGrade / (this.getFilmography().size() - videosWithoutRating);
        return averageGrade;
    }

    /**
     * Gets number of total awards the actors has
     *
     * @param actionInputData information about the action
     * @return total number of awards
     */
    public int getTotalAwards(final ActionInputData actionInputData) {
        int noOfAwards = 0;
        final int noOfFilter = 3;
        for (int i = 0; i < actionInputData.getFilters().size(); i++) {
            if (i == noOfFilter) {
                for (String award : actionInputData.getFilters().get(i)) {
                    if (!this.getAwards().containsKey(Utils.stringToAwards(award))) {
                        return 0;
                    }
                }
            }
        }
        for (var entry : this.getAwards().entrySet()) {
            noOfAwards = noOfAwards + entry.getValue();
        }
        return noOfAwards;
    }

    /**
     * Checks if the actor has the keywords from the filters in his description
     *
     * @param actionInputData information about the action
     * @return true if the actors has keywords, otherwise false
     */
    public boolean hasKeywords(final ActionInputData actionInputData) {
        final int noOfFilter = 2;
        for (String keyword : actionInputData.getFilters().get(noOfFilter)) {
            String keywordLower = keyword.toLowerCase();
            if (!this.getCareerDescription().toLowerCase().contains(keywordLower + " ")
                    && !this.getCareerDescription().toLowerCase().contains(keywordLower + ",")
                    && !this.getCareerDescription().toLowerCase().contains(keywordLower + ".")) {
                return false;
            }
        }
        return true;
    }
}
