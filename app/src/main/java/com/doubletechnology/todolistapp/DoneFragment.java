package com.doubletechnology.todolistapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> done = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.done_recyclerview, container, false);

        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TodoDoneAdapter());
        return view;
    }

    public void addString(String s) {

        System.out.println("get " + s);
        done.add(s);

        System.out.println("size " + done.size());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public class TodoDoneAdapter extends RecyclerView.Adapter<TodoDoneAdapter.ItemViewHolder>{
        private Context context= getActivity();

        @Override
        public TodoDoneAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_done,parent,false);

            return new TodoDoneAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TodoDoneAdapter.ItemViewHolder holder, int position) {
            holder.done_text.setText(done.get(position));

        }

        @Override
        public int getItemCount() {
            return done.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView done_text;


            public ItemViewHolder(View itemView) {
                super(itemView);

                done_text= (TextView)itemView.findViewById(R.id.done_text);
            }
        }
    }



}
