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
package jp.mydns.sys1yagi.android.printingframeworksample.html;

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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HtmlPrintActivity extends Activity {

    public static Intent createIntent(Activity parent) {
        Intent intent = new Intent(parent, HtmlPrintActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_print);

        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl("http://visible-true.blogspot.jp/");

        findViewById(R.id.print_button).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printHtml(webView.getTitle() + ".pdf", webView);
                    }
                });
    }

    private void printHtml(String fileName, WebView webView) {
        if (PrintHelper.systemSupportsPrint()) {
            
            PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter();
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            printManager.print(fileName, adapter, null);
        } else {
            Toast.makeText(this, "この端末では印刷をサポートしていません", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
