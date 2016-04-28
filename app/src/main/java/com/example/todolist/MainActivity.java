package com.example.todolist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.data.provider.TodoItemContract;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoItemBean todoItemBean = new TodoItemBean();
                todoItemBean.setTitle("Test Title");
                todoItemBean.setDescription("Description");
                todoItemBean.setCreatedDate(System.currentTimeMillis());
                ContentResolver contentResolver = getContentResolver();
                Uri insert = contentResolver.insert(TodoItemContract.TodoItem.CONTENT_URI, TodoItemContract.TodoItem.toContentValues(todoItemBean));
                assert insert != null;
                Log.d(TAG, insert.toString());

                Cursor query = contentResolver.query(TodoItemContract.TodoItem.CONTENT_URI, null, null, null, null);
                while (query.moveToNext()) {
                    Log.d(TAG, query.getString(query.getColumnIndex(TodoItemContract.TodoItemsColumns.TITLE)));
                    Log.d(TAG, query.getString(query.getColumnIndex(TodoItemContract.TodoItemsColumns.DESCRIPTION)));
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
