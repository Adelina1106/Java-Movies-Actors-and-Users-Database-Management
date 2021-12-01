package queries;

import fileio.ActionInputData;
import users.User;
import videos.Movie;
import videos.Serial;
import videos.Show;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class ShowQuery {
    /**
     * The method used for sorting the shows by rating
     *
     * @param shows The List to be sorted
     */
    public void sortByRating(final List<Show> shows,
                             final ActionInputData actionInputData) {
        shows.sort((show1, show2) -> {
            if (!Objects.equals(show1.getRating(), show2.getRating())) {
                return Double.compare(show1.getRating(), show2.getRating());
            } else {
                return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(shows);
        }
    }

    /**
     * The method used for sorting the shows by number of appearances in
     * users' favorite video lists
     *
     * @param shows List to be sorted
     * @param users ArrayList with all the users
     * @param actionInputData information about the action
     */
    public void sortByNoOfFavourites(final List<Show> shows,
                                            final ArrayList<User> users,
                                            final ActionInputData actionInputData) {
        shows.sort((show1, show2) -> {
            if (!Objects.equals(show1.getTotalNoOfFavorites(users),
                    show2.getTotalNoOfFavorites(users))) {
                return Integer.compare(show1.getTotalNoOfFavorites(users),
                        show2.getTotalNoOfFavorites(users));
            } else {
                return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(shows);
        }
    }

    /**
     * The method used for sorting shows by duration
     *
     * @param shows List to be sorted
     * @param actionInputData information about the action
     */
    public void sortByDuration(final List<Show> shows,
                               final ActionInputData actionInputData) {
        shows.sort((show1, show2) -> {
            if (!Objects.equals(show1.getDuration(), show2.getDuration())) {
                return Integer.compare(show1.getDuration(), show2.getDuration());
            } else {
                return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(shows);
        }
    }

    /**
     * The method used for sorting shows by number of views
     *
     * @param shows List to be sorted
     * @param actionInputData information about the action
     * @param users ArrayList with all the users
     */
    public void sortByNoOfViews(final List<Show> shows,
                                final ActionInputData actionInputData,
                                final ArrayList<User> users) {
        shows.sort((show1, show2) -> {
            if (!Objects.equals(show1.getNoOfViews(users), show2.getNoOfViews(users))) {
                return Integer.compare(show1.getNoOfViews(users), show2.getNoOfViews(users));
            } else {
                return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(shows);
        }
    }

    /**
     * @param shows List with the shows to be printed
     * @return the result message of the action
     */
    public String getMessage(final List<Show> shows) {
        StringBuilder message = new StringBuilder("Query result: [");
        for (Show show : shows) {
            message.append(show.getTitle());
            message.append(", ");
        }
        if (shows.size() > 0) {
            message.deleteCharAt(message.length() - 1);
            message.deleteCharAt(message.length() - 1);
        }
        message.append("]");
        return new String(message);
    }

    /**
     * Gets te first N shows from a given list
     *
     * @param actionInputData information about the action
     * @param shows List with the shows
     * @return List with first N shows
     */
    public List<Show> getShows(final ActionInputData actionInputData,
                               final List<Show> shows) {
        List<Show> firstNShows = new ArrayList<>();
        if (actionInputData.getNumber() < shows.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                firstNShows.add(shows.get(i));
            }
            return firstNShows;
        } else {
            return shows;
        }
    }

    /**
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return List with all the movies and serials
     */
    public List<Show> getShowsList(final ArrayList<Movie> movies,
                                   final ArrayList<Serial> serials,
                                   final ActionInputData actionInputData) {
        List<Show> shows = new ArrayList<>();
        if (actionInputData.getObjectType().equals("movies")) {
            movies.stream()
                    .filter(show -> show.hasCriteria(actionInputData))
                    .forEach(shows::add);
        }
        if (actionInputData.getObjectType().equals("shows")) {
            serials.stream()
                    .filter(show -> show.hasCriteria(actionInputData))
                    .forEach(shows::add);
        }
        return shows;
    }
}
