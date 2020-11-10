package com.prakash.sysminddemo.ui.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prakash.sysminddemo.database.DatabaseClient;
import com.prakash.sysminddemo.R;
import com.prakash.sysminddemo.adapter.SuperheroesAdapter;
import com.prakash.sysminddemo.model.SuperheroDetails;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code

            saveFirstTime();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSuperherosActivity.class);
                startActivity(intent);
            }
        });

        getTasks();

    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<SuperheroDetails>> {

            @Override
            protected List<SuperheroDetails> doInBackground(Void... voids) {
                List<SuperheroDetails> superheroDetailsList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .superheroesDetailsDao()
                        .getAll();

                Log.d("Prakash : ","superheroDetailsList.size() : "+superheroDetailsList.size());
                return superheroDetailsList;
            }

            @Override
            protected void onPostExecute(List<SuperheroDetails> tasks) {
                super.onPostExecute(tasks);
                SuperheroesAdapter adapter = new SuperheroesAdapter(MainActivity.this, tasks);
                Log.d("Prakash : ","tasks.size() : "+tasks.size());

                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void saveFirstTime(){
        class SaveTaskFirstTime extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                String[] superheroesName = {"Iron Man","Captain America","Thor","Hulk","Captain Marvel","Scarlet Witch","Black Widow","Ultron"};
                String[] superheroesDesc = {"Iron Man Desc","Captain America Desc","Thor Desc","Hulk Desc","Captain Marvel Desc","Scarlet Witch Desc",
                        "Black Widow Desc","Ultron Desc"};
                int[] superheroesImages = {R.drawable.iron_man,
                        R.drawable.captain_america,
                        R.drawable.thor,
                        R.drawable.hulk,
                        R.drawable.captain_marvel,
                        R.drawable.scarlet_witch,
                        R.drawable.black_widow,
                        R.drawable.ultron};



                for (int i = 0; i < 8; i++) {
                    //creating a task
                    SuperheroDetails superheroDetails = new SuperheroDetails();
                    superheroDetails.setCharacter_name(superheroesName[i]);
                    superheroDetails.setCharacter_desc(superheroesDesc[i]);

                    Uri uri = Uri.parse(String.valueOf(giveURIFromDrawable(superheroesImages[i])));

                    superheroDetails.setPhoto_uri(uri.toString());

                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .superheroesDetailsDao()
                            .insert(superheroDetails);
                }

                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Loading Avengers....!", Toast.LENGTH_SHORT).show();
            }
        }

        SaveTaskFirstTime st = new SaveTaskFirstTime();
        st.execute();
    }

    public final Uri giveURIFromDrawable(int i){
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + MainActivity.this.getResources().getResourcePackageName(i)
                + '/' + MainActivity.this.getResources().getResourceTypeName(i)
                + '/' + MainActivity.this.getResources().getResourceEntryName(i) );

        return imageUri;
    }

}