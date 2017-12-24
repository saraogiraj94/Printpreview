package raj.saraogi.com.printerpreview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raj Saraogi on 12-05-2016.
 */
public class Pager extends FragmentPagerAdapter {
    int tabCount;
    String[] name;
    Bundle bundle = new Bundle();
    List<Bitmap> arrayList;
    int pos;
    int show[];
    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount, int pos, List<Bitmap> arrayList,int[] show) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
        this.pos=pos;
        this.arrayList = arrayList;
        this.show=show;
        this.bundle=bundle;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        // bundle.putInt("pos",position);
        // Log.d("pos", String.valueOf(position));
        Bundle bundle =new Bundle();
        bundle.putSerializable("list", (Serializable) arrayList);
        bundle.putInt("pos",position);
        bundle.putIntArray("show",show);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return  pageFragment;
       // BlankFragment blankFragment = new BlankFragment(position);
//        blankFragment.setArguments(bundle);
       // return blankFragment;
        //Returning the current tabs
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
