package com.jinninghui.datasphere.icreditstudio.datasync.service.task;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataxSetting {

    private Speed speed;

    @Data
    public static class Speed {
        private Integer channel;

        public int getChannel() {
            if (channel == null) {
                return 1;
            }
            return channel;
        }
    }
}
