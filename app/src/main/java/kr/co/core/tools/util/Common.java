package kr.co.core.tools.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class Common {
    public static void showToast(Activity act, String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToastLong(Activity act, String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void showToast_net(Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }





    public static boolean isAppTopRun(Context ctx, String baseClassName){
        ActivityManager activityManager = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info;
        info = activityManager.getRunningTasks(1);
        if(info==null || info.size()==0){
            return false;
        }
        if(info.get(0).baseActivity.getClassName().equals(baseClassName)) {
            return true;
        } else {
            return false;
        }
    }

    private static class TIME_MAXIMUM {
        static final int SEC = 60;
        static final int MIN = 60;
        static final int HOUR = 24;
        static final int DAY = 30;
        static final int MONTH = 12;
    }

    public static String formatImeString(Date tempDate) {
        long curTime = System.currentTimeMillis();
        long regTime = tempDate.getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;
        if (diffTime < 0) {
            msg = "0초전";
        } else if (diffTime < TIME_MAXIMUM.SEC) {
            msg = diffTime + "초전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달전";
        } else {
            msg = (diffTime) + "년전";
        }

        return msg;
    }
}
