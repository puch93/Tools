package kr.co.core.tools.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import kr.co.core.tools.MainActivity;
import kr.co.core.tools.R;

public class ChromeTabAct extends AppCompatActivity {
    private CustomTabsServiceConnection tabConnection = new CustomTabsServiceConnection() {
        @Override
        public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
            CustomTabsSession session = client.newSession(new CustomTabsCallback() {
                @Override
                public void onNavigationEvent(int navigationEvent, Bundle extras) {
                    super.onNavigationEvent(navigationEvent, extras);
                    switch (navigationEvent) {
                        case CustomTabsCallback.NAVIGATION_STARTED:
                            Log.e("psbs", "NAVIGATION_STARTED");
                            break;
                        case CustomTabsCallback.NAVIGATION_FINISHED:
                            Log.e("psbs", "NAVIGATION_FINISHED");
                            break;
                        case CustomTabsCallback.NAVIGATION_FAILED:
                            Log.e("psbs", "NAVIGATION_FAILED");
                            break;
                        case CustomTabsCallback.NAVIGATION_ABORTED:
                            Log.e("psbs", "NAVIGATION_ABORTED");
                            break;
                    }
                }
            });

            CustomTabsIntent.Builder customTabsIntentBuilder =
                    new CustomTabsIntent.Builder(session);

            String menuItemTitle = "my menu";
            Intent actionIntent = new Intent(
                    getApplicationContext(), MainActivity.class);
            PendingIntent menuItemPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1, actionIntent, 0);

            customTabsIntentBuilder.addMenuItem(menuItemTitle, menuItemPendingIntent);

            CustomTabsIntent customTabsIntent = customTabsIntentBuilder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://psbs.tistory.com"));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrome_tab);
        CustomTabsClient.bindCustomTabsService(
                getApplicationContext() ,
                "com.android.chrome",
                tabConnection);
    }
}
