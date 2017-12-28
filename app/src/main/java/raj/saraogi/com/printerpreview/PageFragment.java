package raj.saraogi.com.printerpreview;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    ImageView imageView, imageView2;
    FrameLayout fragmentImage;
    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_page, container, false);
        imageView=(ImageView)v.findViewById(R.id.pdfView);
        fragmentImage = (FrameLayout) v.findViewById(R.id.imageFrame);
        List<Bitmap> list= (List<Bitmap>) getArguments().getSerializable("list");
        int pos=getArguments().getInt("pos");
        int[] show=getArguments().getIntArray("show");
        int orientFlag = getArguments().getInt("orientFlag");
        int colorFlag = getArguments().getInt("colorFlag");
        int totalPagesAvailable = getArguments().getInt("totalPagesAvailable");
        boolean bookletFlag = getArguments().getBoolean("bookletFlag");


        if (!bookletFlag) {
            if (orientFlag == 1) {
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 210, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 297, getResources().getDisplayMetrics());

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
                fragmentImage.setLayoutParams(lp);
            } else {
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 297, getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 210, getResources().getDisplayMetrics());

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
                fragmentImage.setLayoutParams(lp);
            }
            if (colorFlag == 1) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                imageView.setColorFilter(filter);
            } else {

            }

            imageView.setImageBitmap(list.get(show[pos]));
        } else {
            Bitmap b;
            int totalpagesSelected = show.length;
            int q = show.length / 4;
            int r = show.length % 4;

            int n = pos * 2;


            if (pos % 2 == 0) {
                //Left side greater
                int currentPageLeft = totalPagesAvailable - pos;
                int currentPageRight = pos + 1;
                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(list.get(show[0]).getWidth(), list.get(show[0]).getHeight(), conf); // this creates a MUTABLE bitmap

                //Check whether left side selected is blank
                if (currentPageLeft > totalpagesSelected && currentPageRight > totalpagesSelected) {
                    //Add blank on both side
                    b = combineImages(bmp, bmp);
                    imageView.setImageBitmap(b);
                } else if (currentPageLeft > totalpagesSelected && currentPageRight <= totalpagesSelected) {
                    //Add blank on left side
                    b = combineImages(bmp, list.get(show[currentPageRight - 1]));
                    imageView.setImageBitmap(b);
                } else if (currentPageLeft <= totalpagesSelected && currentPageRight > totalpagesSelected) {
                    // Add Blank on right side
                    b = combineImages(list.get(show[currentPageLeft - 1]), bmp);
                    imageView.setImageBitmap(b);
                } else {
                    //Add page on both side
                    b = combineImages(list.get(show[currentPageLeft - 1]), list.get(show[currentPageRight - 1]));
                    imageView.setImageBitmap(b);
                }

            } else {
                int currentPageLeft = pos + 1;
                int currentPageRight = totalPagesAvailable - pos;
                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(list.get(show[0]).getWidth(), list.get(show[0]).getHeight(), conf); // this creates a MUTABLE bitmap

                //Check whether left side selected is blank
                if (currentPageLeft > totalpagesSelected && currentPageRight > totalpagesSelected) {
                    //Add blank on both side
                    b = combineImages(bmp, bmp);
                    imageView.setImageBitmap(b);
                } else if (currentPageLeft > totalpagesSelected && currentPageRight <= totalpagesSelected) {
                    //Add blank on left side
                    b = combineImages(bmp, list.get(show[currentPageRight - 1]));
                    imageView.setImageBitmap(b);
                } else if (currentPageLeft <= totalpagesSelected && currentPageRight > totalpagesSelected) {
                    // Add Blank on right side
                    b = combineImages(list.get(show[currentPageLeft - 1]), bmp);
                    imageView.setImageBitmap(b);
                } else {
                    //Add page on both side
                    b = combineImages(list.get(show[currentPageLeft - 1]), list.get(show[currentPageRight - 1]));
                    imageView.setImageBitmap(b);
                }

            }
//
//
//            if(n+1==show.length){
//                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
//                Bitmap bmp = Bitmap.createBitmap(list.get(show[n]).getWidth(),list.get(show[n]).getHeight() , conf); // this creates a MUTABLE bitmap
//                b = combineImages(bmp,list.get(show[n]));
//            }else{
//                 b = combineImages(list.get(show[n]), list.get(show[n + 1]));
//            }
//
//
//            imageView.setImageBitmap(b);

//            if (pos % 2 == 0) {
//                int n=pos*2;
//                Bitmap b = combineImages(list.get(show[n]), list.get(show[n + 1]));
//                imageView.setImageBitmap(b);
//            } else {
//                int n=pos*2;
//                Bitmap b = combineImages(list.get(show[n]), list.get(show[n + 1]));
//                imageView.setImageBitmap(b);

//                if (list.get(show[pos + 1]) != null || list.get(show[pos + 2]) != null) {
//                    Bitmap b = combineImages(list.get(show[pos + 1]), list.get(show[pos + 2]));
//                    imageView.setImageBitmap(b);
//                }


//            }

        }


        return v;
    }

    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        if (s == null) {
            return c;
        }
        Bitmap cs = null;

        int width, height = 0;

        if (c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth() + 20;
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth() + 20;
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth() + 20, 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }

}
