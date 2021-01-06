package com.lc.lmcl;

import java.io.*;

public class MclLoader {
    private MclDir mclDir;
    private String[] command;
    public Process process = null;
    public MclLoader(MclDir mclDir, String[] args){
        this.mclDir = mclDir;
        command = new String[args.length + 3];
        command[0] = System.getProperty("java.home") + "/bin/java";
        command[1] = "-jar";
        command[2] = mclDir.getBootJarPath();
        if (command.length >= 3)
            System.arraycopy(args, 0, command, 3, command.length - 3);

    }

    private static void kill(Process process) throws IOException {
        if (process == null)
            return;
        if (SystemUtil.isWindows())
            killInWin(process);
        else
            killInLinux(process);
    }

    private static void killInLinux(Process process) throws IOException {
        long pid = process.pid();
        String[] killCmd = {"kill", String.valueOf(pid)};
        new ProcessBuilder().command(killCmd).start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void killInWin(Process process) throws IOException {
        long pid = process.pid();
        String[] killCmd = {"taskkill", "/F", "/pid", String.valueOf(pid)};
        new ProcessBuilder().command(killCmd).start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void load() throws Exception {
        stop();
        process = new ProcessBuilder(command).directory(new File(mclDir.getMclPath())).redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectInput(ProcessBuilder.Redirect.INHERIT).start();
    }

    public void stop() throws Exception {
        try {
            kill(process);
        } catch (IOException ignored) {

        }
        if (process == null) return;
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (process.isAlive())
            throw new Exception("杀进程失败");
    }

    public void reload() throws Exception {
        load();
    }
}
