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
import java.util.Comparator;

public class FilterDescription extends QueryActor {
    public ArrayList<Actor> getFilterDescription(ArrayList<Actor> actors, ArrayList<Movie> movies,
                                                 ArrayList<Serial> serials,
                                                 ActionInputData actionInputData) {
        ArrayList<Actor> filterActors = new ArrayList<Actor>();
        for (Actor actor : actors)
            if (actor.hasKeywords(actionInputData))
                filterActors.add(actor);
       filterActors.sort(new Comparator<>() {
            @Override
            public int compare(Actor actor1, Actor actor2) {
                return actor1.getName().compareTo(actor2.getName());
            }
        });
        if (actionInputData.getSortType().equals("desc"))
            Collections.reverse(filterActors);
        return filterActors;
    }

    static public JSONObject filterQuery(ArrayList<Actor> actors, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                        ActionInputData actionInputData, Writer writer) throws IOException {
        FilterDescription filterDescription = new FilterDescription();
        ArrayList<Actor> filterActors = filterDescription.getFilterDescription(actors, movies, serials, actionInputData);
        String message = "Query result: [";
        for (Actor actor : filterActors)
            message = message + actor.getName() + ", ";
        if (filterActors.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
