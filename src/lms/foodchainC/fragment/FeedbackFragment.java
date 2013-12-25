package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author 梦思
 * @description 反馈模块
 * @createTime 2013/12/17
 */
public class FeedbackFragment extends Fragment implements OnClickListener {
	private EditText topic, content, connection;
	private String topictxt, contenttxt, connectiontxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.feedback, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		content = (EditText) getView().findViewById(R.id.content);
		topic = (EditText) getView().findViewById(R.id.topic);
		connection = (EditText) getView().findViewById(R.id.connection);
		getView().findViewById(R.id.submit).setOnClickListener(this);
		getView().findViewById(R.id.servenumber).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			if (topic.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入标题", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				topictxt = topic.getText().toString().trim();
			if (content.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				contenttxt = content.getText().toString().trim();
			if (connection.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入联系方式", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				connectiontxt = connection.getText().toString().trim();
			feedback(topictxt, contenttxt, connectiontxt);
			break;
		case R.id.servenumber:

			break;
		default:
			break;
		}
	}

	private void feedback(String topic, String content, String connection) {
		new FeedBackTask().execute(topic, content, connection);
	}

	private class FeedBackTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			String msg = "";
			// TODO Auto-generated method stub
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if ("".equals(result)) {
				// TODO
			} else
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}
	}
}
