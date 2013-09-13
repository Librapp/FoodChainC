package lms.foodchainC.widget;

import java.util.List;

import lms.foodchainC.R;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WifiScanAdapter extends BaseAdapter {
	private List<ScanResult> wifiScanResult;
	private Context context;

	public WifiScanAdapter(Context context, List<ScanResult> scanResults) {
		wifiScanResult = scanResults;
		this.context = context;
	}

	@Override
	public int getCount() {
		return wifiScanResult.size();
	}

	@Override
	public Object getItem(int position) {
		return wifiScanResult.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.wifiscanresult_list_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		final ScanResult result = wifiScanResult.get(position);
		holder.text.setText(result.SSID);
		return convertView;
	}

}
