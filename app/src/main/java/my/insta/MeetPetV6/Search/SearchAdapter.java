package my.insta.MeetPetV6.Search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SearchAdapter extends FragmentPagerAdapter {


    public SearchAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                PetSearchFragment petSearchFragment = new PetSearchFragment();
                return petSearchFragment;

            case 1:
                UserSearchFragment userSearchFragment = new UserSearchFragment();
                return userSearchFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Питомцы";
            case 1:
                return "Пользователи";
            default:
                return null;
        }
    }
}
