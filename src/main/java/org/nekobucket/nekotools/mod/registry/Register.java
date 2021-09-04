package org.nekobucket.nekotools.mod.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Register {

    static final String NULL = "";

    /**
     * ONLY works for class
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AsItem {
        String id() default NULL;
    }

    /**
     * ONLY works for class
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AsBlock {
        String id() default NULL;
    }
}

