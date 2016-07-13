package util;

/**
 * 常量类用于存储常量信息
 * @author nick
 *
 */
public class Constant {
	private static int screenWidth;
	private static int screenHeight;
	private static int singleRec;

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int width) {
		screenWidth = width;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int height) {
		screenHeight = height;
	}

	public static int getSingleRec(){
		return screenWidth/15;
	}


}
