package com.jinninghui.datasphere.icreditstudio.sparkx.test;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.ExecuteResponse;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.Main;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkEngineSession;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkScalaExecutor;
import org.apache.spark.SparkContext;

public class SparkScalaExecutorTest {

    public static void main(String[] args) {


        SparkEngineSession sparkEngineSession = Main.createEngineConnSession();
        SparkScalaExecutor sparkScalaExecutor = new SparkScalaExecutor(sparkEngineSession);
        sparkScalaExecutor.init();
        ExecuteResponse executeResponse = sparkScalaExecutor.runCode("sql(\"show databases\").show");
        System.out.println(executeResponse);
    }
}
