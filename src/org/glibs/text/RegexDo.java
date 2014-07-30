package org.glibs.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDo {

	private Pattern p = null;
	private Matcher m = null;

	public RegexDo() {
	}

	public Boolean isMatchReg(String regex, String str) {
		p = Pattern.compile(regex);
		m = p.matcher(str);
		return Boolean.valueOf(m.matches());
	}

	public Boolean isInteger(String str) {
		return this.isMatchReg("^-?[1-9]+[0-9]*$", str);
	}

	public Boolean isDouble(String str) {
		return this.isMatchReg("^-?([1-9]+[0-9]*.[0-9]+|0)$", str);
	}

	public Boolean isEnglish(String str) {
		return this.isMatchReg("^[A-Za-z]+$", str);
	}

	public Boolean isEnglishUpper(String str) {
		return this.isMatchReg("^[A-Z]+$", str);
	}

	public Boolean isEnglishLower(String str) {
		return this.isMatchReg("^[a-z]+$", str);
	}

	public Boolean isNumberAndEnglish(String str) {
		return this.isMatchReg("^[A-Za-z0-9]+$", str);
	}

	public Boolean isNumberAndEnglishAndUnderLine(String str) {
		return this.isMatchReg("^[A-Za-z0-9_]+$", str);
	}

	public Boolean isNumberString(String str) {
		return this.isMatchReg("^[0-9]+$", str);
	}

	public Boolean isChinese(String str) {
		return this.isMatchReg("^[u4e00-u9fa5]+$", str);
	}

	public Boolean isIDCardNo(String str) {
		return this.isMatchReg("^[1-9]?([0-9]{14}|[0-9]{17}|[0-9]{16}(x|X))$",
				str);
	}

	public Boolean isEmail(String str) {
		return this.isMatchReg("w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$", str);
	}

	public Boolean isUrl(String str) {
		return this.isMatchReg(
				"^(http|https)://([w-]+.)+[w-]+(/[w- ./?%&=]*)?$", str);
	}
}
