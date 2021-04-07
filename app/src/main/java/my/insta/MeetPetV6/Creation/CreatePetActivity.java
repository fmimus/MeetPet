package my.insta.MeetPetV6.Creation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import my.insta.MeetPetV6.Home;
import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.Utils.methods;

public class CreatePetActivity extends AppCompatActivity {

    ImageView backFromPost,addedImage;
    EditText addedCaption,AddedTag;
    Button postNow;
    DatabaseReference databaseReference,data;
    StorageReference storageReference,ref;

    methods method;
    List<String> classes, types;
    String[] sexes;
    int count = 0;
    int PICK_IMAGE_REQUEST=1;

    Uri imageUri;
    String RandomUId,userId;
    String postCount;
    String RandomPetId;
    String RandomPhotoUId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_createpet);
        sexes = new String[] {
                "Мальчик","Девочка"
        };
        classes = new ArrayList<>();
        types = new ArrayList<>();

        final Spinner sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        final Spinner classSpinner = (Spinner) findViewById(R.id.classSpinner);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);

        final EditText etPetName = findViewById(R.id.petName);
        final EditText etPetAge = findViewById(R.id.petAge);
        final EditText etDescription = findViewById(R.id.Description);

        postNow = (Button)findViewById(R.id.post_now);
        backFromPost = (ImageView)findViewById(R.id.back_from_post);
        addedImage = (ImageView)findViewById(R.id.added_image);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        method = new methods();
        count = getCount();

        backFromPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePetActivity.this, Home.class));
                finish();
            }
        });

        postNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etPetName.getText().toString();
                final String petClass = classSpinner.getSelectedItem().toString();
                final String type = typeSpinner.getSelectedItem().toString().toLowerCase();
                final String sex = sexSpinner.getSelectedItem().toString();
                final String age = etPetAge.getText().toString();
                final String description = etDescription.getText().toString();
            uploadimage(name, petClass, type, sex, age,description);
            }
        });
        addedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                    {
                        classes.clear();
                        for(DataSnapshot childClassesSnapshot : snapshot.child("classes").getChildren()) {

                            String classSpinnerTemp = childClassesSnapshot.getKey();
                            classes.add(classSpinnerTemp);
                        }
                    }
                }



                classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        types.clear();
                        for(DataSnapshot childClassesSnapshot : snapshot.child("classes").child(String.valueOf(parent.getItemAtPosition(position))).getChildren()) {

                            String classSpinnerTemp = childClassesSnapshot.getKey();
                            types.add(classSpinnerTemp);
                        }
                        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<String>(CreatePetActivity.this, android.R.layout.simple_spinner_item, types);
                        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        typeSpinner.setAdapter(typeArrayAdapter);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>(CreatePetActivity.this, android.R.layout.simple_spinner_item, classes);
                classArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                classSpinner.setAdapter(classArrayAdapter);

                ArrayAdapter<String> sexArrayAdapter = new ArrayAdapter<String>(CreatePetActivity.this, android.R.layout.simple_spinner_item, sexes);
                sexArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                sexSpinner.setAdapter(sexArrayAdapter);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            addedImage.setImageURI(imageUri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadimage(final String name, final String petClass, final String type, final String sex, final String age, final String description) {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(CreatePetActivity.this);
            progressDialog.setTitle("Загрузка...");
            progressDialog.show();


            RandomPetId = UUID.randomUUID().toString();
            RandomPhotoUId = UUID.randomUUID().toString();
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref = storageReference.child("photos/users/"+"/"+userId+"/"+RandomPetId+"/"+"/photo"+(count+1));
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            increasePostCount(count);
                            addPost(name, petClass, type, sex, age,description, RandomPetId, getTimestamp(), String.valueOf(uri), RandomPhotoUId );
                            progressDialog.dismiss();
                            Toast.makeText(CreatePetActivity.this, "Успешно создано", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreatePetActivity.this,Home.class));
                            finish();


                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(CreatePetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreatePetActivity.this,Home.class));
                    finish();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });

        }

    }

//******************************FUNCTION TO ADD pet TO FIREBASE STORAGE********
    public void addPost(String name, String petClass, String type, String sex, String age, String description, String pet_id, String date_Created, String image_Path, String photo_id ){


        HashMap<String, String> hashMappp = new HashMap<>();
        hashMappp.put("ownerId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMappp.put("name", name);
        hashMappp.put("petClass", petClass);
        hashMappp.put("type", type);
        hashMappp.put("sex", sex);
        hashMappp.put("age", age);
        hashMappp.put("description", description);
        hashMappp.put("date_Created", date_Created);
        hashMappp.put("image_Path", image_Path);
        hashMappp.put("pet_id", pet_id);
        hashMappp.put("photo_id", photo_id);


        databaseReference.child("pets").child(pet_id).setValue(hashMappp);
        databaseReference.child("user_pets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(pet_id).setValue(hashMappp);


    }

//******************************FUNCTION TO INCREASE POST COUNT********
    public void increasePostCount(final int count){


        data = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postCount = Integer.toString(count+1);
                data.child("posts").setValue(postCount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

//******************************FUNCTION TO GET POST TIME********
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }
//******************************FUNCTION TO GET POST Count********
    public int getCount() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = method.getImagecount(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return count;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}