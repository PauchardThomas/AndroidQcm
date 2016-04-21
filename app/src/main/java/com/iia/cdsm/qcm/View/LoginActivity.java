package com.iia.cdsm.qcm.View;

import android.content.Entity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.cdsm.qcm.Entity.User;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.UserWSAdapter;

import org.json.JSONException;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

     EditText etUsername, etPassword;
     Button btConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Get items from view
         */
        etUsername = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btConnexion = (Button) findViewById(R.id.btConnexion);


        /**
         * Click on connexion button
         */
        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(0, null, null, 0);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                AsyncTask task = new HttpAsyncTask().execute(username, password);

                try {
                    Object resultTask = task.get();
                    user = (User) resultTask;
                    if (user != null) {

                        Intent i = new Intent(LoginActivity.this,ListCategoriesActivity.class);
                        i.putExtra(User.SERIAL,user);
                        startActivity(i);

                    }else {
                        Toast.makeText(getBaseContext(), "Identifiants incorrects", Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... args) {
            User user = new User(0, null, null, 0);
            user.setUsername(args[0]);
            user.setPassword(args[1]);
            String response = null;
            try {

                user = (UserWSAdapter.Login(user, LoginActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return user;

        }

        @Override
        protected void onPostExecute(User user) {

        }
    }
}
