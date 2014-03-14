package lms.foodchainC.fragment;

import lms.foodchainC.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 
 * @author 梦思
 * @description 对话模块
 * @createTime 2014/3/13
 */
public class ConversationFragment extends Fragment implements TextWatcher,
		OnClickListener {
	private ListView list;
	private ImageButton voice, add;
	private EditText ed;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.conversation, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		View v = getView();
		list = (ListView) v.findViewById(R.id.list);
		voice = (ImageButton) v.findViewById(R.id.input_voice);
		voice.setOnClickListener(this);
		ed = (EditText) v.findViewById(R.id.input_ed);
		ed.addTextChangedListener(this);
		add = (ImageButton) v.findViewById(R.id.input_add);
		add.setOnClickListener(this);
	}

	public static ConversationFragment newInstance(int id) {
		ConversationFragment f = new ConversationFragment();
		Bundle args = new Bundle();
		args.putInt("id", id);
		f.setArguments(args);
		return f;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.input_voice:
			// TODO 发声音
			break;
		case R.id.input_add:
			// TODO 添加表情

			break;
		default:
			break;
		}
	}
}
