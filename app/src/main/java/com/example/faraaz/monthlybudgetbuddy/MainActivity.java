package com.example.faraaz.monthlybudgetbuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd,btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    btnAdd = findViewById(R.id.btnAdd);
    btnView = findViewById(R.id.btnView);

    btnAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), String.valueOf(which), Toast.LENGTH_SHORT).show();
                    switch (which) {
                        case -1:
                            Toast.makeText(getApplicationContext(), "Revenue", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                            startActivity(intent);
                            break;
                        case -2:
                            Toast.makeText(getApplicationContext(), "Expense", Toast.LENGTH_SHORT).show();
                            break;
                        case -3:
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };


            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Choose an Option!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Revenue", dialogClickListener);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Income", dialogClickListener);
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancelled", dialogClickListener);
             alertDialog.show();

            //Intent intent = new Intent(MainActivity.this, Add);

        }
    });


    }
}
