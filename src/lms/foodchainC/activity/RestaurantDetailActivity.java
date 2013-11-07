package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import lms.foodchainC.fragment.HallFragment;
import lms.foodchainC.fragment.MenuFragment;
import lms.foodchainC.fragment.ResDetailFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class RestaurantDetailActivity extends SherlockFragmentActivity
		implements OnClickListener {
	private boolean isLocal;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		isLocal = getIntent().getBooleanExtra("isLocal", true);
		ft = getSupportFragmentManager().beginTransaction();
		if (isLocal) {
			ft.replace(R.id.frame,
					ResDetailFragment.instance(RestaurantData.local()));
		} else {
			ft.replace(R.id.frame,
					ResDetailFragment.instance(RestaurantData.current()));
		}
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu:
			ft.replace(R.id.frame, new MenuFragment());
			ft.commit();
			break;
		case R.id.hall:
			ft.replace(R.id.frame, new HallFragment());
			ft.commit();
			break;
		default:
			break;
		}
	}

}
