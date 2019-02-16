/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.utils;

import java.util.Random;

/**
 *
 * @Filename NumUtils.java
 *
 * @Description 生成随机数
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年01月17日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class NumUtils {

	    public static Integer aa () {
	        Random random = new Random();
			Integer num = random.nextInt(50);
			return num;
	    }
}
