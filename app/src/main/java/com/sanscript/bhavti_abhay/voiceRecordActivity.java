package com.sanscript.bhavti_abhay;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.UUID;


public class voiceRecordActivity extends AppCompatActivity {

    ImageButton btnRecord;
    Button btnStop;
    String pathSave = " ";
    MediaRecorder recorder = new MediaRecorder();

    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_record);

        if(!checkPermissionFromDevice()){
            requestPermission();
        }

        btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        btnStop = (Button) findViewById(R.id.btnStop);

            btnRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(checkPermissionFromDevice()){

                    Toast.makeText(getApplicationContext(),"Recording......", Toast.LENGTH_SHORT).show();

                    pathSave = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ UUID.randomUUID().toString()+"_audio_record.3gp"; //Path for the recording


                    setupMediaRecorder();
                    try{
                        recorder.prepare();
                        recorder.start();
                    }catch (IOException e){
                        Log.e(String.valueOf(e), "prepare() failed");
                        Toast.makeText(getApplicationContext(),String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                    btnRecord.setEnabled(false);
                    btnStop.setEnabled(true);
                    }else{
                        requestPermission();
                    }
                }

            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        recorder.stop();
                        btnStop.setEnabled(false);
                        btnRecord.setEnabled(true);

                        Toast.makeText(getApplicationContext(), "Recording Stopped......", Toast.LENGTH_SHORT).show();
                }
            });


    }


    private void setupMediaRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(pathSave);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION_CODE);
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int read_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED && read_external_storage_result == PackageManager.PERMISSION_GRANTED;
    }

}
