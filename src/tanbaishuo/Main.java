package tanbaishuo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 解码表
 * 	0 - "oe", "n", "z"
		1 - "oK", "6", "5"
		2 - "ow", "-", "A"
		3 - "oi", "o", "i"
		4 - "7e", "v", "P"
		5 - "7K", "4", "k"
		6 - "7w", "C", "s"
		7 - "7i", "S", "l"
		8 - "Ne", "c", "F"
		9 - "NK", "E", "q"
 */

public class Main {

	private static ArrayList<String> result = new ArrayList<>();

	// 破解密码
	public static String returnPass(String str) {
		String regex = "(.)";
		String[] strs = new String[12];
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		for (int i = 0; i < 12; i++) {
			if (m.find()) {
				strs[i] = m.group();
			}
		}

		String result = "";
		int count = 1;
		for (int i = 0; i < 12; i++) {
			if (count <= 4) {
				if (count < 2) {
					result += uncode(strs[i] + strs[i + 1]);
					i++;
					count++;
				} else {
					try {
						result += uncode(strs[i]);
					} catch (NullPointerException n) {
					}
				}
			} else {
				count = 0;
				i--;
			}
			count++;
		}
		return result;
	}

	// 密码本
	public static String uncode(String str) {
		switch (str) {
			case "oe":
			case "n":
			case "z":
				return "0";
			case "oK":
			case "6":
			case "5":
				return "1";
			case "ow":
			case "-":
			case "A":
				return "2";
			case "oi":
			case "o":
			case "i":
				return "3";
			case "7e":
			case "v":
			case "P":
				return "4";
			case "7K":
			case "4":
			case "k":
				return "5";
			case "7w":
			case "C":
			case "s":
				return "6";
			case "7i":
			case "S":
			case "l":
				return "7";
			case "Ne":
			case "c":
			case "F":
				return "8";
			case "NK":
			case "E":
			case "q":
				return "9";
			default:
				return "-1";
		}
	}

	// 读取文件
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// 将文本切割成段
					strSplit(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	// 输出文件
	public static void writeTxtFile(String string) throws Exception {
		File file = new File("C:\\Users\\WarMj\\Desktop\\2.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(string);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将整段切开
	private static void strSplit(String str) {
		String[] strs = str.split("\\},\\{");
		for (String s : strs) {
			returnResult(s);
		}
	}

	// 将结果保存到result中
	public static void returnResult(String str) {
		String regex = "\"fromNick\":\"([^\"]+)\".*fromEncodeUin\":" + "\"\\*S1\\*([^\"]+)\".*topicName\":\"([^\"]+)\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			result.add(m.group(1) + " " + returnPass(m.group(2)) + " " + m.group(3));
		}
	}

	public static void main(String argv[]) {
		String inputfilePath = "C:\\Users\\WarMj\\Desktop\\1.txt";
		readTxtFile(inputfilePath);

		String write = "";
		try {
			for (int i = 0; i < result.size(); i++) {
				write += result.get(i) + "\n";
			}
			System.out.println(write);
			writeTxtFile(write);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
