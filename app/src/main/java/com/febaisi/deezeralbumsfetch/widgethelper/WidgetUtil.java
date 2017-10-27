package com.febaisi.deezeralbumsfetch.widgethelper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.febaisi.deezeralbumsfetch.R;

public class WidgetUtil {

    public static TextView createAlbumDetailTextView(Context context, String text, boolean bold) {
        int paddingValue = context.getResources().getDimensionPixelSize(R.dimen.default_padding_album_details_tv);
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        if (bold) {
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        }

        return textView;
    }

    public static LinearLayout.LayoutParams getTvParams(Context context) {
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(context.getResources().getDimensionPixelSize(R.dimen.default_margin_between_tvs_cover), 0, 0,0);

        return tvParams;
    }

    public static LinearLayout createCustomLinearLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (context.getResources().getDimensionPixelSize(R.dimen.default_album_image_size))+20);
        linearLayout.setLayoutParams(params);

        return linearLayout;
    }

    public static GridLayout.LayoutParams getGridNewRowParms(Context context) {
        int margintTopInRows = context.getResources().getDimensionPixelSize(R.dimen.default_margin_between_albums_rows);

        GridLayout.LayoutParams paramsAlbumCover = new GridLayout.LayoutParams();
        paramsAlbumCover.setMargins(0, margintTopInRows, 0, 0);

        return paramsAlbumCover;
    }
}
