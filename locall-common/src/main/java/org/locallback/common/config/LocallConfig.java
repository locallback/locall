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

    public static void enableCallCache() {
        LocallConfig.enableCallCache = true;
    }

    public static void enableConnexus() {
        LocallConfig.enableConnexus = true;
    }

}
