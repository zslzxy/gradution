/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.search.repository;

import com.zsl.common.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @Filename UserRepository.java
 *
 * @Description 用户信息的elasticsearch repository
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月18日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Component
public interface UserRepository extends ElasticsearchRepository<User,Integer> {
}
