package com.example.android.ticker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ticker.data.TickerContract;
import com.example.android.ticker.utilities.NotificationUtils;
import com.example.android.ticker.utilities.ReminderUtilities;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private MenuAdapter mMenuAdapter;
    private TextView mNoTasksTextView;

    private static final int LOADER_ID = 7;
    private int mPosition = RecyclerView.NO_POSITION;

    public static final String[] MAIN_TASK_PROJECTION = {
            TickerContract.TickerEntry.COLUMN_TASK_NAME,
            TickerContract.TickerEntry.COLUMN_PRIORITY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoTasksTextView = (TextView) findViewById(R.id.noTasks_textView);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mMenuAdapter = new MenuAdapter(this);
        mRecyclerView.setAdapter(mMenuAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        getSupportLoaderManager().initLoader(LOADER_ID,
                null, this);

        ReminderUtilities.scheduleTaskReminder(this);

    }

    /*
    -----------------------------------------
    NewTaskActivity started when FloatingActionButton is clicked.
    -----------------------------------------
     */

    public void startNewTaskActivity(View view) {
        Intent newTaskIntent = new Intent(this, NewTaskActivity.class);
        startActivity(newTaskIntent);
    }

    ItemTouchHelper.SimpleCallback simpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                    | ItemTouchHelper.RIGHT) {

        TextView mPendingTextView;

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        /*
        --------------------------------------------------
        Code to delete the swiped task from content provider
        After the task is deleted the adapter calls the notifyItemRemoved method in order to reload the
        Recycler view.
        ----------------------------------------------------
        */

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Toast.makeText(getBaseContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            mPendingTextView = viewHolder.itemView.findViewById(R.id.tv_menu_item);
            String completeTask = mPendingTextView.getText().toString();
            String task = completeTask.split("-")[0];
            getContentResolver().delete(TickerContract.TickerEntry.CONTENT_URI,
                    TickerContract.TickerEntry.COLUMN_TASK_NAME + " = '" + task + "'", null);
            mMenuAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    /*
        --------------------------------------------------
        Implemented cursor-loader
        1. onCreate returns a new CursorLoader when provided with a
        content_uri, projection and a sort order.
        2. onLoadFinished swaps the cursor in MenuAdapter so the MenuAdapter gets
        cursor containing the data and it then populates the RecyclerView.
        ----------------------------------------------------
        */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(this,
                        TickerContract.TickerEntry.CONTENT_URI,
                        MAIN_TASK_PROJECTION,
                        null,
                        null,
                        TickerContract.TickerEntry.COLUMN_PRIORITY);
            default:
                throw new RuntimeException("Loader not implemented "+id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int numberOfTasks = data.getCount();
        if(numberOfTasks > 0) {
            mNoTasksTextView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mMenuAdapter.swapCursor(data); //This swapping is done for populating the RecyclerView
            if(mPosition == RecyclerView.NO_POSITION)
                mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mNoTasksTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMenuAdapter.swapCursor(null);
    }

    //----------------------------------------------------------------------------------------------

}