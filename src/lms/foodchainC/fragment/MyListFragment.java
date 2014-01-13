package lms.foodchainC.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * 
 * @author 梦思
 * @description 列表模块
 */
public class MyListFragment extends ListFragment {
	/** 消息 */
	private final static int MESSAGE = 1;
	/** 订单 */
	private final static int ORDER = 2;
	private static int type;

	public static MyListFragment messageInstance() {
		MyListFragment f = new MyListFragment();
		type = MESSAGE;
		return f;
	}

	public static MyListFragment orderInstance() {
		MyListFragment f = new MyListFragment();
		type = ORDER;
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		switch (type) {
		case MESSAGE:

			break;
		case ORDER:

			break;
		default:
			break;
		}
		super.onActivityCreated(savedInstanceState);
	}
}
