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

/**
 * user
 */
@Data
public class User {

    /**
     * id用户ID
     */
    private String id;

    /**
     * user name
     */
    private String userName;

    /**
     * mail
     */
    private String email;

    /**
     * phone
     */
    private String phone;

    /**
     * tenant code
     */
    private String tenantCode;

    /**
     * alert group
     */
    private String alertGroup;
}
