package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.fragment.AboutFragment;
import lms.foodchainC.fragment.FeedbackFragment;
import lms.foodchainC.fragment.LoginFragment;
import lms.foodchainC.fragment.ResDetailFragment;
import lms.foodchainC.fragment.SendCommentFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 二级界面
 * @createTime 2013/12/19
 */
public class SecondaryActivity extends FragmentActivity implements
		OnClickListener {
	private TextView title;
	private Fragment mContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.secondary);
		super.onCreate(savedInstanceState);
		findViewById(R.id.back).setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		int t = getIntent().getIntExtra("title", R.string.about);
		title.setText(t);
		Bundle b = new Bundle();
		switch (t) {
		case R.string.dianping:
			mContent = new SendCommentFragment();
			break;
		case R.string.about:
			mContent = new AboutFragment();
			break;
		case R.string.login:
			mContent = new LoginFragment();
			break;
		case R.string.feedback:
			mContent = new FeedbackFragment();
			break;
		case R.string.userinfo:

			break;
		case R.string.restaurantdetail:
			mContent = new ResDetailFragment();
			b.putBoolean("isLocal", getIntent()
					.getBooleanExtra("isLocal", true));
			break;
		default:
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame, mContent).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame, mContent).commit();
	}
}
