package lms.foodchainC.netUtil;

import lms.foodchainC.net.JSONRequest;

import org.cybergarage.util.Debug;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-1-17
 * @description 网络通信任务基类
 * @changLog
 */
public class BaseTask extends AsyncTask<Object, Void, String> {
	private final NetConnectionListener listener;
	private String method;

	public BaseTask(NetConnectionListener listener, String method) {
		this.listener = listener;
		this.method = method;
	}

	@Override
	protected void onPreExecute() {
		listener.onNetBegin(method);
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Object... params) {
		Context context;
		JSONRequest request;
		String data;
		context = (Context) params[0];
		request = (JSONRequest) params[1];
		data = request.make();
		if (data != null) {
			if (Debug.isOn())
				Log.e("BaseTask", data);
		}
		String s = NetUtil.executePost(context, data, null);
		if (s != null) {
			if (Debug.isOn())
				Log.e(method, s);
		}
		return s;
	}

	@Override
	protected void onCancelled() {
		listener.onCancel(method);
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		listener.onNetEnd(method, result);
	}
}
