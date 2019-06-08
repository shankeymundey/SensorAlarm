package com.example.yogesh.sensorclr;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sm;
    TextView t1,t2;
    ImageView im1;
    List list;
    int p=0;
    MediaPlayer md;
    boolean swtch=false;
    float x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        im1=(ImageView)findViewById(R.id.im1);
        md=new MediaPlayer();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(list.size()>0){
            sm.registerListener(sel, (Sensor) list.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(savedInstanceState!=null){
            x= savedInstanceState.getFloat("x");
            y= savedInstanceState.getFloat("y");
            z= savedInstanceState.getFloat("z");
        }

    }

    SensorEventListener sel = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            //float[] values = event.values;
            float[] values = event.values;
            x=values[0];
            y=values[1];
            z=values[2];
          /* t1.setText("x: " + values[0] + "\ny: " + values[1]
                    + "\nz: " + values[2]); */


//action to change color
            if(((int)values[1]>=4)||((int)values[1]<=-4)||((int)values[0]>=4)||((int)values[0 ]<=-4))
            {   im1.setImageResource(R.drawable.teasy);
                t2.setText("Busted!!");
                t1.setBackgroundColor(Color.RED);
                if (swtch==false){
                    { md=MediaPlayer.create(getApplicationContext(),R.raw.song);}
                md.start();
                swtch=true;

                }
                }

            else
            {
                im1.setImageResource(R.drawable.angry );
                t2.setText("stay away!");
                md.pause();
                swtch=false;

                t1.setBackgroundColor(Color.CYAN);

            }


        }
    };
    @Override
    protected void onStop() {

        sm.unregisterListener(sel);

        super.onStop();

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
       // super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putFloat("x", x);
        savedInstanceState.putFloat("y", y);
        savedInstanceState.putFloat("z", z);

    }



}
