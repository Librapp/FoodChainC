package lms.foodchainC.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lms.foodchainC.R;
import lms.foodchainC.activity.SecondaryActivity;
import lms.foodchainC.data.Constants;
import lms.foodchainC.data.UserData;
import lms.foodchainC.openAPI.sinaWeibo.AccessTokenKeeper;
import lms.foodchainC.openAPI.sinaWeibo.WeiboData;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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
 * @description 登录模块
 * @createTime 2013/12/17
 */
public class LoginFragment extends Fragment implements OnClickListener,
		RequestListener {
	private EditText name, psw;
	private CheckBox autoLogin;
	private String username, userpsw;
	private LoginButton weibo;
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
		return inflater.inflate(R.layout.login, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		name = (EditText) v.findViewById(R.id.name);
		psw = (EditText) v.findViewById(R.id.password);
		autoLogin = (CheckBox) v.findViewById(R.id.autologin);

		// 创建授权认证信息
		AuthInfo authInfo = new AuthInfo(getActivity(), Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);

		weibo = (LoginButton) v.findViewById(R.id.weibo);
		weibo.setExternalOnClickListener(this);
		weibo.setWeiboAuthInfo(authInfo, mLoginListener);
		weibo.setStyle(LoginButton.LOGIN_INCON_STYLE_2);

		v.findViewById(R.id.register).setOnClickListener(this);
		v.findViewById(R.id.login).setOnClickListener(this);
		v.findViewById(R.id.forgotpassword).setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
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
			} else {
				userpsw = psw.getText().toString().trim();
			}
			login(username, userpsw);
			break;
		case R.id.register:
			SecondaryActivity activity = (SecondaryActivity) getActivity();
			activity.switchContent(new RegisterFragment());
			break;
		case R.id.forgotpassword:
			SecondaryActivity activity1 = (SecondaryActivity) getActivity();
			activity1.switchContent(new RetrievePasswordFragment());
			break;
		default:
			break;
		}
	}

	private void login(String name, String psw) {
		new LoginTask().execute(getActivity(), name, psw);
	}

	private class LoginTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... params) {
			String msg = "";
			// TODO Auto-generated method stub
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			if ("".equals(result)) {
				SharedPreferences sp = getActivity().getSharedPreferences(
						"userSetting", Context.MODE_PRIVATE);
				Editor ed = sp.edit();
				if (autoLogin.isChecked()) {
					ed.putBoolean("autoLogin", true);
					ed.putString("name", UserData.self().getName());
					ed.putString("psw", UserData.self().getPassword());
				} else
					ed.putBoolean("autoLogin", false);
				ed.commit();
				Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT)
						.show();
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			} else
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}

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
				accountAPI.getUid(LoginFragment.this);
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
