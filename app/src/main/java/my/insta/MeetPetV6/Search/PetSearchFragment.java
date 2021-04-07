package my.insta.MeetPetV6.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import my.insta.MeetPetV6.Messages.Adapter.FriendsAdapter;
import my.insta.MeetPetV6.Messages.Model.Chat;
import my.insta.MeetPetV6.Messages.Notification.Token;
import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.Utils.PetAdapter;
import my.insta.MeetPetV6.models.Pet;
import my.insta.MeetPetV6.models.Users;


public class PetSearchFragment extends Fragment {

    private static final String TAG ="PetSearchFragment" ;
    private RecyclerView recyclerView;
    private PetAdapter searchPetsAdapter;
    private List<Pet> mPet;

    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pet_search,null);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        search = (EditText)v.findViewById(R.id.search_pet);

        mPet = new ArrayList<>();
        readUsers();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchPets(s.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void searchPets(String s){

        Query query = FirebaseDatabase.getInstance().getReference("pets").orderByChild("type")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPet.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Pet pet = dataSnapshot.getValue(Pet.class);
                    mPet.add(pet);
                }
                updatePetList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readUsers(){
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("pets");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(search.getText().toString().equals("")){
                    mPet.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        Pet pet = snapshot1.getValue(Pet.class);
                        mPet.add(pet);
                    }
                    updatePetList();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onResume() {

        super.onResume();
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    return true;
                }
                return false;
            }
        });
    }

    private void updatePetList(){

        Log.d(TAG,"updateSearchList : Updating Search List");

        searchPetsAdapter = new PetAdapter(getContext(), mPet);
        recyclerView.setAdapter(searchPetsAdapter);


    }
}