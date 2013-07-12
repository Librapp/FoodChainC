package lms.foodchainC.data;

import java.util.List;

import org.cybergarage.upnp.Device;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 餐厅数据类
 * 
 */
public class RestaurantData extends UserData {
	// 营业时间
	public String opentime = "9:00-21:00";
	// 订餐短信
	public String sms = "13800138000";
	// 座位数
	public int seatCount = 0;
	// 空座位数
	public int freeseat = 0;
	// 排队人数
	public int waitNumber = 0;
	// 是否营业
	public boolean isOpen = false;
	// 服务生
	private List<EmployeeData> waiter;
	// 厨师
	private List<EmployeeData> cooker;
	// 保洁员
	private List<EmployeeData> cleaner;
	// 座位
	private List<SeatData> seat;
	// 桌子类型列表
	private List<TableStyle> tableStyle;
	// 桌子列表
	private List<TableData> table;
	// 顾客
	private List<CustomerData> customer;
	// 原料
	private List<MaterialData> materials;

	private static RestaurantData current;

	public RestaurantData() {

	}

	public RestaurantData(Device d) {
		this.id = d.getUDN();
		this.name = d.getFriendlyName();
		this.headPic = d.getLocation() + d.getIcon(0).getURL();
	}

	public static RestaurantData current() {
		if (current == null) {
			current = new RestaurantData();
		}
		return current;
	}

	public void setWaiter(List<EmployeeData> waiter) {
		this.waiter = waiter;
	}

	public List<EmployeeData> getWaiter() {
		return waiter;
	}

	public void setCooker(List<EmployeeData> cooker) {
		this.cooker = cooker;
	}

	public List<EmployeeData> getCooker() {
		return cooker;
	}

	public void setSeat(List<SeatData> seat) {
		this.seat = seat;
	}

	public List<SeatData> getSeat() {
		return seat;
	}

	public void setCustomer(List<CustomerData> customer) {
		this.customer = customer;
	}

	public List<CustomerData> getCustomer() {
		return customer;
	}

	public void setTableStyle(List<TableStyle> tableStyle) {
		this.tableStyle = tableStyle;
	}

	public List<TableStyle> getTableStyle() {
		return tableStyle;
	}

	public void setTable(List<TableData> table) {
		this.table = table;
	}

	public List<TableData> getTable() {
		return table;
	}

	public void setCleaner(List<EmployeeData> cleaner) {
		this.cleaner = cleaner;
	}

	public List<EmployeeData> getCleaner() {
		return cleaner;
	}

	public void setMaterials(List<MaterialData> materials) {
		this.materials = materials;
	}

	public List<MaterialData> getMaterials() {
		return materials;
	}
}
