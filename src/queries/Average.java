package queries;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;

public final class Average extends ActorQuery {

    /**
     * Gets the first N actors sorted by average rating of the films
     * and series in which the actors played
     *
     * @param actors ArrayList with all the actors
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @return ArrayList with the first N actors
     */
    public ArrayList<Actor> getAverageActors(final ArrayList<Actor> actors,
                                             final ArrayList<Movie> movies,
                                             final ArrayList<Serial> serials,
                                             final ActionInputData actionInputData) {
        ArrayList<Actor> averageActors = new ArrayList<>(actors);
        sortByGrade(movies, serials, averageActors, actionInputData);
        averageActors.removeIf((actor) -> actor.getAverageGrade(movies, serials).isNaN());
        ArrayList<Actor> average = new ArrayList<>();

        //gets the first N actors from the ArrayList
        if (actionInputData.getNumber() < averageActors.size() + 1) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                average.add(averageActors.get(i));
            }
            return average;
        } else {
            return averageActors;
        }
    }

    /**
     * Executes the average query
     *
     * @param actors ArrayList with all the actors
     * @param movies ArrayList with all the movies
     * @param serials ArrayList with all the serials
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject averageQuery(final ArrayList<Actor> actors,
                                          final ArrayList<Movie> movies,
                                          final ArrayList<Serial> serials,
                                          final ActionInputData actionInputData,
                                          final Writer writer) throws IOException {
        Average average = new Average();
        ArrayList<Actor> averageGradeActors = average.getAverageActors(actors, movies,
                serials, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                average.getMessage(averageGradeActors));
    }
}
