package io.github.emrys919.movies.fragments;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by myolwin00 on 8/1/17.
 */

public class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}