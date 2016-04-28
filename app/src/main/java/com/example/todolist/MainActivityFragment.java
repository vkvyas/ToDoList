package com.example.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.adapter.DividerItemDecoration;
import com.example.todolist.adapter.TodoListAdapter;
import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.data.provider.TodoItemContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TODO_LIST_LOADER = 0;
    private RecyclerView mRecyclerView;
    private TodoListAdapter mTodoListAdapter;

    Callbacks mCallbacks;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(TODO_LIST_LOADER, null, this);
        mCallbacks = (Callbacks) getActivity();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
//        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        switch (loaderID) {
            case TODO_LIST_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),
                        TodoItemContract.TodoItem.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mTodoListAdapter == null) {
            mTodoListAdapter = new TodoListAdapter(getContext(), data, mCallbacks);
            mRecyclerView.setAdapter(mTodoListAdapter);
        } else {
            mTodoListAdapter.swapCursor(data);
            mTodoListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface Callbacks {
        public void onDeleteClicked(TodoItemBean todoItemBean);

        public void onItemClicked(TodoItemBean todoItemBean);

    }
}
