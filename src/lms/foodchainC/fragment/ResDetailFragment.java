package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.DetailActivity;
import lms.foodchainC.data.RestaurantData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * 
 * @author 梦思
 * @description 餐厅详情模块
 * @createTime 2013/12/30
 */
public class ResDetailFragment extends Fragment implements OnClickListener {
	private TextView name, address, tel, sms, intro;
	private Button fav;
	private NumberPicker numberPicker;
	private ImageView logo;
	private ListView comment;
	private boolean isLocal;
	private RestaurantData resData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.restaurantdetail, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		View v = getView();
		name = (TextView) v.findViewById(R.id.rd_name_text);
		logo = (ImageView) v.findViewById(R.id.rd_logo);
		address = (TextView) v.findViewById(R.id.rd_address_text);
		tel = (TextView) v.findViewById(R.id.rd_tel_num);
		sms = (TextView) v.findViewById(R.id.rd_sms_num);
		intro = (TextView) v.findViewById(R.id.rd_intro_txt);
		comment = (ListView) v.findViewById(R.id.comment);
		fav = (Button) v.findViewById(R.id.rd_favorite);
		numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker1);
		numberPicker.setMaxValue(12);
		numberPicker.setMinValue(1);
		v.findViewById(R.id.rd_menu).setOnClickListener(this);
		v.findViewById(R.id.rd_hall).setOnClickListener(this);
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

	// public static ResDetailFragment instance(RestaurantData res) {
	// ResDetailFragment frag = new ResDetailFragment();
	// Bundle b = new Bundle();
	// b.putBoolean("isLocal", res.isLocal);
	// frag.setArguments(b);
	// return frag;
	//
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rd_menu:
			Intent iMenu = new Intent(getActivity(), DetailActivity.class);
			iMenu.putExtra("title", R.string.menu);
			iMenu.putExtra("id", resData.id);
			getActivity().startActivity(iMenu);
			break;
		case R.id.rd_hall:
			Intent iHall = new Intent(getActivity(), DetailActivity.class);
			iHall.putExtra("title", R.string.hall);
			iHall.putExtra("id", resData.id);
			getActivity().startActivity(iHall);
			break;
		default:
			break;
		}
	}
}
