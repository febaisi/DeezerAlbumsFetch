package com.febaisi.deezeralbumsfetch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.cache.DiskLruImageCache;
import com.febaisi.deezeralbumsfetch.cache.MemImageCache;
import com.febaisi.deezeralbumsfetch.sharedpreference.SharedPreferenceUtil;
import com.febaisi.deezeralbumsfetch.widgethelper.CustomSpinner;

public class ConfigFragment extends CustomFragment implements View.OnClickListener{

    public static String INTERNET_SLOW_PREF = "INTERNET_SLOW_PREF";
    public static String CONFIG_FRAG_TAG = "CONFIG_FRAG_TAG";
    public static String CONFIG_ANIM_TIME = "CONFIG_ANIM_TIME";
    public static String CONFIG_CACHE_TYPE = "CONFIG_CACHE_TYPE";
    public static String CONFIG_CACHE_TYPE_FS = "FileSys";
    public static String CONFIG_CACHE_TYPE_MEM = "Memory";


    //Widgets
    private ToggleButton mSlowInternetToggleButton;
    private Button mWipeCacheButton;
    private Button mAnimationButton;
    private EditText mAnimationEditText;
    private CoordinatorLayout mRootCoordinatorLayout;
    private CustomSpinner mCustomCacheSpinner;

    @Override
    public String getCustomTag() {
        return CONFIG_FRAG_TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        view.findViewById(R.id.config_main_linear_layout).requestFocus();

        //Finding views
        mAnimationEditText = view.findViewById(R.id.config_anim_duration);
        mWipeCacheButton = view.findViewById(R.id.config_delete_cache);
        mSlowInternetToggleButton = view.findViewById(R.id.config_internet_slow);
        mAnimationButton = view.findViewById(R.id.config_anim_save);
        mRootCoordinatorLayout = view.findViewById(R.id.config_root_layout);
        mCustomCacheSpinner = view.findViewById(R.id.config_cache_spinner);

        //Set click listeners
        mSlowInternetToggleButton.setOnClickListener(this);
        mWipeCacheButton.setOnClickListener(this);
        mAnimationButton.setOnClickListener(this);

        configCacheTypeSpinner();
        updateWidgetsStates();


        return view;
    }

    private void updateWidgetsStates() {
        //Animation value
        mAnimationEditText.setText(Integer.toString(SharedPreferenceUtil.getIntPref(getContext(), CONFIG_ANIM_TIME, 700)));

        if (SharedPreferenceUtil.getBoolPref(getContext(), INTERNET_SLOW_PREF)) {
            mSlowInternetToggleButton.toggle();
        }

        if (SharedPreferenceUtil.getStringPref(getContext(), CONFIG_CACHE_TYPE, CONFIG_CACHE_TYPE_FS) .equals(CONFIG_CACHE_TYPE_FS)) {
            //Set the default value for cache type
            SharedPreferenceUtil.putStringPref(getContext(), CONFIG_CACHE_TYPE, CONFIG_CACHE_TYPE_FS);
            mCustomCacheSpinner.setSelection(0);
        } else {
            mCustomCacheSpinner.setSelection(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.config_internet_slow:
                boolean checked = mSlowInternetToggleButton.isChecked();
                if (checked) {
                    Snackbar.make(mRootCoordinatorLayout, "Internet slow - Cache disabled", Snackbar.LENGTH_SHORT).show();
                }
                SharedPreferenceUtil.putBoolPref(getContext(), INTERNET_SLOW_PREF, checked);
                break;
            case R.id.config_delete_cache:
                deleteAllCache();
                Snackbar.make(mRootCoordinatorLayout, "Cache deleted", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.config_anim_save:
                int newAnimationTime = Integer.parseInt(mAnimationEditText.getText().toString());
                SharedPreferenceUtil.putIntPref(getContext(), CONFIG_ANIM_TIME, newAnimationTime);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Snackbar.make(mRootCoordinatorLayout, "New album cover fade in animation time: " + newAnimationTime, Snackbar.LENGTH_SHORT).show();
                break;
        }

    }

    private void configCacheTypeSpinner() {
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_selected, new String[]{CONFIG_CACHE_TYPE_FS, CONFIG_CACHE_TYPE_MEM});
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mCustomCacheSpinner.setAdapter(adapter);
        mCustomCacheSpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            public void onSpinnerOpened() {
                mCustomCacheSpinner.setSelected(true);
            }
            public void onSpinnerClosed() {
                mCustomCacheSpinner.setSelected(false);
                String selectedValue = mCustomCacheSpinner.getSelectedItem().toString();
                if (!selectedValue.equals(SharedPreferenceUtil.getStringPref(getContext(), CONFIG_CACHE_TYPE, ""))) {
                    SharedPreferenceUtil.putStringPref(getContext(), CONFIG_CACHE_TYPE, selectedValue);
                    deleteAllCache();
                    Snackbar.make(mRootCoordinatorLayout, "New cache type set -- Both caches wiped", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteAllCache() {
        MemImageCache.getInstance().clearCache();
        DiskLruImageCache.getInstance(getContext()).clearCache();
    }

}
