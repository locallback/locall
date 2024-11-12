package org.locallback.framework.executer;

public interface Executer {

    String exec(String args);

    boolean exec(String args, String outPath);

}
