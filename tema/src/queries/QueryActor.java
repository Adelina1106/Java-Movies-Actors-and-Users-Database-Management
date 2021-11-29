package queries;

import actor.Actor;
import fileio.ActionInputData;
import videos.Movie;
import videos.Serial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class QueryActor {
    public void sortByGrade(ArrayList<Movie> movies, ArrayList<Serial> serials, ArrayList<Actor> actors){
        actors.sort(new Comparator<>() {
            @Override
            public int compare(Actor actor1, Actor actor2) {
                if (!Objects.equals(actor1.getAverageGrade(movies, serials), actor2.getAverageGrade(movies, serials)))
                    return Double.compare(actor1.getAverageGrade(movies, serials), actor2.getAverageGrade(movies, serials));
                else return actor1.getName().compareTo(actor2.getName());
            }
        });
    }

    public void sortByNumberOfAwards(ArrayList<Actor> actors, ActionInputData actionInputData){
        actors.sort(new Comparator<>() {
            @Override
            public int compare(Actor actor1, Actor actor2) {
                if (!Objects.equals(actor1.getTotalAwards(actionInputData), actor2.getTotalAwards(actionInputData)))
                    return Integer.compare(actor1.getTotalAwards(actionInputData), actor2.getTotalAwards(actionInputData));
                else return actor1.getName().compareTo(actor2.getName());
            }
        });
    }

}
