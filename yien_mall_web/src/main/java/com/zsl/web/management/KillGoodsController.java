package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillService;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.management.KillGoodsService;
import com.zsl.common.utils.Msg;
//import com.zsl.web.config.TiggerRefresh;
import com.zsl.web.anno.Token;
import com.zsl.web.config.scheduler.KillEndJob;
import com.zsl.web.config.scheduler.KillPreJob;
import com.zsl.web.config.scheduler.QuartzManager;
import com.zsl.web.utils.CronDateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zsl.web.utils.DateUtils.compare_date;
import static com.zsl.web.utils.DateUtils.getAddDay;

/**
 * @author ${张世林}
 * @date 2019/01/27
 * 作用：秒杀商品的controller
 */
@Controller
@RequestMapping("/kill")
public class KillGoodsController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KillGoodsController.class);

	@Reference(group = "management", version = "1.0.0")
	private KillGoodsService killGoodsService;

	@Reference(group = "management", version = "1.0.0")
	private KillTimeService killTimeService;

	@Reference(group = "management", version = "1.0.0")
	private GoodsService goodsService;

	@Reference(group = "yien_mall_kill", version = "1.0.0")
	private KillService killService;

	@Autowired
	private QuartzManager quartzManager;

	@Value("${kill.name.jobGroupName}")
	private String jobGroupName;

	@Value(("${kill.name.triggerGroupName}"))
	private String triggerGroupName;

	/**
	 * 跳转到秒杀页面
	 *
	 * @return
	 */
	@RequestMapping("/toKill")
	public String toKill() {
		return "management/kill";
	}

	/**
	 * 返回秒杀地址，在这个请求进入之前，会先被拦截器进行拦截，验证秒杀是否已经开启了
	 *
	 * @return
	 * @link : com.zsl.web.intercepter.KillInterceptor
	 */
	@ResponseBody
	@RequestMapping("/toKillAjax")
	public Msg toKillAjax() {
		return Msg.success().addInfo("/kill/toKill");
	}

	/**
	 * 根据状态来查询对应的秒杀商品信息
	 *
	 * @param killState
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findKillGoodsByState.html")
	public List<KillGoods> findKillGoodsByState(@RequestParam(name = "state", defaultValue = "UK") String killState) {
		List<KillGoods> killGoods = killGoodsService.selectByState(KillState.valueOf(killState));
		return killGoods;
	}

	/**
	 * 获取到当前日期以后的秒杀时间
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/killTime")
	public List<KillTime> killTime() {
		List<KillTime> list = killTimeService.selectKillTime();
		List<KillTime> list1 = new ArrayList<>();
		for (KillTime killTime : list) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int i = compare_date(df.format(killTime.getKillTime()), df.format(getAddDay(new Date(), 0)));
			if (i == 1) {
				list1.add(killTime);
			}
		}
		return list1;
	}

	@ResponseBody
	@RequestMapping("/killTimeOnlyString")
	public List<String> killTimeOnlyString() {
		List<KillTime> list = killTimeService.selectKillTime();
		List<String> list1 = new ArrayList<>();
		for (KillTime killTime : list) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int i = compare_date(df.format(killTime.getKillTime()), df.format(getAddDay(new Date(), 0)));
			if (i == 1) {
				list1.add(df.format(killTime.getKillTime()));
			}
		}
		return list1;
	}

	/**
	 * 修改秒杀商品
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateKillGoods")
	public Msg updateKillGoods(@RequestParam("id") Integer id,
							   @RequestParam("killId") String killId,
							   @RequestParam("killGoodsId") String killGoodsId,
							   @RequestParam("killGoodsState") String killGoodsState,
							   @RequestParam("killGoodsTime") String killGoodsTime,
							   @RequestParam("killGoodsAmount") String killGoodsAmount,
							   @RequestParam("killDefaultAmount") String killDefaultAmount,
							   @RequestParam("killGoodsName") String killGoodsName,
							   @RequestParam("killNum") Integer killNum) {
		KillGoods killGoods = new KillGoods(id, killId, killGoodsId, killGoodsName, new BigDecimal(killGoodsAmount),
				new BigDecimal(killDefaultAmount),
				KillState.valueOf(killGoodsState), null, null, new Date(), null, killNum);
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(killGoodsTime);
			killGoods.setKillGoodsTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		killGoods.setKillGoodsState(KillState.valueOf(killGoodsState));
		Integer integer = killGoodsService.updateKillGoods(killGoods);
		if (integer > 0) {
			return Msg.success().addInfo("审核认证成功");
		}
		return Msg.fail().addInfo("审核认证失败");
	}

	/**
	 * 新增秒杀时间
	 *
	 * @param killTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addKillTime")
	public Msg addKillTime(KillTime killTime) {
		//查询对应的秒杀记录的数量为多少
		Integer count = killTimeService.selectKillTimeByStateCount(KillTimeState.WK);
		if (count > 5) {
			logger.debug("秒杀队列太多，请等待或删除对应的秒杀批次以后再添加");
			return Msg.fail().addInfo("秒杀队列太多，请等待或删除对应的秒杀批次以后再添加");
		}

		killTime.setState(KillTimeState.WK);
		Integer integer = killTimeService.addKillTimeFromHtml(killTime);

		quartzManager.addJob(KillPreJob.class, KillEndJob.class,
				CronDateUtils.getCron(killTime.getPreTime()), CronDateUtils.getCron(killTime.getEndTime()),
				killTime.getPreTime().toString(), killTime.getEndTime().toString(), jobGroupName,
				killTime.getPreTime().toString(), killTime.getEndTime().toString(), triggerGroupName);

		if (integer > 0) {
			return Msg.success().addInfo("添加时间成功");
		}
		return Msg.fail().addInfo("添加时间失败");
	}

	/**
	 * 获取秒杀的所有的时间
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/killAllTime")
	public List<KillTime> killAllTime() {
		List<KillTime> list = killTimeService.selectKillTime();
		return list;
	}

	/**
	 * 删除对应的秒杀时间
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTime")
	public Msg deleteTime(Integer id) {
		KillTime killTime = killTimeService.selectKillTimeById(id);
		quartzManager.removeJob(killTime.getPreTime().toString(), killTime.getEndTime().toString(), jobGroupName,
				killTime.getPreTime().toString(), killTime.getEndTime().toString(),triggerGroupName);
		Integer integer = killTimeService.deleteKillTime(id);
		if (integer == 1) {
			return Msg.success().addInfo("删除时间成功");
		}
		return Msg.fail().addInfo("删除时间失败");
	}

	/**
	 * 创建秒杀商品数据
	 *
	 * @param goodsId
	 * @param killAmount
	 * @param killTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createKillGoods")
	public Msg createKillGoods(String goodsId, String killAmount, String killTime, Integer killNum) {
		KillGoods killGoods = new KillGoods();

		//修正时间，将字符串转化为date类型进行存储
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date killDate = simpleDateFormat.parse(killTime);
			//            List<KillGoods> list = killGoodsService.selectByKillTime(killTime);
			killGoods.setKillGoodsTime(killDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Goods goods = goodsService.selectGoodsByGoodsId(goodsId);
		//验证对应的商品参与秒杀数量是否超过预期
		if (goods.getGoodsInventory() < killNum) {
			return Msg.fail().addInfo("商品参与秒杀数量超出了对应的商品库存，不符合需求。");
		}
		//验证商品的秒杀价和现价，是否符合实际情况
		int i = new BigDecimal(killAmount).compareTo(goods.getGoodsNowAmount());
		if (i > 0) {
			return Msg.fail().addInfo("商品秒杀金额比商品现价高，不符合秒杀要求。");
		}
		killGoods.setKillNum(killNum);
		killGoods.setKillId(String.valueOf(System.nanoTime()));
		killGoods.setKillGoodsId(goods.getGoodsId());
		killGoods.setKillGoodsName(goods.getGoodsName());
		killGoods.setKillGoodsAmount(new BigDecimal(killAmount));
		killGoods.setKillDefaultAmount(goods.getGoodsDefaultAmount());
		killGoods.setImage(goods.getGoodsImage1());

		killGoods.setKillGoodsState(KillState.WK);
		killGoods.setRawAddTime(new Date());
		Integer integer = killGoodsService.insertOne(killGoods);
		if (integer > 0) {
			return Msg.success().addInfo("商品秒杀申请已提交，等待管理员审核");
		}
		return Msg.fail().addInfo("商品秒杀申请失败，请重试");
	}

	/**
	 * 跳转到秒杀商品详情的页面
	 * @return
	 */
	@RequestMapping("/killGoodDetail/{killGoodsId}/{killGoodsAmount}")
	public String killGoodDetail(@PathVariable("killGoodsId") String killGoodsId,
								 @PathVariable("killGoodsAmount") String killGoodsAmount,
								 Model model) {
		Goods goods = goodsService.selectGoodsByGoodsId(killGoodsId);
		model.addAttribute("goods",goods);
		model.addAttribute("killGoodsAmount",killGoodsAmount);
		return "management/killGoodDetail";
	}

	/**
	 * 跳转到秒杀的页面
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("/killGoodsBuy/{killGoodsId}/{killGoodsAmount}")
	public String killGoodsBuy(@PathVariable("killGoodsId") String killGoodsId,
								 @PathVariable("killGoodsAmount") String killGoodsAmount,
								 Model model) {
		Goods goods = goodsService.selectGoodsByGoodsId(killGoodsId);
		model.addAttribute("goods",goods);
		model.addAttribute("killGoodsAmount",killGoodsAmount);
		return "management/killGoodsBuy";
	}

	/**
	 * 查询对应的秒杀商品是否还有库存
	 * @param goodsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findKillGoodsNum")
	public Msg findKillGoodsNum(String goodsId) {
		Msg msg = killService.findKillGoodsNum(goodsId);
		return msg;
	}
}
