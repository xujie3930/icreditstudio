package com.jinninghui.datasphere.icreditstudio.sparkx.executor.response;

public class IncompleteExecuteResponse implements ExecuteResponse {

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public Object data() {
        return "Incomplete";
    }

    @Override
    public ResponseCode code() {
        return ResponseCode.INCOMPLETE;
    }
}
