package lms.foodchainC.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.data.Constants;
import lms.foodchainC.openAPI.sinaWeibo.AccessTokenKeeper;
import lms.foodchainC.openAPI.sinaWeibo.WeiboData;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.AccountAPI;
import com.sina.weibo.sdk.openapi.legacy.UsersAPI;
import com.sina.weibo.sdk.widget.LoginButton;

/**
 * 
 * @author 梦思
 * @description 注册模块
 * @createTime 2013/12/17
 */
public class RegisterFragment extends Fragment implements OnClickListener,
		RequestListener {
	private EditText name, psw, confirmpsw, email;
	private TextView contract;
	private CheckBox check;
	private LoginButton weibo;

	private String username, userpsw, useremail;

	private int sinaRequest = 0;
	private final int SINA_UID = 0;
	private final int SINA_SHOW = 1;
	/** sina微博登陆认证对应的listener */
	private AuthListener mLoginListener = new AuthListener();
	/** sina accountAPI */
	private AccountAPI accountAPI;
	/** sina userAPI */
	private UsersAPI usersAPI;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.register, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		check = (CheckBox) v.findViewById(R.id.check);
		name = (EditText) v.findViewById(R.id.name);
		psw = (EditText) v.findViewById(R.id.password);
		confirmpsw = (EditText) v.findViewById(R.id.confirmpassword);
		email = (EditText) v.findViewById(R.id.email);
		contract = (TextView) v.findViewById(R.id.contract);
		contract.setText("《用户使用协议》");
		contract.setOnClickListener(this);
		// 创建授权认证信息
		AuthInfo authInfo = new AuthInfo(getActivity(), Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);

		weibo = (LoginButton) v.findViewById(R.id.weibo);
		weibo.setExternalOnClickListener(this);
		weibo.setWeiboAuthInfo(authInfo, mLoginListener);
		weibo.setStyle(LoginButton.LOGIN_INCON_STYLE_2);

		v.findViewById(R.id.register).setOnClickListener(this);
		v.findViewById(R.id.cancel).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			getActivity().finish();
			break;
		case R.id.register:
			if (name.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT)
						.show();
				return;
			} else {
				username = name.getText().toString().trim();
			}
			if (psw.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT)
						.show();
				return;
			} else if (psw.getText().toString().trim().length() < 6) {
				Toast.makeText(getActivity(), "密码长度必须大于6位", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				userpsw = psw.getText().toString().trim();

			if (confirmpsw.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请确认密码", Toast.LENGTH_SHORT)
						.show();
				return;
			} else if (!userpsw.equals(confirmpsw.getText().toString().trim())) {
				Toast.makeText(getActivity(), "两次输入的密码不一致", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (email.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入邮箱", Toast.LENGTH_SHORT)
						.show();
				return;
			} else {
				useremail = email.getText().toString().trim();
			}
			register(username, userpsw, useremail);
			break;
		case R.id.contract:
			check.setChecked(true);
			Intent i = new Intent(getActivity(), DetailActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("url", "file:///android_asset/agreement.htm");
			i.putExtra("title", R.string.webbrowser);
			getActivity().startActivity(i);
			break;
		default:
			break;
		}
	}

	private void register(String username, String userpsw, String useremail) {
		// TODO
	}

	/**
	 * 登入按钮的监听器，接收授权结果。
	 */
	private class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			Oauth2AccessToken accessToken = Oauth2AccessToken
					.parseAccessToken(values);
			if (accessToken != null && accessToken.isSessionValid()) {
				accountAPI = new AccountAPI(accessToken);
				accountAPI.getUid(RegisterFragment.this);
				AccessTokenKeeper.writeAccessToken(getActivity(), accessToken);
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getActivity(),
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		weibo.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onComplete(String response) {
		try {
			JSONObject data = new JSONObject(response);
			switch (sinaRequest) {
			case SINA_SHOW:
				WeiboData.screen_name = data.getString("screen_name");
				WeiboData.name = data.getString("name");
				WeiboData.description = data.getString("description");
				WeiboData.gender = data.getString("gender");
				WeiboData.profile_image_url = data
						.getString("profile_image_url");
				// TODO sina微博直接登录
				break;
			case SINA_UID:
				WeiboData.uid = data.getString("uid");
				sinaRequest = SINA_SHOW;
				usersAPI = new UsersAPI(
						AccessTokenKeeper.readAccessToken(getActivity()));
				usersAPI.show(WeiboData.uid, this);
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onComplete4binary(ByteArrayOutputStream responseOS) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(WeiboException e) {
		// TODO Auto-generated method stub

	}
}
