package com.example.lhh.compasstest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrow = (ImageView) findViewById(R.id.arrow);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onDestroy(){
     super.onDestroy();
        if(sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        float[] magneticSensorValues = new float[3];
        float[] acclerometerSensorValues = new float[3];
        private float lastRotateDegree;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                acclerometerSensorValues = event.values.clone();
            }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticSensorValues = event.values.clone();
            }
            float[] R = new float[9];
            float[] value = new float[3];
            SensorManager.getRotationMatrix(R, null, magneticSensorValues, acclerometerSensorValues);//利用加速度传感器值和地磁传感器计算出一个包含旋转矩阵的R数组
            SensorManager.getOrientation(R, value);//手机各个方向上旋转的数据都传入到value数组中
            float RotateDegree = -(float)Math.toDegrees(value[0]);//将弧度转换为角度
            if(Math.abs(RotateDegree - lastRotateDegree) > 1){
                RotateAnimation rotateAnimation = new RotateAnimation(lastRotateDegree, RotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setFillAfter(true);
                arrow.startAnimation(rotateAnimation);
                lastRotateDegree = RotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
