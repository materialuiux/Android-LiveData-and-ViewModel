package com.materialuiux.androidlivedataandviewmodelwithexample;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label_tx, content_tx, last_edited_tx;
        private ImageView options;

        private ItemViewHolder(View itemView) {
            super(itemView);
            label_tx = itemView.findViewById(R.id.note_label);
            content_tx = itemView.findViewById(R.id.note_content);
            last_edited_tx = itemView.findViewById(R.id.note_last_edit);
            options = itemView.findViewById(R.id.option);

        }

        private void optionDialog(final Note note) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            LinearLayout layout = new LinearLayout(mContext);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(parms);

            layout.setGravity(Gravity.CLIP_VERTICAL);
            layout.setPadding(2, 2, 2, 2);

            TextView edit = new TextView(mContext);
            edit.setText("Edit");
            edit.setPadding(20, 20, 20, 20);
            edit.setGravity(Gravity.LEFT);
            edit.setTextSize(16);

            TextView delete = new TextView(mContext);
            delete.setText("Delete");
            delete.setPadding(20, 20, 20, 20);
            delete.setGravity(Gravity.LEFT);
            delete.setTextSize(16);

            LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(edit,tv1Params);
            layout.addView(delete ,tv1Params);

            alert.setView(layout);
            final AlertDialog alertDialog = alert.create();
            alertDialog.show();
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItemViewModel.delete(note);
                    alertDialog.dismiss();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    editNoteDialog(note);
                }
            });
        }

        public void editNoteDialog(final Note note) {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.note_dialog);
            dialog.setTitle("Edit Note");

            final TextView title = dialog.findViewById(R.id.dialog_title);
            final TextView label = dialog.findViewById(R.id.label_edit_text);
            final TextView content = dialog.findViewById(R.id.content_edit_text);
            final TextView date = dialog.findViewById(R.id.time_line);
            final Button cancel = dialog.findViewById(R.id.btn_cancel);

            label.setText(note.getLabel());
            title.setText("Edit Note");
            content.setText(note.getContent());
            final String mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
                    Note newNote = new Note(note.getUID(), label.getText().toString(), content.getText().toString(), mDate);
                    myItemViewModel.update(newNote);
                    dialog.dismiss();
                }
            });
            if (dialog.getWindow() !=null)
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }


    }

    private List<Note> myItems;
    private final LayoutInflater myInflater;
    private NoteViewModel myItemViewModel;
    private Context mContext;

    NoteListAdapter(Context context, NoteViewModel myItemViewModel) {
        mContext = context;
        myInflater = LayoutInflater.from(context);
        this.myItemViewModel = myItemViewModel;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = myInflater.inflate(R.layout.note_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Note current = myItems.get(position);
        holder.label_tx.setText(current.getLabel());
        holder.content_tx.setText(current.getContent());
        holder.last_edited_tx.setText(current.getLastEdit());
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialog for choosing if user want to delete note or modify note.
                holder.optionDialog(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (myItems != null)
            return myItems.size();
        else return 0;
    }

    void setItems(List<Note> items) {
        Collections.reverse(items);
        myItems = items;
        notifyDataSetChanged();
    }

}
