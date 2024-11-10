package org.locallback.common.utils;

import org.locallback.common.enums.OSEnum;

public class EnvUtils {

    /**
     * 获取当前系统环境
     */
    public static OSEnum getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return OSEnum.WINDOWS;
        } else if (os.contains("linux") && !os.contains("android")) {
            return OSEnum.LINUX;
        } else if (os.contains("mac")) {
            return OSEnum.MAC;
        } else {
            return OSEnum.OTHER;
        }
    }

}
