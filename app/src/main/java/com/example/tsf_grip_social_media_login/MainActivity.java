package com.example.tsf_grip_social_media_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView t1;
    private LoginButton b;
    private CallbackManager c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.t1);
        b = findViewById(R.id.b);
        c = CallbackManager.Factory.create();

        b.setPermissions(Arrays.asList("email", "user_birthday"));
        b.registerCallback(c, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        c.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    AccessTokenTracker t = new AccessTokenTracker() {

        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                t1.setText("");
                Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();

            } else {
                loaduserProfile(currentAccessToken);
            }
        }
    };

    private void loaduserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(new GraphRequest.GraphJSONObjectCallback() {
                         @Override
                               public void onCompleted(JSONObject object, GraphResponse response) {
                                                if (object != null) {
                          try {
                                 String email = object.getString("email");
                                 String id = object.getString("id");
                                            t1.setText(email);
                          } catch (JSONException ex) {
                              ex.printStackTrace();
                          } }
                         }
                         },
                new AccessToken);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }


}
