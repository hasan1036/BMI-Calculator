package com.example.automatechangeactivaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageButton btnTest;

    ToggleButton toggleButton;
    TextView textView;

    int count = 0;
    //    Timer timer;
    Button play, pause, stop, btn_insert, delete;
    EditText xValue, yValue;
    private  boolean isResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.playid);
        pause = findViewById(R.id.pauseid);
        stop = findViewById(R.id.stopid);
        delete = findViewById(R.id.deleteid);

        xValue = findViewById(R.id.x_valueid);
        yValue = findViewById(R.id.y_valueid);
        btn_insert = findViewById(R.id.btnid);


        toggleButton = findViewById(R.id.toggleBtn);
        textView = findViewById(R.id.toggleBtn);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chartTable = database.getReference("ChartValues");
        // DatabaseReference ledDbt1 = database.getReference("ledDbt1");
        DatabaseReference ledDbt2 = database.getReference("ledDbt2");



//Toggle button Start
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    textView.setText("Your Wifi is on");

                }else{

                    textView.setText("Your Wifi is off");
                }
            }
        });

//Toggle button end
        //Count Loop Start
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count += 5;
                                xValue.setText(String.valueOf(count));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        //Count Loop End


//
//        ledDbt1 .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                String value = snapshot.getValue().toString();
//                xValue.setText(value);
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });
        ledDbt2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                yValue.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        autoClick();


        //Count Loop Start


     //   autoClick();


    }

    private void autoClick() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chartTable = database.getReference("ChartValues");

        Thread t = new Thread(){
            @Override
            public  void run()
            {while (!isInterrupted()){
                try {Thread.sleep(1590);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String id = chartTable.push().getKey();
                            int x = Integer.parseInt(xValue.getText().toString());
                            int y = Integer.parseInt(yValue.getText().toString());
                            PointValue pointValue = new PointValue(x,y);
                            chartTable.child(id).setValue(pointValue);}});
                }catch (InterruptedException e){e.printStackTrace();}}}
        };
        t.start();
    }
}