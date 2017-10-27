package com.febaisi.deezeralbumsfetch.network;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.febaisi.deezeralbumsfetch.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class FetchAlbumsAsyncTask extends AsyncTask<Void, Void, Pair<Boolean, String>>  {

    private AlbumsFetchListener mAlbumsFetchListener;

    public FetchAlbumsAsyncTask (AlbumsFetchListener  albumsFetchListener) {
        Log.i(MainActivity.APP_TAG, "Starting albums fetch.");
        this.mAlbumsFetchListener = albumsFetchListener;
    }

    @Override
    protected Pair<Boolean, String> doInBackground(Void... params) {
        String result = "";
        Boolean success = false;

        try {

            //My personal Deezer user -- 694058411
            //Test default user -- 2529
            URL url = new URL("http://api.deezer.com/2.0/user/694058411/albums");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            success = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = "MalformedURLException";
        } catch (ProtocolException e) {
            e.printStackTrace();
            result = "ProtocolException";
        } catch (IOException e) {
            e.printStackTrace();
            result = "IOException";
        }

        return new Pair<>(success, result);
    }

    @Override
    protected void onPostExecute(Pair<Boolean, String> resultPair) {
        if (mAlbumsFetchListener != null) {
            if (resultPair.first.booleanValue()) {
                Log.i(MainActivity.APP_TAG, "Albums fetch succeed.");
                mAlbumsFetchListener.onAlbumsFetched(resultPair.second.toString());
            } else {
                Log.e(MainActivity.APP_TAG, "Albums fetch failed.");
                mAlbumsFetchListener.onAlbumsFetchFail(resultPair.second.toString());
            }
        }
    }
}