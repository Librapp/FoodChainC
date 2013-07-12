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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-18
 * @description 顾客详情
 * @changeLog
 */
public class CustomerDetailActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private Button addFriend;
	private TextView title;

	private ImageView headPic;
	private TextView point;
	private TextView credit;

	private TextView notOrder;
	private ListView bill;
	private MenuAdapter ma;
	private AsyncImageLoader asyncImageLoader;

	private BillData cBill;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerdetail);

		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initViews() {
		title = (TextView) findViewById(R.id.customer_name);
		findViewById(R.id.customer_back).setOnClickListener(this);

		headPic = (ImageView) findViewById(R.id.customer_headpic);
		point = (TextView) findViewById(R.id.customer_point);
		credit = (TextView) findViewById(R.id.customer_credit);
		addFriend = (Button) findViewById(R.id.customer_addfriend);
		addFriend.setOnClickListener(this);
		findViewById(R.id.customer_sendmail).setOnClickListener(this);
		findViewById(R.id.customer_sayhi).setOnClickListener(this);

		notOrder = (TextView) findViewById(R.id.customer_notorder);
		bill = (ListView) findViewById(R.id.customer_bill);
		bill.setOnItemClickListener(this);
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
		point.setText(CustomerData.current().point);
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
		case R.id.customer_addfriend:
			if (CustomerData.current().isFriend) {
				delFriend();
			} else {
				addFriend();
			}
			break;
		case R.id.customer_sendmail:
			sendmail();
			break;
		case R.id.customer_sayhi:
			sayhi();
			break;
		default:
			break;
		}
	}

	private void addFriend() {
		// TODO Auto-generated method stub

	}

	private void delFriend() {
		// TODO Auto-generated method stub

	}

	private void sayhi() {
		// TODO Auto-generated method stub

	}

	private void sendmail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}
}
