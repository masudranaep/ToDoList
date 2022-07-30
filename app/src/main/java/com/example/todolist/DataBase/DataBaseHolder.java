package com.example.todolist.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHolder extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabse";
    private static final String TODO_TABLE = "Todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + TASK + " TEXT, " + STATUS + "INTEGER)";


    private SQLiteDatabase db;

    public DataBaseHolder(@Nullable Context context) {
        super ( context, NAME, null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL ( CREATE_TODO_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL ( "DROP TABLE IF EXISTS " + TODO_TABLE );

        onCreate ( db );

    }

    public void openDatabase(){

        db = this.getWritableDatabase ();
    }

    public void insertTask(TodoModel task){

        ContentValues values = new ContentValues ();
        values.put (TASK, task.getTask () );
        values.put ( STATUS, 0 );
        db.insert ( TODO_TABLE, null, values );

    }

    @SuppressLint("Range")
    public List<TodoModel> getAllTost(){

        List<TodoModel> taskList = new ArrayList<> ();
        Cursor cursor = null;

        db.beginTransaction ();

        try{
                cursor = db.query ( TODO_TABLE, null, null, null, null, null, null, null );
                if (cursor != null){

                    if(cursor.moveToFirst ()){
                        do{

                            TodoModel task = new TodoModel ();

                            task.setId ( cursor.getInt ( cursor.getColumnIndex ( ID ) ) );
                            task.setTask ( cursor.getString ( cursor.getColumnIndex ( TASK ) ) );
                            task.setStatus ( cursor.getInt ( cursor.getColumnIndex ( STATUS ) ) );
                            taskList.add(task);
                        }while (cursor.moveToNext ());
                    }
                }
        }
        finally {

            db.endTransaction ();
            cursor.close ();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){

        ContentValues values = new ContentValues ();
        values.put ( STATUS, status );
        db.update ( TODO_TABLE, values, ID + "=?", new String[]{String.valueOf ( id )} );

    }

    public final void updateTask(int id, String task){

        ContentValues values = new ContentValues ();
        values.put ( TASK, task );

        db.update ( TODO_TABLE, values, ID+ "=?", new String[]{String.valueOf (  id)} );

    }

    public  void deldteTask(int id){

        db.delete ( TODO_TABLE, ID+ "=?", new String[] {String.valueOf (  id)});
    }
}
