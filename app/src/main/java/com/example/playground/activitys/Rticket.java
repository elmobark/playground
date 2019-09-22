package com.example.playground.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.example.playground.R;

public class Rticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rticket);
        NumberPicker ticketpicker = findViewById(R.id.TicketNamePickerD);
        ticketpicker.setMaxValue(45);
        ticketpicker.setMinValue(1);
    }
}
