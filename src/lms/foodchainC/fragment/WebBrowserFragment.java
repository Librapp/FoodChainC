package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebBrowserFragment extends Fragment {
	private WebView web;
	private String url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.webbrowser, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		web = (WebView) v.findViewById(R.id.web);
		url = getArguments().getString("url");
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		web.invokeZoomPicker();
		web.getSettings().setSupportZoom(true);
		web.getSettings().setBuiltInZoomControls(true);

		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setAllowFileAccess(true);
		web.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		web.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {

			}
		});

		int width = getResources().getDisplayMetrics().widthPixels;
		int height = getResources().getDisplayMetrics().heightPixels;
		web.measure(width, height);
		if (url != null)
			web.loadUrl(url);
		else {
			Toast.makeText(getActivity(), "页面加载失败!", Toast.LENGTH_SHORT).show();
			getActivity().finish();
		}
		super.onActivityCreated(savedInstanceState);
	}
}
