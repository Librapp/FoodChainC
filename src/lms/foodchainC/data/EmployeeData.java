package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 雇员数据类
 * 
 */
import java.util.List;

import org.cybergarage.upnp.Device;

public class EmployeeData extends UserData {
	// 职位 1厨师2服务员3保洁
	public int position = 0;
	public static final int COOKER = 1;
	public static final int WAITER = 2;
	public static final int CLEANER = 3;

	// 负责的桌子
	private List<TableData> table;
	// 做的菜
	private List<CaseData> caseList;

	public static EmployeeData current;

	public EmployeeData() {

	}

	public EmployeeData(int position) {
		this.position = position;
	}

	public EmployeeData(Device d, int position) {
		this.name = d.getFriendlyName();
		this.headPic = d.getLocation() + d.getIcon(0).getURL();
		this.position = position;
	}

	public static EmployeeData current() {
		if (current == null)
			current = new EmployeeData();
		return current;
	}

	public List<TableData> getTable() {
		return table;
	}

	public void setTable(List<TableData> table) {
		this.table = table;
	}

	public List<CaseData> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<CaseData> caseList) {
		this.caseList = caseList;
	}
}
