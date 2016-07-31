package com.jwho.lifenoteflip.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.PersistableBundle;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.notestore.NoteStore;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Resource;
import com.evernote.thrift.TException;
import com.evernote.thrift.protocol.TBinaryProtocol;
import com.evernote.thrift.transport.THttpClient;
import com.jwho.lifenoteflip.app.BuildConfig;
import com.jwho.lifenoteflip.dataaccess.AppActivityDbAdapter;
import com.jwho.lifenoteflip.utils.L;

public class EvernoteService extends JobService {

    public static final String BUNDLE_NOTE_FILTER_TAG = "bundleNoteFilterTag";
    private EvernoteGetTaskWithJobService evernoteGetTaskWithJobService = new EvernoteGetTaskWithJobService(this);

    @Override
    public boolean onStartJob(JobParameters params) {
        L.dM(getClass(), "onStartJob", "Starting job in progress...");
        evernoteGetTaskWithJobService.execute(params);
        return true;
    }

    @Override
        public boolean onStopJob(JobParameters params) {
        L.dM(this.getClass(), "onStopJob", "Stopping job in progress...");
        boolean shouldReschedule = evernoteGetTaskWithJobService.stopJob(params);
        return shouldReschedule;
    }

    private static class EvernoteGetTaskWithJobService extends AsyncTask<JobParameters, Void, JobParameters[]> {
        EvernoteService service;

        public EvernoteGetTaskWithJobService(EvernoteService service) {
            this.service = service;
        }

        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            PersistableBundle extras = params[0].getExtras();
            getNote(extras.getString(BUNDLE_NOTE_FILTER_TAG),
                    extras.getString(AppActivityService.BUNDLE_APP_ACTIVITY_STATUS));
            return params;
        }

        public NoteList getNote(String filter, String status) {
            try {
                THttpClient noteStoreTrans = new THttpClient(BuildConfig.NOTE_STORE_URL);
                TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
                NoteStore.Client noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);
                NoteFilter noteFilter = new NoteFilter();
                noteFilter.setWords(filter);

                NoteList result = noteStore.findNotes(BuildConfig.AUTH_TOKEN, noteFilter, 0, 50);
                L.i(service.getClass(), "Found: " + result.getTotalNotes());
                saveEvernoteToStore(result, noteStore, status);

                return result;
//            return new NoteFromEvernote(resGuid, note.getTitle(), note.getContent(), resUrl);
            } catch (Exception e) {
                L.i(service.getClass(), e.getStackTrace().toString());
            }
            return null;
        }

        private void saveEvernoteToStore(NoteList result, NoteStore.Client noteStore, String status) throws EDAMUserException, EDAMSystemException, TException, EDAMNotFoundException {
            AppActivityDbAdapter dbAdapter = new AppActivityDbAdapter(service.getApplicationContext());
            Note noteWithData;
            for (Note note : result.getNotes()) {
                L.i(service.getClass(), "GUID: " + note.getGuid() + " Title: " + note.getTitle());
                noteWithData = noteStore.getNote(BuildConfig.AUTH_TOKEN, note.getGuid(), true, true, false, false);
                dbAdapter.insertAppActivity(Services.EVERNOTE, status);
                dbAdapter.insertNote(noteWithData.getContent(), note.getTitle());
                for (Resource res : noteWithData.getResources()) {
                    dbAdapter.insertResource(res.getGuid(), res.getNoteGuid(), "Started");
                }
            }
        }

        @Override
        protected void onPostExecute(JobParameters[] jobParameters) {
            super.onPostExecute(jobParameters);
            for (JobParameters params : jobParameters) {
                if (!hasJobBeenStopped(params)) {
                    service.jobFinished(params, false);
                }
            }
        }

        private boolean hasJobBeenStopped(JobParameters params) {
            // Logic for checking stop.
            return false;
        }

        public boolean stopJob(JobParameters params) {
            // Logic for stopping a job (task in this case), return true if job should be rescheduled.
            return false;
        }
    }

    private static class EvernoteGetResourcesTaskWithJobService extends AsyncTask<JobParameters, Void, JobParameters[]> {
        EvernoteService service;

        public EvernoteGetResourcesTaskWithJobService(EvernoteService service) {
            this.service = service;
        }

        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            PersistableBundle extras = params[0].getExtras();
            getNote(extras.getString(BUNDLE_NOTE_FILTER_TAG),
                    extras.getString(AppActivityService.BUNDLE_APP_ACTIVITY_STATUS));
            return params;
        }

        public NoteList getNote(String filter, String status) {
            try {
                THttpClient noteStoreTrans = new THttpClient(BuildConfig.NOTE_STORE_URL);
                TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
                NoteStore.Client noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);
                NoteFilter noteFilter = new NoteFilter();
                noteFilter.setWords(filter);

                NoteList result = noteStore.findNotes(BuildConfig.AUTH_TOKEN, noteFilter, 0, 50);
                L.i(service.getClass(), "Found: " + result.getTotalNotes());
                saveEvernoteToStore(result, noteStore, status);

                return result;
//            return new NoteFromEvernote(resGuid, note.getTitle(), note.getContent(), resUrl);
            } catch (Exception e) {
                L.i(service.getClass(), e.getStackTrace().toString());
            }
            return null;
        }

        private void saveEvernoteToStore(NoteList result, NoteStore.Client noteStore, String status) throws EDAMUserException, EDAMSystemException, TException, EDAMNotFoundException {
            AppActivityDbAdapter dbAdapter = new AppActivityDbAdapter(service.getApplicationContext());
            Note noteWithData;
            for (Note note : result.getNotes()) {
                L.i(service.getClass(), "GUID: " + note.getGuid() + " Title: " + note.getTitle());
                noteWithData = noteStore.getNote(BuildConfig.AUTH_TOKEN, note.getGuid(), true, true, false, false);
                dbAdapter.insertAppActivity(Services.EVERNOTE, status);
                dbAdapter.insertNote(noteWithData.getContent(), note.getTitle());
                for (Resource res : noteWithData.getResources()) {
                    dbAdapter.insertResource(res.getGuid(), res.getNoteGuid(), "Started");
                }
            }
        }

        @Override
        protected void onPostExecute(JobParameters[] jobParameters) {
            super.onPostExecute(jobParameters);
            for (JobParameters params : jobParameters) {
                if (!hasJobBeenStopped(params)) {
                    service.jobFinished(params, false);
                }
            }
        }

        private boolean hasJobBeenStopped(JobParameters params) {
            // Logic for checking stop.
            return false;
        }

        public boolean stopJob(JobParameters params) {
            // Logic for stopping a job (task in this case), return true if job should be rescheduled.
            return false;
        }
    }
}
