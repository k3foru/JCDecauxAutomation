package com.rishabhSoft.selenium.JCDecaux.commonLibrary;

import net.sf.jlue.util.RandomString;

public class CommonUtility {

	public static String seperateStringByColon(String str) {
		String[] rightString = str.split(":");
		return rightString[1];
	}

	public static String getStringAfterColon(String str) {
		String charString1 = "";
		Character chr = 'p';
		String charString = "";

		int dec = str.length();
		String stopWhile = "";
		while (!stopWhile.equals("stop")) {
			chr = new Character(str.charAt(dec - 1));
			charString = chr.toString();
			// System.out.println("-------------"+charString);

			if (charString.equals(":")) {
				stopWhile = "stop";
			} else {
				dec--;
				charString1 = charString + charString1;
			}
		}
		String stringWithOutSpace = charString1.trim();
		return stringWithOutSpace;
	}

	public static String getURLLastName(String str) {
		Character chr = 'p';
		String charString = "", charString1 = "";

		int dec = str.length();
		String stopWhile = "";
		while (!stopWhile.equals("stop")) {
			chr = new Character(str.charAt(dec - 6));
			charString = chr.toString();
			// System.out.println("-------------"+charString);

			if (charString.equals("/")) {
				stopWhile = "stop";

			} else {
				dec--;
				charString1 = charString + charString1;
			}
		}
		return charString1;
	}

	@SuppressWarnings("static-access")
	public static String getRandomName(int length) {

		RandomString rs = new RandomString();
		char[] ch = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l', 'm', 'n', 'o' };
		return (rs.random(length, ch));
	}

	@SuppressWarnings("static-access")
	public static String randomAlphaNumeric(int length) {
		RandomString rs = new RandomString();
		// char[] ch = {'a','b','c','d','e','f','g','h','i','j'};
		return (rs.random(length));
	}

	public static void main(String a[]) {
		System.out.println("Name :" + CommonUtility.getURLLastName("https://qaprerelease.zerochaos.com/ZCW/%28S%2852pby4piel3tgcfyltcffp2u%29F%280WTgXk4FJGzqixZr0ycK63dDZzeG4Wn66-tAdAURlzjL6K7HTj3X_h0MbGKrFMaOSrBdzuQkfAsCd3A6NEMI_vQYwlOpLZA1huRegNObm88fA3xqqocTvnKW0i832hhedgY99KQFd_xC0eo7A2DCOq0kHvY1%29%29/Admin/Timesheet/TimesheetHome.aspx?filter=Timesheet"));
	}
}
