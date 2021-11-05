package com.example.myappmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText txtUser, txtPass;
    private Button btnSignIn;
    private FirebaseAuth auth;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        txtUser = findViewById(R.id.txtUserName);
        txtPass = findViewById(R.id.txtPassword);
        btnSignIn= findViewById(R.id.btn_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email",  "public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Lấy access token sử dụng LoginResult
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
            private void useLoginInformation(AccessToken accessToken) {
                /**
                 Creating the GraphRequest to fetch user details
                 1st Param - AccessToken
                 2nd Param - Callback (which will be invoked once the request is successful)
                 **/
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    //OnCompleted is invoked once the GraphRequest is successful
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                            //Sử dụng thông tin lấy được
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // We set parameters to the GraphRequest using a Bundle.
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.width(200)");
                request.setParameters(parameters);
                // Initiate the GraphRequest
                request.executeAsync();
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtUser.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(com.example.myappmusic.Login.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(com.example.myappmusic.Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(com.example.myappmusic.Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    if(pass.length()<6){
                                        Toast.makeText(com.example.myappmusic.Login.this, "Enter Pass", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(com.example.myappmusic.Login.this, "Sign in failed,check email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Intent intent = new Intent(com.example.myappmusic.Login.this,List_Music.class);
                                    startActivity(intent);
                                }

                            }
                        }) ;

            }
        });
    }
}