package com.example.spaceoptima.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.spaceoptima.Dashboard;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    TextInputLayout fullName,email,password, confirm ,userNumber;
    Button gotoLogin, registerBtn;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Boolean valid = true;
    ImageView ImgUserPhoto;
    Uri pickedImgUri;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox isAdminBox,isUserBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.signup_fullname);
        userNumber = findViewById(R.id.signup_userNumber);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirm = findViewById(R.id.signup_confirm);
        ImgUserPhoto = findViewById(R.id.ivUserPhoto);
        gotoLogin = findViewById(R.id.signup_login_button);
        registerBtn = findViewById(R.id.signup_create_button);

        //checkboxes validation
        isAdminBox = findViewById(R.id.cbAdmin);
        isUserBox = findViewById(R.id.cbStudent);

        //Check box Logic
        isUserBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    isAdminBox.setChecked(false);
                }
            }
        });

        isAdminBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    isUserBox.setChecked(false);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateEmail(email);
                validatePassword(password);
                validateConfirm(confirm);
                validateName(fullName);
                validateNumber(userNumber);

                if (!(isAdminBox.isChecked() || isUserBox.isChecked()))
                {
                    Toast.makeText(RegisterActivity.this, "Select Account Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid)
                {
                    fAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),password.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();

                            userInfo.put("FullName", fullName.getEditText().getText().toString());
                            userInfo.put("UserMail", email.getEditText().getText().toString());
                            userInfo.put("UserNumber", userNumber.getEditText().getText().toString());
                            userInfo.put("Confirm", confirm.getEditText().getText().toString());

                            //after user account is created what is needed is to update profile picture name
                            updateUserInfo(pickedImgUri,fAuth.getCurrentUser());

                            //Specify if the user is admin
                            if (isAdminBox.isChecked())
                            {
                                userInfo.put("isAdmin", "1");
                            }
                            if (isUserBox.isChecked())
                            {
                                userInfo.put("isUser", "1");
                            }

                            df.set(userInfo);
                            if (isAdminBox.isChecked())
                            {
                                startActivity(new Intent(getApplicationContext(),Main.class));
                                finish();
                            }
                            if (isUserBox.isChecked())
                            {
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Failed to Create Account!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        ImgUserPhoto = findViewById(R.id.ivUserPhoto);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22)
                {
                    checkAndRequestForPermission();


                }
                else
                {
                    openGallery();
                }
            }
        });
    }

    //Update User photo and name
    private void updateUserInfo(Uri pickedImgUri, final FirebaseUser currentUser)
    {
        //first Upload user photo to database and get url

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image uploaded successfully
                //image url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //url contain user image url

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {
                                            //user info updated successfully
                                            showMessage("Registration Completed!");
                                            updateUI();
                                        }
                                    }

                                });
                    }
                });

            }
        });

    }

    //simple method to show toast messages
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        if (isAdminBox.isChecked())
        {
            startActivity(new Intent(getApplicationContext(),Main.class));
            finish();
        }
        if (isUserBox.isChecked())
        {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
    }

    private void openGallery() {
        //TODO open gallery instant and wait for user to pick an image!

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(RegisterActivity.this, "Please Accept for Required permission", Toast.LENGTH_SHORT).show();
            }

            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }
        else openGallery();
    }

    private boolean validateNumber(TextInputLayout userNumber) {
        String val = userNumber.getEditText().getText().toString().trim();
        if (val.isEmpty())
        {
            userNumber.setError("Field can not be empty!");
            valid = false;
        }
        else
        {
            userNumber.setError(null);
            userNumber.setEnabled(false);
            valid = true;
        }
        return valid;

    }

    private boolean validateName(TextInputLayout fullName) {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty())
        {
            fullName.setError("Field can not be empty!");
            valid = false;
        }else
        {
            fullName.setError(null);
            fullName.setEnabled(false);
            valid = true;
        }
        return valid;
    }

    private boolean validatePassword(TextInputLayout password) {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "[a-zA-Z0-9!@#$]{8,24}";

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

    private boolean validateConfirm(TextInputLayout confirm) {

        String val = confirm.getEditText().getText().toString().trim();
        String checkConfirm = "[a-zA-Z0-9!@#$]{8,24}";

        if (val.isEmpty())
        {
            confirm.setError("Field can not be empty!");
            valid = false;
        }
        else if (!val.matches(checkConfirm))
        {
            confirm.setError("Invalid Password");
            valid = false;
        }
        else
        {
            confirm.setError(null);
            confirm.setEnabled(false);
            valid = true;
        }
        return valid;
    }

    private boolean validateEmail(TextInputLayout email) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null)
        {
            //the user has successfully picked an image
            //we need to save its reference to a Url Variable

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }

    public void callLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.signup_login_button), "transition_login_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }
}