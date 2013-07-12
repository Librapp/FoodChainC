package lms.foodchainC.widget;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

/**
 * @author 李梦思
 * @version 2.2
 * @createTime 2012-12-19
 * @description ViewPager适配
 * @changeLog
 */
public class NewPagerAdapter extends PagerAdapter {

	private List<ListView> mListViews;

	public NewPagerAdapter(List<ListView> mListViews) {
		this.mListViews = mListViews;
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		((ViewPager) collection).addView(mListViews.get(position), 0);
		// LayoutParams para = new LayoutParams();
		// para.width = 100;
		// para.height = LayoutParams.FILL_PARENT;
		// mListViews.get(position).setLayoutParams(para);
		return mListViews.get(position);
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView(mListViews.get(position));
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == (object);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
