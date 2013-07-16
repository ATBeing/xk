package xk.util;


public class RoundTool {
	
//	public static void main(String[] args) {
//		System.out.println("数值123.121保留2位小数:\t" + RoundTool.round(123.121, 2));
//		System.out.println("数值123.456789保留3位小数：\t" + RoundTool.round(123.456789, 3));
//		System.out.println("数值123.1231保留3位小数:\t" + RoundTool.round(123.1231, 3));
//		System.out.println("数值123.5保留3位小数：\t" + RoundTool.round(123.5, 3));
//	}

	public static String round(double value, int dotNum) {
		String strValue = String.valueOf(value);
		int pos = strValue.indexOf(".");// 小数点位置
		int len = strValue.length(); // 数值总位数
		int dotLen = len - 1 - pos;
		double endValue = 0.0;
		String endNum = "";
		if (dotNum < dotLen) {
			String cNum = strValue.substring(pos + dotNum + 1, pos + dotNum + 2);// 获得需要进位的小数位
			int iNum = Integer.valueOf(cNum); // 转换为整数
			String sNum = strValue.substring(0, pos + dotNum + 1);
			endValue = Double.valueOf(sNum); // 转换为double类型
			if (iNum >= 5) { // 如果需要舍入的值大于5
				String endPos = ""; // 存放需要进位的小数点
				String dotValue = ""; // 连接小数点后的多个零
				for (int i = 1; i < dotNum; i++) {
					dotValue = dotValue + "0"; // 将小数点后的多个零连接在一起
				}
				endPos = "0." + dotValue + "1"; // 需要进位的小数值
				endValue = endValue + Double.valueOf(endPos);// 四舍五入处理之后的值
				strValue = String.valueOf(endValue); // 处理后的值转换为字符串
				pos = strValue.indexOf("."); // 小数点的位置
				len = strValue.length(); // 数值总位数
				dotLen = len - 1 - pos; // 小数的位数
				if (dotLen < dotNum) {      //如果小数位数不足，则补足位数
					for (int i = pos + dotLen + 1; i < pos + dotNum + 1; i++) {
						endNum = String.valueOf(endValue) + "0"; // 补足小数位数
					}
				} else { // 如果小数位数正好，或者超过要求，则进行截位处理
					endNum = String.valueOf(endValue).substring(0, pos + dotNum + 1);
				}
			} else {
				endNum = strValue.substring(0, strValue.indexOf("."))
						+ strValue.substring(strValue.indexOf("."), strValue.indexOf(".") + dotNum
								+ 1);
			}
		} else if (dotNum == dotLen) {
			endNum = String.valueOf(value);// 小数位数与要求的位数相同，直接转换为字符串
		} else { // 如果小数位数不足，则补足位数
			for (int i = 1; i <= dotNum - dotLen; i++) {
				strValue = strValue + "0";
			}
			endNum = strValue;    // 最终的值
		}
		return endNum;
	}

}
