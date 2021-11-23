package com.example.firebasetest;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void createProfile_Photo_and_Delete() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String filename = "profile" + num + ".jpg";
        Uri file = uri;
        Log.d("유알", String.valueOf(file));
        StorageReference riversRef = storageRef.child("profile_img/" + filename);
        UploadTask uploadTask = riversRef.putFile(file);
        StorageReference desertRef = storageRef.child("profile_img/" + "profile" + num + ".jpg");
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "프로필 이미지가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFireBaseProfileImage(int num) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/profile_img");

        if (!file.isDirectory()) {
            file.mkdir();
        }
        downloadImg(num);
    }

    private void downloadImg(int num) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReference();
        storageRef.child("profile_img/"+"profile"+num+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("오냐오냐",String.valueOf(uri));
                Glide.with(getContext()).load(uri).into(userView);
                dialogwithUri=uri;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}