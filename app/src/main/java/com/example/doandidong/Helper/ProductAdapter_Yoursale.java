package com.example.doandidong.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter_Yoursale extends RecyclerView.Adapter<ProductAdapter_Yoursale.ProductViewHolder>  {

    private String check = "asd";
    private OnItemListener mOnItemListener;

    //this context we will use to inflate the layout
    private Context mCtx;

    //Tất cả sản phẩm được bỏ vào trong cái list này
    private List<Product_Trangchu> productList;

    //Lấy context and productlist về bằng constructor
    public ProductAdapter_Yoursale(Context mCtx, List<Product_Trangchu> productList, OnItemListener onItemListener) {
            this.mCtx = mCtx;
            this.productList = productList;
            this.mOnItemListener = onItemListener;
            }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.list_sanpham_yoursale, null);
            return new ProductViewHolder(view, mOnItemListener);
            }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
            //getting the product of the specified position
            Product_Trangchu product = productList.get(position);

            //binding the data with the viewholder views

            holder.textViewTitle_yoursale.setText(product.getTitle());
            holder.textViewTitle_yoursale.setMaxLines(2);
            holder.textViewTitle_yoursale.setEllipsize(TextUtils.TruncateAt.END);

            holder.textViewShortDesc_yoursale.setMaxLines(2);
            holder.textViewShortDesc_yoursale.setEllipsize(TextUtils.TruncateAt.END);
            holder.textViewShortDesc_yoursale.setText(product.getShortdesc());
//            holder.textViewRating_yoursale.setText(String.valueOf(product.getRating()));
            holder.textViewPrice_yoursale.setText(String.format("%,.0f", Float.valueOf((float) product.getPrice()))+" Đ"); //format to 100,000,000
            Picasso.with(mCtx).load(product.getImage()).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(holder.imageView);
            holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItemFromList(v, position);
                    notifyDataSetChanged();
                }
            });

    }


    @Override
    public int getItemCount() {
            return productList.size();
            }

    // confirmation dialog box to delete an unit
    private void deleteItemFromList(View v, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Delete");
        builder.setMessage("Are you sure ??")
                .setCancelable(false)
                .setPositiveButton("CONFIRM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int masp;
                                masp = productList.get(position).getId();
                                DeleteInDatabase(masp);
                                productList.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }

    private void DeleteInDatabase(final int masp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_deleteproduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("message");

                            if (Response.equals("success")){
                                Toast.makeText(mCtx, Response, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(mCtx, Response, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mCtx, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String id = Integer.toString(masp);
                params.put("masp", id);
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(mCtx);
        requestQueue1.add(stringRequest);

    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle_yoursale, textViewShortDesc_yoursale, textViewRating_yoursale, textViewPrice_yoursale;
        ImageView imageView;
        ProductAdapter_Yoursale.OnItemListener onItemListener;
        ImageButton imageButtonDelete;

        public ProductViewHolder(View itemView, ProductAdapter_Yoursale.OnItemListener onItemListener) {
            super(itemView);

            textViewTitle_yoursale = itemView.findViewById(R.id.textViewTitle_yoursale);
            textViewShortDesc_yoursale = itemView.findViewById(R.id.textViewShortDesc_yoursale);
//            textViewRating_yoursale = itemView.findViewById(R.id.textViewRating_yoursale);
            textViewPrice_yoursale = itemView.findViewById(R.id.textViewPrice_yoursale);
            imageView = itemView.findViewById(R.id.imageView_yoursale);
            imageButtonDelete = itemView.findViewById(R.id.imageViewDelete_yoursale);
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
