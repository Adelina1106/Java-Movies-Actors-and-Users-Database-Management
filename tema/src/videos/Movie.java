package videos;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show{
    private int duration;

    public Movie(String title,ArrayList<String> cast,
                 ArrayList<String> genres, int year,
                 int duration)
    {
        super(title, year, cast, genres);
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
