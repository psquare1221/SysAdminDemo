package com.prakash.fluperdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakash.fluperdemo.R;
import com.prakash.fluperdemo.model.ProductDetails;
import com.prakash.fluperdemo.ui.view.UpdateTaskActivity;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<ProductDetails> productDetailsList;

    public ProductsAdapter(Context mCtx, List<ProductDetails> productDetailsList) {
        this.mCtx = mCtx;
        this.productDetailsList = productDetailsList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        ProductDetails t = productDetailsList.get(position);
        holder.textViewProdName.setText(t.getProd_name());
        holder.textViewProdDesc.setText(t.getProd_desc());
        holder.textViewRegularPrice.setText(t.getRegular_price());
        holder.textViewSalePrice.setText(t.getSale_price());
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

        holder.imgProductPhoto.setImageBitmap(bitmap);

//        if (t.isFinished())
//            holder.textViewStatus.setText("Completed");
//        else
//            holder.textViewStatus.setText("Not Completed");
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewProdName, textViewProdDesc, textViewRegularPrice, textViewSalePrice;
        ImageView imgProductPhoto;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewProdName = itemView.findViewById(R.id.textViewProdName);
            textViewProdDesc = itemView.findViewById(R.id.textViewProdDesc);
            textViewRegularPrice = itemView.findViewById(R.id.textViewRegularPrice);
            textViewRegularPrice.setPaintFlags(textViewRegularPrice.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
            textViewSalePrice = itemView.findViewById(R.id.textViewSalePrice);

            imgProductPhoto = itemView.findViewById(R.id.imgViewProd);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ProductDetails productDetails = productDetailsList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("products", productDetails);

            mCtx.startActivity(intent);
        }
    }
}