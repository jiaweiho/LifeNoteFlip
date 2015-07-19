package com.jwho.lifenoteflip.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwho.lifenoteflip.dataaccess.AppActivity;
import com.jwho.lifenoteflip.dataaccess.AppActivityItemAdapter;
import com.jwho.lifenoteflip.service.EvernoteGetNotesService;

import java.util.Arrays;

import static android.support.v7.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS;
import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

/**
 * First page in the app.
 */
public class HighlightFragment extends Fragment {
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
        EvernoteGetNotesService evernoteService = new EvernoteGetNotesService(this);
        evernoteService.execute("tag:note");

        // Start sync with other Services

        return view;
    }
}
