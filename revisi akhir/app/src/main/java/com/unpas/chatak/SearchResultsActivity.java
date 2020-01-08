package com.unpas.chatak;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.unpas.chatak.R;

public class SearchResultsActivity extends AppCompatActivity {
    TextView txtViewResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_results);
        super.onCreate(savedInstanceState);
        //assign text view result
        txtViewResult=findViewById(R.id.textViewResult);
        //memanggil method untuk mendapatkan query pencarian
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //mendapatkan kata kunci
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            txtViewResult.setText("Anda Mencari : "+query);
        }
    }
}
