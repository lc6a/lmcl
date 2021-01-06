package com.lc.lmcl;

import org.apache.commons.cli.*;

public class Args {

    //public String mclPath = "E:\\code\\mcl-1.0.3";

    public String mclPath = System.getProperty("user.dir");

    public Args(String[] args) throws Exception {
        String mclPathOpt = "mclPath";
        Options options = new Options();
        options.addOption(mclPathOpt, true, "mcl路径");
        CommandLine commandLine;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("lmcl帮助", options);
            throw new Exception();
        }

        if (commandLine.hasOption(mclPathOpt)) {
            mclPath = commandLine.getOptionValue(mclPathOpt);
        }
    }
}
