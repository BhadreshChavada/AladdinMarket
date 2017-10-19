package alladinmarket.com.alladinmarket.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.pojo.OrderObject.Datum;

/**
 * Created by nmn on 16/4/17.
 */

public class OrdersAdaper extends RecyclerView.Adapter<OrdersAdaper.ViewHolder> {
private String[] mDataset;

    public OrdersAdaper(ArrayList<Datum> orders) {
        this.orders = orders;
    }

    private ArrayList <Datum> orders ;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
 try {

     holder.productCost.setText(orders.get(position).getNet_amount().toString()+"cost");
     holder.productName.setText(orders.get(position).getOrder_id().toString()+"uiu");
     holder.sellerName.setText(orders.get(position).getCustomer().toString()+"name");

 }

 catch (Exception ex)
 {
     ex.printStackTrace();

     Log.v("check","c"+orders.get(position).getNet_amount()+orders.get(position).getOrder_id()+
             orders.get(position).getCustomer());
 }

    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView productName;
        public TextView sellerName;
        public TextView productCost;
    public ViewHolder(View v) {
        super(v);
        productName = (TextView)v.findViewById(R.id.tv_product_name);
        sellerName = (TextView)v.findViewById(R.id.tv_customer_name);
        productCost = (TextView)v.findViewById(R.id.tv_price);

    }
}

    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)
    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.v("size","size"+orders.size()) ;
        return orders.size();
    }
}