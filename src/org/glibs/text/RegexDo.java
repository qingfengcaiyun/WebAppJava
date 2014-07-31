package org.glibs.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDo {

	public static Boolean isMatchReg(String regex, String str) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return Boolean.valueOf(m.matches());
	}

	public static Boolean isInteger(String str) {
		return isMatchReg("^-?[1-9]+[0-9]*$", str);
	}

	public static Boolean isDouble(String str) {
		return isMatchReg("^-?([1-9]+[0-9]*.[0-9]+|0)$", str);
	}

	public static Boolean isEnglish(String str) {
		return isMatchReg("^[A-Za-z]+$", str);
	}

	public static Boolean isEnglishUpper(String str) {
		return isMatchReg("^[A-Z]+$", str);
	}

	public static Boolean isEnglishLower(String str) {
		return isMatchReg("^[a-z]+$", str);
	}

	public static Boolean isNumberAndEnglish(String str) {
		return isMatchReg("^[A-Za-z0-9]+$", str);
	}

	public static Boolean isNumberAndEnglishAndUnderLine(String str) {
		return isMatchReg("^[A-Za-z0-9_]+$", str);
	}

	public static Boolean isNumberString(String str) {
		return isMatchReg("^[0-9]+$", str);
	}

	public static Boolean isChinese(String str) {
		return isMatchReg("^[u4e00-u9fa5]+$", str);
	}

	public static Boolean isIDCardNo(String str) {
		return isMatchReg("^[1-9]?([0-9]{14}|[0-9]{17}|[0-9]{16}(x|X))$", str);
	}

	public static Boolean isEmail(String str) {
		return isMatchReg("w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$", str);
	}

	public static Boolean isUrl(String str) {
		return isMatchReg("^(http|https)://([w-]+.)+[w-]+(/[w- ./?%&=]*)?$",
				str);
	}
}
