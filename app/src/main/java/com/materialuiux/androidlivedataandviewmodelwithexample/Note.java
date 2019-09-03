package com.materialuiux.androidlivedataandviewmodelwithexample;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class to store in Room Database
 */
@Entity(tableName = "note_table")
public class Note {

    /**
     * id that auto generate
     */
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int UID;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "lastEdit")
    private String lastEdit;

    public Note(@NonNull int UID, String label, String content, String lastEdit) {
        this.UID = UID;
        this.label = label;
        this.content = content;
        this.lastEdit = lastEdit;
    }

    @NonNull
    public int getUID() {
        return UID;
    }

    public String getLabel() {
        return label;
    }

    public String getContent() {
        return content;
    }

    public String getLastEdit() {
        return lastEdit;
    }
}
