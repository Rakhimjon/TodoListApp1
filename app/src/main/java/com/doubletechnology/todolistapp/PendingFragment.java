package com.doubletechnology.todolistapp;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doubletechnology.todolistapp.db.TaskCont;
import com.doubletechnology.todolistapp.db.TaskHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {
    RecyclerView recyclerView;
    EditText todo_input;
    RadioButton highBtn;
    RadioButton normBtn;
    RadioButton lowBtn;

    private final int PEND = 0;
    private final int HIGH = 1;
    private final int NORMAL = 2;
    private final int LOW = 3;

    List<PendingItem> pending = new ArrayList<>();

    private TaskHelper taskHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending_recyclerview_fab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        todo_input = (EditText) view.findViewById(R.id.todo_input);
        highBtn = (RadioButton) view.findViewById(R.id.highBtn);
        normBtn = (RadioButton) view.findViewById(R.id.normBtn);
        lowBtn = (RadioButton) view.findViewById(R.id.lowBtn);

        taskHelper = new TaskHelper(getContext());
        db = taskHelper.getWritableDatabase();

        Cursor cursor = db.query(TaskCont.TaskEntry.TABLE,
                new String[]{TaskCont.TaskEntry._ID, TaskCont.TaskEntry.TASK_TITLE, TaskCont.TaskEntry.PRIORITY},
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            int idTitle = cursor.getColumnIndex(TaskCont.TaskEntry.TASK_TITLE);
            int idPrio = cursor.getColumnIndex(TaskCont.TaskEntry.PRIORITY);
            int idColumn = cursor.getColumnIndex(TaskCont.TaskEntry._ID);
            String title = cursor.getString(idTitle);
            int priority = cursor.getInt(idPrio);
            int id = cursor.getInt(idColumn);
            pending.add(new PendingItem(title, priority, id));
        }
        cursor.close();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAndShowIntutDialog(null);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TodoPendingAdapter());

        return view;
    }

    private void buildAndShowIntutDialog(final PendingItem pendingItem) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.todo_enter));

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View dialogView = layoutInflater.inflate(R.layout.alert_dialog, null);
        final EditText todo_input = (EditText) dialogView.findViewById(R.id.todo_input);
        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.rgAlert);

        if (pendingItem != null) {
            todo_input.setText(pendingItem.getTitle());
            switch (pendingItem.getPriority()) {
                case PEND: {
                    break;
                }
                case HIGH: {
                    radioGroup.check(R.id.highBtn);
                    break;
                }
                case NORMAL: {
                    radioGroup.check(R.id.normBtn);
                    break;
                }
                case LOW: {
                    radioGroup.check(R.id.lowBtn);
                    break;
                }

            }
        }

        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int priority = PEND;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.highBtn: {
                        priority = HIGH;
                        break;
                    }
                    case R.id.normBtn: {
                        priority = NORMAL;
                        break;
                    }
                    case R.id.lowBtn: {
                        priority = LOW;
                        break;
                    }
                }
                addTodoItem(todo_input.getText().toString(), priority, pendingItem);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
//        final AlertDialog dialogInterface = builder.show();
//        todo_input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_DONE ||
//                        (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
//                                keyEvent.getKeyCode() == keyEvent.KEYCODE_ENTER)) {
//                    dialogInterface.dismiss();
//                    addTodoItem(todo_input.getText().toString());
//                    return true;
//                }
//                return true;
//            }
//        });
    }

    private void addTodoItem(String toDoitemText, int priority, PendingItem pendingItem) {
        if (toDoitemText == null || toDoitemText.length() == 0) {
            Toast.makeText(getContext(), "Ma'lumot kiritilmadi bo'sh joy", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (pendingItem != null) {
                int position = pending.indexOf(pendingItem);
                pendingItem.setPriority(priority);
                pendingItem.setTitle(toDoitemText);
                pending.set(position, pendingItem);
                recyclerView.getAdapter().notifyItemChanged(position);
            } else {
                pendingItem = new PendingItem();
                pendingItem.setTitle(toDoitemText);
                pendingItem.setPriority(priority);
                pending.add(pendingItem);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    public class TodoPendingAdapter extends RecyclerView.Adapter<TodoPendingAdapter.ViewHolder> {

        View view = null;
        private Context context = getActivity();

        @Override
        public TodoPendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == PEND)
                view = LayoutInflater.from(context).inflate(R.layout.fragment_pending, parent, false);
            else if (viewType == HIGH)
                view = LayoutInflater.from(context).inflate(R.layout.high_item, parent, false);
            else if (viewType==NORMAL)
                view=LayoutInflater.from(context).inflate(R.layout.normal_item,parent,false);
            else if (viewType==LOW)
                view=LayoutInflater.from(context).inflate(R.layout.low_item, parent,false);

            return new TodoPendingAdapter.ViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(TodoPendingAdapter.ViewHolder holder, final int position) {
            switch (getItemViewType(position)) {
                case PEND:
                    holder.pending_text.setText(pending.get(position).getTitle());
                    break;
                case HIGH:
                    holder.high.setText(pending.get(position).getTitle());
                    break;
                case NORMAL:
                    holder.norm.setText(pending.get(position).getTitle());
                    break;
                case LOW:
                    holder.low.setText(pending.get(position).getTitle());
                    break;
            }
            if (getItemViewType(position) == PEND) {
                holder.done_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // int position1 = recyclerView.getChildPosition(view);
//                        db.delete(TaskCont.TaskEntry.TABLE,
//                                TaskCont.TaskEntry._ID + " = ?",
//                                new String[]{"" + pending.get(position).getId()});
                       ((MainActivity)getActivity()).addDoneText(pending.remove(position));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildAndShowIntutDialog(pending.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return pending.size();
        }

        @Override
        public int getItemViewType(int position) {
            return pending.get(position).getPriority();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView pending_text;
            TextView high;
            TextView low;
            TextView norm;

            ImageView done_btn;

            public ViewHolder(View itemView, int viewType) {
                super(itemView);
                // if (viewType==Constants.FIRST_ROW){

                high = (TextView) itemView.findViewById(R.id.high);
                low = (TextView) itemView.findViewById(R.id.low);
                norm = (TextView) itemView.findViewById(R.id.norm);

                //   }else if (viewType==Constants.OTHER_ROW){

                pending_text = (TextView) itemView.findViewById(R.id.pending_text);
                done_btn = (ImageView) itemView.findViewById(R.id.done_btn);
                // }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ContentValues values = new ContentValues();
        for (PendingItem pendingItem : pending) {
            values.put(TaskCont.TaskEntry.TASK_TITLE, pendingItem.getTitle());
            values.put(TaskCont.TaskEntry.PRIORITY, pendingItem.getPriority());
            db.insertWithOnConflict(TaskCont.TaskEntry.TABLE,
                    null, values, SQLiteDatabase.CONFLICT_REPLACE);
            values.clear();
        }
        db.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
