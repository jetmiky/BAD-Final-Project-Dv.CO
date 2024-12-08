package util;

public class StringHelper {

	public static String addLeadingZeros(Integer digit, Integer length) {
		String result = "";
		String digitInString = digit.toString();
		
		if (digitInString.length() >= length) return digitInString;
		
		while(result.length() < length - digitInString.length()) {
			result += "0";
		}
		
		return result + digitInString;
	}
}
