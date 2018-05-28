package com.yxld.xzs.activity.workbench;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.http.api.API;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William on 2017/12/20.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;

    private long[] mHits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("电子券审批");

        initData();
    }

    private void initData() {
        mHits = new long[2];

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDefaultFontSize(16);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // WebView加载web资源
        webView.loadUrl(API.DZQ_URL+"/ticketList.html?"+"uuid="+ Contains.uuid);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });


        webView.getSettings().setCacheMode(
                WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new WebChromeClient());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (300 >= (mHits[mHits.length - 1] - mHits[0])) {
                    finish();
                }else {
                    KLog.i("WebView","goBack");
                    webView.goBack();// 返回上一页面
                }
                return true;
            } else {
                finish();// 退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (webView != null && webView.canGoBack()) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (300 >= (mHits[mHits.length - 1] - mHits[0])) {
                    finish();
                }else {
                    KLog.i("WebView","goBack");
                    webView.goBack();// 返回上一页面
                }
                return true;
            } else {
                finish();// 退出程序
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView!=null){
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }
}
