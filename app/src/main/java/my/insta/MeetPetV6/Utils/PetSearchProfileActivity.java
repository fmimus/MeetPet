package my.insta.MeetPetV6.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.models.Pet;
import my.insta.MeetPetV6.models.Users;

public class PetSearchProfileActivity extends AppCompatActivity {
    private static final String TAG ="PetSearchActivity" ;

    String searchedPetId;
    ImageView petPhoto;
    TextView petName,petType, petAge,petDescription, petSex, petSearchProfile;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_search_profile);

        //searchedPetId = getIntent().getStringExtra("SearchedPetID");
        searchedPetId = getIntent().getStringExtra("SearchedPetID");
        Log.d(TAG, "Item Clicked Getting UID "+searchedPetId);


        petPhoto = (ImageView)findViewById(R.id.petImage);
        petType = (TextView)findViewById(R.id.petType);
        petAge = (TextView)findViewById(R.id.petAge);
        petDescription = (TextView)findViewById(R.id.petDescription);
        petName = (TextView)findViewById(R.id.petName);
        petSex = (TextView)findViewById(R.id.petSex);
        mProgressBar = (ProgressBar)findViewById(R.id.PetSearchProfile_ProgressBar);

        dataretrive();
    }


    private void dataretrive(){

        databaseReference = FirebaseDatabase.getInstance().getReference("pets").child(searchedPetId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pet pet = snapshot.getValue(Pet.class);


                String ageWord = new String();
                if((Integer.parseInt(pet.getAge())%10 == 2 || Integer.parseInt(pet.getAge())%10 == 3 || Integer.parseInt(pet.getAge())%10 == 4)
                        && Integer.parseInt(pet.getAge()) != 12 && Integer.parseInt(pet.getAge()) != 13 && Integer.parseInt(pet.getAge()) != 14) {
                    ageWord = "года";
                } else if((Integer.parseInt(pet.getAge())%10 == 1 )
                        && Integer.parseInt(pet.getAge()) != 11){
                    ageWord = "год";
                } else {
                    ageWord = "лет";
                }
                petName.setText(pet.getName());

                petType.setText(pet.getType());
                petAge.setText(pet.getAge()+" "+ageWord);
                petDescription.setText(pet.getDescription());
                petSex.setText(pet.getSex());
                Glide.with(PetSearchProfileActivity.this)
                        .load(pet.getImage_Path())
                        .centerCrop()
                        .into(petPhoto);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }










}
