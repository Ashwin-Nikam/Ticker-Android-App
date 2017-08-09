package com.example.android.ticker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ticker.data.TickerContract;

public class PendingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PendingAdapter mPendingAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pending);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mPendingAdapter = new PendingAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mPendingAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
            | ItemTouchHelper.RIGHT) {

        TextView mPendingTextView;

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Toast.makeText(getBaseContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            mPendingTextView = (TextView) viewHolder.itemView.findViewById(R.id.tv_pending_task);
            String completeTask = mPendingTextView.getText().toString();
            String task = completeTask.split(" ")[0];
            int numDeleted = getContentResolver().delete(TickerContract.TickerEntry.CONTENT_URI,
                    TickerContract.TickerEntry.COLUMN_TASK_NAME+" = '"+task+"'", null);
            Log.i("Deleted rows", Integer.toString(numDeleted));
        }
    };

}
