package com.ano_rc.USST;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.ano_rc.USST.LineView;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Manifest;
import android.os.Handler;


public class ActivityControl extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MSG_DATA_CHANGE = 1;

    private int mX = 0;


    Timer send_timer = new Timer();

    TextView tv_int_datax;
    TextView tv_int_datay;
    TextView tv_int_dataz;
    TextView tv_string;



   private final Handler ui_handler = new Handler();


    private final  Runnable ui_task = new Runnable() {
        @Override
        public void run() {

            ui_handler.postDelayed(this, 30);

           // tv_int_datax.setText("" + MainActivity.IntDataX);
            //tv_int_datay.setText("" + MainActivity.IntDataY);
            //tv_int_dataz.setText("" + MainActivity.IntDataZ);
            tv_string.setText(""+MainActivity.sbuf);
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_control);


        Log.e("display", "activity");
        //tv_int_datax = (TextView) findViewById(R.id.IX);
        //tv_int_datay = (TextView) findViewById((R.id.IY));
        //tv_int_dataz = (TextView) findViewById((R.id.IZ));
        tv_string = (TextView) findViewById(R.id.dataString);
        LineView mLineView = (LineView) findViewById(R.id.display);
        final Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // mLineView.handler.sendEmptyMessage(0x1111)
               // LineView.thread.start();
                LineView.btn_flag = true;
                btn_start.setEnabled(false);


            }
        });
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineView.btn_flag = false;
                btn_start.setEnabled(true);

            }
        });

        ui_handler.postDelayed(ui_task, 100);
        //  send_timer.schedule(send_task,1000,50);



    }




    protected void onDestroy()
    {
        if (send_timer != null)
        {
            send_timer.cancel();
            send_timer = null;
        }
        super.onDestroy();
    }




}
