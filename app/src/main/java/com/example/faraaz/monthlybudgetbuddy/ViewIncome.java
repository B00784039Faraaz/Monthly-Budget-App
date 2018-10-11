package com.example.faraaz.monthlybudgetbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewIncome extends AppCompatActivity {

    private ListView listViewIncome;

    DatabaseReference databaseIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        databaseIncome = FirebaseDatabase.getInstance().getReference("income");

        listViewIncome = findViewById(R.id.listViewIncome);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseIncome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot incomeSnapshot: dataSnapshot.getChildren() ){

                    Income income = incomeSnapshot.getValue(Income.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
