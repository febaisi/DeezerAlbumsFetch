package com.febaisi.deezeralbumsfetch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.SharedPreferenceUtil;
import com.febaisi.deezeralbumsfetch.cache.ImagesCache;

public class ConfigFragment extends CustomFragment {

    public static String INTERNET_SLOW_PREF = "INTERNET_SLOW_PREF";
    public static String CONFIG_FRAG_TAG = "CONFIG_FRAG_TAG";
    public static String CONFIG_ANIM_TIME = "CONFIG_ANIM_TIME";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        view.findViewById(R.id.config_main_layout).requestFocus();

        //Config Internet Slow **
        boolean internetSlowPref = SharedPreferenceUtil.getBoolPref(getContext(), INTERNET_SLOW_PREF);
        final ToggleButton slowInternetToggleButton = view.findViewById(R.id.config_internet_slow);
        if (internetSlowPref) {
            slowInternetToggleButton.toggle();
        }
        slowInternetToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = slowInternetToggleButton.isChecked();
                if (checked) {
                    Toast.makeText(getContext(), "Cache disabled", Toast.LENGTH_SHORT).show();
                }
                SharedPreferenceUtil.putBoolPref(getContext(), INTERNET_SLOW_PREF, checked);
            }
        });

        //Config Internet Slow **
        view.findViewById(R.id.config_delete_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cache deleted", Toast.LENGTH_SHORT).show();
                ImagesCache.getInstance().clearCache();
            }
        });
        //Config Wipe Cache **

        //Config Animation Ms **
        int animationTime = SharedPreferenceUtil.getIntPref(getContext(), CONFIG_ANIM_TIME, 700);
        final EditText animationEditText = view.findViewById(R.id.config_anim_duration);
        animationEditText.setText(Integer.toString(animationTime));
        animationEditText.clearFocus();
        view.findViewById(R.id.config_anim_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAnimationTime = Integer.parseInt(animationEditText.getText().toString());
                SharedPreferenceUtil.putIntPref(getContext(), CONFIG_ANIM_TIME, newAnimationTime);
                Toast.makeText(getContext(), "New album cover fade in animation time: " + newAnimationTime, Toast.LENGTH_SHORT).show();
            }
        });
        //Config Animation Ms **



        return view;
    }

    @Override
    public String getCustomTag() {
        return CONFIG_FRAG_TAG;
    }
}
