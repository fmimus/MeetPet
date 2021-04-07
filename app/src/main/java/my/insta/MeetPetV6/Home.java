package my.insta.MeetPetV6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.insta.MeetPetV6.LikeFragment;
import my.insta.MeetPetV6.Messages.ChatActivity;
import my.insta.MeetPetV6.Creation.CreatePetActivity;
import my.insta.MeetPetV6.Profile.ProfileFragment;
import my.insta.MeetPetV6.Search.SearchFragment;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigationView = findViewById(R.id.insta_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if (name != null){
            loadfragment(new ProfileFragment());

        }else{
            loadfragment(new ProfileFragment());

        }

    }
    private boolean loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.Messages:
                fragment = null;
                startActivity(new Intent(Home.this, ChatActivity.class));
                break;

            case R.id.search:
                fragment = new SearchFragment();
                break;

            case R.id.post:
                fragment = null;
                startActivity(new Intent(Home.this, CreatePetActivity.class));
                break;

            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadfragment(fragment);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Вы уверены, что хотите выйти?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }


}