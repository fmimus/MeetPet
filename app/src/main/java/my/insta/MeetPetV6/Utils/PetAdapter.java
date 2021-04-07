package my.insta.MeetPetV6.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import my.insta.MeetPetV6.R;
import my.insta.MeetPetV6.models.Pet;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {


    private Context mcontext;
    private List<Pet> mPet;
    String TAG = "PetAdapter";




    public PetAdapter(Context mcontext, List<Pet> mPet) {
        this.mcontext = mcontext;
        this.mPet = mPet;
    }

    @NonNull
    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.pet_view_items,parent,false);
        return new PetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pet pet = mPet.get(position);
        holder.petName.setText(pet.getName());
        holder.petType.setText(pet.getType());
        Glide.with(mcontext)
                .load(pet.getImage_Path())
                .centerCrop()
                .into(holder.petImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Pet Profile UID "+ pet.getPet_id());
                Intent intent=new Intent(mcontext, PetSearchProfileActivity.class);
                intent.putExtra("SearchedPetID", pet.getPet_id());
                mcontext.startActivity(intent);



            }
        });

    }


    @Override
    public int getItemCount() {
        return mPet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView petName,petType;
        public ImageView petImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            petName = (TextView)itemView.findViewById(R.id.petName);
            petType = (TextView)itemView.findViewById(R.id.petType);
            petImage = (ImageView)itemView.findViewById(R.id.pet_img);
        }
    }
}
