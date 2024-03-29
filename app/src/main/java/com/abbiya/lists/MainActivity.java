package com.abbiya.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private ItemViewModel mItemViewModel;
    public static final String PARENT_ID = "parent_id";

    final ItemAdapter adapter = new ItemAdapter();

    Integer parentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        if (i != null && i.getExtras() != null) {
            int parentID = i.getExtras().getInt(PARENT_ID);
            if (parentID != 0) {
                this.parentID = new Integer(parentID);
            }
        } else {
            this.parentID = null;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        adapter.setViewModel(mItemViewModel);

        populateList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void populateList() {
        if (parentID == null) {
            mItemViewModel.getRoots().observe(this, new Observer<PagedList<Item>>() {
                @Override
                public void onChanged(@Nullable PagedList<Item> items) {
                    adapter.submitList(items);
                }
            });
        } else {
            mItemViewModel.getChildren(parentID).observe(this, new Observer<PagedList<Item>>() {
                @Override
                public void onChanged(@Nullable PagedList<Item> items) {
                    adapter.submitList(items);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                mItemViewModel.search(parentID, s.trim()).observe(MainActivity.this, new Observer<PagedList<Item>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Item> items) {
                        adapter.submitList(items);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.trim().isEmpty()){
                    populateList();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String content = data.getStringExtra(NewItemActivity.EXTRA_REPLY);

            Date d = new Date();

            Item item = new Item();
            item.setContent(content);
            if (parentID != null) {
                Log.e(this.getClass().getSimpleName(), "hello" + parentID.toString());
                item.setParentId(parentID);
            }
            item.setCreatedAt(d);
            item.setUpdatedAt(d);

            mItemViewModel.insert(item);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
