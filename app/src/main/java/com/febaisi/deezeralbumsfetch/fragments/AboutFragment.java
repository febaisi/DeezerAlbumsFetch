package com.febaisi.deezeralbumsfetch.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.widgethelper.AlbumCoverImageBuilder;
import com.febaisi.deezeralbumsfetch.widgethelper.AlbumDefaultImageView;

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
