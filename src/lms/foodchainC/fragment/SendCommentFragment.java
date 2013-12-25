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
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * 
 * @author 梦思
 * @description 发表点评模块
 * @createTime 2013/12/18
 */
public class SendCommentFragment extends Fragment implements OnClickListener {
	private EditText edit;
	private RatingBar mark;
	private String content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.senddianping, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getView().findViewById(R.id.submit).setOnClickListener(this);
		mark = (RatingBar) getView().findViewById(R.id.mark);
		edit = (EditText) getView().findViewById(R.id.edit);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			if (edit.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入点评内容", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				content = edit.getText().toString().trim();
			sendDianPing(content, mark.getRating());
			break;
		default:
			break;
		}
	}

	private void sendDianPing(String content, float mark) {
		new SendCommentTask().execute(content, mark);
	}

	private class SendCommentTask extends AsyncTask<Object, Void, String> {

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
