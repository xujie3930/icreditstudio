package com.jinninghui.datasphere.icreditstudio.datasync.service.task;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataxCore {

    private Transport transport;

    @Data
    public static class Transport {
        private Channel channel;
    }

    @Data
    public static class Channel {
        private Speed speed;
    }

    @Data
    public static class Speed {
        private Integer channel;
        private Integer record;

        public int getChannel() {
            if (channel == null) {
                return 1;
            }
            return channel;
        }

        public int getRecord() {
            if (record == null) {
                return 20000;
            }
            return record;
        }
    }
}
