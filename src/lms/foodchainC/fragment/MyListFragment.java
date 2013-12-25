package lms.foodchainC.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * 
 * @author 梦思
 * @description 列表模块
 */
public class MyListFragment extends ListFragment {
	private static int type;
	private final static int MESSAGE = 1;
	private final static int YOUERYUAN = 2;

	public static MyListFragment messageInstance() {
		MyListFragment f = new MyListFragment();
		type = MESSAGE;
		return f;
	}

	public static MyListFragment youeryuanInstance() {
		MyListFragment f = new MyListFragment();
		type = YOUERYUAN;
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		switch (type) {
		case MESSAGE:

			break;
		case YOUERYUAN:

			break;
		default:
			break;
		}
		super.onActivityCreated(savedInstanceState);
	}
}
