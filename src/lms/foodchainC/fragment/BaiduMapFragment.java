package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.activity.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 
 * @author 梦思
 * @description 百度地图模块
 * @createTime 2013/12/17
 */
public class BaiduMapFragment extends Fragment implements OnClickListener {
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	private EditText edit;
	private String target;
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getActivity());
		mBMapMan.init("9MWcjx4PePAbpKKIGkEGNITV", null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.baidumap, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		View v = getView();
		edit = (EditText) v.findViewById(R.id.edit);
		v.findViewById(R.id.changeview).setOnClickListener(this);
		v.findViewById(R.id.search_btn).setOnClickListener(this);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		mMapView = (MapView) v.findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);// 设置地图zoom级别
	}

	@Override
	public void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changeview:
			mContent = new SearchFragment();
			switchFragment(mContent);
			break;
		case R.id.search_btn:
			if (edit.getText().toString().trim().equals("")) {
				Toast.makeText(getActivity(), "请输入搜索关键字", Toast.LENGTH_SHORT)
						.show();
				return;
			} else
				target = edit.getText().toString().trim();
			search(target);
			break;
		default:
			break;
		}
	}

	private void search(String target) {
		// TODO Auto-generated method stub

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
