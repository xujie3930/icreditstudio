package com.jinninghui.datasphere.icreditstudio.sparkx.executor.response;

public class UnknownExecuteResponse implements OutputExecuteResponse{
    @Override
    public boolean success() {
        return true;
    }

    @Override
    public Object data() {
        return "Unknown";
    }

    @Override
    public ResponseCode code() {
        return ResponseCode.UNKNOWN;
    }
}
