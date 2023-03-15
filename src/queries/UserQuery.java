package queries;

import fileio.ActionInputData;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserQuery {
    /**
     * The method used for sorting users by number of ratings
     *
     * @param users ArrayList to be sorted
     * @param actionInputData information about the action
     */
    public void sortByNoOfRatings(final ArrayList<User> users,
                                  final ActionInputData actionInputData) {
        users.sort((user1, user2) -> {
            if (!Objects.equals(user1.getRatings().size(), user2.getRatings().size())) {
                return Integer.compare(user1.getRatings().size(), user2.getRatings().size());
            } else {
                return user1.getUsername().compareTo(user2.getUsername());
            }
        });
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(users);
        }
    }

    /**
     * @param users ArrayList with the users to be printed
     * @return the result message of the action
     */
    public String getMessage(final List<User> users) {
        StringBuilder message = new StringBuilder("Query result: [");
        for (User user : users) {
            message.append(user.getUsername());
            message.append(", ");
        }
        if (users.size() > 0) {
            message.deleteCharAt(message.length() - 1);
            message.deleteCharAt(message.length() - 1);
        }
        message.append("]");
        return new String(message);
    }
}
