/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dolphinscheduler.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * monitor record for zookeeper
 */
@Data
@NoArgsConstructor
public class ZookeeperRecord {

    /**
     * hostname
     */
    private String hostname;

    /**
     * connections
     */
    private int connections;

    /**
     * max connections
     */
    private int watches;

    /**
     * sent
     */
    private long sent;

    /**
     * received
     */
    private long received;

    /**
     * mode: leader or follower
     */
    private String mode;

    /**
     * min Latency
     */
    private float minLatency;

    /**
     * avg Latency
     */
    private float avgLatency;

    /**
     * max Latency
     */
    private float maxLatency;

    /**
     * node count
     */
    private int nodeCount;

    /**
     * date
     */
    private Date date;

    /**
     * is normal or not, 1:normal
     */
    private int state;

    public ZookeeperRecord(String hostname, int connections, int watches, long sent, long received, String mode, float minLatency, float avgLatency, float maxLatency, int nodeCount, int state, Date date) {
        this.hostname = hostname;
        this.connections = connections;
        this.watches = watches;
        this.sent = sent;
        this.received = received;
        this.mode = mode;
        this.minLatency = minLatency;
        this.avgLatency = avgLatency;
        this.maxLatency = maxLatency;
        this.nodeCount = nodeCount;
        this.state = state;
        this.date = date;
    }
}
