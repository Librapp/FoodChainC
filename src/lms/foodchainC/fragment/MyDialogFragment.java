package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {
	public static final int OPENWIFI = 0;
	private static int mNum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_yesorno, container);
		switch (mNum) {
		case OPENWIFI:
			v = inflater.inflate(R.layout.dialog_yesorno, container);
			v.findViewById(R.id.yes).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					WifiManager wifiManager = (WifiManager) getActivity()
							.getSystemService(Context.WIFI_SERVICE);
					if (!wifiManager.setWifiEnabled(true))
						Toast.makeText(getActivity(), "请打开wifi然后重试",
								Toast.LENGTH_SHORT).show();
					dismiss();
				}
			});
			v.findViewById(R.id.no).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		default:
			break;
		}
		return v;
	}

	public static MyDialogFragment openWifiInstance() {
		mNum = OPENWIFI;
		MyDialogFragment f = new MyDialogFragment();
		Bundle args = new Bundle();
		args.putCharSequence("title", "是否开启wifi");
		f.setArguments(args);
		return f;
	}
}
