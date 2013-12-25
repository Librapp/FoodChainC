package lms.foodchainC.fragment;

import lms.foodchainC.R;
import lms.foodchainC.dao.Case_DBHelper;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.util.CommonUtils;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-18
 * @description 菜品详情模块
 * @changeLog
 */
public class CaseDetailFragment extends Fragment implements OnClickListener {

	private Case_DBHelper cdb;
	private ImageView pic;

	private TextView name, price, intro, cooktime;
	private RatingBar mark;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.casedetail, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cdb = new Case_DBHelper(getActivity());
		cdb.getCaseDetail(CaseData.current());
		initView();
		initData();
		loadLocalPic();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		View v = getView();
		name = (TextView) v.findViewById(R.id.name_txt);
		price = (TextView) v.findViewById(R.id.price_txt);
		intro = (TextView) v.findViewById(R.id.case_intro_context);
		cooktime = (TextView) v.findViewById(R.id.cooktime_num);
		mark = (RatingBar) v.findViewById(R.id.mark);
		v.findViewById(R.id.back).setOnClickListener(this);
		pic = (ImageView) v.findViewById(R.id.case_pic);
		pic.setOnClickListener(this);

	}

	// 加载本地图片
	private void loadLocalPic() {
		Drawable d = Drawable.createFromPath(CaseData.current().picPath);
		if (d != null) {
			pic.setImageDrawable(d);
		}
		int w = CommonUtils.dip2px(getActivity(), 100);
		RelativeLayout.LayoutParams lp = new LayoutParams(w, w);
		pic.setLayoutParams(lp);
		pic.setScaleType(ScaleType.FIT_XY);
	}

	private void initData() {
		mark.setRating((float) CaseData.current().mark);
		name.setText(CaseData.current().name);
		price.setText(CaseData.current().price + "");
		intro.setText(CaseData.current().intro);
		cooktime.setText(CaseData.current().cookTime + "");

		price.setVisibility(View.VISIBLE);
		intro.setVisibility(View.VISIBLE);
		cooktime.setVisibility(View.VISIBLE);

		mark.setEnabled(false);
		pic.setClickable(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			getActivity().finish();
			break;
		default:
			break;
		}
	}
}
