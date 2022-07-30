package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleIremToucHolder extends ItemTouchHelper.SimpleCallback {


    private TodoAdapter adapter;

    public RecycleIremToucHolder(TodoAdapter adapter) {

        super ( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        this.adapter = adapter;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition ();
        if (direction == ItemTouchHelper.LEFT) {

        AlertDialog.Builder builder = new AlertDialog.Builder ( adapter.getContext () );
        builder.setTitle ( "Delete  Task" );
        builder.setMessage ( "Are you sure you to delete yhis Task" );
        builder.setPositiveButton ( "Confim",
                new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        adapter.deleteItem ( position );
                    }
                } );
        builder.setNegativeButton ( R.string.cancel, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyItemChanged ( viewHolder.getAdapterPosition () );


            }
        } );
        AlertDialog dialog = builder.create ();
        dialog.show ();

    }else

    {

        adapter.editItem (position);
    }
}

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );


        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;

        int backgroundCornerOffset = 20;

        if(dX > 0){

            icon = ContextCompat.getDrawable ( adapter.getContext (), R.drawable.ic_baseline_edit_24 );

            background = new ColorDrawable (ContextCompat.getColor ( adapter.getContext (), R.color.design_default_color_primary ));

        }else {
            icon = ContextCompat.getDrawable ( adapter.getContext (), R.drawable.ic_baseline_delete_24 );

            background = new ColorDrawable (ContextCompat.getColor ( adapter.getContext (), R.color.teal_200 ));


        }

        int iconMargin = (itemView.getHeight () - icon.getIntrinsicHeight ()) / 2;
        int iconTop = itemView.getTop () + (itemView.getHeight () - icon.getIntrinsicHeight ()) / 2;
        int iconBotton = iconTop + icon.getIntrinsicHeight ();

        if (dX > 0){ //swiping to the right

            int iconLeft = itemView.getLeft () + iconMargin;
            int iconRight = itemView.getLeft () + iconMargin + icon.getIntrinsicWidth ();
            icon.setBounds ( iconLeft, iconTop, iconRight, iconBotton);

            background.setBounds ( itemView.getLeft (), itemView.getTop (), itemView.getLeft () + ((int) dX) + backgroundCornerOffset, itemView.getBottom () );


        }
        else if (dX < 0){// swiping to the left
            int iconLeft = itemView.getRight () - iconMargin - icon.getIntrinsicWidth ();
            int iconRight = itemView.getRight () - iconMargin;
            icon.setBounds ( iconLeft, iconTop, iconRight, iconBotton );

            background.setBounds ( itemView.getRight () + ((int) dX) - backgroundCornerOffset, itemView.getTop (),
                    itemView.getRight (), itemView.getBottom ());



        }else {

            background.setBounds ( 0,0,0,0 );
        }
        background.draw ( c );
        icon.draw ( c );

    }
}
