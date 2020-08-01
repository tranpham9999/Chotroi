package com.example.doandidong.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ProductViewHolder> {

    private OnItemListener mOnItemListener;


    //this context we will use to inflate the layout
    private Context mCtx;

    //Tất cả sản phẩm được bỏ vào trong cái list này
    private List<Comment> productList;

    //Lấy context and productlist về bằng constructor
    public CommentAdapter(Context mCtx, List<Comment> productList, OnItemListener onItemListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnItemListener = onItemListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_comment, null);
        return new ProductViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Comment product = productList.get(position);

        //binding the data with the viewholder views
        holder.tvContent.setText(product.getContent());
        holder.tvTenngdung.setText(product.getTenngdung());
        holder.tvTime.setText(product.getTime());

        Picasso.with(mCtx).load(product.getAvatar()).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(holder.avt);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvContent, tvTenngdung, tvTime;
        CircleImageView avt;
        OnItemListener onItemListener;

        public ProductViewHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tvContent);
            tvTenngdung = itemView.findViewById(R.id.tvTenngdung_Comment);
            tvTime = itemView.findViewById(R.id.tvTime);
            avt = itemView.findViewById(R.id.avatarComment);


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