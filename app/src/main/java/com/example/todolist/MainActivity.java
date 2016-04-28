package com.example.todolist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.service.TodoIntentService;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callbacks {

    private static final String TAG = "MainActivity";
    private static final String FRAGMENT_MAIN_TAG = "fragment_main_tag";
    FragmentManager mFragmentManager;

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
                TodoIntentService.startActionAddTodoItem(getApplicationContext(), todoItemBean);
            }
        });
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            final FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.contentMain, mainActivityFragment, FRAGMENT_MAIN_TAG);
            fragmentTransaction.commit();
            Log.d(TAG, "added fragment");
        }
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

    @Override
    public void onDeleteClicked(TodoItemBean todoItemBean) {
        Log.d(TAG, todoItemBean.getId() + " " + todoItemBean.getDescription());
    }

    @Override
    public void onItemClicked(TodoItemBean todoItemBean) {
        Log.d(TAG, todoItemBean.getId() + " " + todoItemBean.getDescription());
    }
}
