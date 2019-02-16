/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @Filename Token.java
 *
 * @Description 自定义Tkoen注解，用于防止表单重复提交的操作
 *
 * @Version 1.0
 *
 * @Author 张世林
 *
 * @Email 张世林@yiji.com
 *
 * @History
 *<li>Author: 张世林</li>
 *<li>Date: 2019年02月12日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 * 关于这个方法的用法是：
 * 在需要生成token的controller上增加@Token(save=true)，
 * 而在需要检查重复提交的controller上添加@Token(remove=true)就可以了
 * 另外，你需要在view里在form里增加下面代码：
 *   <input type="hidden" name="token" value="${token}">
 *   此时会在拦截器中验证是否重复提交
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

	boolean save() default false ;

	boolean remove() default false ;
}
