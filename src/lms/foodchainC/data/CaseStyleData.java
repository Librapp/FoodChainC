package lms.foodchainC.data;

import java.util.ArrayList;

/**
 * @author 李梦思
 * @description 菜单分类数据类
 * @createTime 2013-3-26
 * @version 1.0
 * 
 */
public class CaseStyleData {

	public int id;
	public String name;
	ArrayList<CaseData> list;
	public String startTime;
	public String endTime;
	public int weekday;

	public CaseStyleData() {

	}

	public CaseStyleData(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setList(ArrayList<CaseData> list) {
		this.list = list;
	}

	public ArrayList<CaseData> getList() {
		return list;
	}
}
