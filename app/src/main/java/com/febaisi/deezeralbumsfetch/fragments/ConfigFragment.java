package com.febaisi.deezeralbumsfetch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.febaisi.deezeralbumsfetch.R;

public class ConfigFragment extends CustomFragment {

    private static String CONFIG_FRAG_TAG = "CONFIG_FRAG_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public String getCustomTag() {
        return CONFIG_FRAG_TAG;
    }
}
