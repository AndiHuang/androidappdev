package com.example.eandihu.androidcall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Main activity, with button to toggle phone calls detection on and off.
 * @author Moskvichev Andrey V.
 *
 */
public class MainActivity extends Activity {

    private boolean detectEnabled;

    private TextView textViewDetectState;
    private Button buttonToggleDetect;
    private EditText textfield;
    private RelativeLayout l;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDetectState = (TextView) findViewById(R.id.textViewDetectState);

        l=(RelativeLayout) findViewById(R.id.background);
        buttonToggleDetect = (Button) findViewById(R.id.buttonDetectToggle);
        buttonToggleDetect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetectEnabled(!detectEnabled);
            }
        });


    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true;
    //}

    private void setDetectEnabled(boolean enable) {
        detectEnabled = enable;

        Intent intent = new Intent(this, CallDetectService.class);
        if (enable) {
            // start detect service

            l.setBackgroundColor(Color.RED);
            textfield=(EditText) findViewById(R.id.phonenumfield);
            String num = textfield.getText().toString();
            //System.out.println(num);
            intent.putExtra(EXTRA_MESSAGE, num);
            startService(intent);
            buttonToggleDetect.setText("Turn off");
            textViewDetectState.setText("Detecting");
        }
        else {
            // stop detect service
            stopService(intent);
            l.setBackgroundColor(Color.GREEN);
            buttonToggleDetect.setText("Turn on");
            textViewDetectState.setText("Not detecting");
        }
    }

}
