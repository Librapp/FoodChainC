package lms.foodchainC.data;

/**
 * @author 李梦思
 * @description 时间段
 * @createTime 2013-3-27
 * @version 1.0
 * 
 */
public class CaseTimeData extends CaseStyleData {
	/** 开始时间 */
	public String startTime = "00:00";
	/** 结束时间 */
	public String endTime = "23:59";
	public int weekday = 0;

	public CaseTimeData() {

	}

	public CaseTimeData(int id, String name, String startTime, String endTIme,
			int weekday) {
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTIme;
		this.weekday = weekday;
	}
}
