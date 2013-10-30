package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.data.RestaurantData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ResDetailFragment extends Fragment implements OnClickListener {
	private TextView name;
	private TextView address;
	private TextView tel;
	private TextView sms;
	private TextView intro;
	private ImageView logo;
	private boolean isLocal;
	private RestaurantData resData;
	private OnClickListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.restaurantdetail, container);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mListener = (OnClickListener) getActivity();
		name = (TextView) getView().findViewById(R.id.name);
		address = (TextView) getView().findViewById(R.id.address);
		tel = (TextView) getView().findViewById(R.id.tel);
		sms = (TextView) getView().findViewById(R.id.sms);
		intro = (TextView) getView().findViewById(R.id.intro_txt);
		logo = (ImageView) getView().findViewById(R.id.logo);
		getView().findViewById(R.id.menu).setOnClickListener(mListener);
		getView().findViewById(R.id.hall).setOnClickListener(mListener);
		isLocal = getArguments().getBoolean("isLocal");
		if (isLocal)
			resData = RestaurantData.local();
		else
			resData = RestaurantData.current();
		name.setText(resData.name);
		address.setText(resData.address);
		tel.setText(resData.tel);
		sms.setText(resData.sms);
		intro.setText(resData.intro);
		// TODO logo图片
		super.onActivityCreated(savedInstanceState);
	}

	public static ResDetailFragment instance(RestaurantData res) {
		ResDetailFragment frag = new ResDetailFragment();
		Bundle b = new Bundle();
		b.putBoolean("isLocal", res.isLocal);
		frag.setArguments(b);
		return frag;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
}
