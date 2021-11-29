package users;

import videos.Serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private List<String> favourites;
    private Map<String, Integer> history;
    private String subscriptionType;
    private Map<String, Double> ratings = new HashMap<>();

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, List<String> favourites,Map<String, Integer> history, String subscriptionType) {
        this.username = username;
        this.favourites = favourites;
        this.history = history;
        this.subscriptionType = subscriptionType;
    }

    public User(User userCopy){
        this.username = userCopy.getUsername();
        this.favourites = userCopy.getFavourites();
        this.history = new HashMap<>();
        this.history = userCopy.getHistory();
        this.subscriptionType = userCopy.getSubscriptionType();
        this.ratings = userCopy.ratings;
    }
    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }

}
