package lms.foodchainC.data;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * 
 * @author 李梦思
 * @version 1.0
 * @createTime 2012-11-17
 * @description 其他类型数据类
 * 
 */
public class OtherData {
	// 编辑
	public static boolean isEdit = false;

	public static Bitmap tempBitmap;
	// 当前位置
	public static String currentCity;

	// 添加按钮
	public static final int MENULIST_BTN_MORE = 0x11;
	public static final int MENULIST_BTN_ALLMORE = 0x12;
	// 所有菜单创建新菜
	public static final int MENULIST_BTN_CREATE = 0x13;
	// 结束编辑
	public static final int MENULIST_BTN_CONFIRM = 0x14;
	/* 图片相关 */
	public static final String PICTYPE = ".jpg";
	// 储存菜图片
	public static final String CASE_PIC = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "FCR/CASE/";
	public static final String CASE_PIC_SHORT = "FCR/CASE/";
	// 储存餐馆图片
	public static final String RDETAIL_PIC = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "FCR/RDETAIL/";
	public static final String RDETAIL_PIC_SHORT = "FCR/RDETAIL/";

	/* 数据库相关 */
	// 一般数据库
	public static final String NORMAL_DB = "fcrn.db";
	// 加密数据库
	public static final String SECRET_DB = "fcrs.db";
	// 顾客表
	public static final String CUSTOMERDATA = "customerData";
	public static final String CUSTOMERDATA_DB = "fcrcd.db";
	// 雇员表
	public static final String EMPLOYEEDATA = "employeeData";
	public static final String EMPLOYEEDATA_DB = "fcred.db";
	// 原料表
	public static final String MATERIALDATA = "materialData";
	public static final String MATERIALDATA_DB = "fcrmad.db";
	// 评论表
	public static final String COMMENTDATA = "commentData";
	// 消息表
	public static final String MEASSAGEDATA = "messageData";
	// 聊天表
	public static final String CHATDATA = "chatData";

	/* 配置文件 */
	// 本地数据
	public static final String LOCALSETTING = "localSetting";
	// 网络数据
	public static final String ONLINESETTING = "onlineSetting";
	/** 缓存餐厅信息 */
	public static final String TEMPRESID = "tempResInfo";
	// 设备类型
	public static final String CUSTOMERDEVICETYPE = "urn:schemas-upnp-org:device:Customer";
	public static final String WAITERDEVICETYPE = "urn:schemas-upnp-org:device:Waiter";
	public static final String RESTAURANTDEVICETYPE = "urn:schemas-upnp-org:device:Restaurant";
	public static final String CLEANERDEVICETYPE = "urn:schemas-upnp-org:device:Cleaner";
	public static final String COOKERDEVICETYPE = "urn:schemas-upnp-org:device:Cooker";

	// 描述文件
	public static final String DESCRIPTIONURL = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "FCR"
			+ File.separator + "description.xml";
}
