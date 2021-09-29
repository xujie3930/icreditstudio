package com.jinninghui.datasphere.icreditstudio.sparkx.executor.response;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.ResultRecord;

import java.util.Collection;

public class ErrorExecuteResponse<V extends ResultRecord> implements CompletedExecuteResponse{

    private final Collection<V> records;

    public ErrorExecuteResponse(Collection<V> records) {
        this.records = records;
    }

    public Collection<V> getExecuteResults() {
        return records;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public Object data() {
        return getExecuteResults();
    }

    @Override
    public ResponseCode code() {
        return ResponseCode.ERROR;
    }
}
