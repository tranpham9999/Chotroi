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

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ProductViewHolder> {

    private ChatAdapter.OnItemListener mOnItemListener;


    //this context we will use to inflate the layout
    private Context mCtx;

    //Tất cả sản phẩm được bỏ vào trong cái list này
    private List<Chat> productList;

    //Lấy context and productlist về bằng constructor
    public ChatAdapter(Context mCtx, List<Chat> productList, ChatAdapter.OnItemListener onItemListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnItemListener = onItemListener;
    }

    @Override
    public ChatAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_messages, null);
        return new ChatAdapter.ProductViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Chat product = productList.get(position);

        //binding the data with the viewholder views
        holder.textView_Username.setText(product.getTennguoidung());
        holder.textView_Username.setMaxLines(1);
        holder.textView_Username.setEllipsize(TextUtils.TruncateAt.END);

        holder.textView_Message.setText(product.getMessage());
        holder.textView_Message.setMaxLines(1);
        holder.textView_Message.setEllipsize(TextUtils.TruncateAt.END);




        Picasso.with(mCtx).load(product.getImageAvatar()).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(holder.imageView_Avatar);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_Username, textView_Message;
        CircleImageView imageView_Avatar;
        ChatAdapter.OnItemListener onItemListener;

        public ProductViewHolder(View itemView, ChatAdapter.OnItemListener onItemListener) {
            super(itemView);

            textView_Username = itemView.findViewById(R.id.textViewName_message);
            textView_Message = itemView.findViewById(R.id.textViewMessage_message);
            imageView_Avatar = itemView.findViewById(R.id.circleimageViewAvatar_message);


            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
