package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.DataBase.DataBaseHolder;
import com.example.todolist.DataBase.DialogCloseListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView recyclerView;

    private TodoAdapter adapter;

    private List<TodoModel> taskList;

    private DataBaseHolder  db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        getSupportActionBar ().hide ();

        db = new DataBaseHolder ( this );
        db.openDatabase ();

        taskList = new ArrayList<> ();

        recyclerView = (RecyclerView) findViewById ( R.id.taskRecyclerView );

        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        adapter = new TodoAdapter ( db,this );

        recyclerView.setAdapter ( adapter );

        TodoModel task = new TodoModel ( );

        fab = findViewById ( R.id.fab );


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( new RecycleIremToucHolder ( adapter ) );

        itemTouchHelper.attachToRecyclerView (recyclerView );

//        task.setTask ( "This is Test Task" );
//        task.setStatus ( 0 );
//        task.setId ( 1 );
//
//        taskList.add ( task );
//        taskList.add ( task );
//        taskList.add ( task );
//        taskList.add ( task );
//        taskList.add ( task );
//        taskList.add ( task );
//
//        adapter.setTasks ( taskList );

        taskList = db.getAllTost ();
        Collections.reverse ( taskList );
        adapter.setTasks ( taskList );


        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance ().show ( getSupportFragmentManager (), AddNewTask.TAG );
            }
        } );

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTost ();
        Collections.reverse (taskList);
        adapter.setTasks ( taskList );
        adapter.notifyDataSetChanged ();
    }
}