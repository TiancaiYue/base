package cn.utils;

public class YZFastClickUtil {
    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于300ms
    private static long lastClickTime;

    /**
     * 限制按钮多次点击0.5秒之内不能重复点击
     *
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 限制按钮多次点击delayTime秒之内不能重复点击
     *
     * @param delayTime
     * @return
     */
    public static boolean isFastClick(int delayTime) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= delayTime) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
