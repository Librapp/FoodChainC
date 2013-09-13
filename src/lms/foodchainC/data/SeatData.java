package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 座位数据类
 * 
 */
public class SeatData {
	public static final int DIRTY = 1;
	public static final int AVAILIABLE = 2;
	public static final int OCCUPY = 3;
	public static final int HAVING = 4;
	public static final int WAITING = 5;
	public static final int PAY = 6;

	public int state = AVAILIABLE;
	// 类型
	public String styleId;
	// 桌号
	public String tableId;
	// 座位号
	public String seatId;
	// 图标
	public String icon;
	// 预定时间
	public String bookTime;
	/** 顾客Id */
	public int customerId;
	/** 顾客姓名 */
	public String customerName;
	/** 顾客头像 */
	public String customerPic;
	/** 顾客地址 */
	public String customerURL;

	private static SeatData current;

	public static SeatData current() {
		if (current == null) {
			current = new SeatData();
		}
		return current;
	}

	public SeatData() {

	}

	public SeatData(String seatId, String tableId, String styleId, int status,
			int customerId, String customerName, String bookTime) {
		this.seatId = seatId;
		this.tableId = tableId;
		this.state = status;
		this.styleId = styleId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.bookTime = bookTime;
	}

	public SeatData(TableData t, int j) {
		String id = j + "";
		if (j < 10) {
			id = "0" + id;
		}
		this.seatId = t.id + id;
		this.tableId = t.id;
		this.styleId = t.styleId;
	}
}
