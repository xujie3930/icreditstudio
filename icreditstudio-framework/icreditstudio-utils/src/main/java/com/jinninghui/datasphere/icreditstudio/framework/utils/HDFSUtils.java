package com.jinninghui.datasphere.icreditstudio.framework.utils;

/**
 * @author xujie
 * @description hdfs工具类
 * @create 2021-08-27 14:13
 **/

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class HDFSUtils {
    //TODO:这里改为配置文件配置
    public static String HDFS_URL = "hdfs://192.168.0.17:8020";
    static Configuration conf = new Configuration();
    static {
        conf.set("fs.defaultFS", HDFS_URL);
        //hadoop的hdfs-site.xml文件,也要配置该impl参数
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    }

    private static FileSystem createFileSystem(URI uri, Configuration conf) throws IOException {
        Class<?> clazz = conf.getClass("fs." + uri.getScheme() + ".impl", null);
        if (clazz == null) {
            throw new IOException("No FileSystem for scheme: " + uri.getScheme());
        }
        FileSystem fs = (FileSystem) ReflectionUtils.newInstance(clazz, conf);
        fs.initialize(uri, conf);
        return fs;
    }


    public static Boolean addPath(String filePath) throws IOException, InterruptedException {
        boolean result = false;
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        Path path = new Path(filePath);
        result = fs.mkdirs(path);
        return result;
    }


    /**
     *
     * @Title: deletePath
     * @Description: 删除路径
     * @param filePath
     * @return
     * @throws IOException
     * @throws InterruptedException
     *             Boolean
     * @author 刘云生
     * @date 2018年4月10日下午5:09:56
     */
    public static Boolean deletePath(String filePath) throws IOException, InterruptedException {
        boolean result = false;
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        Path deletePath = new Path(filePath);
        result = fs.delete(deletePath, true);
        return result;
    }


    public static Boolean ReName(String oldPathString, String newPathString) throws IOException, InterruptedException {
        boolean result = false;
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        Path oldPath = new Path(oldPathString);
        Path newPath = new Path(newPathString);
        result = fs.rename(oldPath, newPath);
        return result;
    }


    public static void copyFileFromHDFS(String HDFSPathString, String LocalPathString)
            throws IOException, InterruptedException {
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        InputStream in = fs.open(new Path(HDFSPathString));
        OutputStream out = new FileOutputStream(LocalPathString);
        IOUtils.copyBytes(in, out, 4096, true);
        System.out.println("拷贝完成...");
    }


    public static void copyFileToHDFS(String srcFile, String destPath) throws Exception {


        FileInputStream fis = new FileInputStream(new File(srcFile));// 读取本地文件
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        OutputStream os = fs.create(new Path("/hdfs/"+destPath));
        // copy
        IOUtils.copyBytes(fis, os, 4096, true);
        fs.close();
    }

    public static String copyStringToHDFS(String str, String destPath) throws Exception{

        InputStream fis = new ByteArrayInputStream(str.getBytes());
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), conf, "root");
        String storePath = "/datasource/" + destPath + ".txt";
        OutputStream os = fs.create(new Path(storePath));
        // copy
        IOUtils.copyBytes(fis, os, 4096, true);
        fs.close();
        return storePath;
    }

    public static String getStringFromHDFS(String destPath) throws Exception{

        StringBuffer stringBuffer = null;
        FileSystem fs=FileSystem.get(URI.create(HDFSUtils.HDFS_URL),conf,"root");
        FSDataInputStream in =fs.open(new Path(destPath));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String lineTxt = null;
        stringBuffer = new StringBuffer();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            stringBuffer.append(lineTxt);
        }
        fs.close();
        return stringBuffer.toString();
    }


    public static List<String> recursiveHdfsPath(FileSystem hdfs, Path listPath)
            throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<>();
        FileStatus[] files = null;
        files = hdfs.listStatus(listPath);
        for (FileStatus f : files) {
            if (files.length == 0 || f.isFile()) {
                list.add(f.getPath().toUri().getPath());
            } else {
                list.add(f.getPath().toUri().getPath());
                // 是文件夹，且非空，就继续遍历
                recursiveHdfsPath(hdfs, f.getPath());
            }
        }
        for (String a : list) {
            System.out.println(a);
        }
        return list;
    }


    public static void main(String[] args) throws Exception {
        String stringFromHDFS = getStringFromHDFS("/datasource/890255717552893952.txt");
        System.out.println(stringFromHDFS);
    }

}
