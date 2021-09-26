package com.jinninghui.datasphere.icreditstudio.framework.utils;

/**
 * @author xujie
 * @description hdfs工具类
 * @create 2021-08-27 14:13
 **/

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;


public class HDFSUtils {
    //TODO:这里改为配置文件配置，，同时引入hadoop-hdfs的pom版本得和hadoop安装的版本一致
    public static String HDFS_URL = "hdfs://192.168.0.174:8020";
    static Configuration conf = new Configuration();
    static FileSystem fs;
    static {
        conf.set("fs.defaultFS", HDFS_URL);
        //hadoop的hdfs-site.xml文件,也要配置该impl参数
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        try {
            fs=FileSystem.get(URI.create(HDFSUtils.HDFS_URL),conf,"root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String copyStringToHDFS(String str, String destPath) throws Exception{

        InputStream fis = new ByteArrayInputStream(str.getBytes());
        String storePath = "/datasource/" + destPath + ".txt";
        OutputStream os = fs.create(new Path(storePath));
        IOUtils.copyBytes(fis, os, 4096, true);
        return storePath;
    }

    public static String getStringFromHDFS(String destPath) throws Exception{

        StringBuffer stringBuffer = new StringBuffer();;
        FSDataInputStream in =fs.open(new Path(destPath));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String lineTxt ;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            stringBuffer.append(lineTxt);
        }
        fs.close();
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        String stringFromHDFS = getStringFromHDFS("/datasource/890255717552893952.txt");
        System.out.println(stringFromHDFS.length());
        System.out.println(stringFromHDFS);
    }

}
