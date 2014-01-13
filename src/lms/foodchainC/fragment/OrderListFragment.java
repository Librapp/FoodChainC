package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Bill_DBHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author 梦思
 * @description 订单模块
 * @createTime 2014/1/13
 */
public class OrderListFragment extends Fragment {
	private Bill_DBHelper bdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.orderlist, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		bdb = new Bill_DBHelper(getActivity());
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
