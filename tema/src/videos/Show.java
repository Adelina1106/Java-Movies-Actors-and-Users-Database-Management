package videos;

import entertainment.Genre;
import fileio.ActionInputData;
import users.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Show {
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private Double rating = 0d;
    private ArrayList<Double> ratings = new ArrayList<>();

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Show(String title, int year, ArrayList<String> cast, ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        //this.cast = new ArrayList<>();
        this.cast = cast;
        //this.genres = new ArrayList<>();
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalNoOfFavorites(ArrayList<User> users) {
        int noOfFavorites = 0;
        for (User user : users)
            if (user.getFavourites().contains(this.getTitle()))
                noOfFavorites++;
        return noOfFavorites;
    }

    public boolean hasCriteria(ActionInputData actionInputData) {
        for (int i = 0; i < actionInputData.getFilters().size(); i++)
            if (i == 0) {
                for (String criteria : actionInputData.getFilters().get(i))
                    if (criteria != null)
                        if (this.getYear() != Integer.valueOf(criteria))
                            return false;
            } else if (i == 1) {
                for (String genre : actionInputData.getFilters().get(i))
                    if (genre != null)
                        if (!this.getGenres().contains(genre))
                            return false;
            }
        return true;
    }

    public abstract int getDuration();

    public int getNoOfViews(ArrayList<User> users) {
        int noOfViews = 0;
        for (var user : users)
            if (user.getHistory().containsKey(this.getTitle()))
                noOfViews = noOfViews + user.getHistory().get(this.getTitle());
        return noOfViews;
    }
}
