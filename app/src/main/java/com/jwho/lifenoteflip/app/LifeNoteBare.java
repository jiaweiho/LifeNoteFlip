package com.jwho.lifenoteflip.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jwho.lifenoteflip.app.helpers.TaskCallback;
import com.jwho.lifenoteflip.dataaccess.AppActivityDbAdapter;
import com.jwho.lifenoteflip.service.Services;

import java.util.ArrayList;

import static com.jwho.lifenoteflip.service.Services.UNKNOWN;

public class LifeNoteBare extends Activity implements TaskCallback {

    private String appSharedContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_note_bare);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        // handle Intent and decide what to do with it
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        Uri data = intent.getData();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Handle intent in order to create a post with minimal GUI
            // from another app...
            // Get link
            appSharedContent = sharedText;

        }
    }

    public void onClickSaveActivity(View view) {
        // Save in local DB
        Editable statusText = ((EditText) findViewById(R.id.status_edit)).getText();
        Editable appNameText = ((EditText) findViewById(R.id.app_name_edit)).getText();
        new SaveAppAction(this).execute(statusText.toString(), appNameText.toString(), appSharedContent);

        // Start intentservice that syncs with server

    }

    @Override
    public void postFinish() {
        finish();
    }

    private class SaveAppAction extends AsyncTask<String, Object, Boolean> {
        private final TaskCallback callback;
        private AppActivityDbAdapter adapter;

        private SaveAppAction(TaskCallback callback) {
            this.callback = callback;
            adapter = new AppActivityDbAdapter(getApplicationContext());
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return adapter.insertAppActivity(UNKNOWN, params[0], params[2]);
        }

        @Override
        protected void onPostExecute(Boolean successInsert) {
            super.onPostExecute(successInsert);
            // Notify user of status?
            callback.postFinish();
        }
    }
}
