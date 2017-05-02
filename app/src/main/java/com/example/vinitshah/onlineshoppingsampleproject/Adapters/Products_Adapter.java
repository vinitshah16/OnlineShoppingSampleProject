package com.example.vinitshah.onlineshoppingsampleproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinitshah.onlineshoppingsampleproject.Model.ProductModel;
import com.example.vinitshah.onlineshoppingsampleproject.ProductInformation;
import com.example.vinitshah.onlineshoppingsampleproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vinit.shah on 25/04/2017.
 */

public class Products_Adapter extends ArrayAdapter<ProductModel>{

    private final Context context;
    private List<ProductModel> itemsArrayList;
    private View rowView;
    private LinearLayout linearLayout;

    public Products_Adapter(Context context, List<ProductModel> itemsArrayList) {
        super(context, R.layout.row_productlist, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_productlist, parent, false);

        linearLayout = (LinearLayout) rowView.findViewById(R.id.ll_row_product_list_information);
        TextView txtName = (TextView) rowView.findViewById(R.id.txtProductName);
        ImageView img = (ImageView) rowView.findViewById(R.id.imgProduct);
        txtName.setText(itemsArrayList.get(position).getName());
        Picasso.with(this.getContext())
                .load(itemsArrayList.get(position).getImageUrl())
                .placeholder(R.drawable.no_product)
                .error(R.drawable.no_product)
                .into(img);

        final ProductModel dataModel = itemsArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ProductInformation.class);
                i.putExtra("pno",String.valueOf(dataModel.getPno()));
                i.putExtra("name", dataModel.getName());
                i.putExtra("price", dataModel.getPriceInDouble());
                i.putExtra("image",dataModel.getImageUrl());
                i.putExtra("category",dataModel.getCategory());
                context.startActivity(i);
            }
        });
        return rowView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(ProductModel item) {
        return super.getPosition(item);
    }
}
