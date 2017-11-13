package com.febaisi.deezeralbumsfetch.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.fragments.ConfigFragment;
import com.febaisi.deezeralbumsfetch.network.DownloadImagePriorityRunnable;
import com.febaisi.deezeralbumsfetch.network.ImageDownloadListener;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.DefaultExecutorSupplier;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.Priority;
import com.febaisi.deezeralbumsfetch.sharedpreference.SharedPreferenceUtil;


public class CoverDownloaderImageView extends AppCompatImageView {

    private boolean mShowArtist;
    private Context mContext;
    private String mAlbumTitle; // Used to debug
    private Activity mActivity;

    public CoverDownloaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mShowArtist = false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Default custom configs
        setVisibility(View.INVISIBLE);

    }

    public void downloadCover(boolean isArtist, String imageUrl) {

        if (mActivity == null) {
            return;
        }

        mShowArtist = isArtist;
        ImageDownloadListener imageDownloadListener = new ImageDownloadListener() {
            @Override
            public void onDrawableAvailable(boolean isArtist, Drawable drawable) {
                setRealCover(isArtist, drawable);
            }

            @Override
            public void onRequestFail(String failResult) {

            }
        };

        //If it's an artist request set the priority to IMMEDIATE
        DefaultExecutorSupplier.getInstance().getNetworkThreadPoolExecutor()
                .submit(new DownloadImagePriorityRunnable(isArtist ? Priority.IMMEDIATE : Priority.MEDIUM,
                        isArtist, mContext, mAlbumTitle, imageUrl, imageDownloadListener));
    }

    private void setRealCover(final boolean isArtist, final Drawable drawable) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mShowArtist && !isArtist) {
                    //Prevent to set album cover is we should only show the artist cover
                    return;
                }
                setImageDrawable(drawable);
                int imagePixels = getResources().getDimensionPixelSize(R.dimen.album_image_size);
                getLayoutParams().height = imagePixels;
                getLayoutParams().width = imagePixels;

                Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_fade_in);
                fadeInAnimation.setFillAfter(true);
                fadeInAnimation.setDuration(SharedPreferenceUtil.getIntPref(mContext, ConfigFragment.CONFIG_ANIM_TIME, 700));
                startAnimation(fadeInAnimation);
            }
        });
    }

    public void setAlbumTitle(String albumTitle) {
        this.mAlbumTitle = albumTitle;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

}