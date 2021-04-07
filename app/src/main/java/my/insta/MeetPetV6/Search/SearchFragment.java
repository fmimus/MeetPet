package my.insta.MeetPetV6.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.insta.MeetPetV6.Messages.Adapter.PageAdapter;
import my.insta.MeetPetV6.Utils.SearchUsersAdapter;
import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.models.Users;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private RecyclerView recyclerView;
    private SearchUsersAdapter searchUsersAdapter;
    private List<Users> mUser;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SearchAdapter searchAdapter;
    private TabLayout tabLayout;
    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        viewPager = v.findViewById(R.id.SearchActivity_mainTabPager);
        searchAdapter = new SearchAdapter(getChildFragmentManager());
        viewPager.setAdapter(searchAdapter);

        tabLayout = v.findViewById(R.id.SearchActivity_maintabs);
        tabLayout.setupWithViewPager(viewPager);
    return  v;
    }
}