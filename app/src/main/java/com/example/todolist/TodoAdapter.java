package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.DataBase.DataBaseHolder;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {


    private List<TodoModel> taskList;
    private MainActivity activity;
    private DataBaseHolder db;

    public TodoAdapter(DataBaseHolder db, MainActivity activity){
        this.db = db;
        this.activity = activity;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.task_layout, parent, false );

        return new ViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        db.openDatabase ();
        TodoModel item = taskList.get ( position );
        holder.checkBox.setText ( item.getTask () );
        holder.checkBox.setChecked ( toBoolean ( item.getStatus () ) );

        holder.checkBox.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    db.updateStatus ( item.getId (), 1 );
                }else {
                    db.updateStatus ( item.getId (), 0 );
                }
            }
        } );


    }

    private boolean toBoolean(int n){
        return n != 0;
    }
    @Override
    public int getItemCount() {
        return  taskList.size ();
    }

    public void setTasks(List<TodoModel> taskList){

        this.taskList = taskList;
        notifyDataSetChanged ();

    }
    public Context getContext(){

        return activity;
    }

    //delete
    public void deleteItem(int position){

        TodoModel item = taskList.get ( position );
        db.deldteTask ( item.getId () );
        taskList.remove ( position );
        notifyItemRemoved ( position );
    }

    public void editItem(int position){

        TodoModel  item = taskList.get ( position );
        Bundle bundle = new Bundle ();
        bundle.putInt ( "id", item.getId () );
        bundle.putString ( "task", item.getTask () );
        AddNewTask fragment = new AddNewTask ();
        fragment.setArguments ( bundle );
        fragment.show ( activity.getSupportFragmentManager (), AddNewTask.TAG );
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );

            checkBox = itemView.findViewById ( R.id.taskcheckbox );
        }
    }
}
