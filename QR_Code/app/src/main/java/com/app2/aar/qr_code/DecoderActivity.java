package com.app2.aar.qr_code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;


public class DecoderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {
    private TextView myTextView;
    private QRCodeReaderView mydecoderview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        myTextView = (TextView) findViewById(R.id.textView1);
    }

    public final static String MESSAGE = "com.app1.aaru.myapp1.MESSAGE";

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed

    public static String data;
    @Override
    public void onQRCodeRead(String text, PointF[] points)
    {
        data=text;
        myTextView.setText(text);

    }
    public void showVal(View v){
        Intent intent=new Intent();
        intent.putExtra("MESSAGE",data);
        setResult(2,intent);
        finish();
    }


    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
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
}
