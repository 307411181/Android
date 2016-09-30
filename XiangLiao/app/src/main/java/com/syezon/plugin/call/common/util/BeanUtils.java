//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BeanUtils {
    public BeanUtils() {
    }

    protected static Field getDeclaredField(Class<?> var0, String var1) {
        Class var2 = var0;

        while(var2 != Object.class) {
            try {
                Field var4 = var2.getDeclaredField(var1);
                return var4;
            } catch (NoSuchFieldException var5) {
                var5.printStackTrace();
                var2 = var2.getSuperclass();
            }
        }

        return null;
    }

    protected static Field getDeclaredField(Object var0, String var1) {
        return getDeclaredField(var0.getClass(), var1);
    }

    protected static void makeAccessible(Field var0) {
        if(!Modifier.isPublic(var0.getModifiers()) || !Modifier.isPublic(var0.getDeclaringClass().getModifiers())) {
            var0.setAccessible(true);
        }

    }

    public static void setFieldValue(Object var0, String var1, Object var2) {
        Field var3 = getDeclaredField(var0, var1);
        if(var3 == null) {
            throw new IllegalArgumentException("Could not find field [" + var1 + "] on target [" + var0 + "]");
        } else {
            makeAccessible(var3);

            try {
                var3.set(var0, var2);
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
            }
        }
    }
}
