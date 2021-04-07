package my.insta.MeetPetV6.Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import my.insta.MeetPetV6.Profile.Account_Settings;
import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.Utils.PetAdapter;
import my.insta.MeetPetV6.Utils.UniversalImageLoader;
import my.insta.MeetPetV6.models.Pet;
import my.insta.MeetPetV6.models.Photo;
import my.insta.MeetPetV6.models.Users;
import my.insta.MeetPetV6.models.privatedetails;

public class UserSearchProfileActivity extends AppCompatActivity {

    private static final int NUM_GRID_COLUMNS = 2;

    String searchedUserId;
    private static final String TAG ="UserSearchActivity" ;
    ImageView account_setting_menu;
    ImageView profilePhoto;
    TextView name, description,website,username;
    DatabaseReference databaseReference;
    private ProgressBar mProgressBar;
    private ArrayList<Photo> mPhotos;
    private ArrayList<Photo> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private ListView mListView;

    private int mResults;
    ImageView message;
    private RecyclerView recyclerView;
    PetAdapter petAdapter;
    private List<Pet> mPet;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_profile);
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();
        mPaginatedPhotos = new ArrayList<>();
        searchedUserId = getIntent().getStringExtra("SearchedUserid");

        mPet = new ArrayList<>();
        readPets();
        recyclerView = (RecyclerView) findViewById(R.id.gridview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        initImageLoader();
        account_setting_menu = (ImageView) findViewById(R.id.account_settingMenu);
        profilePhoto = (ImageView) findViewById(R.id.user_img);
        name = (TextView) findViewById(R.id.display_name);
        description = (TextView) findViewById(R.id.description);
        website = (TextView) findViewById(R.id.website);
        username = (TextView) findViewById(R.id.profileName);

        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);


        final TextView tvPhone = (TextView) findViewById(R.id.phone);
        final TextView tvEmail = (TextView) findViewById(R.id.email);


        // Retriving data
        final String userid = searchedUserId;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Users user = snapshot.getValue(Users.class);
                try {
                    name.setText(user.getFullName());
                    description.setText(user.getDiscription());
                    website.setText(user.getWebsite());
                    username.setText(user.getUsername());

                } catch (Exception e) {
                }


                Glide.with(UserSearchProfileActivity.this)
                        .load(user.getProfilePhoto())
                        .into(profilePhoto);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Privatedetails")
                .child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final privatedetails privatedetail = snapshot.getValue(privatedetails.class);
                try {
                    tvEmail.setText("Email: " + privatedetail.getEmail());

                    tvPhone.setText("Телефон: +7" + privatedetail.getPhoneNumber());
                } catch (Exception e) {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void readPets(){

        Query query = FirebaseDatabase.getInstance().getReference("user_pets").orderByChild("owner_id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPet.clear();
                for(DataSnapshot dataSnapshot : snapshot.child(searchedUserId).getChildren()){
                    Pet pets = dataSnapshot.getValue(Pet.class);
                    mPet.add(pets);



                }
                updateSearchList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void updateSearchList(){

        Log.d(TAG,"updateSearchList : Updating Search List");

        petAdapter = new PetAdapter(this,mPet);
        recyclerView.setAdapter(petAdapter);


    }

    }


