package users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class User {
    /**
     * User's username
     */
    private String username;
    /**
     * User's list of favourites
     */
    private List<String> favourites;
    /**
     * User's history
     */
    private Map<String, Integer> history;
    /**
     * User's subscription type
     */
    private final String subscriptionType;
    /**
     * Ratings given by user
     */
    private Map<String, Double> ratings = new HashMap<>();

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public void setFavourites(final List<String> favourites) {
        this.favourites = favourites;
    }

    public User(final String username, final List<String> favourites,
                final Map<String, Integer> history, final String subscriptionType) {
        this.username = username;
        this.favourites = favourites;
        this.history = history;
        this.subscriptionType = subscriptionType;
    }

    /**
     * copy constructor
     *
     * @param userCopy user to copy
     */
    public User(final User userCopy) {
        this.username = userCopy.getUsername();
        this.favourites = userCopy.getFavourites();
        this.history = userCopy.getHistory();
        this.subscriptionType = userCopy.getSubscriptionType();
        this.ratings = userCopy.ratings;
    }

}
