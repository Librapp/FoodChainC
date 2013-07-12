package lms.foodchainC.netUtil;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-1-17
 * @description 网络通信监听接口
 * @changLog
 */
public interface NetConnectionListener {
	public void onNetBegin(String method);

	public void onNetEnd(String method, String msg);

	public void onCancel(String method);
}
