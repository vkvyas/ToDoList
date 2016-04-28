/*
 * Copyright 2015.  Emin Yahyayev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.todolist.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;

import static com.example.todolist.data.provider.TodoItemContract.TodoItemsColumns;

/**
 * Created by vishalvyas on 4/27/16.
 * TodoItem content provider
 */
final class TodoDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "todolist.db";
    private static final int DB_VERSION = 1;

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");

    private final Context mContext;

    interface Tables {
        String TODO_ITEMS = "todo_items";
    }

    public TodoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.TODO_ITEMS + "("
                + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + TodoItemsColumns.TITLE + " TEXT NOT NULL,"
                + TodoItemsColumns.DESCRIPTION + " TEXT NOT NULL,"
                + TodoItemsColumns.CREATED_DATE + " INTEGER,"
                + TodoItemsColumns.UPDATED_DATE + " INTEGER,"
                + TodoItemsColumns.IS_DELETED + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }
}