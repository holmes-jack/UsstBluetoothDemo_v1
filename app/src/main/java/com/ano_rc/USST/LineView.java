package com.ano_rc.USST;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yanzheng on 16/5/7.
 */
public class LineView extends View {


    private int XPoint = 80;
    private int YPoint = 700;
    private int XScale = 20; // 刻度长度
    private int YScale = 50;
    private int YmScale = 10;
    private int XLength = 1250;
    private int YLength = 650;
    public static boolean btn_flag = false;
    public  static  Thread thread;

    private int MaxDataSize = XLength / XScale;

    private List<Integer> data = new ArrayList<Integer>();

    private String[] YLabel = new String[YLength / YScale];

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1111) {
                Log.e("msg","receive0x1111");
                LineView.this.invalidate();
            }
        }

        ;
    };

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < YLabel.length; i++) {
            YLabel[i] = (i * 50) + "g";
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if(btn_flag == true)
                    {
                        try {
                            Thread.sleep(1000);
                            Log.e("thread","start");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.e("thread","error");
                        }
                        if (data.size() >= MaxDataSize) {
                            data.remove(0);
                        }
                        //data.add(200);
                        //data.add(new Random().nextInt(4) + 1);
                        data.add((int) Float.parseFloat(MainActivity.sbuf));
                        Log.e("Lineview", "create");
                        handler.sendEmptyMessage(0x1111);
                    }

                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint xy = new Paint();
        Paint num = new Paint();
        Paint text = new Paint();
        xy.setStyle(Paint.Style.STROKE);

        xy.setAntiAlias(true); // 去锯齿
        xy.setColor(Color.BLUE);
        xy.setStrokeWidth((float) 3.0);
        num.setAntiAlias(true);
        num.setColor(Color.RED);
        num.setStrokeWidth((float) 5.0);
        text.setAntiAlias(true);
        text.setColor(Color.BLUE);
        text.setTextSize((float) 30.0);
        // 画Y轴
        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, xy);

        // Y轴箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
                + 6, xy); // 箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
                + 6, xy);

        // 添加刻度和文字

        for (int i = 0; i * YScale < YLength; i++) {
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
                    * YScale, xy); // 刻度

            canvas.drawText(YLabel[i], XPoint - 80, YPoint - i * YScale, text);// 文字
        }

        // 画X轴
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, xy);

        // 绘折线

          if(data.size() > 1){ for(int i=1; i<data.size(); i++){
          canvas.drawLine(XPoint + (i-1) * XScale, YPoint - data.get(i-1)/10 *
          YmScale, XPoint + i * XScale, YPoint - data.get(i)/10 * YmScale, num); }
          }

//        paint.setStyle(Paint.Style.FILL);
//        if (data.size() > 1) {
//            Path path = new Path();
//            path.moveTo(XPoint, YPoint);
//            for (int i = 0; i < data.size(); i++) {
//                path.lineTo(XPoint + i * XScale, YPoint - data.get(i) * YScale);
//            }
//            path.lineTo(XPoint + (data.size() - 1) * XScale, YPoint);
//            canvas.drawPath(path, paint);
//
//        }
    }
}
