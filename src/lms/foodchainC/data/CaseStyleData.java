package lms.foodchainC.data;

import java.util.List;

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
	List<CaseData> list;
	public String startTime;
	public String endTime;
	public int weekday;

	public CaseStyleData() {

	}

	public CaseStyleData(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setList(List<CaseData> list) {
		this.list = list;
	}

	public List<CaseData> getList() {
		return list;
	}
}
