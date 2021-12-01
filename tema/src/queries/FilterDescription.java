package queries;

import actors.Actor;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class FilterDescription extends ActorQuery {
    /**
     * Gets all the actors that have the keywords from the query in their description
     *
     * @param actors ArrayList with all the actors
     * @param actionInputData information about the action
     * @return ArrayList with the actors
     */
    public ArrayList<Actor> getFilterDescription(final ArrayList<Actor> actors,
                                                 final ActionInputData actionInputData) {
        ArrayList<Actor> filterActors = new ArrayList<>();
        actors.stream()
                .filter(actor -> actor.hasKeywords(actionInputData))
                .forEach(filterActors::add);
        filterActors.sort(Comparator.comparing(Actor::getName));
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(filterActors);
        }
        return filterActors;
    }

    /**
     * Executes the filter description query
     *
     * @param actors ArrayList with all the actors
     * @param actionInputData information about the action
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject filterQuery(final ArrayList<Actor> actors,
                                         final ActionInputData actionInputData,
                                         final Writer writer) throws IOException {
        FilterDescription filterDescription = new FilterDescription();
        ArrayList<Actor> filterActors = filterDescription.getFilterDescription(actors,
                actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                filterDescription.getMessage(filterActors));
    }
}
