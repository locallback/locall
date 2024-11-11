package org.locallback.common.config;

public class LocallConfig {

    /**
     * 是否启用 Connexus, 默认不启用
     */
    private static boolean enableConnexus = false;

    /**
     * 是否启用 CallCache, 默认不启用
     */
    private static boolean enableCallCache = false;

    private static String ip = "127.0.0.1";
    private static int port = 18232;

    public static void enableCallCache() {
        LocallConfig.enableCallCache = true;
    }

    public static void enableConnexus() {
        LocallConfig.enableConnexus = true;
    }

    public static void setConnexus(String ip, int port) {
        LocallConfig.ip = ip;
        LocallConfig.port = port;
    }

    // region Getter and Setter
    public static boolean isEnableConnexus() {
        return enableConnexus;
    }

    public static void setEnableConnexus(boolean enableConnexus) {
        LocallConfig.enableConnexus = enableConnexus;
    }

    public static boolean isEnableCallCache() {
        return enableCallCache;
    }

    public static void setEnableCallCache(boolean enableCallCache) {
        LocallConfig.enableCallCache = enableCallCache;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        LocallConfig.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        LocallConfig.port = port;
    }

    // endregion

}
