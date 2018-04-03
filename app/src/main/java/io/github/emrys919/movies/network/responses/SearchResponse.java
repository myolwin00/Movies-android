
package io.github.emrys919.movies.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.emrys919.movies.data.vos.MovieVO;

public class SearchResponse {

    @SerializedName("page")
    private Long page;
    @SerializedName("results")
    private List<MovieVO> results;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<MovieVO> getResults() {
        return results;
    }

    public void setResults(List<MovieVO> results) {
        this.results = results;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
