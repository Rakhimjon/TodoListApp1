package com.doubletechnology.todolistapp;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {
    RecyclerView recyclerView;
    EditText todo_input;


    List<String> pending = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending_recyclerview_fab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        todo_input = (EditText) view.findViewById(R.id.todo_input);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                buildAndShowIntutDialog();
            }


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TodoPendingAdapter());

        return view;
    }

    private void buildAndShowIntutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.todo_enter));

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View dialogView = layoutInflater.inflate(R.layout.alert_dialog, null);
        final EditText todo_input = (EditText) dialogView.findViewById(R.id.todo_input);

        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addTodoItem(todo_input.getText().toString());

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        final AlertDialog dialogInterface = builder.show();
        todo_input.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                keyEvent.getKeyCode() == keyEvent.KEYCODE_ENTER)) {
                    dialogInterface.dismiss();
                    addTodoItem(todo_input.getText().toString());
                    return true;
                }
                return true;

            }

        });


    }

    private void addTodoItem(String toDoitemText) {
        if (toDoitemText == null || toDoitemText.length() == 0) {
            Toast.makeText(getContext(), "Ma'lumot kiritilmadi bo'sh joy", Toast.LENGTH_SHORT).show();
            return;

        } else {
            pending.add(toDoitemText);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    public class TodoPendingAdapter extends RecyclerView.Adapter<TodoPendingAdapter.ViewHolder> {
        private Context context = getActivity();

        @Override
        public TodoPendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_pending, parent, false);

            return new TodoPendingAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoPendingAdapter.ViewHolder holder, final int position) {
            holder.pending_text.setText(pending.get(position));
            holder.done_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // int position1 = recyclerView.getChildPosition(view);
                    MainActivity activity = (MainActivity) getActivity();
                    System.out.println("send " + pending.get(position));
                    activity.addDoneText(pending.remove(position));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return pending.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView pending_text;

            ImageView done_btn;

            public ViewHolder(View itemView) {
                super(itemView);
                pending_text = (TextView) itemView.findViewById(R.id.pending_text);
                done_btn = (ImageView) itemView.findViewById(R.id.done_btn);
            }
        }
    }

}
