package org.locallback.common.config;

public class LocallConfig {

    /**
     * 是否启用 Connexus, 默认不启用
     */
    public static boolean enableConnexus = false;

    /**
     * 是否启用 CallCache, 默认不启用
     */
    public static boolean enableCallCache = false;

    public static String ip = "127.0.0.1";
    public static int port = 18232;

    public static void setConnexusConfig(String ip, int port) {
        LocallConfig.ip = ip;
        LocallConfig.port = port;
    }

    public static void enableCallCache() {
        LocallConfig.enableCallCache = true;
    }

    public static void enableConnexus() {
        LocallConfig.enableConnexus = true;
    }

}
