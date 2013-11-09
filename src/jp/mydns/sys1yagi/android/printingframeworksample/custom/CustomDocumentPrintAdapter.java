/*
 * Copyright (C) 2013 Toshihiro Yagi. http://visible-true.blogspot.jp/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.mydns.sys1yagi.android.printingframeworksample.custom;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

/**
 * @author yagitoshihiro
 * 
 */
public class CustomDocumentPrintAdapter extends PrintDocumentAdapter {

    private Paint mPaint = new Paint();
    private Context mContext;
    private Bitmap mBitmap;
    private String mTitle;
    private String mMessage;

    // render resources
    PrintedPdfDocument mPdfDocument;

    public CustomDocumentPrintAdapter(Context context, Bitmap bitmap,
            String title, String message) {
        mContext = context;
        mBitmap = bitmap;
        mTitle = title;
        mMessage = message;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    };

    @Override
    public void onLayout(PrintAttributes oldAttributes,
            PrintAttributes newAttributes,
            CancellationSignal cancellationSignal,
            LayoutResultCallback callback, Bundle extras) {

        mPdfDocument = new PrintedPdfDocument(mContext, newAttributes);

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }
        int pages = 1;
        PrintDocumentInfo info = new PrintDocumentInfo.Builder("androids.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(pages).build();
        callback.onLayoutFinished(info, true);
    }

    public void onWrite(android.print.PageRange[] pages,
            android.os.ParcelFileDescriptor destination,
            CancellationSignal cancellationSignal, WriteResultCallback callback) {
        if (mPdfDocument == null) {
            return;
        }
        for (int i = 0; i < pages.length; i++) {

            PdfDocument.Page page = mPdfDocument.startPage(i);
            if (cancellationSignal.isCanceled()) {
                callback.onWriteCancelled();
                mPdfDocument.close();
                mPdfDocument = null;
                return;
            }
            onDraw(page.getCanvas());
            mPdfDocument.finishPage(page);
        }

        try {
            mPdfDocument.writeTo(new FileOutputStream(destination
                    .getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            mPdfDocument.close();
            mPdfDocument = null;
        }
        callback.onWriteFinished(pages);
    };

    public void onDraw(Canvas canvas) {
        mPaint.setTextSize(30);
        mPaint.setColor(Color.GRAY);
        int offsetX = 50;
        int offsetY = 20;

        canvas.drawText("Hello DocumentPrintAdapter", offsetX,
                offsetY - mPaint.getFontMetrics().top, mPaint);
        offsetY += (-mPaint.getFontMetrics().top);
        offsetY += 40; // margin
        if (mBitmap != null) {
            printBitmapCenterX(canvas, offsetY, mBitmap, mPaint);
            offsetY += mBitmap.getHeight();
        }

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(45);
        printTextCenterX(canvas, offsetY, mTitle, mPaint);
        offsetY += (-mPaint.getFontMetrics().top);

        offsetY += 20; // margin
        mPaint.setTextSize(24);
        int paddingX = offsetX;
        if (mBitmap != null) {
            paddingX = canvas.getWidth() / 2 - mBitmap.getWidth() / 2;
        }
        printWrapText(canvas, mMessage, paddingX, offsetY, 18, mPaint);
    }

    private void printBitmapCenterX(Canvas canvas, int offsetY, Bitmap bitmap,
            Paint paint) {
        canvas.drawBitmap(bitmap,
                canvas.getWidth() / 2 - bitmap.getWidth() / 2, offsetY, paint);
    }

    private void printTextCenterX(Canvas canvas, int offsetY, String text,
            Paint paint) {
        float[] widths = new float[text.length()];
        paint.getTextWidths(text, widths);
        int width = 0;
        for (float w : widths) {
            width += w;
        }
        int offsetX = canvas.getWidth() / 2 - width / 2;
        canvas.drawText(text, offsetX, offsetY - paint.getFontMetrics().top,
                paint);
    }

    private int printWrapText(Canvas canvas, String text, int paddingX,
            int startY, int lineSpace, Paint paint) {
        int renderWidth = canvas.getWidth() - paddingX * 2;
        int textHeight = -(int) paint.getFontMetrics().top;
        int width = 0;
        char[] c = new char[1];
        float[] w = new float[1];
        int index = 0;
        for (int i = 0; i < text.length(); i++) {
            c[0] = text.charAt(i);
            paint.getTextWidths(c, 0, 1, w);
            if (width + w[0] > renderWidth) {
                canvas.drawText(text.substring(index, i), paddingX, startY
                        + textHeight, paint);
                startY += textHeight + lineSpace;
                index = i;
                width = 0;
            } else {
                width += w[0];
            }

            if (i + 1 >= text.length()) {
                // render
                if (index != i) {
                    canvas.drawText(text.substring(index, i + 1), paddingX,
                            startY + textHeight, paint);
                    startY += textHeight + lineSpace;
                }
            }
        }
        return startY;
    }
}
