package david.testweb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWebView = (WebView) findViewById(R.id.webview);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mWebView.loadUrl("javascript:show('" + getJsonData() + "')");
            }
        });

        initWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        //	webSettings.setGeolocationEnabled(true);

        mWebView.loadUrl("file:///android_asset/android.html");

//        mWebView.loadUrl("javascript:test()");
        mWebView.addJavascriptInterface(new ContactPlugin(), "contact");
    }


    private String getJsonData() {
        JSONArray jsonArray = new JSONArray();

        JSONObject object1 = new JSONObject();
        try {
            object1.put("lat", 28.71720);
            object1.put("lng", 115.83345);

            JSONObject object2 = new JSONObject();
            object2.put("lat", 31.19517);
            object2.put("lng", 121.64377);

            JSONObject object3 = new JSONObject();
            object3.put("lat", 31.215283);
            object3.put("lng", 121.481894);

            jsonArray.put(object1);
            jsonArray.put(object2);
            jsonArray.put(object3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }


    final class ContactPlugin {
        @JavascriptInterface
        public void showcontacts() {
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:test()");
                        mWebView.loadUrl("javascript:show('" + getJsonData() + "')");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void call(String phone) {
        }

        public void startNewActivity() {
        }

    }
}
