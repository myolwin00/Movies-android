package io.github.emrys919.movies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.github.emrys919.movies.R;
import io.github.emrys919.movies.controllers.TrailerItemController;
import io.github.emrys919.movies.data.vos.TrailerVO;
import io.github.emrys919.movies.views.holders.TrailerVH;

/**
 * Created by myolwin00 on 8/7/17.
 */

public class TrailerAdapter extends BaseRecyclerAdapter<TrailerVH, TrailerVO> {

    private TrailerItemController mController;

    public TrailerAdapter(Context context, TrailerItemController controller) {
        super(context);
        mController = controller;
    }

    @Override
    public TrailerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.trailer_item, parent, false);
        return new TrailerVH(view, mController);
    }

    @Override
    public void onBindViewHolder(TrailerVH holder, int position) {
        holder.bind(mData.get(position));
    }
}
