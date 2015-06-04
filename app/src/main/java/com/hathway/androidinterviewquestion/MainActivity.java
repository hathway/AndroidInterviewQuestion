package com.hathway.androidinterviewquestion;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.getinch.retrogram.Instagram;
import com.getinch.retrogram.model.Popular;

import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity {

    Instagram instagramService;
    ListView photoListView;
    MainActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_main);
        photoListView = (ListView) findViewById(R.id.listView);

        instagramService = new Instagram(getString(R.string.instagram_id), RestAdapter.LogLevel.HEADERS_AND_ARGS);
        new DownloadWebpageTask().execute(instagramService);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadWebpageTask extends AsyncTask<Instagram, Void, Popular> {
        @Override
        protected Popular doInBackground(Instagram... instagrams) {
            Instagram instaSvc = instagrams[0];
            return instaSvc.getMediaEndpoint().getPopular();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Popular popular) {
            InstagramFeedAdapter adapter = new InstagramFeedAdapter(thisActivity, popular);
            photoListView.setAdapter(adapter);
        }
    }
}
