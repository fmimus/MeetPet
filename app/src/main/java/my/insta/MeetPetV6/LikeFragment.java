package my.insta.MeetPetV6;

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

import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.Utils.PetAdapter;
import my.insta.MeetPetV6.Utils.UniversalImageLoader;
import my.insta.MeetPetV6.models.Pet;
import my.insta.MeetPetV6.models.Photo;
import my.insta.MeetPetV6.models.Users;
import my.insta.MeetPetV6.models.privatedetails;

public class LikeFragment extends Fragment {



}
