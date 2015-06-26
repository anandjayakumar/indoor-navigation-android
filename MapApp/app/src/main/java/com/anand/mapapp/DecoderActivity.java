package com.anand.mapapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;


public class DecoderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView mydecoderview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

    }

    public final static String MESSAGE = "com.app1.aaru.myapp1.MESSAGE";
    public static String data;

    @Override
    public void onQRCodeRead(String text, PointF[] points)
    {
        data=text;
        Intent intent=new Intent();
        intent.putExtra("MESSAGE",data);
        setResult(2,intent);
        finish();

    }




    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent=new Intent();
            intent.putExtra("MESSAGE","null");
            setResult(2,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}