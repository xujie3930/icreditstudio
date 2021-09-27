package com.jinninghui.datasphere.icreditstudio.sparkx.executor.response;

import java.io.Serializable;

public interface ExecuteResponse extends Serializable {

    boolean success();

    Object data();

    ResponseCode code();

}
