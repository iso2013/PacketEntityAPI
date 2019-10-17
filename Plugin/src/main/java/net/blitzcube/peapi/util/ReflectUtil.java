package net.blitzcube.peapi.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 10/15/19.
 */
public class ReflectUtil {
    private static final String VERSION;

    static {
        String packageVer = Bukkit.getServer().getClass().getPackage().getName();
        packageVer = packageVer.substring(packageVer.lastIndexOf('.') + 1);
        VERSION = packageVer;
    }

    public static Class<?> getNMSClass(String name){
        if(name == null) return null;

        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getCBClass(String name){
        if(name == null) return null;

        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name){
        if(clazz == null || name == null) return null;

        try {
            Field f = clazz.getDeclaredField(name);
            if(!f.isAccessible()) f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, Class<?> type){
        if(clazz == null || type == null) return null;

        for(Field f : clazz.getDeclaredFields()) {
            if(f.getType() == type) {
                if(!f.isAccessible()) f.setAccessible(true);
                return f;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... params) {
        if(clazz == null || name == null) return null;

        try {
            Method m = clazz.getDeclaredMethod(name, params);
            if(!m.isAccessible()) m.setAccessible(true);
            return m;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, Class<?> returnType) {
        if(clazz == null || returnType == null) return null;

        for(Method m : clazz.getDeclaredMethods()) {
            if(m.getReturnType() == returnType) {
                if(!m.isAccessible()) m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Constructor getConstructor(Class<?> clazz, Class<?>... classes) {
        if(clazz == null) return null;

        try {
            Constructor c = clazz.getDeclaredConstructor(classes);
            if(!c.isAccessible()) c.setAccessible(true);
            return c;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static int getVersionNumber() {
        String number = VERSION.substring(VERSION.indexOf('_') + 1, VERSION.lastIndexOf('_'));
        return Integer.valueOf(number);
    }
}
