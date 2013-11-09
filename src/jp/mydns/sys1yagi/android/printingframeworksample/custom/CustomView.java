/**
 * 
 */
package jp.mydns.sys1yagi.android.printingframeworksample.custom;

import jp.mydns.sys1yagi.android.printingframeworksample.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author yagitoshihiro
 * 
 */
public class CustomView extends View {

    private CustomDocumentPrintAdapter mAdaper;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAdaper = new CustomDocumentPrintAdapter(context,
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.androids), "Hello Kitkat!!", getContext()
                        .getString(R.string.long_message));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mAdaper.onDraw(canvas);
    }

    public CustomDocumentPrintAdapter getAdapter() {
        return mAdaper;
    }
}
