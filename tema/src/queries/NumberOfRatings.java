package queries;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;
import users.User;

import java.io.IOException;
import java.util.ArrayList;

public class NumberOfRatings extends UserQuery{
    public ArrayList<User> getUsersByNoOfRatings (ArrayList<User> users, ActionInputData actionInputData)
    {
        ArrayList<User> usersByNoOfRatings = new ArrayList<>();
        for(var user:users)
            usersByNoOfRatings.add(user);
        sortByNoOfRatings(usersByNoOfRatings, actionInputData.getSortType());
        usersByNoOfRatings.removeIf((user) -> user.getRatings().size() == 0);
        ArrayList<User> usersByRatings = new ArrayList<>();
        if (actionInputData.getNumber() < usersByRatings.size() + 1){
            for (int i = 0; i < actionInputData.getNumber(); i++)
                usersByRatings.add(usersByNoOfRatings.get(i));
        return usersByRatings;}
        else return usersByNoOfRatings;

    }

    static public JSONObject NumberOfRatingsQuery(ArrayList<User> users,
                                         ActionInputData actionInputData, Writer writer) throws IOException {
        NumberOfRatings numberOfRatings = new NumberOfRatings();
        ArrayList<User> usersByRating = numberOfRatings.getUsersByNoOfRatings(users, actionInputData);
        String message = "Query result: [";
        for (User user : usersByRating)
            message = message + user.getUsername() + ", ";
        if (usersByRating.size() > 0)
            message = message.substring(0, message.length() - 2);
        message = message + "]";
        org.json.simple.JSONObject object = writer.writeFile(actionInputData.getActionId(), null,
                message);
        return object;
    }
}
