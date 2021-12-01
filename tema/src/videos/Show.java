package videos;

import fileio.ActionInputData;
import users.User;

import java.util.ArrayList;

public abstract class Show {
    /**
     * Title of the show
     */
    private String title;
    /**
     * Year of the show
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private ArrayList<String> genres;
    /**
     * Show rating, initially 0
     */
    private Double rating = 0d;
    /**
     * Ratings of the show
     */
    private ArrayList<Double> ratings = new ArrayList<>();

    /**
     * @return ArrayList of ratings of the current show
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to be set
     */
    public void setRatings(final ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    /**
     * @return the rating of the current show
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to be set
     */
    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    /**
     * @return the title of the current show
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to be set
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the cast of the current show
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * @param cast the cast to be set
     */
    public void setCast(final ArrayList<String> cast) {
        this.cast = cast;
    }

    /**
     * @return ArrayList of genres of the current show
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @param genres genres to be set
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * @return year of the current show
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to be set
     */
    public void setYear(final int year) {
        this.year = year;
    }

    /**
     * Gets number of appearances of the current show in users'
     * favorite video list
     *
     * @param users ArrayList with all the users
     * @return number of appearances
     */
    public int getTotalNoOfFavorites(final ArrayList<User> users) {
        int noOfFavorites = 0;
        for (User user : users) {
            if (user.getFavourites().contains(this.getTitle())) {
                noOfFavorites++;
            }
        }
        return noOfFavorites;
    }

    /**
     * Checks if the current show has the criteria from the query
     *
     * @param actionInputData information about the action
     * @return true if the current show has the criteria from the query, false otherwise
     */
    public boolean hasCriteria(final ActionInputData actionInputData) {
        for (int i = 0; i < actionInputData.getFilters().size(); i++) {
            //checks the year
            if (i == 0) {
                for (String criteria : actionInputData.getFilters().get(i)) {
                    if (criteria != null && this.getYear() != Integer.parseInt(criteria)) {
                        return false;
                    }
                }
                //checks the genre
            } else if (i == 1) {
                for (String genre : actionInputData.getFilters().get(i)) {
                    if (genre != null && !this.getGenres().contains(genre)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @return duration of the current show
     */
    public abstract int getDuration();

    /**
     * Gets total number of views of the current show
     *
     * @param users ArrayList with all the users
     * @return int representing total number of views
     */
    public int getNoOfViews(final ArrayList<User> users) {
        var ref = new Object() {
            private int noOfViews = 0;
        };
        users.stream()
                .filter((user) -> user.getHistory().containsKey(this.getTitle()))
                .forEach((user) -> ref.noOfViews = ref.noOfViews
                        + user.getHistory().get(this.getTitle()));
        return ref.noOfViews;
    }

    /**
     * @param user            user for whom the command applies
     * @param grade           the rating to be given
     * @param actionInputData information about the action
     */
    public abstract void ratingShow(User user, double grade, ActionInputData actionInputData);
}
