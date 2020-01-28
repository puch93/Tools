package kr.co.core.tools.date_picker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityDatePickerMainBinding;

public class DatePickerMainAct extends AppCompatActivity {
    ActivityDatePickerMainBinding binding;

    private android.app.DatePickerDialog.OnDateSetListener listener = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            binding.tvDate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker_main, null);

        binding.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DatePickerMainAct.this, DatePickerDialog.class), 1000);
            }
        });

        binding.datePickerCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(DatePickerMainAct.this, listener, year, month, day);
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            int year = data.getIntExtra("year", 0);
            int month = data.getIntExtra("month", 0);
            int day = data.getIntExtra("day", 0);
            binding.tvDate.setText(year + "-" + month + "-" + day);
        }
    }
}
