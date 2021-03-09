package com.example.photogallery.ui.photogallery.view.gallery

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.photogallery.R
import com.example.photogallery.ui.photogallery.view.gallery.base.VisibleFragment

class PhotoPageFragment : VisibleFragment() {

    private lateinit var uri: Uri
    private var webView: WebView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_photo_page, container, false)
        .also { setupView(view = it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uri = arguments?.getParcelable(ARG_URI) ?: Uri.EMPTY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        progressBar?.max = 100
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView = null
        progressBar = null
    }

    private fun setupView(view: View) {
        webView = view.findViewById(R.id.web_view)
        progressBar = view.findViewById(R.id.progressBar)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() =
        webView?.let { webView ->
            webView.settings.javaScriptEnabled = true
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) {
                        progressBar?.isVisible = false
                    } else {
                        progressBar?.isVisible = true
                        progressBar?.progress = newProgress
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    (activity as AppCompatActivity).supportActionBar?.subtitle = title
                }
            }
            webView.webViewClient = WebViewClient()
            webView.loadUrl(uri.toString())
        }


    companion object {
        private const val ARG_URI = "photo_page_uri"

        fun newInstance(uri: Uri) = PhotoPageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_URI, uri)
            }
        }
    }
}