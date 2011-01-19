package org.tby.jeesample.presentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Index {

    // @Inject
    // private MovieService mMovieService;
    //
    // private Movie mMovie = new Movie();
    //
    // public String getMovieTitle() {
    // return mMovie.getTitle();
    // }
    //
    // public void setMovieTitle(String aTitle) {
    // mMovie.setTitle(aTitle);
    // }
    //
    // public List<String> getAllMovies() {
    // List<String> result = new ArrayList<String>();
    // List<Movie> all = mMovieService.findAll();
    // for (Movie movie : all) {
    // result.add(movie.getTitle());
    // }
    //
    // return result;
    // }
    //
    // public void save() {
    // mMovieService.save(mMovie);
    // }
}
