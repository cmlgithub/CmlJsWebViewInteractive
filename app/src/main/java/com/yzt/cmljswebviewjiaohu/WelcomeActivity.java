package com.yzt.cmljswebviewjiaohu;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WelcomeActivity extends ListActivity {

    DemoBean[] demoBeen = {
            new DemoBean("android and js 交互 demo",MainActivity.class),
            new DemoBean("html 全屏播放 demo",H5VideoActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<DemoBean>(this,android.R.layout.simple_list_item_activated_1,demoBeen));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this,demoBeen[position].activityClass));
    }
}


