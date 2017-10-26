package com.febaisi.deezeralbumsfetch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.febaisi.deezeralbumsfetch.R;

public class AlbumsFragment extends CustomFragment {

    private static String ALBUMS_FRAG_TAG = "ALBUMS_FRAG_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public String getCustomTag() {
        return ALBUMS_FRAG_TAG;
    }
}
