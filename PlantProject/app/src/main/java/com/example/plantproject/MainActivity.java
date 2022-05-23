package com.example.plantproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    boolean water_on = false;
    boolean light_on = false; // 조명이 켜져있는지 꺼져있는지 알기 위한 변수. false는 꺼져있는 상태
    boolean wind_on = false; // 공기순환 모터가 꺼져있는지 알기 위한 변수.
    private BackKeyHandler backKeyHandler = new BackKeyHandler(this); // 뒤로가기 이벤트 핸들러 변수

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    ArrayList<dataClass> m_arritems;

    //뒤로가기 두번 누르면 종료
    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed("\'뒤로\' 버튼을 두 번 누르면 종료됩니다.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //자동 ON/OFF 버튼 및 텍스트 변수
        ToggleButton waterToggle = findViewById(R.id.water_auto_btn);
        ToggleButton lightToggle = findViewById(R.id.light_auto_btn);
        ToggleButton windToggle = findViewById(R.id.wind_auto_btn);
        TextView waterText = findViewById(R.id.water_auto_text);
        TextView lightText = findViewById(R.id.light_auto_text);
        TextView windText = findViewById(R.id.wind_auto_text);

        TextView sh_data_tv = findViewById(R.id.soil_humanity_data);
        //수동 버튼 변수
        Button handWaterBtn = findViewById(R.id.hand_water_btn);
        Button handLightBtn = findViewById(R.id.hand_light_btn);
        Button handWindBtn = findViewById(R.id.hand_wind_btn);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Data");
        databaseReference.child("sh_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int value = (int)snapshot.getValue(Integer.class);
                sh_data_tv.setText(String.valueOf(value));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //자동 ON/OFF 토글버튼 이벤트 코드
        waterToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                        String waterAutoText;
                        if(ischecked){
                            waterAutoText = "물 ON";

                        }
                        else {
                            waterAutoText = "물 OFF";
                        }
                        waterText.setText(waterAutoText);

                    }
                }
        );

        lightToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                        String lightAutoText;
                        if(ischecked){
                            lightAutoText = "조명 ON";
                        }
                        else {
                            lightAutoText = "조명 OFF";
                        }
                        lightText.setText(lightAutoText);

                    }
                }
        );

        windToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                        String windAutoText;
                        if(ischecked){
                            windAutoText = "바람 ON";
                        }
                        else {
                            windAutoText = "바람 OFF";
                        }
                        windText.setText(windAutoText);

                    }
                }
        );

        //수동 버튼 이벤트
        handWaterBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //만약 물이 안 나오고 있으면
                        if(water_on==false){
                            Toast.makeText(getApplicationContext(),"물주기를 실행합니다.", Toast.LENGTH_SHORT).show();
                            water_on=true;
                            handWaterBtn.setText("물 끄기");
                            handWaterBtn.setTextColor(Color.WHITE);
                            handWaterBtn.setBackground(getResources().getDrawable(R.drawable.hand_on));

                        }
                        //만약 물이 나오고 있으면
                        else {
                            Toast.makeText(getApplicationContext(),"물을 껐습니다.", Toast.LENGTH_SHORT).show();
                            water_on=false;
                            handWaterBtn.setText("물 주기");
                            handWaterBtn.setTextColor(Color.BLACK);
                            handWaterBtn.setBackground(getResources().getDrawable(R.drawable.btn_hand));





                        }
                    }
                }
        );
        handLightBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //만약 불이 꺼져있는 상태라면
                        if(light_on==false){
                            Toast.makeText(getApplicationContext(),"조명을 켰습니다.", Toast.LENGTH_SHORT).show();
                            light_on=true;
                            handLightBtn.setText("조명 끄기");
                            handLightBtn.setTextColor(Color.WHITE);
                            handLightBtn.setBackground(getResources().getDrawable(R.drawable.hand_on));
                        }
                        //만약 불이 켜져있는 상태라면
                        else {
                            Toast.makeText(getApplicationContext(),"조명을 껐습니다.", Toast.LENGTH_SHORT).show();
                            light_on=false;
                            handLightBtn.setText("조명 켜기");
                            handLightBtn.setTextColor(Color.BLACK);
                            handLightBtn.setBackground(getResources().getDrawable(R.drawable.btn_hand));
                        }

                    }
                }
        );
        handWindBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        //만약 불이 꺼져있는 상태라면
                        if(wind_on==false){
                            Toast.makeText(getApplicationContext(),"공기순환을 실행합니다.", Toast.LENGTH_SHORT).show();
                            wind_on=true;
                            handWindBtn.setText("공기 순환 종료");
                            handWindBtn.setTextColor(Color.WHITE);
                            handWindBtn.setBackground(getResources().getDrawable(R.drawable.hand_on));
                        }
                        //만약 불이 켜져있는 상태라면
                        else {
                            Toast.makeText(getApplicationContext(),"공기순환을 종료합니다.", Toast.LENGTH_SHORT).show();
                            wind_on=false;
                            handWindBtn.setText("공기 순환 실행");
                            handWindBtn.setTextColor(Color.BLACK);
                            handWindBtn.setBackground(getResources().getDrawable(R.drawable.btn_hand));
                        }

                    }
                }
        );

    }
}