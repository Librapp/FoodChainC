package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.fragment.AboutFragment;
import lms.foodchainC.fragment.ConversationFragment;
import lms.foodchainC.fragment.HallFragment;
import lms.foodchainC.fragment.LoginFragment;
import lms.foodchainC.fragment.MenuFragment;
import lms.foodchainC.fragment.OrderListFragment;
import lms.foodchainC.fragment.VerificationFragment;
import lms.foodchainC.fragment.WebBrowserFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 详情界面
 * @createTime 2013/12/17
 */
public class DetailActivity extends FragmentActivity implements OnClickListener {
	private TextView title;
	private Fragment mContent;
	public final static int GETIMAGE_BYSDCARD = 0;
	public final static int GETIMAGE_BYCAMERA = 1;
	public final static int PHOTORESOULT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.detail);

		super.onCreate(savedInstanceState);
		findViewById(R.id.back).setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		int t = getIntent().getIntExtra("title", R.string.about);
		title.setText(t);
		Bundle b = new Bundle();
		switch (t) {
		case R.string.mycomment:
			mContent = new AboutFragment();
			break;
		case R.string.message:
			mContent = new LoginFragment();
			break;
		case R.string.menu:
			b.putInt("id", getIntent().getIntExtra("id", 0));
			mContent = new MenuFragment();
			break;
		case R.string.hall:
			b.putInt("id", getIntent().getIntExtra("id", 0));
			mContent = new HallFragment();
			break;
		case R.string.webbrowser:
			String url = getIntent().getStringExtra("url");
			mContent = new WebBrowserFragment();
			b.putString("url", url);
			break;
		case R.string.sendorder:
			mContent = new OrderListFragment();
			break;
		case R.string.conversation:
			mContent = ConversationFragment.newInstance(getIntent()
					.getIntExtra("id", 0));
			break;
		case R.string.verification:
			mContent = VerificationFragment.newInstance(getIntent()
					.getIntExtra("id", 0));
			break;
		default:
			break;
		}
		mContent.setArguments(b);
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
