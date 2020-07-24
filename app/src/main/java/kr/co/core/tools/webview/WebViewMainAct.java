package kr.co.core.tools.webview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityWebViewMain2Binding;

public class WebViewMainAct extends AppCompatActivity implements View.OnTouchListener, SwipeRefreshLayout.OnRefreshListener {
    //    ActivityWebViewMainBinding binding;
    ActivityWebViewMain2Binding binding;
    Activity act;
    NestedWebView webView;

    private static final String TAG = "TEST_HOME";
    public static final String INTENT_PROTOCOL_START = "intent:";
    public static final String INTENT_PROTOCOL_INTENT = "#Intent;";

    String searchUrl = "https://www.google.co.kr";
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    private InputMethodManager imm;

    ListView listView;
    TestBaseAdapter adapter;
    ArrayList<RecentData> recent_data;

    boolean search_check = false;
    SwipeRefreshLayout refreshLayout;

    ActionBar actionBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_main, null);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_main2, null);
        act = this;

        setActionBar();

        progressBar = binding.progressBar;

//        refreshLayout = binding.refresh;
//        refreshLayout.setOnRefreshListener(this);


        // 키보드바 내리기 위함
        imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);


        setListener();

        setWebView();

        setListView();
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_home_black_36dp);
    }


    private void setListView() {
        recent_data = new ArrayList<>();
        /* 리스트뷰 */
        listView = binding.listView;
        adapter = new TestBaseAdapter(recent_data, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imm.hideSoftInputFromWindow(binding.etUrl.getWindowToken(), 0);
                binding.llAddress.setVisibility(View.GONE);
                webView.loadUrl(((RecentData) adapter.getItem(position)).getUrl());
            }
        });
    }

    private void setWebView() {
        /* WebView 설정 */
        webView = binding.webview;
        webView.setWebChromeClient(new WebViewChromeClientClass());

        /* pc버전 */
        /* 좋음 but 로또 무쓸모 */
//        String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50";

//        String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
//        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50"
//        "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1"
//        webView.getSettings().setUserAgentString(ua);



        // javaScript 선택 (필수)
        webView.getSettings().setJavaScriptEnabled(true);
        // WebView 클라이언트를 상속한 이너클래스를 새로 정의한 클래스로 사용하도록 설정
        webView.setWebViewClient(new WebViewClientClass());
        //파일및 콘텐츠 엑세스 허용
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        //웹뷰 새창관련 허용
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // Google 보안을 위해 사용

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);


        /* what is this */
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        /*  */
//        webView.setGestureDetector(new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                if(e1 == null || e2 == null) return false;
//                if(e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
//                else {
//                    try {
//                        if(e1.getY() - e2.getY() > 20 ) {
//                            // Hide Actionbar
////                            getSupportActionBar().hide();
//                            binding.llTopBar.setVisibility(View.GONE);
//                            webView.invalidate();
//                            return false;
//                        }
//                        else if (e2.getY() - e1.getY() > 20 ) {
//                            // Show Actionbar
////                            getSupportActionBar().show();
//                            binding.llTopBar.setVisibility(View.VISIBLE);
//                            webView.invalidate();
//                            return false;
//                        }
//
//                    } catch (Exception e) {
//                        webView.invalidate();
//                    }
//                    return false;
//                }
//            }
//        }));
        webView.loadUrl(searchUrl);
        binding.etUrl.setText(searchUrl);
    }


    private void setListener() {
        /* 터치리스너 */
        binding.etUrl.setOnTouchListener(this);
        binding.webview.setOnTouchListener(this);
        binding.llRemainder.setOnTouchListener(this);


//        binding.llBottom.setOnTouchListener(this);
//
//
//
//        /* 클릭리스너 */
//        // 이전 페이지 가기
//        binding.btnWebBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (webView.canGoBack())
//                    webView.goBack();
//            }
//        });
//
//        // 이후 페이지 가기
//        binding.btnWebForward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (webView.canGoForward())
//                    webView.goForward();
//            }
//        });




        /* EditText 소프트 키보드 리스너 */
        binding.etUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    binding.llAddress.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();


                    imm.hideSoftInputFromWindow(binding.etUrl.getWindowToken(), 0);
                    searchUrl = binding.etUrl.getText().toString();
                    if (checkUrl(searchUrl)) {
                        webView.loadUrl(searchUrl);
                    } else {
                        webView.loadUrl("https://" + searchUrl);
                    }

                    search_check = true;
                }
                return false;
            }
        });
    }


    /* menu xml과 툴바 연결 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.appbar_action, menu);
        getMenuInflater().inflate(R.menu.appbar_action, menu);
        return true;
    }


    /* tool bar 메뉴 이벤트 정의 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                binding.llAddress.setVisibility(View.GONE);

                imm.hideSoftInputFromWindow(binding.etUrl.getWindowToken(), 0);
                searchUrl = binding.etUrl.getText().toString();
                if (checkUrl(searchUrl)) {
                    webView.loadUrl(searchUrl);
                } else {
                    webView.loadUrl("https://" + searchUrl);
                }
                Log.e(TAG, "searchUrl: " + searchUrl);

                search_check = true;
                return true;

            case R.id.action_more_bookmarks:
                return true;

            case R.id.action_more_share:
                return true;

            case R.id.action_more_delete:
                return true;


            case R.id.action_reload:
                webView.reload();
                return true;


            case android.R.id.home:
                webView.loadUrl("https://www.google.co.kr/");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class WebViewChromeClientClass extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);

            if (newProgress >= 100) {
//                if (refreshLayout.isRefreshing()) {
//                    refreshLayout.setRefreshing(false);
//                }

                progressBar.setVisibility(View.GONE);
            } else if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 웹뷰를 사용하려면 그에 따른 클래스를 정의해야 함
     */
    private class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
            String url;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            } else {
                url = null;
            }
            Log.e(TAG, "check URL: " + url);

            //웹뷰상에 전화걸기, 문자, 메일 url 캐치 후 네이티브 앱으로 실행.
            if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:") || url.startsWith("mms:") || url.startsWith("mmsto:") || url.startsWith("mailto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getBaseContext().startActivity(intent);
                return true;
            }

            //intent 캐치 -> 모바일웹으로 제작된 웹페이지에서는 네이티브의 특정한 기능을 호출할 때는 "intent:" 이런형식으로 url이 구성됨.
            if (url.startsWith(INTENT_PROTOCOL_START)) {
                final int customUrlStartIndex = INTENT_PROTOCOL_START.length();
                final int customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT);
                if (customUrlEndIndex < 0) {
                    return false;
                } else {
                    final String customUrl = url.substring(customUrlStartIndex, customUrlEndIndex);
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)));
                    } catch (ActivityNotFoundException e) {
//                            final int packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length();
//                            final int packageEndIndex = url.indexOf(INTENT_PROTOCOL_END);
//
//                            final String packageName = url.substring(packageStartIndex, packageEndIndex < 0 ? url.length() : packageEndIndex);
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)));
                    }
                    return true;
                }
            }
            view.loadUrl(url);
            return true;
        }


        /* 페이지로딩 시작/끝 이벤트 정의 */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //페이지 시작시 url주소표시 / EditText 포커스 없앰
            binding.etUrl.setText(url);
            binding.etUrl.clearFocus();

            webView.setEnabled(false);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            searchUrl = url;

            if (search_check) {
                recent_data.add(new RecentData(url));
                search_check = false;
            }

            webView.setEnabled(true);
            super.onPageFinished(view, url);
        }


        /* 에러처리 */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e(TAG, "onReceivedError Occured!!");

            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.e(TAG, "onReceivedHttpError Occured!!");
            Log.e(TAG, "errorResponse.getEncoding(): " + errorResponse.getEncoding());
            Log.e(TAG, "errorResponse.getMimeType(): " + errorResponse.getMimeType());
            Log.e(TAG, "errorResponse.getData(): " + errorResponse.getData());

            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e(TAG, "onReceivedSslError Occured!!");
            Log.e(TAG, "error.getUrl()" + error.getUrl());
            Log.e(TAG, "error.getCertificate()" + error.getCertificate());
            Log.e(TAG, "error.getPrimaryError()" + error.getPrimaryError());

            super.onReceivedSslError(view, handler, error);
        }
    }


    /* http/https 포함여부확인 */
    private boolean checkUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://"))
            return true;
        else
            return false;
    }


    /* 뒤로가기 버튼 이벤트 정의 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (binding.llAddress.getVisibility() == View.VISIBLE) {
                binding.llAddress.setVisibility(View.GONE);
            } else {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    super.onBackPressed();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /* 새로고침 */
    @Override
    public void onRefresh() {
        webView.reload();
    }


    /* 주소창 클릭시 방문기록 보이기, 다른 곳 클릭시 숨기기 */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.et_url) {
            binding.llAddress.setVisibility(View.VISIBLE);
        } else {
            binding.llAddress.setVisibility(View.GONE);
        }
        return false;
    }
}
