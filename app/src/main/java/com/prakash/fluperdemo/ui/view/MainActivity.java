package com.prakash.fluperdemo.ui.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prakash.fluperdemo.database.DatabaseClient;
import com.prakash.fluperdemo.R;
import com.prakash.fluperdemo.adapter.ProductsAdapter;
import com.prakash.fluperdemo.model.ProductDetails;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductsActivity.class);
                startActivity(intent);
            }
        });

        getTasks();

    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<ProductDetails>> {

            @Override
            protected List<ProductDetails> doInBackground(Void... voids) {
                List<ProductDetails> productDetailsList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productsDao()
                        .getAll();
                return productDetailsList;
            }

            @Override
            protected void onPostExecute(List<ProductDetails> tasks) {
                super.onPostExecute(tasks);
                ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}