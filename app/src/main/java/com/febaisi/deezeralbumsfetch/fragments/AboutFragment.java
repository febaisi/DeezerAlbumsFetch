package com.febaisi.deezeralbumsfetch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.febaisi.deezeralbumsfetch.R;

public class AboutFragment extends CustomFragment {

    private static String ABOUT_FRAG_TAG = "ABOUT_FRAG_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public String getCustomTag() {
        return ABOUT_FRAG_TAG;
    }
}
