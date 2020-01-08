package com.unpas.chatak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unpas.chatak.R;

public class KetentuanPenggunaActivity extends AppCompatActivity {
    private WebView webView;
    private String SourceURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketentuan_pengguna);
        webView = findViewById(R.id.webView);
        Bundle data =getIntent().getExtras();

        if(data.containsKey("aturan_pengguna"))
        {
            SourceURL =data.getString("aturan_pengguna");
        }
        else if(data.containsKey("kebijakan_privasi"))
        {
            SourceURL =data.getString("kebijakan_privasi");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(SourceURL);
    }
    //mangatur halaman kententuan apabila dapat kembali ke halaman web sebelumnya.
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
