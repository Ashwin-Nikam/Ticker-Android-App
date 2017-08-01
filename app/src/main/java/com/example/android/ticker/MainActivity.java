package com.example.android.ticker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MenuAdapter.MenuAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MenuAdapter mMenuAdapter;
    public String menuItems[] = {"Pending", "Done"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuAdapter.setMenuItems(this.menuItems);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mMenuAdapter = new MenuAdapter(this);
        mRecyclerView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onClick(String menuItem) {
        Toast.makeText(this, menuItem, Toast.LENGTH_SHORT).show();
    }


}
