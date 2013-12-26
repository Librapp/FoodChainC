package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.data.UserData;
import lms.foodchainC.util.ImageLoaderHelper;
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
 * @description 个人信息模块
 * @createTime 2013/12/19
 */
public class UserInfoFragment extends Fragment implements OnClickListener {
	private TextView username, email, nickname, signature;
	private ImageView photo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.userinfo, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		username = (TextView) v.findViewById(R.id.username);
		email = (TextView) v.findViewById(R.id.email);
		nickname = (TextView) v.findViewById(R.id.nickname);
		signature = (TextView) v.findViewById(R.id.signature);
		photo = (ImageView) v.findViewById(R.id.photo);
		username.setText(UserData.self().name);
		email.setText(UserData.self().email);
		nickname.setText(UserData.self().nickname);
		signature.setText(UserData.self().signature);
		ImageLoaderHelper loaderHelper = new ImageLoaderHelper();
		loaderHelper.loadImage(photo, UserData.self().headPic,
				R.drawable.user_default_icon);
		v.findViewById(R.id.edit).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit:
			// TODO
			break;
		default:
			break;
		}
	}
}
