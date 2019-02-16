/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.zsl.web.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张世林 (张世林@yiji.com)
 * @version 1.0
 * @filename com.zsl.web.pay
 * @since 2019/2/14
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@RequestMapping("/toPay/{orderId}")
	public String toPay(@PathVariable("orderId") String orderId) {
		System.out.println("对应的信息为："+orderId);
		return "management/pay";
	}
}
