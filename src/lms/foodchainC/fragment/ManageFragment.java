package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.data.Self;
import lms.foodchainC.util.CommonUtils;
import lms.foodchainC.util.PicUtils;
import lms.foodchainC.util.SharePerformanceUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ManageFragment extends Fragment implements OnClickListener,
		OnItemLongClickListener {
	private PicUtils pu;

	private static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 1;
	private static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 2;
	public static final int PHOTORESULT = 4;

	public static final int PIC_SIZE_LIMITE_W = 150;
	public static final int PIC_SIZE_LIMITE_H = 150;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private String theSmall = null;
	private String theLarge = null;

	private Bitmap smallResult;
	private Bitmap largeResult;
	private Bitmap result;

	private RelativeLayout rdButtom;
	private LinearLayout rdMiddle;

	private ImageView logo;
	private ImageView bg;

	private TextView name;
	private TextView tel;
	private TextView location;
	private TextView intro;

	private EditText nameE;
	private EditText telE;
	private EditText locationE;
	private EditText introE;

	private Button edit;
	private Button open;

	// 编辑餐厅信息
	private boolean modified = false;

	private String FileDIR;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.restaurantdetail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initViews();
		getData();
		super.onActivityCreated(savedInstanceState);
	}

	private void getData() {

	}

	private void initViews() {
		rdMiddle = (LinearLayout) getView().findViewById(R.id.rd_mid);
		rdButtom = (RelativeLayout) getView().findViewById(R.id.rd_bottom);
		FileDIR = OtherData.RDETAIL_PIC + "logo.jpg";
		logo = (ImageView) getView().findViewById(R.id.rd_logo);

		Drawable d = Drawable.createFromPath(FileDIR);
		if (d != null) {
			logo.setImageDrawable(d);
		}
		int w = CommonUtils.dip2px(getActivity(), 100);
		RelativeLayout.LayoutParams lp = new LayoutParams(w, w);
		logo.setLayoutParams(lp);
		logo.setScaleType(ScaleType.FIT_XY);
		logo.setClickable(false);
		logo.setOnClickListener(this);

		bg = (ImageView) getView().findViewById(R.id.rd_bg);
		bg.setOnClickListener(this);

		tel = (TextView) getView().findViewById(R.id.rd_tel_num);
		tel.setText(Self.current().tel);
		telE = (EditText) getView().findViewById(R.id.rd_tel_edit);

		location = (TextView) getView().findViewById(R.id.rd_location_txt);
		location.setText(Self.current().address);
		locationE = (EditText) getView().findViewById(R.id.rd_location_edit);

		intro = (TextView) getView().findViewById(R.id.rd_intro_txt);
		intro.setText(Self.current().intro);
		introE = (EditText) getView().findViewById(R.id.rd_intro_edit);

		edit = (Button) getView().findViewById(R.id.rd_edit);
		edit.setOnClickListener(this);
		getView().findViewById(R.id.rd_employee).setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rd_edit:
			if (modified) {
				if (!nameE.getText().toString().trim().equals("")) {
					Self.current().name = nameE.getText().toString();
					name.setText(Self.current().name);
				}
				nameE.setVisibility(View.GONE);
				name.setVisibility(View.VISIBLE);
				if (!telE.getText().toString().equals("")) {
					Self.current().tel = telE.getText().toString();
					tel.setText(Self.current().tel);
				}
				telE.setVisibility(View.GONE);
				tel.setVisibility(View.VISIBLE);
				if (!locationE.getText().toString().equals("")) {
					Self.current().address = locationE.getText().toString();
					location.setText(Self.current().address);
				}
				locationE.setVisibility(View.GONE);
				location.setVisibility(View.VISIBLE);
				if (!introE.getText().toString().equals("")) {
					Self.current().intro = introE.getText().toString();
					intro.setText(Self.current().intro);
				}
				introE.setVisibility(View.GONE);
				intro.setVisibility(View.VISIBLE);
				open.setVisibility(View.VISIBLE);
				rdMiddle.setVisibility(View.VISIBLE);
				rdButtom.setVisibility(View.VISIBLE);
				// TODO
				SharePerformanceUtil.saveLInfo(getActivity());
				logo.setClickable(false);
				edit.setText("编辑");
				modified = false;
			} else {
				rdMiddle.setVisibility(View.GONE);
				rdButtom.setVisibility(View.GONE);
				open.setVisibility(View.GONE);
				name.setVisibility(View.GONE);
				nameE.setVisibility(View.VISIBLE);
				tel.setVisibility(View.GONE);
				telE.setVisibility(View.VISIBLE);
				location.setVisibility(View.GONE);
				locationE.setVisibility(View.VISIBLE);
				intro.setVisibility(View.GONE);
				introE.setVisibility(View.VISIBLE);

				logo.setClickable(true);
				edit.setText("完成");
				modified = true;
			}
			break;
		case R.id.rd_logo:

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}
}
