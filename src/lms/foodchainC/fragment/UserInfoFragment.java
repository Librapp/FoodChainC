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
	private Button sayhi, bill;
	private UserData user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.userinfo, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		user = new UserData();
		user.id = getArguments().getInt("id");
		// TODO 判断是否是好友
		View v = getView();
		username = (TextView) v.findViewById(R.id.username);
		email = (TextView) v.findViewById(R.id.email);
		nickname = (TextView) v.findViewById(R.id.nickname);
		signature = (TextView) v.findViewById(R.id.signature);
		photo = (ImageView) v.findViewById(R.id.photo);
		sayhi = (Button) v.findViewById(R.id.sayhi);
		bill = (Button) v.findViewById(R.id.helpbuybill);
		switch (user.security) {
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
		switch (user.gender) {
		case 0:
			bill.setText("帮她买单");
			break;
		case 1:
			bill.setText("帮他买单");
			break;
		default:
			break;
		}
		bill.setOnClickListener(this);
		username.setText(UserData.self().name);
		email.setText(UserData.self().email);
		nickname.setText(UserData.self().nickname);
		signature.setText(UserData.self().signature);
		ImageLoaderHelper loaderHelper = new ImageLoaderHelper();
		loaderHelper.loadImage(photo, UserData.self().headPic,
				R.drawable.user_default_icon);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sayhi:
			switch (user.security) {
			case 0:
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("title", R.string.conversation);
				i.putExtra("id", user.id);
				getActivity().startActivity(i);
				break;
			case 1:
				Intent iV = new Intent(getActivity(), DetailActivity.class);
				iV.putExtra("title", R.string.verification);
				iV.putExtra("id", user.id);
				getActivity().startActivity(iV);
				break;
			default:
				break;
			}
			break;
		case R.id.helpbuybill:
			// TODO 跳转到当前客户账单界面
			break;
		default:
			break;
		}
	}

	public static UserInfoFragment newInstance(int id) {
		UserInfoFragment f = new UserInfoFragment();
		Bundle args = new Bundle();
		args.putInt("id", id);
		f.setArguments(args);
		return f;
	}
}
