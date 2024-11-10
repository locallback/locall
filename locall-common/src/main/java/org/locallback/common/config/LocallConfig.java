package org.locallback.common.config;

public class LocallConfig {

    /**
     * 是否启用 Connexus, 默认不启用
     */
    private boolean enableConnexus = false;

    public boolean isEnableConnexus() {
        return enableConnexus;
    }

    public void setEnableConnexus(boolean enableConnexus) {
        this.enableConnexus = enableConnexus;
    }

}
