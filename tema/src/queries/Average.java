package queries;

import actor.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import videos.Movie;
import videos.Serial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Average extends QueryActor {

    public ArrayList<Actor> getAverageActors(ArrayList<Actor> actors, ArrayList<Movie> movies,
                                             ArrayList<Serial> serials, int N, String SortType) {
        ArrayList<Actor> averageActors = new ArrayList<>(actors);
        sortByGrade(movies, serials, averageActors);
        averageActors.removeIf((actor) -> actor.getAverageGrade(movies, serials) == -1d);
        averageActors.removeIf((actor) -> actor.getAverageGrade(movies, serials).isNaN());
        if (SortType.equals("desc"))
            Collections.reverse(averageActors);
        ArrayList<Actor> average = new ArrayList<>();
        if (N < averageActors.size() + 1) {
            for (int i = 0; i < N; i++){
                average.add(averageActors.get(i));}
            return average;
        } else return averageActors;
    }

    static public JSONObject AverageQuery(ArrayList<Actor> actors, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                          ActionInputData actionInputData, Writer writer) throws IOException {
        Average average = new Average();
        ArrayList<Actor> averageGradeActors = average.getAverageActors(actors, movies, serials, actionInputData.getNumber(), actionInputData.getSortType());
        String message = "Query result: [";
        for (Actor actor : averageGradeActors)
            message = message + actor.getName() + ", ";
        message = message.substring(0, message.length() - 2);
        message = message + "]";
        return  writer.writeFile(actionInputData.getActionId(), null, message);
    }
}
