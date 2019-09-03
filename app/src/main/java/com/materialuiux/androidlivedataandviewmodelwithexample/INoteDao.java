package com.materialuiux.androidlivedataandviewmodelwithexample;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface INoteDao {

    /**
     * Function to insert a note in room database
     *
     * @param note to be inserted in database
     */
    @Insert
    void insertNote(Note note);


    /**
     * Function to Update an note in room database
     *
     * @param note the object to be Update
     */
    @Update
    void updateNote(Note note);


    /**
     * Function to delete an note in room database
     *
     * @param note the object to be deleted
     */
    @Delete
    void deleteNote(Note note);

    /**
     * Get all Notes in database ordered by ASC
     *
     * @return a list with all Contacts
     */
    @Query("SELECT * from note_table ")
    LiveData<List<Note>> getItemList();

}
