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
package org.apache.dolphinscheduler.server.utils;

import org.apache.dolphinscheduler.service.registry.ZookeeperOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.apache.dolphinscheduler")
public class RemoveZKNode implements CommandLineRunner {

    private static final Integer ARGS_LENGTH = 1;

    private static final Logger logger = LoggerFactory.getLogger(RemoveZKNode.class);


    /**
     * zookeeper operator
     */
    @Autowired
    private ZookeeperOperator zookeeperOperator;

    public static void main(String[] args) {

        new SpringApplicationBuilder(RemoveZKNode.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length != ARGS_LENGTH) {
            logger.error("Usage: <node>");
            return;
        }

        zookeeperOperator.remove(args[0]);
        zookeeperOperator.close();

    }
}
