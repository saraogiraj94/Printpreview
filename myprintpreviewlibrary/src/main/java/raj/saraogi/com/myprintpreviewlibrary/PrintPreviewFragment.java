package raj.saraogi.com.myprintpreviewlibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrintPreviewFragment extends Fragment {

    TextView tvTest, tvTest2;

    public PrintPreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_print_preview, container, false);
        tvTest = (TextView) v.findViewById(R.id.tvTest);
        tvTest2 = (TextView) v.findViewById(R.id.tvTest2);
        String b = getArguments().getString("sample");
        tvTest.setText(b);
        tvTest2.setText(b);
        return v;
    }

}
