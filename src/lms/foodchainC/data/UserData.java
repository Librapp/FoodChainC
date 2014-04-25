package lms.foodchainC.data;

import java.util.List;

import org.cybergarage.upnp.Device;

/**
 * @author 李梦思
 * @description 用户基类
 */
public class UserData {
	private static UserData self;
	public static UserData current = new UserData();
	/** 就餐人数 */
	public int peoplecount = 1;
	// ID
	public int id = 0;
	// 用户名
	public String name = "";
	/** 签名 */
	public String signature = "";
	/** 昵称 */
	public String nickname = "";
	// 头像
	public String headPic = "";
	// 地址ַ
	public String address = "";
	// 简介
	public String intro = "";
	// 邮箱
	public String email = "";
	// 电话
	public String tel = "";
	// 来的时间
	public String comeTime = "";
	// 离开时间
	public String leaveTime = "";
	/** 密码 */
	public String password = "";
	// 级别
	public int level = 0;
	// 信用
	public int credit = 0;
	// 积分
	public int point = 0;
	// 性别0女1男2其他
	public int gender = 0;
	// 性取向0女1男2其他
	public int sex = 0;
	// 状态
	public int state = 0;
	/** 隐私设置 0不需要验证1需要 */
	public int security = 0;
	// 设备
	public Device device;
	// 评论
	private List<CommentData> comment;
	// 消息
	private List<MessageData> message;

	public static UserData self() {
		if (self == null) {
			self = new UserData();
		}
		return self;
	}

	public void setComment(List<CommentData> comment) {
		this.comment = comment;
	}

	public List<CommentData> getComment() {
		return comment;
	}

	public void setMessage(List<MessageData> message) {
		this.message = message;
	}

	public List<MessageData> getMessage() {
		return message;
	}

}
