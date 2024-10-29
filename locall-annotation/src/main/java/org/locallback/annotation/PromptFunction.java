package org.locallback.annotation;

public @interface PromptFunction {

    /**
     * prompt信息，填充词请使用{yourContent}，如{param0}
     */
    String prompt() default "";

    /**
     * 填充参数列表，请保持与prompt中填充词一致
     */
    String[] params() default {};

}
