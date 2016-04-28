package com.example.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todolist.data.models.TodoItemBean;
import com.example.todolist.service.TodoIntentService;

public class NewTodoActivity extends AppCompatActivity {

    EditText txtTitle, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
    }

    public void save(View view) {
        add();
        finish();
    }

    public void saveAndContinue(View view) {
        add();
        txtTitle.setText(null);
        txtDescription.setText(null);
    }

    void add() {
        TodoItemBean todoItemBean = new TodoItemBean();
        todoItemBean.setTitle(txtTitle.getText().toString());
        todoItemBean.setDescription(txtDescription.getText().toString());
        todoItemBean.setCreatedDate(System.currentTimeMillis());
        TodoIntentService.startActionAddTodoItem(getApplicationContext(), todoItemBean);
    }
}
