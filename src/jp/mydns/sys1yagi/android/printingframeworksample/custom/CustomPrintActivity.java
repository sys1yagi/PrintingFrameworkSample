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

import jp.mydns.sys1yagi.android.printingframeworksample.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CustomPrintActivity extends Activity {

    public static Intent createIntent(Activity parent) {
        Intent intent = new Intent(parent, CustomPrintActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_print);

        findViewById(R.id.print_button).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomView cutomView = (CustomView)findViewById(R.id.custom);
                        printWithAdapter("androids.pdf", cutomView.getAdapter());
                    }
                });
    }

    private void printWithAdapter(String jobName, PrintDocumentAdapter adapter) {
        if (PrintHelper.systemSupportsPrint()) {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            printManager.print(jobName, adapter, null);
        } else {
            Toast.makeText(this, "この端末では印刷をサポートしていません", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
