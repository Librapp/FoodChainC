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
 * @description 密码找回模块
 * @createTime 2013/12/18
 */
public class RetrievePasswordFragment extends Fragment implements
		OnClickListener {
	private EditText email, name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.retrievepassword, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		email = (EditText) v.findViewById(R.id.email);
		name = (EditText) v.findViewById(R.id.name);
		v.findViewById(R.id.retrieve).setOnClickListener(this);
		v.findViewById(R.id.back).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.retrieve:
			String username = "";
			String bindemail = "";
			if (name.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				username = name.getText().toString().trim();
			if (email.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入邮箱", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				bindemail = email.getText().toString().trim();
			new RetrieveTask().execute(username, bindemail);
			break;
		case R.id.back:
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	private class RetrieveTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if ("".equals(result)) {
				Toast.makeText(getActivity(), "请去邮箱重置密码", Toast.LENGTH_SHORT)
						.show();
				getActivity().finish();
			} else
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}

	}
}
