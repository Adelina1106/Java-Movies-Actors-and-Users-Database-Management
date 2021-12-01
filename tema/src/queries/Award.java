package queries;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public final class Award extends ActorQuery {

    /**
     * Gets the actors that have all the awards from query
     *
     * @param actors ArrayList with all the actors
     * @param actionInputData information about input action
     * @return ArrayList with all actors with the awards from query
     */
    public ArrayList<Actor> getAwards(final ArrayList<Actor> actors,
                                      final ActionInputData actionInputData) {
        ArrayList<Actor> awardsActors = new ArrayList<>(actors);
        awardsActors.removeIf((actor) -> actor.getTotalAwards(actionInputData) == 0);
        sortByNumberOfAwards(awardsActors, actionInputData);
        return awardsActors;
    }

    /**
     * Executes the award query
     *
     * @param actors ArrayList with all the actors
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject awardQuery(final ArrayList<Actor> actors,
                                        final ActionInputData actionInputData,
                                        final Writer writer) throws IOException {
        Award award = new Award();
        ArrayList<Actor> awardsActors = award.getAwards(actors, actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                award.getMessage(awardsActors));
    }
}
