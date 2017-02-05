package alien.orientationdetector;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor gravitySensor;
    private TextView X,Y,Z,status;
    private double x, y, z;
    private double xoffset = 0, yoffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        X = (TextView)findViewById(R.id.txtX);
        Y = (TextView)findViewById(R.id.txtY);
        Z = (TextView)findViewById(R.id.txtZ);
        status =  (TextView)findViewById(R.id.status);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i){

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,gravitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            x -= xoffset;
            y -= yoffset;

            x = Math.round(x * 100.0)/100.0;
            y = Math.round(y * 100.0)/100.0;
            z = Math.round(z * 100.0)/100.0;

            X.setText(x + "");
            Y.setText(y + "");
            Z.setText(z + "");

            if(y < 0.2 && y > -0.2 && x < 0.2 && x > -0.2){
                status.setText("Flat Surface");
                status.setBackgroundColor(0xff8bc34a);
            }else{
                status.setText("Not a Flat Surface");
                status.setBackgroundColor(0xfff44336);
            }
        }
    }

    public void setFlatSurface(View v){
        xoffset = x;
        yoffset = y;
    }
}
