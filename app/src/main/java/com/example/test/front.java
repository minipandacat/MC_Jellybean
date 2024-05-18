package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.db.AppDatabase;
import com.example.test.db.User;
import com.example.test.db.UserDao;

import java.util.List;

public class front extends AppCompatActivity {
    private UserDao userDao;
    private EditText loginEmail;
    private EditText loginPassword;
    private  UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front);
       /*  AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        userDao = db.userDao(); */

        Button createAccountButton1 = findViewById(R.id.createAccountButton1);

        createAccountButton1.setOnClickListener(v -> {
            Intent intent = new Intent(front.this, createAccount.class);
            startActivity(intent);
        });
        final EditText loginEmail = findViewById(R.id.loginEmail);
        final EditText loginPassword = findViewById(R.id.loginPassword);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(v -> {

            String email = loginEmail.getText().toString();

            String password = loginPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(front.this, "Email address and password are required.", Toast.LENGTH_SHORT).show();
            } else{
                AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
                userDao = db.userDao();

                User user = userDao.getUser(email, password);
                if (user != null){
                    // Sign in successful
                    Intent intent = new Intent(front.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    // Sign in failed
                    Toast.makeText(front.this, "Email address or password is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }

            /*
            Too many issues; rewriting
                if (!TextUtils.isEmpty(email1) && !TextUtils.isEmpty(password1)) {
                        // Check if user exists by email
                        User user = userDao.findByEmail(email);
                        if (user != null && user.getPassword(password).equals(password)) {
                            // Sign in successful
                            Intent intent = new Intent(front.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign in failed
                            Toast.makeText(front.this, "Email address or password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Display error message if email or password is empty
                        Toast.makeText(front.this, "Email address and password are required.", Toast.LENGTH_SHORT).show();
                    }*/
        });
        //initRecyclerView();
        //loadUserList();
    }

    /* Moved to CreateAccount Activity

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
*/

}
