package queries;

import actors.Actor;
import fileio.ActionInputData;
import videos.Movie;
import videos.Serial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ActorQuery {
    /**
     * Sorts actors from ArrayList by average rating of the films and series in which
     * the actors played
     *
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actors the ArrayList to be sorted
     * @param actionInputData information about the action
     */
    public void sortByGrade(final ArrayList<Movie> movies, final ArrayList<Serial> serials,
                            final ArrayList<Actor> actors, final ActionInputData actionInputData) {
        actors.sort((actor1, actor2) -> {
            if (!Objects.equals(actor1.getAverageGrade(movies, serials),
                    actor2.getAverageGrade(movies, serials))) {
                return Double.compare(actor1.getAverageGrade(movies, serials),
                        actor2.getAverageGrade(movies, serials));
            } else {
                return actor1.getName().compareTo(actor2.getName());
            }
        });

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(actors);
        }
    }

    /**
     * Sorts actors from ArrayList by total number of awards
     *
     * @param actors          the ArrayList to be sorted
     * @param actionInputData information about the action
     */
    public void sortByNumberOfAwards(final ArrayList<Actor> actors,
                                     final ActionInputData actionInputData) {
        actors.sort((actor1, actor2) -> {
            if (!Objects.equals(actor1.getTotalAwards(actionInputData),
                    actor2.getTotalAwards(actionInputData))) {
                return Integer.compare(actor1.getTotalAwards(actionInputData),
                        actor2.getTotalAwards(actionInputData));
            } else {
                return actor1.getName().compareTo(actor2.getName());
            }
        });

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(actors);
        }
    }

    /**
     * Gets the message with the result of the query
     *
     * @param actors The List to be printed
     * @return message for output
     */
    public String getMessage(final List<Actor> actors) {
        StringBuilder message = new StringBuilder("Query result: [");
        for (Actor actor : actors) {
            message.append(actor.getName());
            message.append(", ");
        }
        if (actors.size() > 0) {
            message.deleteCharAt(message.length() - 1);
            message.deleteCharAt(message.length() - 1);
        }
        message.append("]");
        return new String(message);
    }

}
