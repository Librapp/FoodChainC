package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.data.BillData;
import lms.foodchainC.data.CustomerData;
import lms.foodchainC.widget.AsyncImageLoader;
import lms.foodchainC.widget.AsyncImageLoader.ImageCallback;
import lms.foodchainC.widget.MenuAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-18
 * @description 雇员详情
 * @changeLog
 */
public class EmployeeDetailActivity extends BaseActivity implements
		OnClickListener {
	private Button addFriend;
	private TextView title;

	private ImageView headPic;
	private TextView level;
	private TextView credit;

	private TextView notOrder;
	private ListView bill;
	private MenuAdapter ma;
	private BillData cBill;

	private AsyncImageLoader asyncImageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employeedetail);

		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initViews() {
		title = (TextView) findViewById(R.id.ed_name);
		findViewById(R.id.ed_back).setOnClickListener(this);

		headPic = (ImageView) findViewById(R.id.ed_headpic);
		level = (TextView) findViewById(R.id.ed_level);
		credit = (TextView) findViewById(R.id.customer_credit);
		addFriend = (Button) findViewById(R.id.customer_addfriend);
		addFriend.setOnClickListener(this);
		findViewById(R.id.customer_sendmail).setOnClickListener(this);
		findViewById(R.id.customer_sayhi).setOnClickListener(this);

		notOrder = (TextView) findViewById(R.id.customer_notorder);
		asyncImageLoader = new AsyncImageLoader();

	}

	private void initData() {
		title.setText(CustomerData.current().name);

		Drawable cachedImage = asyncImageLoader.loadDrawable(
				CustomerData.current().headPic, new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						headPic.setImageDrawable(imageDrawable);
					}
				});

		if (cachedImage == null) {
			headPic.setImageResource(R.drawable.seat_icon_default);
		} else {
			headPic.setImageDrawable(cachedImage);
		}
		level.setText(CustomerData.current().point);
		credit.setText(CustomerData.current().credit);

		if (CustomerData.current().bill != null) {
			cBill = CustomerData.current().bill;
			ma = new MenuAdapter(this, cBill.getCaseList());
			bill.setAdapter(ma);
			bill.setVisibility(View.VISIBLE);
			notOrder.setVisibility(View.GONE);
		} else {
			// TODO ���ֵ��Ч����Ϣѯ�ʻ�з���Ա

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ed_message:
			sendMessage();
			break;
		case R.id.ed_call:

			break;
		default:
			break;
		}
	}

	private void sendMessage() {
		// TODO Auto-generated method stub

	}
}
