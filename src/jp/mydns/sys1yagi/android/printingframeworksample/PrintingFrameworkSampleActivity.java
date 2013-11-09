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
package jp.mydns.sys1yagi.android.printingframeworksample;

import jp.mydns.sys1yagi.android.printingframeworksample.custom.CustomPrintActivity;
import jp.mydns.sys1yagi.android.printingframeworksample.html.HtmlPrintActivity;
import jp.mydns.sys1yagi.android.printingframeworksample.image.ImagePrintActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrintingFrameworkSampleActivity extends Activity {
    /**
     get this object for enclosing objects.
     @return PrintingFrameworkSampleActivity this object.
     */
    private PrintingFrameworkSampleActivity This() {
        return this;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printing_framework_sample);
        ListView listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        
        adapter.add("Image Print");
        adapter.add("HTML Print");
        adapter.add("Custom Print");
        
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos,
                    long id) {
                Intent intent = null;
                switch(pos){
                case 0:
                    intent = ImagePrintActivity.createIntent(This());
                    break;
                case 1:
                    intent = HtmlPrintActivity.createIntent(This());
                    break;
                case 2:
                    intent = CustomPrintActivity.createIntent(This());
                    break;
                }
                
                if(intent != null){
                    startActivity(intent);
                }
                
            }
        });
    }
}
