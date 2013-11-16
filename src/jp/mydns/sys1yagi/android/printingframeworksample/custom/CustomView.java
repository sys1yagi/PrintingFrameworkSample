/*
 * Copyright (C) 2013 Toshihiro Yagi. https://github.com/sys1yagi
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
