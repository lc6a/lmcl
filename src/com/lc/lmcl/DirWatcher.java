package com.lc.lmcl;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class DirWatcher extends FileAlterationListenerAdaptor {

    /**
     * 文件改变时的处理器
     */
    @FunctionalInterface
    public interface FileChangeHandle{
        /**
         * 当文件改变时调用，添加、修改、删除都算
         * @param file 变化的文件
         */
        void onChange(File file);
    }

    private FileAlterationMonitor monitor;
    private FileChangeHandle handle;

    public DirWatcher(String dirPath, long interval, FileChangeHandle handle) {
        this.handle = handle;
        monitor = new FileAlterationMonitor(interval);
        FileAlterationObserver fileObserver = new FileAlterationObserver(dirPath);
        fileObserver.addListener(this);
        monitor.addObserver(fileObserver);
    }

    public void start(boolean isDaemonThread) throws Exception {
        if (isDaemonThread) {
            monitor.setThreadFactory(r -> {
                Thread th = new Thread(r);
                th.setDaemon(true);
                return th;
            });
        }
        monitor.start();
    }

    @Override
    public void onFileChange(File file) {
        handle.onChange(file);
    }

    @Override
    public void onFileCreate(File file) {
        handle.onChange(file);
    }

    @Override
    public void onFileDelete(File file) {
        handle.onChange(file);
    }
}
