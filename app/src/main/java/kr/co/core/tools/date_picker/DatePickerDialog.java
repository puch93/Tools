package kr.co.core.tools.date_picker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import kr.co.core.tools.R;

public class DatePickerDialog extends AppCompatActivity {
    private static final String TAG = "TEST_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_dialog);

        DatePicker datePicker = (DatePicker)findViewById(R.id.dataPicker);
        datePicker.init(1970, 0, 1, null);

        LinearLayout confirmBtn = (LinearLayout) findViewById(R.id.ll_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(DatePickerDialog.this, DatePickerMainAct.class);
                resultIntent.putExtra("year", datePicker.getYear());
                resultIntent.putExtra("month", datePicker.getMonth()+1);
                resultIntent.putExtra("day", datePicker.getDayOfMonth());

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
