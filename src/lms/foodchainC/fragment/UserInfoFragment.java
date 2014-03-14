package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.data.UserData;
import lms.foodchainC.util.ImageLoaderHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	private Button sayhi;

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
		sayhi = (Button) v.findViewById(R.id.sayhi);
		switch (UserData.current.security) {
		case 0:
			sayhi.setText("发消息");
			break;
		case 1:
			sayhi.setText("打招呼");
			break;
		default:
			break;
		}
		sayhi.setOnClickListener(this);
		username.setText(UserData.self().name);
		email.setText(UserData.self().email);
		nickname.setText(UserData.self().nickname);
		signature.setText(UserData.self().signature);
		ImageLoaderHelper loaderHelper = new ImageLoaderHelper();
		loaderHelper.loadImage(photo, UserData.self().headPic,
				R.drawable.user_default_icon);
		v.findViewById(R.id.sayhi).setOnClickListener(this);
		v.findViewById(R.id.gift).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sayhi:
			switch (UserData.current.security) {
			case 0:
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("title", R.string.conversation);
				getActivity().startActivity(i);
				break;
			case 1:
				Intent iV = new Intent(getActivity(), DetailActivity.class);
				iV.putExtra("title", R.string.verification);
				getActivity().startActivity(iV);
				break;
			default:
				break;
			}
			break;
		case R.id.gift:
			// TODO 跳转到菜单界面，送礼物
			break;
		default:
			break;
		}
	}
}
