package com.drofjike.logincoloragecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginField = (EditText)findViewById(R.id.loginEditText);
        passwordField = (EditText)findViewById(R.id.passwordEditText);
        submitButton = (Button)findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =  loginField.getText().toString();
                String passw = passwordField.getText().toString();
                logIn(name, passw);
            }
        });
    }

    private void logIn(String name, String passw) {
        if(!name.equals(passw)) {
            passwordField.setText("");
            Toast.makeText(MainActivity.this, "Read manual, " + name, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Kewl, " + name, Toast.LENGTH_LONG).show();
        }

    }
}
