package jp.mydns.sys1yagi.android.printingframeworksample.image;

import jp.mydns.sys1yagi.android.printingframeworksample.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ImagePrintActivity extends Activity {

    public static Intent createIntent(Activity parent) {
        Intent intent = new Intent(parent, ImagePrintActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_print);

        findViewById(R.id.print_button).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView imageView = (ImageView) findViewById(R.id.image);
                        BitmapDrawable drawable = (BitmapDrawable) imageView
                                .getDrawable();
                        printImage("kitkat.pdf", drawable.getBitmap());
                    }
                });
    }

    private void printImage(String fileName, Bitmap bitmap) {
        if (PrintHelper.systemSupportsPrint()) {
            PrintHelper printHelper = new PrintHelper(this);
            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.printBitmap(fileName, bitmap);
        } else {
            Toast.makeText(this, "この端末では印刷をサポートしていません", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
