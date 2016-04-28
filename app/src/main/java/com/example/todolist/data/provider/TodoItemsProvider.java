package com.example.todolist.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.todolist.data.provider.TodoDatabase.Tables;
import static com.example.todolist.data.provider.TodoItemContract.CONTENT_AUTHORITY;
import static com.example.todolist.data.provider.TodoItemContract.PATH_TODO_ITEMS;
import static com.example.todolist.data.provider.TodoItemContract.TodoItem;

/**
 * Created by vishalvyas on 4/27/16.
 * TodoItem content provider
 */
public class TodoItemsProvider extends ContentProvider {

    private SQLiteDatabase mSqLiteDatabase;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static final int CODE_TODO_ITEMS = 200;
    private static final int CODE_TODO_ITEMS_ID = 201;

    private static final String TAG = "TodoItemsProvider";


    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TODO_ITEMS, CODE_TODO_ITEMS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TODO_ITEMS + "/*", CODE_TODO_ITEMS_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        TodoDatabase mOpenHelper = new TodoDatabase(getContext());
        mSqLiteDatabase = mOpenHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, uri.toString());
        final int code = sUriMatcher.match(uri);
        switch (code) {
            case CODE_TODO_ITEMS:
                return mSqLiteDatabase.query(Tables.TODO_ITEMS, projection, selection, selectionArgs, null, null, sortOrder);
            case CODE_TODO_ITEMS_ID:
                return mSqLiteDatabase.query(Tables.TODO_ITEMS, projection, TodoItem._ID + "=?", new String[]{TodoItem.getTodoId(uri)}, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int code = sUriMatcher.match(uri);
        switch (code) {
            case CODE_TODO_ITEMS:
                return TodoItem.CONTENT_TYPE;
            case CODE_TODO_ITEMS_ID:
                return TodoItem.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, uri.toString() + ", values=" + values.toString());
        final int code = sUriMatcher.match(uri);
        switch (code) {
            case CODE_TODO_ITEMS:
            case CODE_TODO_ITEMS_ID:
                String selection = TodoItem._ID + "=?";
                String selectionArgs[] = new String[]{TodoItem.getTodoId(uri)};

                // UPDATE if the todoitem already exists in database. If not then INSERT
                final Cursor cursor = query(uri, new String[]{TodoItem._ID}, selection, selectionArgs, null);
                if (cursor != null && cursor.getCount() > 0) {
                    Log.d(TAG, "Todoitem exists. Updating");
                    final int update = update(uri, values, selection, selectionArgs);
                    Log.d(TAG, "Todoitem exists. Rows affected " + update);
                } else {
                    final long id = mSqLiteDatabase.insertOrThrow(Tables.TODO_ITEMS, null, values);
                }
                notifyChange(uri);
                return TodoItem.buildTodoItemUri(values.getAsString(TodoItem._ID));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delete = mSqLiteDatabase.delete(Tables.TODO_ITEMS
                , TodoItem._ID + "=?"
                , new String[]{TodoItem.getTodoId(uri)});
        notifyChange(uri);
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mSqLiteDatabase.update(Tables.TODO_ITEMS, values, selection, selectionArgs);
    }

    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
