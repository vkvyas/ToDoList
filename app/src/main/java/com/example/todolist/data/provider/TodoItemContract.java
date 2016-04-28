package com.example.todolist.data.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.todolist.data.models.TodoItemBean;

/**
 * Created by vishalvyas on 4/27/16.
 * TodoItem content provider
 */
public final class TodoItemContract {

    public interface TodoItemsColumns {
        String TITLE = "title";
        String DESCRIPTION = "description";
        String CREATED_DATE = "created_date";
        String UPDATED_DATE = "updated_date";
        String IS_DELETED = "is_deleted";
    }

    public static final String CONTENT_AUTHORITY = "com.example.todolist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TODO_ITEMS = "todoitems";

    public static class TodoItem implements TodoItemsColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODO_ITEMS).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.todolist.todo";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.todolist.todo";

        /**
         * Default "ORDER BY" clause.
         */
        public static final String DEFAULT_SORT = BaseColumns._ID + " DESC";

        /**
         * Build {@link Uri} for requested {@link #_ID}.
         */
        public static Uri buildTodoItemUri(String pkTodoId) {
            return CONTENT_URI.buildUpon().appendPath(pkTodoId).build();
        }

        /**
         * Read {@link #_ID} from {@link TodoItem} {@link Uri}.
         */
        public static String getTodoId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static ContentValues toContentValues(TodoItemBean todoItemBean) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoItem.TITLE, todoItemBean.getTitle());
            contentValues.put(TodoItem.DESCRIPTION, todoItemBean.getDescription());
            contentValues.put(TodoItem.CREATED_DATE, todoItemBean.getCreatedDate());
            contentValues.put(TodoItem.UPDATED_DATE, todoItemBean.getUpdatedDate());
            contentValues.put(TodoItem.IS_DELETED, todoItemBean.isDeleted());
            return contentValues;
        }

        public static TodoItemBean fromCursor(Cursor cursor) {
            TodoItemBean todoItemBean = new TodoItemBean();
            todoItemBean.setId(cursor.getString(cursor.getColumnIndex(_ID)));
            todoItemBean.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            todoItemBean.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            todoItemBean.setCreatedDate(cursor.getLong(cursor.getColumnIndex(CREATED_DATE)));
            todoItemBean.setUpdatedDate(cursor.getLong(cursor.getColumnIndex(UPDATED_DATE)));
            todoItemBean.setDeleted(cursor.getInt(cursor.getColumnIndex(IS_DELETED)));
            return todoItemBean;
        }
    }

    private TodoItemContract() {
        throw new AssertionError("No instances.");
    }
}