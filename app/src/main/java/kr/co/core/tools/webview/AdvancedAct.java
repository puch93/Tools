package kr.co.core.tools.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import im.delight.android.webview.AdvancedWebView;
import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityAdvancedBinding;

public class AdvancedAct extends AppCompatActivity implements AdvancedWebView.Listener{
    private AdvancedWebView mWebView;
    ActivityAdvancedBinding binding;
    Activity act;

    private static final String TAG = "TEST_HOME";
    String searchUrl;
    ProgressDialog progressDialog;

    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advanced, null);
        act = this;

        // 키보드바 내리기 위함
        imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);


        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(true);

        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                AdvancedWebView newWebView = new AdvancedWebView(AdvancedAct.this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                return true;
            }
        });

        mWebView.loadUrl("http://www.example.org/");






        /* 리스너 */
        // 검색버튼
        binding.ivExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(binding.etUrl.getWindowToken(), 0);
                searchUrl = binding.etUrl.getText().toString();
                if (checkUrl(searchUrl)) {
                    mWebView.loadUrl(searchUrl);
                } else {
                    mWebView.loadUrl("http://" + searchUrl);
                }
                Log.e(TAG, "searchUrl: " + searchUrl);
            }
        });

        // 이전
        binding.btnWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack())
                    mWebView.goBack();
                Toast.makeText(act, "goBack", Toast.LENGTH_SHORT).show();
            }
        });

        // 이후
        binding.btnWebForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward())
                    mWebView.goForward();
                Toast.makeText(act, "goForward", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }








    private boolean checkUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://"))
            return true;
        else
            return false;
    }
}
