package com.anand.mapapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.anand.mapapp.Database.DatabaseHandler;
import com.anand.mapapp.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class DecoderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView mydecoderview;
    DatabaseHandler db = new DatabaseHandler(this);
    public String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

    }
    public void callCancel(View v){
        v.startAnimation(AnimationUtils.loadAnimation(DecoderActivity.this, R.anim.image_click));
        Toast.makeText(getApplicationContext(), "No QR code read", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "null");
        setResult(3, intent);
        finish();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points)
    {
        data=text;
        if(!db.isValidQR(text)){
            data="null";

            Toast.makeText(getApplicationContext(), "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", data);
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
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(getApplicationContext(), "No QR code read", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.putExtra("MESSAGE","null");
            setResult(3,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}