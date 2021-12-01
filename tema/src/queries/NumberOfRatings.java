package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;

import java.io.IOException;
import java.util.ArrayList;

public final class NumberOfRatings extends UserQuery {
    /**
     * Gets first N users sorted by number of ratings
     *
     * @param users ArrayList with all the users
     * @param actionInputData information about the action
     * @return ArrayList with N users
     */
    public ArrayList<User> getUsersByNoOfRatings(final ArrayList<User> users,
                                                 final ActionInputData actionInputData) {
        ArrayList<User> usersByNoOfRatings = new ArrayList<>(users);
        sortByNoOfRatings(usersByNoOfRatings, actionInputData);
        usersByNoOfRatings.removeIf((user) -> user.getRatings().size() == 0);
        ArrayList<User> usersByRatings = new ArrayList<>();
        if (actionInputData.getNumber() < usersByNoOfRatings.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                usersByRatings.add(usersByNoOfRatings.get(i));
            }
            return usersByRatings;
        } else {
            return usersByNoOfRatings;
        }
    }

    /**
     * Executes number of ratings query
     *
     * @param users ArrayList with all the users
     * @param actionInputData information about the input
     * @param writer used for transforming the output in a JSONObject
     * @return JSONObject with the result message
     */
    public static JSONObject numberOfRatingsQuery(final ArrayList<User> users,
                                                  final ActionInputData actionInputData,
                                                  final Writer writer) throws IOException {
        NumberOfRatings numberOfRatings = new NumberOfRatings();
        ArrayList<User> usersByRating = numberOfRatings.getUsersByNoOfRatings(users,
                actionInputData);
        return writer.writeFile(actionInputData.getActionId(), null,
                numberOfRatings.getMessage(usersByRating));
    }
}
