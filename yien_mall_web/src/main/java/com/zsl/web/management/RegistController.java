package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.User;
import com.zsl.common.interfaces.management.RegistService;
import com.zsl.common.myenum.Level;
import com.zsl.common.utils.Msg;
import com.zsl.web.anno.Token;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/01/06
 * 作用：注册的 controller
 */
@Controller
@RequestMapping("/regist")
public class RegistController {

	@Reference(group = "management", version = "1.0.0")
	private RegistService registService;

	@Token(save = true)
	@RequestMapping("/toRegist")
	public String toRegist() {
		return "management/regist";
	}

	/**
	 * 将对象新增到数据库中
	 *
	 * @param user
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/regist")
	public Msg reegist(User user) {
		//加密方式
		String hashAlgorithmName = "MD5";
		//密码原值
		Object crdentials = user.getUserPassword();
		//盐值
		Object salt = "yien";
		//加密1024次
		int hashIterations = 1024;
		//进行MD5加密
		String result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations).toString();
		user.setUserPassword(result);
		user.setRawAddTime(new Date());
		user.setUserLevel(Level.user);
		//插入数据
		Msg msg = registService.insertOne(user);
		System.out.println("注册返回的信息：" + msg);
		return msg;
	}

}
