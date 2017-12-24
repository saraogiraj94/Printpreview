package raj.saraogi.com.printerpreview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    ImageView imageView;
    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_page, container, false);
        imageView=(ImageView)v.findViewById(R.id.pdfView);
        List<Bitmap> list= (List<Bitmap>) getArguments().getSerializable("list");
        int pos=getArguments().getInt("pos");
        int[] show=getArguments().getIntArray("show");
        imageView.setImageBitmap(list.get(show[pos]));

        return v;
    }

}
