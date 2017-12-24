package raj.saraogi.com.printerpreview;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class TestPreview extends AppCompatActivity {
    ImageView pdfView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_preview);
        url= getIntent().getStringExtra("uri");
        Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        pdfView=(ImageView)findViewById(R.id.imgPreview);
        try {
            openPDF();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Something Wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void openPDF() throws IOException {
        FileManager fileManager = FileManager.getInstance();
        File fileInstance = new File(fileManager.getPath(this, Uri.parse(url)));


        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                fileInstance, ParcelFileDescriptor.MODE_READ_ONLY);

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
        Toast.makeText(this,
                "pageCount = " + pageCount,
                Toast.LENGTH_LONG).show();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        pdfView.setImageBitmap(bitmap);
        rendererPage.close();

        pdfRenderer.close();
        fileDescriptor.close();
    }
}
