package com.prakash.sysminddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakash.sysminddemo.R;
import com.prakash.sysminddemo.model.SuperheroDetails;
import com.prakash.sysminddemo.ui.view.UpdateTaskActivity;

import java.util.List;

public class SuperheroesAdapter extends RecyclerView.Adapter<SuperheroesAdapter.TasksViewHolder> {

    private final Context mCtx;
    private final List<SuperheroDetails> superheroDetailsList;

    public SuperheroesAdapter(Context mCtx, List<SuperheroDetails> superheroDetailsList) {
        this.mCtx = mCtx;
        this.superheroDetailsList = superheroDetailsList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        SuperheroDetails t = superheroDetailsList.get(position);
        Log.d("Prakash : ","t.getCharacter_name : "+t.getCharacter_name());
        holder.textViewSuperheroName.setText(t.getCharacter_name());
        holder.textViewSuperheroDesc.setText(t.getCharacter_desc());

        Bitmap bitmap = null;
        try
        {
             bitmap = MediaStore.Images.Media.getBitmap(mCtx.getContentResolver() ,
                    Uri.parse(String.valueOf(t.getPhoto_uri())));
        }
        catch (Exception e)
        {
            //handle exception
        }

        Log.e("Prakash : ", "bitmap : "+t.getPhoto_uri());


        holder.imgProductPhoto.setImageBitmap(bitmap);

//        if (t.isFinished())
//            holder.textViewStatus.setText("Completed");
//        else
//            holder.textViewStatus.setText("Not Completed");
    }

    @Override
    public int getItemCount() {
        return superheroDetailsList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewSuperheroName, textViewSuperheroDesc;
        ImageView imgProductPhoto;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewSuperheroName = itemView.findViewById(R.id.textViewSuperheroName);
            textViewSuperheroDesc = itemView.findViewById(R.id.textViewSuperheroDesc);

            imgProductPhoto = itemView.findViewById(R.id.imgViewProd);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SuperheroDetails superheroDetails = superheroDetailsList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("superheroes", superheroDetails);

            mCtx.startActivity(intent);
        }
    }
}