package com.lc.lmcl;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Logger logger = new Logger();
    static MclDir mclDir;
    static MclLoader mclLoader;

    public static void main(String[] args) throws Exception {
        Args myArgs = new Args(args);

        mclDir = new MclDir(myArgs.mclPath);
        mclLoader = new MclLoader(mclDir, args);
        mclLoader.load();

        DirWatcher dirWatcher = new DirWatcher(mclDir.getPluginsPath(), 5000L, Main::reload);
        dirWatcher.start(false);
        /*String s;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            s = scanner.nextLine();
            if ("/stop".equals(s) || "stop".equals(s)) {
                mclLoader.stop();
                logger.i("正在退出");
                System.exit(0);
            }
        }*/
    }

    static void reload(File file) {
        try {
            logger.i("插件[" + file.getName() + "]发生变化，重启mcl......");
            mclLoader.reload();
            logger.i("重启mcl成功");
        } catch (IOException e) {
            logger.e("重启mcl失败,mcl启动路径：" + mclDir.getBootJarPath());
        } catch (Exception e) {
            logger.e(e.getMessage());
        }
    }

}
