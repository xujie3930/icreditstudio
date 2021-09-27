package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset;

import java.io.OutputStream;
import java.util.Collection;

public abstract class ResultOutputStream<V extends ResultRecord> extends OutputStream {

    public abstract Collection<V> finalFlush();

}
