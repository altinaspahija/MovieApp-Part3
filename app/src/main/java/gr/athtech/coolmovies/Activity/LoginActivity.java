package gr.athtech.coolmovies.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;


import java.util.HashMap;
import java.util.Map;

import gr.athtech.coolmovies.R;

public class LoginActivity extends AbstractActivity {

    EditText username;
    TextInputLayout password;
    Button btnLogin, btnCancel;
    String correct_username = "altina";
    String correct_password = "As.2021";
    // Get a request token - https://api.themoviedb.org/3/authentication/token/new?api_key=<<api_key>>
    String correct_token = "25b26d5d5af3b8bf8301391d37a3aea906d1b16f";
    // then get or ask for permission - https://www.themoviedb.org/authenticate/{token}
    // then post username, password and the token for Login
    String URLLine = "https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=40219592510d78632bfd917982326cc7";

    @Override
    int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    void initLayout() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login_button);
        btnCancel = findViewById(R.id.cancel_button);

        btnLogin.setOnClickListener(v -> {

            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getEditText().getText().toString())) {
                Toast.makeText(LoginActivity.this, "Username/password are not provided", Toast.LENGTH_LONG).show();
            } else if (username.getText().toString().equals(correct_username) && password.getEditText().getText().toString().equals(correct_password)) {
                postDataLogin();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
            }
            });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }

    public void postDataLogin() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, URLLine,
                response -> {
                    // response
                    Log.d("Response", response);
                    Toast.makeText(LoginActivity.this, "Successfully login", Toast.LENGTH_LONG).show();
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                },

                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(LoginActivity.this, "Invalid Token", Toast.LENGTH_LONG).show();
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Add your parameters here as key-value pairs
                params.put("username", correct_username);
                params.put("password", correct_password);
                params.put("request_token", correct_token);
                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    void runOperations() {

    }

    @Override
    void stopOperations() {

    }

}

