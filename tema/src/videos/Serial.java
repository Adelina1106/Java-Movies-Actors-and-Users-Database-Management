package videos;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Show {
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    public Serial(String title, ArrayList<String> cast,
                  ArrayList<String> genres,
                  int numberOfSeasons, ArrayList<Season> seasons,
                  int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = new ArrayList<Season>();
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public int getDuration() {
        int duration = 0;
        for (var season : seasons)
            duration = duration + season.getDuration();
        return duration;

    }
}
