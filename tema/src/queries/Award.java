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

public class Award extends QueryActor {

    public ArrayList<Actor> getAwards(ArrayList<Actor> actors, ArrayList<Movie> movies,
                                      ArrayList<Serial> serials,
                                      ActionInputData actionInputData) {
        ArrayList<Actor> awardsActors = new ArrayList<Actor>();
        for (Actor actor : actors)
            awardsActors.add(actor);
        sortByNumberOfAwards(awardsActors, actionInputData);
        awardsActors.removeIf((actor) -> actor.getTotalAwards(actionInputData) == 0);
        if (actionInputData.getSortType().equals("desc"))
            Collections.reverse(awardsActors);
        return awardsActors;
    }

    static public JSONObject AwardQuery(ArrayList<Actor> actors, ArrayList<Movie> movies, ArrayList<Serial> serials,
                                        ActionInputData actionInputData, Writer writer) throws IOException {
        Award award = new Award();
        ArrayList<Actor> awardsActors = award.getAwards(actors, movies, serials, actionInputData);
        String message = "Query result: [";
        for (Actor actor : awardsActors)
            message = message + actor.getName() + ", ";
        if (awardsActors.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
