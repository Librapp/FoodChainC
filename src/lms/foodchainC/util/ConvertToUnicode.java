package lms.foodchainC.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author 郭鹏
 * @version 2.0
 * @createTime
 * @description 汉字与Unicode转换
 * @changLog
 */
public class ConvertToUnicode {

	/**
	 * �?汉字转换�?Unicode编码的内码格式�?
	 * 
	 * @param strs
	 * @return 汉字转为Unicode编码
	 */
	public static String StrTOUnicode(String strs) {
		StringBuffer str = new StringBuffer();
		int len = strs.length();
		String[] s = new String[len];
		for (int i = 0; i < len; i++) {
			char c = strs.charAt(i);
			s[i] = Integer.toString(c, 16);
			str.append(s[i]);
		}
		return str.toString();
	}

	// 汉字，英文，数字混合串转换为unicode
	public static String AllStrTOUnicode(String strs) {
		StringBuffer str = new StringBuffer();
		int len = strs.length();
		String[] s = new String[len];
		for (int i = 0; i < len; i++) {
			char c = strs.charAt(i);
			// // 数字�?
			// if (c >= '0' && c <= '9'){
			// s[i] = Integer.toString(c, 16);
			// str.append("00");
			// str.append(s[i]);
			// }
			// // 小写英文字母�?
			// else if (c >= 'a' && c <= 'z'){
			// s[i] = Integer.toString(c, 16);
			// str.append("00");
			// str.append(s[i]);
			// }
			// // 大写英文字母�?
			// else if (c >= 'A' && c <= 'z'){
			// s[i] = Integer.toString(c, 16);
			// str.append("00");
			// str.append(s[i]);
			// }
			// 带标点符号的�?��字符
			if (c >= ' ' && c <= '~') {
				s[i] = Integer.toString(c, 16);
				str.append("00");
				str.append(s[i]);
			}
			// 是汉�?
			else if (Character.isLetter(c)) {
				s[i] = Integer.toString(c, 16);
				str.append(s[i]);
			}

			else {
				s[i] = Integer.toString(c, 16);
				str.append(s[i]);
			}
		}
		return str.toString();
	}

	/**
	 * �?Unicode编码的内码格式转换到 汉字�?
	 * 
	 * @param strs
	 * @return
	 */
	public static String UnicodeTOStr(String strs) {
		StringBuffer str = new StringBuffer();
		int len = strs.length();
		String[] s = new String[len];
		s = strs.split("");
		for (int i = 1; i < s.length - 3; i += 4) {
			char c = (char) Integer.valueOf(
					s[i] + s[i + 1] + s[i + 2] + s[i + 3], 16).intValue();
			str.append(c);
			// System.out.println("\\u" + s[i]+s[i+1]+s[i+2]+s[i+3] + "\t" + c);
		}
		// System.out.println("UnicodeTOStr:"+str.toString());
		return str.toString();
	}

	// 汉字转换为UTF-8编码方式
	public static String strToUTF8(String str) {
		if (str != null) {
			try {
				str = new String(str.trim().getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
			return str;
		} else {
			return "";
		}
	}

}