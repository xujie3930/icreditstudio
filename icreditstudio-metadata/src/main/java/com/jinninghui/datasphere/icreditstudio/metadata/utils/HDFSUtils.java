package com.jinninghui.datasphere.icreditstudio.metadata.utils;

/**
 * @author xujie
 * @description hdfs工具类
 * @create 2021-08-27 14:13
 **/
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


public class HDFSUtils {
    public static String HDFS_URL = "hdfs://192.168.0.116:9000";


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
        OutputStream os = fs.create(new Path(destPath));
        // copy
        IOUtils.copyBytes(fis, os, 4096, true);
        fs.close();
    }

    public static String copyStringToHDFS(String str, String destPath) throws Exception {


        InputStream fis = new ByteArrayInputStream(str.getBytes());
        FileSystem fs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        OutputStream os = fs.create(new Path(destPath));
        // copy
        IOUtils.copyBytes(fis, os, 4096, true);
        fs.close();
        return URI.create(HDFSUtils.HDFS_URL) + destPath;
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
        FileSystem hdfs = FileSystem.get(URI.create(HDFSUtils.HDFS_URL), new Configuration(), "root");
        HDFSUtils.recursiveHdfsPath(hdfs, new Path("/"));
        HDFSUtils.copyStringToHDFS("hello", System.currentTimeMillis() + "");
    }

}
