package queries;

import users.User;
import videos.Show;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class VideoQuery {

    public static void sortByRating(ArrayList<Show> ratingVideos){
        ratingVideos.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                if (!Objects.equals(show1.getRating(), show2.getRating()))
                    return Double.compare(show1.getRating(), show2.getRating());
                else return show1.getTitle().compareTo(show2.getTitle());
            }
        });
    }

    public void sortByNoOfFavourites(ArrayList<Show> favouriteVideos, ArrayList<User> users, String SortType){
        favouriteVideos.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                if (!Objects.equals(show1.getTotalNoOfFavorites(users), show2.getTotalNoOfFavorites(users)))
                    return Integer.compare(show1.getTotalNoOfFavorites(users), show2.getTotalNoOfFavorites(users));
                else return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (SortType.equals("desc"))
            Collections.reverse(favouriteVideos);
    }

    public void sortByDuration(ArrayList<Show> durationVideos, String SortType){
        durationVideos.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                if (!Objects.equals(show1.getDuration(), show2.getDuration()))
                    return Integer.compare(show1.getDuration(), show2.getDuration());
                else return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (SortType.equals("desc"))
            Collections.reverse(durationVideos);
    }

    public static void sortByNoOfViews(ArrayList<Show> videosByViews, String SortType, ArrayList<User> users){
        videosByViews.sort(new Comparator<>() {
            @Override
            public int compare(Show show1, Show show2) {
                if (!Objects.equals(show1.getNoOfViews(users), show2.getNoOfViews(users)))
                    return Integer.compare(show1.getNoOfViews(users), show2.getNoOfViews(users));
                else return show1.getTitle().compareTo(show2.getTitle());
            }
        });
        if (SortType.equals("desc"))
            Collections.reverse(videosByViews);
    }
}
