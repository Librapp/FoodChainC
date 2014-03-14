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

/**
 * 
 * @author Luke
 * @description 输入模块
 * @createTime 2014/3/13
 */
public class InputFragment extends Fragment implements TextWatcher,
		OnClickListener {
	private ImageButton voice, add;
	private EditText ed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.input, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initViews();
		super.onActivityCreated(savedInstanceState);
	}

	private void initViews() {
		View v = getView();
		voice = (ImageButton) v.findViewById(R.id.input_voice);
		voice.setOnClickListener(this);
		ed = (EditText) v.findViewById(R.id.input_ed);
		ed.addTextChangedListener(this);
		add = (ImageButton) v.findViewById(R.id.input_add);
		add.setOnClickListener(this);
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

			break;
		case R.id.input_add:

			break;
		default:
			break;
		}
	}

}
