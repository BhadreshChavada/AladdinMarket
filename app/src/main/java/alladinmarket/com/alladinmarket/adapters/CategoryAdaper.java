package alladinmarket.com.alladinmarket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.SeacrhProductActivity;
import alladinmarket.com.alladinmarket.network.pojo.Category;
import alladinmarket.com.alladinmarket.network.pojo.CategoryItem;

/**
 * Created by nmn on 16/4/17.
 */

public class CategoryAdaper extends RecyclerView.Adapter<CategoryAdaper.ViewHolder> implements Filterable{
private String[] mDataset;
    private Context mContext ;

    private ArrayList<Category> categoryItemArrayList = new ArrayList<>();
    private ArrayList<Category> mFilteredList = new ArrayList<>();
    public CategoryAdaper(Context mContext,ArrayList<Category> categoryItemArrayList) {
        this.mContext = mContext;
        this.categoryItemArrayList = categoryItemArrayList ;
        this.mFilteredList = categoryItemArrayList ;
    }

    private OnItemClickListener listener;

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.v("searchValueAdapter",charString);

                if (charString.isEmpty()) {

                    mFilteredList = categoryItemArrayList;
                } else {

                    ArrayList<Category> filteredList = new ArrayList<>();

                    for (Category categoryItem : categoryItemArrayList) {

                        if (categoryItem.getName().toLowerCase().contains(charString) ) {

                            filteredList.add(categoryItem);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        } ;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
try {
    holder.categoryName.setText(mFilteredList.get(position).getName());
    //notifyItemChanged(position);
//    notifyDataSetChanged();
}
catch (IndexOutOfBoundsException ie)
{
    ie.printStackTrace();
  //  notifyDataSetChanged();
    holder.categoryName.setVisibility(View.GONE);
    holder.categoryName.setText("");
   // delete(position);
}

    }
    public void delete(int position) { //removes the row
        mFilteredList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public  class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView categoryName;

          public ViewHolder(View v) {
        super(v);
        categoryName = (TextView)v.findViewById(R.id.tv_category_name);

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


    @Override
    public int getItemCount() {
        return categoryItemArrayList.size();
    }
}