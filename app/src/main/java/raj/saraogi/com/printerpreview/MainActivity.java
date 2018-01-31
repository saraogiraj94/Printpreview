package raj.saraogi.com.printerpreview;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import raj.saraogi.com.printpreviewlibrary.DocumentPreview;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 101;
    private static final int READ_REQUEST_CODE = 106;
    private static final int PERMISSION_FOR_FINELOCATION_ACCESS = 105;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Button btnSelectFile, btnProtrait, btnLandscape;
    RangeSeekBar<Integer> seekBar;
    ImageView pdfView;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ViewPager viewPager;
    int orientFlag = 1, colorFlag = 2, typeFlag = 1;
    private String fileName;
    private String TAG = "DashBoardFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectFile=(Button)findViewById(R.id.btnSelectFile);
        viewPager=(ViewPager)findViewById(R.id.pdfPager);
        btnProtrait = (Button) findViewById(R.id.btnportrait);

        //pdfView=(ImageView)findViewById(R.id.pdfView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void selectFile(View v){
        try {
            if (verifyStoragePermissions(this))
                openDialogForSelectFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED)
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE); // We don't have permission so prompt the user
        else
            return true;

        return false;
    }

    private void openDialogForSelectFile() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        String[] extraMimeTypes = {"image/*", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/pdf", "image/png", "image/jpeg", ""};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
        // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(intent, READ_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            sendFileResult(requestCode, resultCode, data);

        }
    }

    public void sendFileResult(int requestCode, int resultCode, final Intent data) {

        Uri uri = null;
        if (data != null) {
            try {
                uri = data.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bundle b = new Bundle();
            if(uri!=null){
                String uriPath = uri.toString();
                int indexOf = uriPath.indexOf("com.google.android.apps");

                useLib(uri);
//                try {
//
//                    openPDF(uriPath,uri);
//                } catch (IOException e) {
//                    Toast.makeText(this,"In Exception",Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
            }

            // startActivity(new Intent(this,TestPreview.class).putExtra("uri",uri));
        }

    }

    public void changePortrait(View v) {
        orientFlag = 1;
        renderPager();
    }

    public void changeLandscape(View v) {
        orientFlag = 2;
        renderPager();
    }

    public void changeColor(View v) {
        colorFlag = 2;
        renderPager();
    }

    public void changeBlack(View v) {
        colorFlag = 1;
        renderPager();
    }


    private void openPDF(String path,Uri uri) throws IOException {
        Toast.makeText(this, "In view", Toast.LENGTH_LONG).show();
        FileManager fileManager = FileManager.getInstance();
        File file = new File(fileManager.getPath(this, uri));
        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pdfRenderer = new PdfRenderer(fileDescriptor);


            final int pageCount = pdfRenderer.getPageCount();
            Toast.makeText(this,
                    "pageCount = " + pageCount,
                    Toast.LENGTH_LONG).show();

            //Display page 0

            for(int i=0;i<pageCount;i++){
                PdfRenderer.Page rendererPage = pdfRenderer.openPage(i);
                int rendererPageWidth = rendererPage.getWidth();
                int rendererPageHeight = rendererPage.getHeight();
                Bitmap bitmap = Bitmap.createBitmap(
                        rendererPageWidth,
                        rendererPageHeight,
                        Bitmap.Config.ARGB_8888);
                rendererPage.render(bitmap, null, null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                bitmapArrayList.add(bitmap);
                rendererPage.close();

                //pdfRenderer.close();

            }

            //renderMultiplePages();
            renderPager();



//            PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
//            int rendererPageWidth = rendererPage.getWidth();
//            int rendererPageHeight = rendererPage.getHeight();
//            Bitmap bitmap = Bitmap.createBitmap(
//                    rendererPageWidth,
//                    rendererPageHeight,
//                    Bitmap.Config.ARGB_8888);
//            rendererPage.render(bitmap, null, null,
//                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

           // pdfView.setImageBitmap(bitmap);

        }
    }

    public void renderBooklet(View v) {
        typeFlag = 2;
        renderBooklet();
    }

    public void renderMultiple(View v) {
        typeFlag = 3;
        renderMultiplePages();
    }

    public void renderNormal(View v) {
        typeFlag = 1;
        renderPager();
    }

    public void renderPager() {
        viewPager.setAdapter(null);
        Toast.makeText(this, "In page render", Toast.LENGTH_SHORT).show();
        int show[] = {0, 1, 2, 3};
        int tabs = show.length;

        Pager pager = new Pager(getSupportFragmentManager(), tabs, show[0], bitmapArrayList, show, orientFlag, colorFlag, tabs, true, false);
        pager.notifyDataSetChanged();
        viewPager.setAdapter(pager);
        viewPager.setOffscreenPageLimit(0);
    }

    public void renderBooklet() {

        viewPager.setAdapter(null);
        Toast.makeText(this, "In page render", Toast.LENGTH_SHORT).show();
        int show[] = {0, 1, 2, 3};

        int tabs;
        int q = show.length / 4;
        int r = show.length % 4;

        if (r == 0)
            tabs = q * 2;
        else
            tabs = (q + 1) * 2;
        int totalPagesAvailabe = tabs * 2;

        Pager pager = new Pager(getSupportFragmentManager(), tabs, show[0], bitmapArrayList, show, orientFlag, colorFlag, totalPagesAvailabe, false, true);
        pager.notifyDataSetChanged();
        viewPager.setAdapter(pager);
        viewPager.setOffscreenPageLimit(0);

    }

    public void renderMultiplePages() {
        viewPager.setAdapter(null);
        Toast.makeText(this, "In page render", Toast.LENGTH_SHORT).show();
        int show[] = {0, 1, 2, 3, 4, 5, 6, 7};

        int totalPagesSelected = show.length;
        int totalPagesRow = 2;
        int totalColumns = 3;
        int totalPagesPerSheet = totalPagesRow * totalColumns;

        int noOfTabs;
        if (totalPagesSelected % totalPagesPerSheet == 0) {
            noOfTabs = totalPagesSelected / totalPagesPerSheet;
        } else {
            noOfTabs = (totalPagesSelected / totalPagesPerSheet) + 1;
        }


        Pager pager = new Pager(getSupportFragmentManager(), noOfTabs, show[0], bitmapArrayList, show, orientFlag, colorFlag, totalPagesSelected, totalPagesPerSheet, totalPagesRow, totalColumns);
        pager.notifyDataSetChanged();
        viewPager.setAdapter(pager);
        viewPager.setOffscreenPageLimit(0);


    }

    public void useLib(Uri uri) {
        DocumentPreview documentPreview = new DocumentPreview();
        try {
            documentPreview.openPDF(uri, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
