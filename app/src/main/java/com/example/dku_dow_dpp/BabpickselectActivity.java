package com.example.dku_dow_dpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BabpickselectActivity extends AppCompatActivity {
    ImageButton backbtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babpickselect);
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Intent getstr = getIntent();

        String restaurant = getstr.getStringExtra("food");

        LinearLayout container = (LinearLayout) findViewById(R.id.select_ll);
        CollectionReference productRef = db.collection("babpick").document("식당별").collection(restaurant);

        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 0;

                    for (DocumentSnapshot document : task.getResult()) {
                        count++;

                        String name = document.getData().get("name").toString();
                        String hour = document.get("hour").toString();
                        String min = document.get("min").toString();
                        String time = hour + " : " + min;

                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.activity_babpickselectpeople, null);
                        EditText ed_text = view.findViewById(R.id.get_name);
                        ed_text.setId(count);
                        ed_text.setText(name);

                        EditText ed_text_time = view.findViewById(R.id.get_time);
                        ed_text_time.setId(count+10);
                        ed_text_time.setText(time);

                        Button send_btn = view.findViewById(R.id.send_btn);
                        send_btn.setId(count+20);
                        send_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(BabpickselectActivity.this, BabpickresultActivity.class);
                                try
                                {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                intent.putExtra("getname", name);
                                intent.putExtra("gettime", time);

                                startActivity(intent);
                            }
                        });
                        
                        container.addView(view);
                    }
                } else {
                    Log.d("TAG","", task.getException());
                }
            }
        });


        backbtn = findViewById(R.id.button);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BabpickselectActivity.this, BabpickActivity.class);
                startActivity(intent);
            }
        });
    }
}