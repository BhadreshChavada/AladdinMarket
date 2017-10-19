package alladinmarket.com.alladinmarket.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;

/**
 * Created by nmn on 3/4/17.
 */

public class ImageAdapter extends PagerAdapter{


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private Bitmap[] bitmaps = new Bitmap[]{} ;

    public ImageAdapter(Context context,Bitmap[] bitmaps) {
        this.context = context;
        this.bitmaps=bitmaps;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


       // imageView.setImageResource(IMAGES.get(position));
            imageView.setImageBitmap(bitmaps[position]);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
