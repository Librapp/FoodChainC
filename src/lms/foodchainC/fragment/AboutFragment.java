package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 
 * @author 梦思
 * @description 关于模块
 * @createTime 2013/12/17
 */
public class AboutFragment extends Fragment implements OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getView().findViewById(R.id.servenumber).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.servenumber:

			break;
		default:
			break;
		}
	}
}
