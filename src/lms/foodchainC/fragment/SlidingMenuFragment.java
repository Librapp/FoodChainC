package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author 梦思
 * @description 左侧滑出菜单模块
 * @createTime 2013/12/20
 */
public class SlidingMenuFragment extends Fragment implements
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
		list.setOnItemClickListener(this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new SearchFragment();
			break;
		case 1:
			newContent = new HallFragment();
			break;
		case 2:
			newContent = new MenuFragment();
			break;
		case 3:
			Toast.makeText(getActivity(), "跳转到账单页", Toast.LENGTH_SHORT).show();
			// newContent = new BillFragment();
			break;
		case 4:
			Toast.makeText(getActivity(), "跳转到聊天页", Toast.LENGTH_SHORT).show();
			// newContent = new ChatFragment();
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
