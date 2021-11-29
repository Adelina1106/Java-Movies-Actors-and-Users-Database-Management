package queries;

import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserQuery {
    public void sortByNoOfRatings(ArrayList<User> users, String SortType){
        users.sort(new Comparator<>() {
            @Override
            public int compare(User user1, User user2) {
               // if (!Objects.equals(user1.getRatings().size(), user2.getRatings().size()))
                    return Integer.compare(user1.getRatings().size(), user2.getRatings().size());
                //else return user1.getUsername().compareTo(user2.getUsername());
            }
        });
        if (SortType.equals("desc"))
            Collections.reverse(users);
    }
}
