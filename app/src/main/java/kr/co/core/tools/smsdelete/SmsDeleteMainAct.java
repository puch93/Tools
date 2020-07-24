package kr.co.core.tools.smsdelete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.core.tools.R;

public class SmsDeleteMainAct extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_delete_main);
        mContext = this;
        Button btn = (Button) findViewById(R.id.bt_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteSMS()) {
                    Toast.makeText(mContext, "Your message is deleted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Sorry we can't delete messages.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean deleteSMS() {
        boolean isDeleted = false;
        try {
            mContext.getContentResolver().delete(Uri.parse("content://sms/"), null, null);
            isDeleted = true;
        } catch (Exception ex) {
            isDeleted = false;
        }
        return isDeleted;
    }
}
