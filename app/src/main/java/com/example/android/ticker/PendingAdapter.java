package com.example.android.ticker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ticker.data.TickerContract;

/**
 * Created by ashwin on 8/3/17.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public PendingAdapter(Context context) {
        mContext = context;
        mCursor = mContext.getContentResolver().query(TickerContract.TickerEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        mCursor.moveToFirst();
    }

    @Override
    public PendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.pending_list_task, parent, false);
        PendingViewHolder viewHolder = new PendingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PendingViewHolder holder, int position) {
        String task = mCursor.getString(mCursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_TASK_NAME));
        String priority = mCursor.getString(mCursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_PRIORITY));
        holder.taskTextView.setText(task+" | "+priority);
        mCursor.moveToNext();
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class PendingViewHolder extends RecyclerView.ViewHolder {

        TextView taskTextView;

        public PendingViewHolder(View itemView) {
            super(itemView);
            taskTextView = (TextView) itemView.findViewById(R.id.tv_pending_task);
        }
    }

}
