package com.example.vkr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityGraphBinding


class GraphActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityGraphBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val webview = bindingClass.webview as WebView

        val webSettings = webview.settings

        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        webview.requestFocusFromTouch()
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()

        var inc = 1
        val extras = intent.extras
        if (extras != null) {
            inc = extras.getInt("inc")
        }

        when (inc) {
            1 -> webview.loadUrl(HttpRoutes.INC1)
            2 -> webview.loadUrl(HttpRoutes.INC2)
            3 -> webview.loadUrl(HttpRoutes.INC3)
        }
    }
}