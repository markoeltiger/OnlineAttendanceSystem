package webviewgold.myappname;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.GeolocationPermissions;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginandSignUp extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginand_sign_up);
        webView=(WebView)findViewById(R.id.loginwebView);

        webView.loadUrl("https://fullmarkinphysics.net/en/user-account/");

        webView.clearCache(true);

    }

}