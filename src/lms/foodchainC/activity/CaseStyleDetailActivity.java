package lms.foodchainC.activity;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.CaseStyleData;
import lms.foodchainC.widget.MenuAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 李梦思
 * @description 编辑分类界面
 * @createTime 2013-3-26
 * @version 1.0
 * 
 */
public class CaseStyleDetailActivity extends Activity implements
		OnClickListener, OnItemLongClickListener {

	private TextView name;
	private ListView listView;

	private final int REQUEST_CREATECASE = 1;
	private final int DETAIL = 1;
	private final int DELETE = 2;
	private int currentItem = 0;

	private CaseStyleData csd;
	private MenuAdapter ma;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.casestyledetail);

		initViews();
		initData();
	}

	private void initViews() {
		name = (TextView) findViewById(R.id.name);
		listView = (ListView) findViewById(R.id.list);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);
	}

	private void initData() {
		csd = new CaseStyleData();
		csd.id = getIntent().getIntExtra("id", 0);
		csd.name = getIntent().getStringExtra("name");
		// if (MenuService.getCaseStyleData(csd)) {
		// name.setText(csd.name);
		// } else {
		// setResult(RESULT_CANCELED);
		// finish();
		// }
		ma = new MenuAdapter(this, csd.getList());
		listView.setAdapter(ma);
		listView.setOnItemLongClickListener(this);
		listView.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:
			openCreateNewCaseActivity();
			break;
		default:
			break;
		}
	}

	private void openCreateNewCaseActivity() {
		Intent i = new Intent(this, CaseDetailActivity.class);
		CaseData.current = new CaseData();
		CaseData.current().isNew = true;
		CaseData.current().style = csd.id;
		startActivityForResult(i, REQUEST_CREATECASE);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		currentItem = csd.getList().get(position).id;
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, DETAIL, 1, "查看");
		menu.add(0, DELETE, 2, "删除");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DETAIL:
			Intent i = new Intent(this, CaseDetailActivity.class);
			CaseData.current = new CaseData();
			CaseData.current().isNew = false;
			CaseData.current().id = currentItem;
			startActivity(i);
			return true;
		case DELETE:
			CaseData c = new CaseData();
			c.id = currentItem;
			// if (MenuService.deleteCase(c)) {
			// // TODO 刷新
			// Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			// } else
			// Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}
