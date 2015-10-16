package com.jwho.lifenoteflip.app;

import android.app.Fragment;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwho.lifenoteflip.dataaccess.AppActivity;
import com.jwho.lifenoteflip.dataaccess.AppActivityItemAdapter;
import com.jwho.lifenoteflip.service.AppActivityService;
import com.jwho.lifenoteflip.service.EvernoteService;
import com.jwho.lifenoteflip.utils.L;

import java.util.Arrays;

import static android.support.v7.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS;
import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

/**
 * First page in the app.
 */
public class HighlightFragment extends Fragment {
    private static final int JOB_ID = 0;
    private AppActivityItemAdapter adapter;

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

        // Sets the adapter for recyclerview
        AppActivity evernote = new AppActivity("Freelancing", "Evernote", "green", "");
        AppActivity youtube = new AppActivity("Watching inspirationally", "Youtube", "red", "");
        AppActivity facebook = new AppActivity("Netsocialing", "Facebook", "blue", "");
        AppActivity hangouts = new AppActivity("Chatting", "Hangouts", "yellow", "");
        adapter = new AppActivityItemAdapter(Arrays.asList(evernote, youtube, facebook, hangouts));
        recList.setAdapter(adapter);

        // Start download of note from Evernote and its resources.
        /*EvernoteGetNotesService evernoteService = new EvernoteGetNotesService(this);
        evernoteService.execute("tag:note");*/

        if (savedInstanceState != null) {
            //savedInstanceState.getParcelable();
        } else {

        }
        PersistableBundle bundle = new PersistableBundle();
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
        }

        return view;
    }

    @Override
    public void onPause() {
        L.dM(this.getClass(), "onPause", "Will run Scheduler.cancelAll()...");
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
        super.onPause();
    }
}
