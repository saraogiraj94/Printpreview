package raj.saraogi.com.printerpreview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    ImageView imageView;
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

        imageView.setImageBitmap(list.get(show[pos]));

        return v;
    }

}
