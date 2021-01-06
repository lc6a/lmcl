package com.lc.lmcl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * 简易日志类
 */
public class Logger {

    /**
     * 日志适配器，可以适配到其他日志库
     */
    @FunctionalInterface
    public interface LogAdapter{
        void adapter(Logger logger, Level level, String message);
    }

    public enum Level{
        V,I, D, W, E
    }
    public enum Color {

        RESET("\u001b[0m"),

        WHITE("\u001b[30m"),
        RED("\u001b[31m"),
        EMERALD_GREEN("\u001b[32m"),
        GOLD("\u001b[33m"),
        BLUE("\u001b[34m"),
        PURPLE("\u001b[35m"),
        GREEN("\u001b[36m"),

        GRAY("\u001b[90m"),
        LIGHT_RED("\u001b[91m"),
        LIGHT_GREEN("\u001b[92m"),
        LIGHT_YELLOW("\u001b[93m"),
        LIGHT_BLUE("\u001b[94m"),
        LIGHT_PURPLE("\u001b[95m"),
        LIGHT_CYAN("\u001b[96m");

        private String s;

        Color(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }
    }

    private String getColor(Level level){
        switch (level) {
            case V -> {
                return Color.RESET.toString();
            }
            case I -> {
                return Color.LIGHT_GREEN.toString();
            }
            case D -> {
                return Color.LIGHT_CYAN.toString();
            }
            case W -> {
                return Color.LIGHT_RED.toString();
            }
            case E -> {
                return Color.RED.toString();
            }
        }
        return Color.RESET.toString();
    }

    /**
     * 标签
     */
    public String label = "";

    /**
     * 输出位置
     */
    public Consumer<String> out = System.out::println;

    /**
     * 是否带颜色
     */
    public boolean color = false;

    /**
     * 日志适配器
     */
    public LogAdapter adapter = null;

    private String format(Level level, String msg){
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String lv = level.toString();
        if (color)
            return String.format("%s[%s %s/%s]: %s%s", getColor(level), time, lv, label, msg, Color.RESET);
        return String.format("[%s %s/%s]: %s", time, lv, label, msg);
    }

    public void log(Level level, String message){
        if (adapter != null){
            adapter.adapter(this, level, message);
            return;
        }
        out.accept(format(level, message));
    }

    public void v(String message) {
        log(Level.V, message);
    }

    public void i(String message) {
        log(Level.I, message);
    }

    public void d(String message) {
        log(Level.D, message);
    }

    public void w(String message) {
        log(Level.W, message);
    }

    public void e(String message) {
        log(Level.E, message);
    }
}
