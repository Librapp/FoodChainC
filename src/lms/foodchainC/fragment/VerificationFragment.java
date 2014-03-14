package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 
 * @author Luke
 * @description 加好友验证模块
 * @createTime 2014/3/13
 */
public class VerificationFragment extends Fragment implements OnClickListener {
	private EditText ed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.verification, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		ed = (EditText) v.findViewById(R.id.ed);
		v.findViewById(R.id.send).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			// TODO 发送验证
			break;
		default:
			break;
		}
	}

}
