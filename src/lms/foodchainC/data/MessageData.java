package lms.foodchainC.data;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 消息数据类
 * 
 */
public class MessageData {
	private static MessageData current;
	// 接收时间
	public String time;
	// 文字内容
	public String content;
	// 图片
	public String pic = "";
	// 链接
	public String url;
	// 用户名
	public String userName;
	/** 用户头像 */
	public String userPic;
	/** 发送用户Id */
	public int sId;
	/** 接收用户Id */
	public int rId;
	// 消息id
	public int id;
	/** 来源 0发1收 */
	public int direction = 1;
	/** 是否已读 0否1是 */
	public int hasRead = 0;

	public static MessageData current() {
		if (current == null) {
			current = new MessageData();
		}
		return current;
	}
}
