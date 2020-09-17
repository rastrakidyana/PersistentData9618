package com.maderastra.persistentdata9618;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editText;
    private Button addBtn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.input_name);
        addBtn = findViewById(R.id.btn_add);
        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.user_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                refreshLayout.setRefreshing(false);
            }
        });

        getUsers();
    }

    private void addUser(){
        final String name = editText.getText().toString();

        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();
                editText.setText("");
                getUsers();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setFullName(name);

                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .userDAO()
                        .insert(user);
                return null;
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }

    private void getUsers(){
        class GetUsers extends AsyncTask<Void, Void, List<User>>{

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(MainActivity.this, users);
                recyclerView.setAdapter(adapter);
                if (users.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .userDAO()
                        .getAll();
                return userList;
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}