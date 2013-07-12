package lms.foodchainC.dao;

/**
 * 
 * @author 李梦思
 * @description 数据库读取监听器
 * 
 */
public interface LoadDataListener {

	public void onLoadDBStart();

	public void onLoadDBOver(String errorMessage);
}
