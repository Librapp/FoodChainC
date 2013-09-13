package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 桌子数据类
 * 
 */
import java.util.List;

public class TableData {
	public static final int DIRTY = 1;
	public static final int AVAILIABLE = 2;
	public static final int OCCUPY = 3;
	public static final int HAVING = 4;
	public static final int WAITING = 5;
	public static final int PAY = 6;
	// 服务生Id
	public int waiterId;
	// 座位数
	public int seatCount = 4;
	// 状态
	public int state = AVAILIABLE;
	// 座位
	private List<SeatData> seat;
	// 桌号
	public String id = "";
	/** 饭店ID */
	public String restaurantId = "";
	// 类型
	public String styleId = "";
	// 预定时间
	public String bookTime;
	// 图标
	public String icon;
	/**
	 * @param 顾客Id
	 */
	public int customerId;
	/**
	 * @param 顾客姓名
	 */
	public String customerName;

	private static TableData current;

	public TableData() {

	}

	public TableData(TableStyleData ts, int i) {
		String id = i + "";
		if (i < 10) {
			id = "00" + id;
		} else if (i < 100) {
			id = "0" + id;
		}
		this.id = ts.id + id;
		this.styleId = ts.id;
		this.seatCount = ts.seatCount;
	}

	public TableData(String tableId, String styleId, int seatCount, int state,
			int customerId, String customerName, String bookTime) {
		this.id = tableId;
		this.styleId = styleId;
		this.seatCount = seatCount;
		this.state = state;
		this.customerId = customerId;
		this.customerName = customerName;
		this.bookTime = bookTime;
	}

	public static TableData current() {
		if (current == null) {
			current = new TableData();
		}
		return current;
	}

	public void setSeat(List<SeatData> seat) {
		this.seat = seat;
	}

	public List<SeatData> getSeat() {
		return seat;
	}
}
