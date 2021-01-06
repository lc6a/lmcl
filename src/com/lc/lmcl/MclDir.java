package com.lc.lmcl;

import java.io.File;

public class MclDir {
    private final String mclPath;
    public MclDir(String path) {
        mclPath = path;
    }

    public String getBootJarPath() {
        return mclPath + File.separatorChar + "mcl.jar";
    }

    public String getMclPath() {
        return mclPath;
    }

    public String getPluginsPath() {
        return mclPath + File.separatorChar + "plugins";
    }
}
