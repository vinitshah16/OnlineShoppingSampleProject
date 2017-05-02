package com.example.vinitshah.onlineshoppingsampleproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinitshah.onlineshoppingsampleproject.ProductInformation;
import com.example.vinitshah.onlineshoppingsampleproject.Model.CartModel;
import com.example.vinitshah.onlineshoppingsampleproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vinit.shah on 26/04/2017.
 */

public class Cart_Adapter extends ArrayAdapter<CartModel> {

    public interface Listener {
        public void onClick(int position);
        public void onClick(String pno,String pname,double price,String image,double qty);
    }

    private final Context context;
    private List<CartModel> itemsArrayList;
    private View rowView;
    private LinearLayout linearLayout,linearLayout_info;
    private Listener mListener;

    public Cart_Adapter(Context context, List<CartModel> itemsArrayList) {
        super(context, R.layout.row_cartlist, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_cartlist, parent, false);

        linearLayout = (LinearLayout) rowView.findViewById(R.id.ll_cart);
        //linearLayout_info = (LinearLayout) linearLayout.findViewById(R.id.ll_cart_product_list_information);
        TextView txtPName = (TextView) rowView.findViewById(R.id.txtCartName);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.txtCartPrice);
        final TextView txtQty = (TextView) rowView.findViewById(R.id.txtCartQty);
        final TextView txtTotal = (TextView) rowView.findViewById(R.id.txtCartTotal);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgCart);
        Button btnRemoveCart = (Button) rowView.findViewById(R.id.btnRemoveFromCart);

        txtPName.setText(itemsArrayList.get(position).getpName());
        txtPrice.setText(String.valueOf(itemsArrayList.get(position).getPrice()));
        txtQty.setText(String.valueOf(itemsArrayList.get(position).getQty()));
        txtTotal.setText(String.valueOf(itemsArrayList.get(position).getTotal()));

        final CartModel dataModel = itemsArrayList.get(position);

        Picasso.with(this.getContext())
                .load(dataModel.getImageUrl())
                .placeholder(R.drawable.no_product)
                .error(R.drawable.no_product)
                .into(imageView);

        btnRemoveCart.setTag(position);
        btnRemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = dataModel.getOrderNo();
                if (mListener != null) {
                    mListener.onClick(pos);
                }
            }
        });

        txtPName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext().getApplicationContext(), ProductInformation.class);
                getContext().startActivity(i);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pno = String.valueOf(dataModel.getPno());
                String pname = dataModel.getpName();
                double price = dataModel.getPrice();
                String image = dataModel.getImageUrl();
                double qty = dataModel.getQty();

                if(mListener!=null)
                {
                    //mListener.onClick(pno,pname,price,image,qty);
                }

                Intent i = new Intent(getContext().getApplicationContext(), ProductInformation.class);

                //Log.d("After CLICKED",""+dataModel.() + "");
                i.putExtra("pno",String.valueOf(dataModel.getPno()));
                i.putExtra("name", dataModel.getpName());
                i.putExtra("price", dataModel.getPrice());
                i.putExtra("image",dataModel.getImageUrl());
                i.putExtra("Qty",dataModel.getQty());


                getContext().startActivity(i);
            }
        });
        return rowView;
    }


    @Nullable
    @Override
    public CartModel getItem(int position) {
        if (position >= 0 && position < getCount()) {
            return itemsArrayList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {

        return itemsArrayList != null ? itemsArrayList.size() : 0;
    }
}
