package com.example.android.ticker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ticker.data.TickerContract;

/**
 * Created by ashwin on 8/1/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public MenuAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, parent, false);
        MenuAdapter.MenuViewHolder viewHolder = new MenuAdapter.MenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MenuViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String task = mCursor.getString(mCursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_TASK_NAME));
        int priority = mCursor.getInt(mCursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_PRIORITY));
        GradientDrawable gradientDrawable = (GradientDrawable)holder.priorityView.getDrawable();
        switch (priority) {
            case 1:
                int highColor = mContext.getResources().getColor(R.color.high_priority_color);
                gradientDrawable.setColor(highColor);
                break;
            case 2:
                int mediumColor = mContext.getResources().getColor(R.color.medium_priority_color);
                gradientDrawable.setColor(mediumColor);
                break;
            case 3:
                int lowColor = mContext.getResources().getColor(R.color.low_priority_color);
                gradientDrawable.setColor(lowColor);
                break;
            default:
                break;
        }
        holder.taskTextView.setText(task);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null)
            return 0;
        else
            return mCursor.getCount();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView taskTextView;
        ImageView  priorityView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.tv_menu_item);
            priorityView = itemView.findViewById(R.id.priorityView);
        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

}
