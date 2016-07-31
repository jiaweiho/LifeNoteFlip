package com.jwho.lifenoteflip.app;

import android.support.v4.app.Fragment;
import android.app.job.JobScheduler;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.io.Files;
import com.jwho.lifenoteflip.app.helpers.HighlightActivityImageAdapter;
import com.jwho.lifenoteflip.dataaccess.AppActivity;
import com.jwho.lifenoteflip.service.Services;
import com.jwho.lifenoteflip.utils.L;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static android.support.v7.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS;
import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

/**
 * Highlights activities from your activities.
 */
public class HighlightFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int JOB_ID = 0;
    public static final Services[] SERVICES = Services.values();
    //    private AppActivityItemAdapter adapter;
    private HighlightActivityImageAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // List view with better recycling of list elements.
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recList.setHasFixedSize(true);
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, VERTICAL);
        sgm.setGapStrategy(GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recList.setLayoutManager(sgm);

        // Sets the adapter for recyclerview. TODO: Get from cursorloader.
        AppActivity evernote = new AppActivity("Freelancing", "Evernote", "green", "");
        AppActivity youtube = new AppActivity("Watching inspirationally", "Youtube", "red", "");
        AppActivity facebook = new AppActivity("Netsocialing", "Facebook", "blue", "");
        AppActivity hangouts = new AppActivity("Chatting", "Hangouts", "yellow", "");

        File externalFilesDir = getActivity().getExternalFilesDir("icons");
        try {
            Files.createParentDirs(externalFilesDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter = new HighlightActivityImageAdapter(getActivity(),
                externalFilesDir.getAbsoluteFile(), 100, 100);

//        mAdapter.swapData(new ArrayList<>(Arrays.asList(evernote, youtube, facebook, hangouts)));
        recList.setAdapter(mAdapter);

        getActivity().getSupportLoaderManager().initLoader(R.id.highlights_apps, null, this);

        // Start download of note from Evernote and its resources.
        /*EvernoteGetNotesService evernoteService = new EvernoteGetNotesService(this);
        evernoteService.execute("tag:note");*/

        if (savedInstanceState != null) {
            //savedInstanceState.getParcelable();
        } else {

        }
        /*PersistableBundle bundle = new PersistableBundle();
        bundle.putString(EvernoteService.BUNDLE_NOTE_FILTER_TAG, "tag:note");
        bundle.putString(AppActivityService.BUNDLE_APP_ACTIVITY_STATUS, "Being Creative");
        ComponentName evernoteService = new ComponentName(getActivity(), EvernoteService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, evernoteService)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setExtras(bundle)
                .setPeriodic(20000)
                .build();

        // Start sync with other Services
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            L.d(this.getClass(), "Job scheduled successfully");
        }*/

        return view;
    }

    @Override
    public void onPause() {
        L.dM(getClass(), "onPause", "Will run Scheduler.cancelAll()...");
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new HighlightCursorLoader(getActivity().getApplicationContext(), );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
