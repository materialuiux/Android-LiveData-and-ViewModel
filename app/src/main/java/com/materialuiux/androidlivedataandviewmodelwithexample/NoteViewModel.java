package com.materialuiux.androidlivedataandviewmodelwithexample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {

    private NoteRepository myRepository;
    private LiveData<List<Note>> allItems;

    // Create a LiveData with a NoteRepository
    public NoteViewModel(Application application) {
        super(application);
        myRepository = new NoteRepository(application);
        allItems = myRepository.getAllItems();
    }

    void insert(Note note) {
        myRepository.insert(note);
    }
    void delete(Note note) {
        myRepository.delete(note);
    }
    void update(Note note) {
        myRepository.update(note);
    }

    LiveData<List<Note>> getAllItems() {
        return allItems;
    }

}
