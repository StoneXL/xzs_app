package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.http.api.API;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyBuyActivity extends BaseActivity {

    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.webView)
    WebView WwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_repair);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
//        final List<ApplyRepairEntity> entities = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            ApplyRepairEntity entity = new ApplyRepairEntity();
//            entities.add(entity);
//        }
//        final ApplyRepairAdapter adapter = new ApplyRepairAdapter(entities);
//        recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                if ("delete".equals(view.getTag())) {
//                    adapter.getData().remove(i);
//                    adapter.notifyItemRemoved(i);
//                } else {
//                    Intent intent = new Intent(ApplyRepairActivity.this, ApplyRepairCheckMaterialActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//        });
            WwebView.getSettings().setBuiltInZoomControls(true);
            WwebView.getSettings().setDefaultFontSize(16);
            WwebView.getSettings().setDisplayZoomControls(false);
            WwebView.getSettings().setSupportZoom(true);
            WwebView.getSettings().setLoadWithOverviewMode(true);
            WwebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            WwebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            WwebView.getSettings().setDefaultTextEncodingName("UTF -8");
            WwebView.getSettings().setJavaScriptEnabled(true);
            WwebView.getSettings().setAllowContentAccess(true);
            WwebView.getSettings().setAppCacheEnabled(false);
            WwebView.getSettings().setUseWideViewPort(true);
            WwebView.getSettings().setLoadWithOverviewMode(true);
            WwebView.getSettings().setLayoutAlgorithm(
                    WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // WebView加载web资源
            WwebView.loadUrl(API.IP_PRODUCT + "/appealList.html?uuid=" + Contains.uuid);
//        WwebView.addJavascriptInterface(new PayJavaScriptInterface(), "js");
//        WwebView.loadUrl("javascript:callFromJava('1')");
            // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            WwebView.setWebViewClient(new WebViewClient() {
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
        WwebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onBackPressed() {
        if (WwebView.canGoBack()){
            if(WwebView.getUrl().equals(API.IP_PRODUCT + "/appealList.html?uuid=" + Contains.uuid)){
                super.onBackPressed();
            }else{
                WwebView.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }

}
