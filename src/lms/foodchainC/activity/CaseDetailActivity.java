package lms.foodchainC.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import lms.foodchainC.R;
import lms.foodchainC.data.CaseData;
import lms.foodchainC.data.OtherData;
import lms.foodchainC.util.BitmapUtils;
import lms.foodchainC.util.CommonUtils;
import lms.foodchainC.util.FileInfoUtils;
import lms.foodchainC.util.InfomationHelper;
import lms.foodchainC.util.MediaInfoUtils;
import lms.foodchainC.util.PicUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-18
 * @description 菜品详情
 * @changeLog
 */
public class CaseDetailActivity extends Activity implements OnClickListener {

	private String theSmall = null;
	private ImageView pic;
	private Bitmap result;

	private TextView name;
	private TextView price;
	private TextView intro;
	private TextView cooktime;
	private EditText nameEd;
	private EditText priceEd;
	private EditText introEd;
	private EditText cooktimeEd;
	private Button edit;
	private RatingBar mark;

	public static final int DETAIL = 0;
	public static final int CREATE = 1;
	public static final int EDIT = 2;

	private boolean isEdit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.casedetail);
		if (CaseData.current().isNew)
			isEdit = true;
		initView();
		initData();
		loadLocalPic();
	}

	private void initView() {
		name = (TextView) findViewById(R.id.name_txt);
		price = (TextView) findViewById(R.id.price_txt);
		intro = (TextView) findViewById(R.id.case_intro_context);
		cooktime = (TextView) findViewById(R.id.cooktime_num);
		nameEd = (EditText) findViewById(R.id.name_edit);
		priceEd = (EditText) findViewById(R.id.price_edit);
		introEd = (EditText) findViewById(R.id.case_intro_edit);
		cooktimeEd = (EditText) findViewById(R.id.case_cooktime_edit);
		mark = (RatingBar) findViewById(R.id.mark);
		edit = (Button) findViewById(R.id.edit);
		edit.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		pic = (ImageView) findViewById(R.id.case_pic);
		pic.setOnClickListener(this);

	}

	// 加载本地图片
	private void loadLocalPic() {
		Drawable d = Drawable.createFromPath(CaseData.current().picPath);
		if (d != null) {
			pic.setImageDrawable(d);
		}
		int w = CommonUtils.dip2px(getApplicationContext(), 100);
		RelativeLayout.LayoutParams lp = new LayoutParams(w, w);
		pic.setLayoutParams(lp);
		pic.setScaleType(ScaleType.FIT_XY);
	}

	private void initData() {
		mark.setRating((float) CaseData.current().mark);
		if (isEdit) {
			name.setVisibility(View.GONE);
			price.setVisibility(View.GONE);
			intro.setVisibility(View.GONE);
			cooktime.setVisibility(View.GONE);

			nameEd.setVisibility(View.VISIBLE);
			nameEd.setText(CaseData.current().name);
			priceEd.setVisibility(View.VISIBLE);
			priceEd.setText(CaseData.current().price + "");
			introEd.setVisibility(View.VISIBLE);
			introEd.setText(CaseData.current().intro);
			cooktimeEd.setVisibility(View.VISIBLE);
			cooktimeEd.setText(CaseData.current().cookTime + "");

			mark.setEnabled(true);
			edit.setText("保存");
			pic.setClickable(true);
		} else {
			nameEd.setVisibility(View.GONE);
			priceEd.setVisibility(View.GONE);
			introEd.setVisibility(View.GONE);
			cooktimeEd.setVisibility(View.GONE);

			name.setText(CaseData.current().name);
			price.setText(CaseData.current().price + "");
			intro.setText(CaseData.current().intro);
			cooktime.setText(CaseData.current().cookTime + "");

			price.setVisibility(View.VISIBLE);
			intro.setVisibility(View.VISIBLE);
			cooktime.setVisibility(View.VISIBLE);

			mark.setEnabled(false);
			edit.setText("编辑");
			pic.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit:
			if (isEdit) {
				try {
					CaseData.current().name = nameEd.getText().toString()
							.trim();
				} catch (Exception e) {
					Toast.makeText(this, "请输入正确格式的名称", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				// if (MenuService.getCaseDetail(CaseData.current())) {
				// Toast.makeText(this, "与已有菜名重复", Toast.LENGTH_SHORT).show();
				// break;
				// }

				try {
					CaseData.current().price = Float.parseFloat(priceEd
							.getText().toString());
				} catch (Exception e) {
					Toast.makeText(this, "请输入正确格式的价格", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				try {
					CaseData.current().intro = introEd.getText().toString();
				} catch (Exception e) {
					Toast.makeText(this, "请输入正确格式的简介", Toast.LENGTH_SHORT)
							.show();
					break;
				}

				try {
					CaseData.current().cookTime = Integer.parseInt(cooktimeEd
							.getText().toString());
				} catch (Exception e) {
					Toast.makeText(this, "请输入正确的烹饪时间，只能为正整数",
							Toast.LENGTH_SHORT).show();
					break;
				}

				CaseData.current().mark = mark.getRating();
				// if (CaseData.current().isNew)
				// MenuService.createCase(CaseData.current());
				// else
				// MenuService.upgradeCase(CaseData.current());
				setResult(RESULT_OK);
			}
			isEdit = !isEdit;
			initData();
			break;
		case R.id.case_pic:
			CharSequence[] items = { "相册", "拍照" };
			ChooseImage(items);
			break;
		case R.id.back:
			if (isEdit) {
				isEdit = false;
				initData();
				setResult(RESULT_CANCELED);
			} else {
				finish();
			}
			break;
		default:
			break;
		}
	}

	public void ChooseImage(CharSequence[] items) {
		AlertDialog imageDialog = new AlertDialog.Builder(
				CaseDetailActivity.this).setTitle("请选择图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							Intent intent = new Intent(
									Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
							startActivityForResult(intent, PicUtils.PHOTO);
						} else if (item == 1) {
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");

							String fileName = "FCRc.tmp";
							File camerFile = new File(InfomationHelper
									.getCamerPath(), fileName);
							Toast.makeText(CaseDetailActivity.this,
									InfomationHelper.getCamerPath(),
									Toast.LENGTH_SHORT).show();

							theSmall = InfomationHelper.getCamerPath()
									+ fileName;

							Uri originalUri = Uri.fromFile(camerFile);
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									originalUri);
							startActivityForResult(intent, PicUtils.CAMERA);
						}
					}
				}).create();
		imageDialog.show();
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, PicUtils.IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", PicUtils.PIC_SIZE_LIMITE_H);
		intent.putExtra("outputY", PicUtils.PIC_SIZE_LIMITE_W);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PicUtils.PHOTORESULT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case PicUtils.PHOTORESULT:
				Bundle extras = data.getExtras();
				if (extras != null) {
					result = extras.getParcelable("data");
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					result.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					BitmapUtils.saveBitmap(OtherData.CASE_PIC_SHORT,
							CaseData.current().name + ".jpg", result);
					pic.setImageBitmap(result);
					CaseData.current().picPath = OtherData.CASE_PIC
							+ CaseData.current().name + ".jpg";
				}
				break;
			case PicUtils.CAMERA:
				// 设置原始图像
				result = InfomationHelper.getFinalScaleBitmap(this, theSmall);
				pic.setImageBitmap(result);

				Uri uri = Uri.fromFile(new File(theSmall));
				startPhotoZoom(uri);
				break;
			case PicUtils.PHOTO:
				if (data == null)
					return;

				Uri thisUri = data.getData();
				try {
					result = MediaStore.Images.Media.getBitmap(
							getContentResolver(), thisUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] proj = { MediaStore.Images.Media.DATA };
				CursorLoader loader = new CursorLoader(this, thisUri, proj,
						null, null, null);
				loader.registerListener(0,
						new OnLoadCompleteListener<Cursor>() {

							@Override
							public void onLoadComplete(Loader<Cursor> loader,
									Cursor data) {
								if (data != null) {
									int column_index = data
											.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
									if (data.getCount() > 0
											&& data.moveToFirst()) {
										theSmall = data.getString(column_index);
										String attFormat = FileInfoUtils
												.getFileFormat(theSmall);
										if (!"photo".equals(MediaInfoUtils
												.getContentType(attFormat))) {
											Toast.makeText(
													CaseDetailActivity.this,
													"请选择图片文件",
													Toast.LENGTH_SHORT).show();
											return;
										}
										// 设置原始图片
										result = InfomationHelper
												.getFinalScaleBitmap(
														CaseDetailActivity.this,
														theSmall);
										pic.setImageBitmap(result);

										Uri uri1 = Uri.fromFile(new File(
												theSmall));
										startPhotoZoom(uri1);
									}
								}
							}
						});
				loader.startLoading();
				break;
			default:
				break;
			}
		} else {

		}
	}
}
