package com.febaisi.deezeralbumsfetch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.sharedpreference.SharedPreferenceUtil;
import com.febaisi.deezeralbumsfetch.cache.ImagesCache;

public class ConfigFragment extends CustomFragment implements View.OnClickListener{

    public static String INTERNET_SLOW_PREF = "INTERNET_SLOW_PREF";
    public static String CONFIG_FRAG_TAG = "CONFIG_FRAG_TAG";
    public static String CONFIG_ANIM_TIME = "CONFIG_ANIM_TIME";

    private ToggleButton mSlowInternetToggleButton;
    private Button mWipeCacheButton;
    private Button mAnimationButton;
    private EditText mAnimationEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        view.findViewById(R.id.config_main_layout).requestFocus();

        //Finding views
        mAnimationEditText = view.findViewById(R.id.config_anim_duration);
        mWipeCacheButton = view.findViewById(R.id.config_delete_cache);
        mSlowInternetToggleButton = view.findViewById(R.id.config_internet_slow);
        mAnimationButton = view.findViewById(R.id.config_anim_save);

        //Set click listeners
        mSlowInternetToggleButton.setOnClickListener(this);
        mWipeCacheButton.setOnClickListener(this);
        mAnimationButton.setOnClickListener(this);

        updateWidgetsStates();

        return view;
    }

    private void updateWidgetsStates() {
        //Animation value
        mAnimationEditText.setText(Integer.toString(SharedPreferenceUtil.getIntPref(getContext(), CONFIG_ANIM_TIME, 700)));

        if (SharedPreferenceUtil.getBoolPref(getContext(), INTERNET_SLOW_PREF)) {
            mSlowInternetToggleButton.toggle();
        }
    }

    @Override
    public String getCustomTag() {
        return CONFIG_FRAG_TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.config_internet_slow:
                boolean checked = mSlowInternetToggleButton.isChecked();
                if (checked) {
                    Toast.makeText(getContext(), "Internet slow - Cache disabled", Toast.LENGTH_SHORT).show();
                }
                SharedPreferenceUtil.putBoolPref(getContext(), INTERNET_SLOW_PREF, checked);
                break;
            case R.id.config_delete_cache:
                Toast.makeText(getContext(), "Cache deleted", Toast.LENGTH_SHORT).show();
                ImagesCache.getInstance().clearCache();
                break;
            case R.id.config_anim_save:
                int newAnimationTime = Integer.parseInt(mAnimationEditText.getText().toString());
                SharedPreferenceUtil.putIntPref(getContext(), CONFIG_ANIM_TIME, newAnimationTime);
                Toast.makeText(getContext(), "New album cover fade in animation time: " + newAnimationTime, Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }

    }
}
