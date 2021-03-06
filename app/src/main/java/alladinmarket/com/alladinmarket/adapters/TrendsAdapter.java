package alladinmarket.com.alladinmarket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.pojo.NewTrendsItem;
import alladinmarket.com.alladinmarket.network.pojo.Trend.Datum;

/**
 * Created by nmn on 16/4/17.
 */

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.ViewHolder> {
private String[] mDataset;


    private OnItemClickListener listener;
    // Define the listener interface


    public TrendsAdapter(ArrayList<Datum> newTrendsItems) {
        this.newTrendsItems = newTrendsItems;
    }

    private ArrayList<Datum> newTrendsItems = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gridproduct, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    holder.productName.setText(newTrendsItems.get(position).getProductName());

    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public  class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView productName;
        public TextView sellerName;
        public TextView productCost;
    public ViewHolder(View v) {
        super(v);
        productName = (TextView)v.findViewById(R.id.tv_product_name);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            }
        });


    }
}

    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)
    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newTrendsItems.size();
    }
}