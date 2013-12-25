package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author 梦思
 * @description 左侧滑出菜单模块
 * @createTime 2013/12/20
 */
public class SlidingMenuFragment extends Fragment implements OnClickListener,
		OnItemClickListener {
	private ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slidingmenu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		list = (ListView) getView().findViewById(R.id.slidingmenu_list);
		String[] slidingmenu = getResources().getStringArray(
				R.array.slidingmenu);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, slidingmenu);
		list.setAdapter(colorAdapter);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newContent = new Fragment();
		switch (position) {
		case 0:
			newContent = new SearchFragment();
			break;
		case 1:

			break;
		default:
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragment);
		}
	}

}
