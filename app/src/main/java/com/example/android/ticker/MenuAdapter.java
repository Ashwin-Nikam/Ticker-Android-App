package com.example.android.ticker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ashwin on 8/1/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private static String[] menuItems;
    MenuAdapterOnClickHandler mClickHandler;

    public interface MenuAdapterOnClickHandler {
        void onClick(String menuItem);
    }

    public MenuAdapter(MenuAdapterOnClickHandler onClickHandler) {
        mClickHandler = onClickHandler;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.menu_list_item;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        String menuItem = menuItems[position];
        holder.listItemMenuView.setText(menuItem);
    }

    @Override
    public int getItemCount() {
        if(menuItems == null)
            return 0;
        else
            return menuItems.length;
    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView listItemMenuView;

        public MenuViewHolder (View itemView) {
            super(itemView);
            listItemMenuView = (TextView) itemView.findViewById(R.id.tv_menu_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String menuItem = menuItems[adapterPosition];
            mClickHandler.onClick(menuItem);
        }
    }

    public static void setMenuItems(String[] items) {
        menuItems = items;
    }

}
