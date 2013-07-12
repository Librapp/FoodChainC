package lms.foodchainC.util;

import android.content.Context;
import android.widget.Toast;

public class DialogUtil {

	public static void alertToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
