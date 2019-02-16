package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.Address;
import com.zsl.common.entity.Bank;
import com.zsl.common.entity.User;
import com.zsl.common.fastdfs.FastDFSService;
import com.zsl.common.interfaces.management.UserService;
import com.zsl.common.utils.Msg;
import com.zsl.web.anno.Token;
import com.zsl.web.shiro.ShiroUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/06
 * 作用：
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(group = "management", version = "1.0.0")
	private UserService userService;

	@Autowired
	private FastDFSService fastDFSService;

	/**
	 * 跳转到修改的界面
	 *
	 * @param userId
	 * @param model
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("/toUpdateUser/{userId}")
	public String toUpdate(@PathVariable("userId") String userId, Model model) {
		User user = userService.selectOneByUserId(userId);
		model.addAttribute("user", user);
		return "management/updateUser";
	}

	/**
	 * 更新个人数据
	 *
	 * @param user
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/update")
	public Msg updateOne(User user) {
		user.setRawUpdateTime(new Date());
		if (user.getUserPassword().length() < 16) {
			String hashAlgorithmName = "MD5";
			Object crdentials = user.getUserPassword();
			Object salt = "yien";
			int hashIterations = 1024;
			String result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations).toString();
			user.setUserPassword(result);
		}
		Msg msg = userService.updateOne(user);
		ShiroUtils.setUser(user);
		return msg;
	}

	/**
	 * 头像上传
	 *
	 * @param uploadFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fileUpload")
	public Msg fileUpload(@RequestParam("userHeadImage") MultipartFile uploadFile, @RequestParam("userId") Integer id) {
		Msg msg = fastDFSService.fileUpload(uploadFile);
		User user = new User();
		user.setId(id);
		user.setUserHeadImage(msg.getExtend().get("url").toString());
		userService.updateOne(user);
		return msg;
	}

	/**
	 * 删除对应的用户
	 * @param userIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUser")
	public Msg deleteUser(@RequestParam("userIds") String[] userIds) {
		Msg msg = userService.deleteBatch(userIds);
		return msg;
	}

	/**
	 * 进入到个人主页
	 *
	 * @return
	 */
	@RequestMapping("/myIndex")
	public String myIndex() {
		return "myindex/myindex";
	}

	/**
	 * 进入到个人地址页面
	 *
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("/toUserAddress")
	public String addUserAddress(@RequestParam(name = "addressId", defaultValue = "-1") String addressId, Model model) {
		Address address = null;
		if (addressId.equals("-1")) {
			address = new Address();
		} else {
			address = userService.selectAddressByAddressId(addressId);
		}
		model.addAttribute("address", address);
		return "management/addUserAddress";
	}

	/**
	 * 新增地址
	 *
	 * @param address
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/addAddress")
	public Msg addAddress(Address address) {
		address.setId(null);
		Msg msg = userService.addAddress(address);
		System.out.println(address);
		return msg;
	}

	/**
	 * 进入到个人银行卡页面
	 *
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("/toUserBank")
	public String addUserBank(@RequestParam(name = "bankId", defaultValue = "-1") String bankId, Model model) {
		Bank bank = null;
		if (bankId.equals("-1")) {
			bank = new Bank();
		} else {
			bank = userService.selectBankByBAnkId(bankId);
		}
		model.addAttribute("bank", bank);
		return "management/addUserBank";
	}

	/**
	 * 新增银行卡
	 *
	 * @param bank
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/addBank")
	public Msg addBank(Bank bank) {
		bank.setId(null);
		Msg msg = userService.addBank(bank);
		return msg;
	}

	/**
	 * 修改银行卡
	 *
	 * @param bank
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/updateBank")
	public Msg updateBank(Bank bank) {
		Msg msg = userService.updateBank(bank);
		return msg;
	}

	/**
	 * 根据用户id查询对应的银行卡
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectBankByUserId/{userId}")
	public List<Bank> selectBankByUserId(@PathVariable("userId") String userId) {
		List<Bank> list = userService.selectBankByUserId(userId);
		return list;
	}

	/**
	 * 单挑删除银行卡的controller
	 *
	 * @param bankId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteBankByBankId")
	public Msg deleteBankByBankId(@RequestParam("bankId") String bankId) {
		String[] strings = new String[] { bankId };
		Msg msg = userService.deleteBankBatch(strings);
		return msg;
	}

	/**
	 * 修改地址
	 *
	 * @param address
	 * @return
	 */
	@Token(remove = true)
	@ResponseBody
	@RequestMapping("/updateAddress")
	public Msg updateAddress(Address address) {
		Msg msg = userService.updateAddress(address);
		System.out.println(address);
		return msg;
	}

	/**
	 * 根据用户id查询对应的地址
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectAddressByUserId/{userId}")
	public List<Address> selectAddressByUserId(@PathVariable("userId") String userId) {
		List<Address> list = userService.selectAddressByUserId(userId);
		return list;
	}

	/**
	 * 单挑删除地址的controller
	 *
	 * @param addressId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteAddressByAddressId")
	public Msg deleteAddressByAddressId(@RequestParam("addressId") String addressId) {
		System.out.println("addressId为:" + addressId);
		String[] strings = new String[] { addressId };
		Msg msg = userService.deleteAddressBatch(strings);
		return msg;
	}
}
