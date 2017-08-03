package com.example.android.ticker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.ticker.data.TickerContract;

/**
 * Created by ashwin on 8/3/17.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder> {

    Context mContext;
    Cursor mCursor;

    @Override
    public PendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.pending_list_task, parent, false);
        PendingViewHolder viewHolder = new PendingViewHolder(view);
        mCursor = mContext.getContentResolver().query(TickerContract.TickerEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PendingViewHolder holder, int position) {
        mCursor.move(position);
        String task = mCursor.getString(mCursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_TASK_NAME));
        holder.taskTextView.setText(task);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null)
            return 0;
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
