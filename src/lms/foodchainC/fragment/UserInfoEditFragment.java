package lms.foodchainC.fragment;

import java.io.File;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.data.UserData;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 个人信息编辑模块
 * @createTime 2013/12/23
 */
public class UserInfoEditFragment extends Fragment implements OnClickListener {
	private EditText nickname, signature, password;
	private ImageView photo;
	private RelativeLayout pswEdit;
	private TextView resetPsw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.userinfo_edit, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		nickname = (EditText) v.findViewById(R.id.nickname);
		signature = (EditText) v.findViewById(R.id.signature);
		password = (EditText) v.findViewById(R.id.password);
		pswEdit = (RelativeLayout) v.findViewById(R.id.passwordedit);
		photo = (ImageView) v.findViewById(R.id.photo);
		resetPsw = (TextView) v.findViewById(R.id.resetpassword);
		nickname.setText(UserData.self().getNickname());
		signature.setText(UserData.self().getSignature());
		ImageLoaderHelper loaderHelper = new ImageLoaderHelper();
		loaderHelper.loadImage(photo, UserData.self().getPhoto(),
				R.drawable.user_default_icon);
		photo.setOnClickListener(this);
		resetPsw.setOnClickListener(this);
		v.findViewById(R.id.submit).setOnClickListener(this);
		v.findViewById(R.id.ok).setOnClickListener(this);
		v.findViewById(R.id.cancel).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			// TODO
			break;
		case R.id.resetpassword:
			resetPsw.setVisibility(View.GONE);
			pswEdit.setVisibility(View.VISIBLE);
			break;
		case R.id.submit:
			// TODO
			break;
		case R.id.photo:
			MyDialogFragment.choosePicInstance().show(
					getChildFragmentManager(), "choose picture");
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case DetailActivity.GETIMAGE_BYCAMERA:
				Uri uri = Uri.fromFile(new File(UserData.self().getPhoto()));
				startPhotoZoom(uri);
				break;
			case DetailActivity.GETIMAGE_BYSDCARD:
				if (data == null)
					return;
				Uri thisUri = data.getData();
				startPhotoZoom(thisUri);
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, DetailActivity.PHOTORESOULT);
	}
}
