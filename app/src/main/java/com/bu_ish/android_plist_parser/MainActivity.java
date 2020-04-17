package com.bu_ish.android_plist_parser;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.longevitysoft.android.xml.plist.PListXMLHandler;
import com.longevitysoft.android.xml.plist.PListXMLParser;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PList;
import com.longevitysoft.android.xml.plist.domain.PListObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvText = findViewById(R.id.tvText);
        tvText.setMovementMethod(LinkMovementMethod.getInstance());
        tvText.setText(parsePlist());
    }

    private StringBuilder parsePlist() {
        StringBuilder builder = new StringBuilder();
        try {
            PListXMLParser parser = new PListXMLParser();
            parser.setHandler(new PListXMLHandler());
            parser.parse(getAssets().open("Expressions.plist"));
            PList pList = ((PListXMLHandler) parser.getHandler()).getPlist();
            Array array = (Array) pList.getRootElement();
            for (PListObject object : array) {
                Dict dict = (Dict) object;
                String chs = dict.getConfiguration("chs").getValue();
                String png = dict.getConfiguration("png").getValue();
                builder.append(chs).append(" === ").append(png).append('\n');
            }
        } catch (IOException ex) {
            Log.e(TAG, null, ex);
        }
        return builder;
    }
}
