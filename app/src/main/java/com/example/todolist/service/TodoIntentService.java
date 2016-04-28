package com.example.todolist.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.data.provider.TodoItemContract;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TodoIntentService extends IntentService {

    private static final String ACTION_ADD_TODO_ITEM = "com.example.todolist.service.action.todoitem.add";
    private static final String ACTION_DELETE_TODO_ITEM = "com.example.todolist.service.action.todoitem.delete";
    private static final String ACTION_UPDATE_TODO_ITEM = "com.example.todolist.service.action.todoitem.update";

    private static final String EXTRA_TODO_ITEM = "com.example.todolist.service.extra.TODO_ITEM";
    private static final String TAG = "Service";

    public TodoIntentService() {
        super("TodoIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionAddTodoItem(Context context, TodoItemBean todoItemBean) {
        Intent intent = new Intent(context, TodoIntentService.class);
        intent.setAction(ACTION_ADD_TODO_ITEM);
        intent.putExtra(EXTRA_TODO_ITEM, todoItemBean);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpdateTodoItem(Context context, TodoItemBean todoItemBean) {
        Intent intent = new Intent(context, TodoIntentService.class);
        intent.setAction(ACTION_ADD_TODO_ITEM);
        intent.putExtra(EXTRA_TODO_ITEM, todoItemBean);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDeleteTodoItem(Context context, TodoItemBean todoItemBean) {
        Intent intent = new Intent(context, TodoIntentService.class);
        intent.setAction(ACTION_DELETE_TODO_ITEM);
        intent.putExtra(EXTRA_TODO_ITEM, todoItemBean);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final TodoItemBean todoItemBean = intent.getParcelableExtra(EXTRA_TODO_ITEM);
            if (ACTION_ADD_TODO_ITEM.equals(action)) {
                handleActionAddTodoItem(todoItemBean);
            } else if (ACTION_DELETE_TODO_ITEM.equals(action)) {
                handleActionDeleteTodoItem(todoItemBean);
            } else if (ACTION_UPDATE_TODO_ITEM.equals(action)) {
                handleActionUpdateTodoItem(todoItemBean);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAddTodoItem(TodoItemBean todoItemBean) {
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

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeleteTodoItem(TodoItemBean todoItemBean) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateTodoItem(TodoItemBean todoItemBean) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}