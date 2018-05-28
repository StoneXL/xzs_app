package com.yxld.xzs.activity.patrol;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.http.api.API;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemoteHistoryDetailActivity extends BaseActivity {
    private static final String URL_PATH = "wygl_xungeng_app/task/id?";
    public static final String KEY_JILU_ID = "key_jilu_id";
    public static final String KEY_XIANLU_ID = "key_xianlu_id";
    @BindView(R.id.webView)
    WebView webView;


    private int mJiluId;
    private int mXianluId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_history_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null || !bundle.containsKey(KEY_JILU_ID) || !bundle.containsKey(KEY_XIANLU_ID)){
            Toast.makeText(this,"未获取到对应的记录ID和线路ID",Toast.LENGTH_SHORT).show();
            return;
        }
        mJiluId = bundle.getInt(KEY_JILU_ID);
        mXianluId = bundle.getInt(KEY_XIANLU_ID);
        init();
    }

    private void init() {
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
        showProgressDialog();
        String url = API.IP_XUNGENG + URL_PATH+"uuid=" + Contains.uuid+"&jiluid="+mJiluId+"&xianluid="+mXianluId;
        Log.i("geek","nfc_detail:"+url);
        // WebView加载web资源
        webView.loadUrl(url);
//        WwebView.addJavascriptInterface(new PayJavaScriptInterface(), "js");
//        WwebView.loadUrl("javascript:callFromJava('1')");
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
                dismissProgressDialog();
            }
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            if(webView.getUrl().equals(API.IP_XUNGENG + URL_PATH+"uuid=" + Contains.uuid+"&jiluid="+mJiluId+"&xianluid="+mXianluId)){
                super.onBackPressed();
            }else{
                webView.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView!=null){
            webView.destroy();
        }
    }
}
