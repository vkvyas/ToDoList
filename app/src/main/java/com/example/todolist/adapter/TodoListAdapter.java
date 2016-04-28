package com.example.todolist.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolist.MainActivityFragment;
import com.example.todolist.R;
import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.data.provider.TodoItemContract;

import static com.example.todolist.data.provider.TodoItemContract.TodoItemsColumns;

/**
 * Created by vishal on 29/04/16.
 */
public class TodoListAdapter extends CursorRecyclerViewAdapter<TodoListAdapter.ViewHolder> implements View.OnClickListener {

    private final MainActivityFragment.Callbacks mCallbacks;
    LayoutInflater mInflater;

    public TodoListAdapter(Context context, Cursor cursor, MainActivityFragment.Callbacks callbacks) {
        super(context, cursor);
        mInflater = LayoutInflater.from(context);
        mCallbacks = callbacks;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        TodoItemBean todoItemBean = TodoItemContract.TodoItem.fromCursor(cursor);
        viewHolder.txtTitle.setText(todoItemBean.getTitle());
        viewHolder.txtDescription.setText(todoItemBean.getDescription());
        viewHolder.itemView.setTag(todoItemBean);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.row_todo_item, parent, false), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                mCallbacks.onDeleteClicked((TodoItemBean) ((View) v.getParent()).getTag());
                break;
            case R.id.list_item:
                mCallbacks.onItemClicked((TodoItemBean) v.getTag());
                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;

        public ViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            View btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(onClickListener);
        }
    }
}
