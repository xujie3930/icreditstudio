package com.jinninghui.datasphere.icreditstudio.sparkx.executor.response;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultRecord;

import java.util.Collection;

public class SuccessExecuteResponse<V extends ResultRecord> implements CompletedExecuteResponse{
    private final Collection<V> records;

    public SuccessExecuteResponse(Collection<V> records) {
        this.records = records;
    }
    public Collection<V> getExecuteResults() {
        return records;
    }

    @Override
    public boolean success() {
        return true;
    }

    @Override
    public Object data() {
        return getExecuteResults();
    }

    @Override
    public ResponseCode code() {
        return ResponseCode.SUCCESS;
    }
}
