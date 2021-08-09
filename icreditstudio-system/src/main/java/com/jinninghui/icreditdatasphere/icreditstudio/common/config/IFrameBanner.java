package com.jinninghui.icreditdatasphere.icreditstudio.common.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * Project：iFrame
 * Package：com.hashtech.business.common.config
 * ClassName: iFrameBanner
 * Description:  iFrameBanner类
 * Date: 2021/4/16 2:30 下午
 *
 * @author liyanhui
 */
public class IFrameBanner implements Banner {

    private static final String IFRAME_BANNER =
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
        out.print(IFRAME_BANNER);
    }
}
