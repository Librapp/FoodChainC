package lms.foodchainC.activity;

import java.util.List;

import lms.foodchainC.R;
import lms.foodchainC.data.CommentData;
import lms.foodchainC.widget.ChatListAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-9-25
 * @description
 * @changeLog
 */
public class ChatActivity extends TabActivity implements OnClickListener,
		OnItemClickListener {

	private ListView comment;
	private ListView chat;
	private ListView message;

	private ChatListAdapter cla;
	private LinearLayout inputLayout;
	private EditText input;
	private LinearLayout addLayout;
	private GridView emotiongrid;
	private TabHost tabhost;

	private List<CommentData> commentList;

	// private List<Customer> chatList;
	// private List<Message> messageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		initViews();
	}

	private void initViews() {
		tabhost = getTabHost();
		comment = (ListView) findViewById(R.id.comment);
		chat = (ListView) findViewById(R.id.chat);
		message = (ListView) findViewById(R.id.message);
		tabhost.addTab(tabhost.newTabSpec("comment").setIndicator("评论")
				.setContent(R.id.comment));
		tabhost.addTab(tabhost.newTabSpec("chat").setIndicator("私聊")
				.setContent(R.id.chat));
		tabhost.addTab(tabhost.newTabSpec("message").setIndicator("消息")
				.setContent(R.id.message));
		// 设置TabHost的背景颜色
		tabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));
		// 设置TabHost的背景图片资源
		// tabhost.setBackgroundResource(R.drawable.bg0);
		// 设置当前显示哪个标签
		tabhost.setCurrentTab(0);
		// 标签切换事件处理，setOnTabChangedListener
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				if (tabId.equals("comment")) {

				} else if (tabId.equals("chat")) {

				} else if (tabId.equals("message")) {

				}
			}
		});
		inputLayout = (LinearLayout) findViewById(R.id.chat_input);

		inputLayout.findViewById(R.id.input_send).setOnClickListener(this);
		inputLayout.findViewById(R.id.input_voice).setOnClickListener(this);
		inputLayout.findViewById(R.id.input_add).setOnClickListener(this);
		addLayout = (LinearLayout) inputLayout
				.findViewById(R.id.input_add_layout);
		inputLayout.findViewById(R.id.input_emotion).setOnClickListener(this);
		inputLayout.findViewById(R.id.input_pic).setOnClickListener(this);
		emotiongrid = (GridView) inputLayout
				.findViewById(R.id.input_emotion_grid);
		input = (EditText) inputLayout.findViewById(R.id.input_ed);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.input_send:
			addLayout.setVisibility(View.GONE);
			emotiongrid.setVisibility(View.GONE);

			break;
		case R.id.input_voice:

			break;
		case R.id.input_add:
			if (addLayout.isShown()) {
				addLayout.setVisibility(View.GONE);

			} else {
				addLayout.setVisibility(View.VISIBLE);

			}
			break;
		case R.id.input_pic:

			break;
		case R.id.input_emotion:
			if (emotiongrid.isShown()) {
				emotiongrid.setVisibility(View.GONE);

			} else {
				emotiongrid.setVisibility(View.VISIBLE);

			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("确定要退出吗?");
		builder.setCancelable(false);
		builder.setIcon(R.drawable.icon);
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

}
