package lms.foodchainC.net;

/**
 * @author 李梦思
 * @version 1.0
 * @createTime 2013-5-22
 * @description 餐厅详情请求类
 * @changeLog
 */
public class ResDetailRequest extends JSONRequest {

	public ResDetailRequest() {
		this.method = NetMessageType.RESDETAIL;
	}

	@Override
	public String make() {
		return holder.toString();
	}

}
