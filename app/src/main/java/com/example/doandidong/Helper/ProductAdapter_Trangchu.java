package com.example.doandidong.Helper;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter_Trangchu extends RecyclerView.Adapter<ProductAdapter_Trangchu.ProductViewHolder> {

    private OnItemListener mOnItemListener;


    //this context we will use to inflate the layout
    private Context mCtx;

    //Tất cả sản phẩm được bỏ vào trong cái list này
    private List<Product_Trangchu> productList;

    //Lấy context and productlist về bằng constructor
    public ProductAdapter_Trangchu(Context mCtx, List<Product_Trangchu> productList, OnItemListener onItemListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnItemListener = onItemListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_sanpham_trangchu, null);
            return new ProductViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product_Trangchu product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle_yoursale.setText(product.getTitle());
        holder.textViewTitle_yoursale.setMaxLines(2);
        holder.textViewTitle_yoursale.setEllipsize(TextUtils.TruncateAt.END);


        holder.textViewShortDesc_yoursale.setMaxLines(2);
        holder.textViewShortDesc_yoursale.setEllipsize(TextUtils.TruncateAt.END);
        holder.textViewShortDesc_yoursale.setText(product.getShortdesc());

//        holder.textViewRating_yoursale.setText(String.valueOf(product.getRating()));
        holder.textViewPrice_yoursale.setText(String.format("%,.0f", Float.valueOf((float) product.getPrice()))+" Đ"); //format to 100,000,000
        holder.textViewPrice_yoursale.setMaxLines(1);
        Picasso.with(mCtx).load(product.getImage()).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle_yoursale, textViewShortDesc_yoursale, textViewRating_yoursale, textViewPrice_yoursale;
        ImageView imageView;
        OnItemListener onItemListener;

        public ProductViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);

            textViewTitle_yoursale = itemView.findViewById(R.id.textViewTitle_yoursale);
            textViewShortDesc_yoursale = itemView.findViewById(R.id.textViewShortDesc_yoursale);
//            textViewRating_yoursale = itemView.findViewById(R.id.textViewRating_yoursale);
            textViewPrice_yoursale = itemView.findViewById(R.id.textViewPrice_yoursale);
            imageView = itemView.findViewById(R.id.imageView);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }


}