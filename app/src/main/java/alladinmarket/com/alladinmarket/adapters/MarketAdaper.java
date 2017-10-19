package alladinmarket.com.alladinmarket.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.ApiClient;
import alladinmarket.com.alladinmarket.network.ApiInterface;
import alladinmarket.com.alladinmarket.network.pojo.Market_item;

/**
 * Created by nmn on 16/4/17.
 */

public class MarketAdaper extends RecyclerView.Adapter<MarketAdaper.ViewHolder> {
private String[] mDataset= {"market","market"};



    public MarketAdaper(ArrayList<Market_item> market_items) {
        this.market_items = market_items;
    }

    private ArrayList<Market_item> market_items = new ArrayList<>();
    private OnItemClickListener listener;
    // Define the listener interface

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
                .inflate(R.layout.item_market, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TextView marketName = (TextView)v.findViewById(R.id.tv_market_name) ;
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Log.v("checkName","name"+market_items.get(position).getName()) ;
            holder.marketName.setText(market_items.get(position).getName());
            Log.v("checkNameAgain","name"+market_items.get(position).getName()) ;
        }
        catch (NullPointerException ne)
        {
            Log.v("checkError",ne.getMessage()) ;
          //  ne.printStackTrace();
        }


    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public  class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView marketName;
        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            marketName = (TextView)v.findViewById(R.id.tv_market_name) ;
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
        try {

            return market_items.size();
        }
        catch (NullPointerException ne){
            return  10 ;
        }

    }
}