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

package org.apache.dolphinscheduler.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.ProjectUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * project mapper interface
 */
public interface ProjectMapper extends BaseMapper<Project> {
    /**
     * query project detail by code
     *
     * @param projectCode projectCode
     * @return project
     */
    Project queryByCode(@Param("projectCode") Long projectCode);

    /**
     * TODO: delete
     * query project detail by id
     *
     * @param projectId projectId
     * @return project
     */
    Project queryDetailById(@Param("projectId") String projectId);

    /**
     * query project detail by code
     *
     * @param projectCode projectCode
     * @return project
     */
    Project queryDetailByCode(@Param("projectCode") Long projectCode);

    /**
     * query project by name
     *
     * @param projectName projectName
     * @return project
     */
    Project queryByName(@Param("projectName") String projectName);

    /**
     * project page
     *
     * @param page       page
     * @param userId     userId
     * @param searchName searchName
     * @return project Ipage
     */
    IPage<Project> queryProjectListPaging(IPage<Project> page,
                                          @Param("userId") String userId,
                                          @Param("searchName") String searchName);

    /**
     * query create project user
     *
     * @param userId userId
     * @return project list
     */
    List<Project> queryProjectCreatedByUser(@Param("userId") String userId);

    /**
     * query authed project list by userId
     *
     * @param userId userId
     * @return project list
     */
    List<Project> queryAuthedProjectListByUserId(@Param("userId") String userId);

    /**
     * query relation project list by userId
     *
     * @param userId userId
     * @return project list
     */
    List<Project> queryRelationProjectListByUserId(@Param("userId") String userId);

    /**
     * query project except userId
     *
     * @param userId userId
     * @return project list
     */
    List<Project> queryProjectExceptUserId(@Param("userId") int userId);

    /**
     * query project list by userId
     *
     * @param userId
     * @return
     */
    List<Project> queryProjectCreatedAndAuthorizedByUserId(@Param("userId") int userId);

    /**
     * query project name and user name by processInstanceId.
     *
     * @param processInstanceId processInstanceId
     * @return projectName and userName
     */
    ProjectUser queryProjectWithUserByProcessInstanceId(@Param("processInstanceId") int processInstanceId);

    /**
     * query all project
     *
     * @return projectList
     */
    List<Project> queryAllProject();

}
