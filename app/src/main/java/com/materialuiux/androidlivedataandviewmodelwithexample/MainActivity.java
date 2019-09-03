package com.materialuiux.androidlivedataandviewmodelwithexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel myItemViewModel;
    private FloatingActionButton actionButton;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionButton = findViewById(R.id.Add_Note);
        myRecyclerView = findViewById(R.id.recyclerview);

        // Get the ViewModel.
        myItemViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        // pass the viewModel to the adapter
        final NoteListAdapter myAdapter = new NoteListAdapter(this, myItemViewModel);
        myRecyclerView.setAdapter(myAdapter);
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(_sGridLayoutManager);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        myItemViewModel.getAllItems().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> items) {
                // Update the UI.
                myAdapter.setItems(items);
            }

        });


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new Note
                newNoteDialog();
            }
        });

    }

    public void newNoteDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.note_dialog);
        final TextView label = dialog.findViewById(R.id.label_edit_text);
        final TextView content = dialog.findViewById(R.id.content_edit_text);
        final TextView title = dialog.findViewById(R.id.dialog_title);
        final TextView date = dialog.findViewById(R.id.time_line);
        final Button cancel = dialog.findViewById(R.id.btn_cancel);

        final String mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        title.setText("Create New Note");
        date.setText(mDate);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button ok = dialog.findViewById(R.id.btn_okay);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(0, label.getText().toString(), content.getText().toString(), mDate);
                myItemViewModel.insert(note);
                dialog.dismiss();
            }
        });
        if (dialog.getWindow() != null)
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}
