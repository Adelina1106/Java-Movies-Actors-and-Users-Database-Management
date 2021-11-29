package actor;

import fileio.ActionInputData;
import users.User;
import utils.Utils;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography = new ArrayList<String>();
    ;
    private Map<ActorsAwards, Integer> awards;

    public Actor(String name, String careerDescription, ArrayList<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public Double getAverageGrade(ArrayList<Movie> movies, ArrayList<Serial> serials) {
        Double averageGrade = 0d;
        for (String name : this.getFilmography()) {
            Show show = Utils.findShow(movies, serials, name);
            if (show != null && show.getRating() != null)
                averageGrade = show.getRating() + averageGrade;
        }
        averageGrade = averageGrade / this.getFilmography().size();
        return averageGrade;
    }

    public int getTotalAwards(ActionInputData actionInputData) {
        int noOfAwards = 0;
        for (int i = 0; i < actionInputData.getFilters().size(); i++)
            if (i == 3)
                for (String award : actionInputData.getFilters().get(i))
                    if (this.getAwards().containsKey(Utils.stringToAwards(award)))
                        noOfAwards = noOfAwards + this.getAwards().get(Utils.stringToAwards(award));
                    else return 0;
        return noOfAwards;
    }

    public boolean hasKeywords(ActionInputData actionInputData) {
        for (int i = 0; i < actionInputData.getFilters().size(); i++)
            if (i == 2)
                for (String keyword : actionInputData.getFilters().get(i))
                    if (!this.getCareerDescription().contains(keyword))
                        return false;
        return true;
    }
}
