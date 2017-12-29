package raj.saraogi.com.printerpreview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raj Saraogi on 12-05-2016.
 */
public class Pager extends FragmentStatePagerAdapter {
    int tabCount, totalPagesAvailable, totalPagesSelected, pagesPerSheet, rows, columns;
    String[] name;
    Bundle bundle = new Bundle();
    List<Bitmap> arrayList;
    boolean normalFlag = false, bookletFlag = false, multipleFlag = false;
    int pos, orientFlag, colorFlag;

    int show[];
    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount, int pos, List<Bitmap> arrayList, int[] show, int orientFlag, int colorFlag, int totalPagesAvailable, boolean normal, boolean booklet) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
        this.pos=pos;
        this.arrayList = arrayList;
        this.show=show;
        this.orientFlag = orientFlag;
        this.colorFlag = colorFlag;
        this.totalPagesAvailable = totalPagesAvailable;
        this.normalFlag = normal;
        this.bookletFlag = booklet;
        this.bundle = bundle;
    }

    public Pager(FragmentManager supportFragmentManager, int noOfTabs, int pos, ArrayList<Bitmap> bitmapArrayList, int[] show, int orientFlag, int colorFlag, int totalPagesSelected, int pagesPerSheet, int rows, int columns) {
        super(supportFragmentManager);
        this.tabCount = noOfTabs;
        this.pos = pos;
        this.arrayList = bitmapArrayList;
        this.show = show;
        this.orientFlag = orientFlag;
        this.colorFlag = colorFlag;
        this.totalPagesSelected = totalPagesSelected;
        this.pagesPerSheet = pagesPerSheet;
        this.rows = rows;
        this.columns = columns;
        this.multipleFlag = true;

    }


    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        // bundle.putInt("pos",position);
        // Log.d("pos", String.valueOf(position));
        Bundle bundle =new Bundle();
        bundle.putSerializable("list", (Serializable) arrayList);
        bundle.putInt("pos",position);
        bundle.putInt("colorFlag", colorFlag);
        bundle.putInt("orientFlag", orientFlag);
        bundle.putIntArray("show",show);
        bundle.putInt("totalPagesAvailable", totalPagesAvailable);
        bundle.putBoolean("bookletFlag", bookletFlag);
        bundle.putBoolean("multipleFlag", multipleFlag);
        bundle.putInt("totalPagesSelected", totalPagesSelected);
        bundle.putInt("pagesPerSheet", pagesPerSheet);
        bundle.putInt("rows", rows);
        bundle.putInt("columns", columns);
        bundle.putBoolean("scaleFlag", true);

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


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

}
