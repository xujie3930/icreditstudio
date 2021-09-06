package com.jinninghui.datasphere.icreaditstudio.workspace.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
public class ICreditBanner implements Banner {

    private static final String ICREDIT_BANNER =
            " .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
                    "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                    "| |     _____    | || |  _________   | || |  _______     | || |      __      | || | ____    ____ | || |  _________   | |\n" +
                    "| |    |_   _|   | || | |_   ___  |  | || | |_   __ \\    | || |     /  \\     | || ||_   \\  /   _|| || | |_   ___  |  | |\n" +
                    "| |      | |     | || |   | |_  \\_|  | || |   | |__) |   | || |    / /\\ \\    | || |  |   \\/   |  | || |   | |_  \\_|  | |\n" +
                    "| |      | |     | || |   |  _|      | || |   |  __ /    | || |   / ____ \\   | || |  | |\\  /| |  | || |   |  _|  _   | |\n" +
                    "| |     _| |_    | || |  _| |_       | || |  _| |  \\ \\_  | || | _/ /    \\ \\_ | || | _| |_\\/_| |_ | || |  _| |___/ |  | |\n" +
                    "| |    |_____|   | || | |_____|      | || | |____| |___| | || ||____|  |____|| || ||_____||_____|| || | |_________|  | |\n" +
                    "| |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                    "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                    " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' " +
                    " Version 1.3.0\n";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.print(ICREDIT_BANNER);
    }
}