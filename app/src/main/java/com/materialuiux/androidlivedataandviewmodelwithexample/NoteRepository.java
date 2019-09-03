package com.materialuiux.androidlivedataandviewmodelwithexample;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {

    private INoteDao myNotsDao;
    private LiveData<List<Note>> noteList;


    NoteRepository(Application application) {
        // initialize the database
        NoteRoomDatabase roomDatabase = NoteRoomDatabase.getDatabase(application);
        myNotsDao = roomDatabase.iNoteDao();
        noteList = myNotsDao.getItemList();
    }


    LiveData<List<Note>> getAllItems() {
        return noteList;
    }

    public void insert(Note note) {
        new insertAsyncTask(myNotsDao).execute(note);
    }

    public void update(Note note) {
        new updateAsyncTask(myNotsDao).execute(note);
    }

    public void delete(Note note) {
        new deleteAsyncTask(myNotsDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private INoteDao myAsyncDao;

        insertAsyncTask(INoteDao dao) {
            myAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            myAsyncDao.insertNote(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private INoteDao myAsyncDao;

        deleteAsyncTask(INoteDao dao) {
            myAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            myAsyncDao.deleteNote(params[0]);
            return null;
        }

    }

    private static class updateAsyncTask extends AsyncTask<Note, Void, Void> {

        private INoteDao myAsyncDao;

        updateAsyncTask(INoteDao dao) {
            myAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            myAsyncDao.updateNote(params[0]);
            return null;
        }

    }
}

