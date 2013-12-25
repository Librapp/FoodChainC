package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.SecondaryActivity;
import lms.foodchainC.data.UserData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 第二菜单模块
 * @createTime 2013/12/18
 */
public class SecondMenuFragment extends Fragment implements OnClickListener {
	private TextView name;
	private ImageView pic;
	private final int LOGIN = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.secondmenu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getView().findViewById(R.id.about).setOnClickListener(this);
		getView().findViewById(R.id.update).setOnClickListener(this);
		getView().findViewById(R.id.exit).setOnClickListener(this);
		getView().findViewById(R.id.feedback).setOnClickListener(this);
		pic = (ImageView) getView().findViewById(R.id.user_pic);
		name = (TextView) getView().findViewById(R.id.user_name);
		name.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), SecondaryActivity.class);
		switch (v.getId()) {
		case R.id.about:
			intent.putExtra("title", R.string.about);
			getActivity().startActivity(intent);
			break;
		case R.id.update:
			update();
			break;
		case R.id.feedback:
			intent.putExtra("title", R.string.feedback);
			getActivity().startActivity(intent);
			break;
		case R.id.exit:
			getActivity().finish();
			break;
		case R.id.user_name:
			if (UserData.self().getState() == 0) {
				intent.putExtra("title", R.string.login);
				getActivity().startActivityForResult(intent, LOGIN);
			} else {
				intent.putExtra("title", R.string.userinfo);
				getActivity().startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	private void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case LOGIN:
				// TODO
				break;
			default:
				break;
			}
		} else {

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
