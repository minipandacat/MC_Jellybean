package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.db.AppDatabase;
import com.example.test.db.User;

import java.util.List;


public class createAccount extends AppCompatActivity {
    private UserListAdapter userListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText firstNameInput = findViewById(R.id.firstNameInput);
        final EditText lastNameInput = findViewById(R.id.lastNameInput);
        final EditText emailAddressInput = findViewById(R.id.emailAddressInput);
        final EditText confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        final EditText userPasswordInput = findViewById(R.id.userPasswordInput);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstNameInput.getText().toString().isEmpty()) {
                    Toast.makeText(createAccount.this, "First Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(lastNameInput.getText().toString().isEmpty()) {
                    Toast.makeText(createAccount.this, "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(emailAddressInput.getText().toString().isEmpty()) {
                    Toast.makeText(createAccount.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (userPasswordInput.getText().toString().isEmpty()) {
                    Toast.makeText(createAccount.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!userPasswordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
                    Toast.makeText(createAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    saveNewUser(firstNameInput.getText().toString(), lastNameInput.getText().toString(),emailAddressInput.getText().toString(),userPasswordInput.getText().toString());
                    Toast.makeText(createAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                }
            }

            private void saveNewUser(String firstName, String lastName, String email, String password) {
                AppDatabase db  = AppDatabase.getDbInstance(getApplicationContext());

                User user = new User();
                user.firstName = firstName;
                user.lastName = lastName;
                user.email = email;
                user.password = password;

                db.userDao().insertUser(user);

                finish();
            } });

        initRecyclerView();
        loadUserList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this);
        recyclerView.setAdapter(userListAdapter);
    }
    private void loadUserList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList =db.userDao().getAllUsers();
        userListAdapter.setUserList(userList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadUserList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
