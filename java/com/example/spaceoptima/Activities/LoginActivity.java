package com.example.spaceoptima.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.spaceoptima.Dashboard;
import com.example.spaceoptima.ForgotPassword;
import com.example.spaceoptima.Main;
import com.example.spaceoptima.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout email, password;
    Button login,create, forgotPassword;
    Boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.btnLogin);
        create = findViewById(R.id.btnCreate);
        forgotPassword = findViewById(R.id.forgot_Password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail(email);
                validatePassword(password);

                Log.d("TAG", "onClick: " + email.getEditText().getText().toString());

                if (valid)
                {
                    fAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if (documentSnapshot.getString("isAdmin") != null)
                {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser") != null)
                {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    finish();
                }
            }
        });
    }


    private boolean validatePassword(TextInputLayout password) {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";

        if (val.isEmpty())
        {
            password.setError("Field can not be empty!");
            valid = false;
        }
        else if (!val.matches(checkPassword))
        {
            password.setError("Invalid Password");
            valid = false;
        }
        else
        {
            password.setError(null);
            password.setEnabled(false);
            valid = true;
        }
        return valid;
    }

    public boolean validateEmail(TextInputLayout email)
    {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty())
        {
            email.setError("Field can not be empty!");
            valid = false;
        }
        else if (!val.matches(checkEmail))
        {
            email.setError("Invalid Password!");
            valid = false;
        }
        else
        {
            email.setError(null);
            email.setEnabled(false);
            valid = true;
        }
        return valid;
    }

    public void callCreateScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.btnCreate), "signup_user_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    public void callForgotPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.forgot_Password), "signup_forgot_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isAdmin") != null)
                    {
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        finish();
                    }

                    if (documentSnapshot.getString("isUser") != null)
                    {
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            });
        }

    }

}
