package com.example.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.service.TodoIntentService;

public class NewTodoActivity extends AppCompatActivity {

    EditText txtTitle, txtDescription;
    public static final String EXTRA_TODO_ITEM = "com.example.todolist.service.extra.TODO_ITEM";

    TodoItemBean mTodoItemBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        final Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("Add new to-do");
        } else {
            setTitle("Update to-do");
            mTodoItemBean = extras.getParcelable(EXTRA_TODO_ITEM);
            txtTitle.setText(mTodoItemBean.getTitle());
            txtDescription.setText(mTodoItemBean.getDescription());
        }
    }

    private boolean save() {
        if (!validate()) {
            return false;
        }
        if (mTodoItemBean == null) {
            add();
        } else {
            update();
        }
        return true;
    }

    // Click listener
    public void save(View view) {
        if (save()) {
            finish();
        }
    }

    // Click listener
    public void saveAndContinue(View view) {
        if (save()) {
            txtTitle.setText(null);
            txtDescription.setText(null);
        }
    }

    void add() {
        TodoItemBean todoItemBean = new TodoItemBean();
        todoItemBean.setTitle(txtTitle.getText().toString());
        todoItemBean.setDescription(txtDescription.getText().toString());
        todoItemBean.setCreatedDate(System.currentTimeMillis());
        todoItemBean.setUpdatedDate(System.currentTimeMillis());
        TodoIntentService.startActionAddTodoItem(getApplicationContext(), todoItemBean);
    }

    boolean validate() {
        if (isEmpty(txtTitle)) {
            txtTitle.setError("Please add a valid title!");
            return false;
        }
        return true;
    }

    boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }

    private void update() {
        mTodoItemBean.setTitle(txtTitle.getText().toString());
        mTodoItemBean.setDescription(txtDescription.getText().toString());
        mTodoItemBean.setUpdatedDate(System.currentTimeMillis());
        TodoIntentService.startActionUpdateTodoItem(getApplicationContext(), mTodoItemBean);
    }

}
