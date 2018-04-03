
package io.github.emrys919.movies.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.github.emrys919.movies.data.vos.MovieVO;

public class MoviesResponse {

    @SerializedName("page")
    private Long page;

    @SerializedName("results")
    private List<MovieVO> movieList;

    @SerializedName("total_pages")
    private Long totalPages;

    @SerializedName("total_results")
    private Long totalResults;

    public Long getPage() {
        return page;
    }

    public List<MovieVO> getMovieList() {
        return movieList;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

}
