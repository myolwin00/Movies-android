
package io.github.emrys919.movies.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.github.emrys919.movies.data.vos.TrailerVO;

public class TrailerResponse {

    @SerializedName("id")
    private Long mId;
    @SerializedName("results")
    private List<TrailerVO> mTrailers;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<TrailerVO> getTrailers() {
        return mTrailers;
    }

    public void setTrailer(List<TrailerVO> trailerVOs) {
        mTrailers = trailerVOs;
    }

}
