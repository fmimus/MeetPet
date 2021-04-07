package my.insta.MeetPetV6;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MeetpetOffline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
