package gr.athtech.coolmovies.Model;

import java.util.Objects;

public class MovieData{

    private int id;

    private String cast;


    private String originalTitle;

    private String originalLanguage;


    private String title;


    private String posterPath;


    private boolean adult;


    private String overview;


    private String releaseDate;


    private String backdropPath;


    private double popularity;


    private int voteCount;


    private boolean video;


    private double voteAverage;

    public MovieData() {
        super();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCast(String original_name) {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieData movieData = (MovieData) o;
        return id == movieData.id &&
                adult == movieData.adult &&
                Double.compare(movieData.popularity, popularity) == 0 &&
                voteCount == movieData.voteCount &&
                video == movieData.video &&
                Double.compare(movieData.voteAverage, voteAverage) == 0 &&
                Objects.equals(cast, movieData.cast) &&
                Objects.equals(originalTitle, movieData.originalTitle) &&
                Objects.equals(originalLanguage, movieData.originalLanguage) &&
                Objects.equals(title, movieData.title) &&
                Objects.equals(posterPath, movieData.posterPath) &&
                Objects.equals(overview, movieData.overview) &&
                Objects.equals(releaseDate, movieData.releaseDate) &&
                Objects.equals(backdropPath, movieData.backdropPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cast, originalTitle, originalLanguage, title, posterPath, adult, overview, releaseDate, backdropPath, popularity, voteCount, video, voteAverage, cast);
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id=" + id +
                ", cast=" + cast +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", cast='" + cast + '\'' +
                '}';
    }


}
