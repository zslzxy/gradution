package com.zsl.web.management;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.Merchants;
import com.zsl.common.entity.Ztree;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.interfaces.management.MerchantsService;
import com.zsl.common.interfaces.search.GoodsSearchService;
import com.zsl.common.utils.Msg;
import com.zsl.web.utils.NumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author ${张世林}
 * @date 2019/01/27
 * 作用：商家管理的冲突roller
 */
@Controller
@RequestMapping("/merchants")
public class MerchantsController {

    @Autowired
    private Gson gson;

    @Value("${management.merchant.password}")
    private String yienMerchantPassword;

    @Reference(group = "management", version = "1.0.0")
    private MerchantsService merchantsService;

    @Reference(group = "management", version = "1.0.0")
    private GoodsService goodsService;

    @Reference(group = "search",version = "1.0.0")
    private GoodsSearchService goodsSearchService;

    @ResponseBody
    @RequestMapping("/addMerchant.html")
    public Msg addMerchant(@RequestParam("password") String password, Merchants merchants) {
        if (!password.equals(yienMerchantPassword)) {
            return Msg.fail().addInfo("新增商家管理密码错误");
        }
        //设置商家得的唯一索引
        merchants.setId(null);
        merchants.setMerchantsId(NumUtils.aa());
        //新增商家
        Integer integer = merchantsService.insertOne(merchants);
        if (integer == 1) {
            return Msg.success().addMap("merchants",merchants).addInfo("新增商家成功");
        }
        return Msg.fail().addInfo("新增商家失败");
    }

    /**
     * 修改商家数据
     * @param password
     * @param merchants
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMerchant.html")
    public Msg updateMerchant(@RequestParam("password") String password, Merchants merchants) {
        if (!password.equals(yienMerchantPassword)) {
            return Msg.fail().addInfo("修改商家管理密码错误");
        }
        Integer integer = merchantsService.updateOne(merchants);
        if (integer == 1) {
            return Msg.success().addMap("merchants",merchants).addInfo("修改商家成功");
        }
        return Msg.fail().addInfo("修改商家失败");
    }

    /**
     * 查询对应的商家的信息
     * @param merchantsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByMerchantsId.html")
    public Msg findByMerchantsId(@RequestParam(name = "id",defaultValue = "-1") Integer merchantsId) {
        if (merchantsId.equals(new Integer("-1"))) {
            return Msg.fail().addInfo("没有对应的商家");
        }
        Merchants merchants = merchantsService.selectOne(merchantsId);
        return Msg.success().addInfo("查询成功").addMap("merchants",merchants);
    }

    @RequestMapping("/toMerchants")
    public String toMerchants() {
        return "management/merchants";
    }

    /**
     * 创建商家对应的商品节点
     * @return
     */
    @ResponseBody
    @RequestMapping("/createMerchantsTree")
    public String createMerchantsTree(String userId) {

        List<Ztree> ztrees = new LinkedList<Ztree>();

        List<Merchants> merchantss = merchantsService.selectByUserId(userId);
        for (Merchants merchants : merchantss) {

            //创建商家节点
            Ztree ztreeMerchants = new Ztree(merchants.getMerchantsId().toString(), -1, merchants.getMerchantsStoreName(), "", "");
            //创建商品节点下的分类
            Ztree ztreeBook = new Ztree(merchants.getMerchantsId() + "6",merchants.getMerchantsId(),"图书","","");
            Ztree ztreePaper = new Ztree(merchants.getMerchantsId() + "7", merchants.getMerchantsId(), "纸巾","","");
            Ztree ztreeOrther = new Ztree(merchants.getMerchantsId() + "8", merchants.getMerchantsId(), "百货","","");
            ztrees.add(ztreeMerchants);
            ztrees.add(ztreeBook);
            ztrees.add(ztreePaper);
            ztrees.add(ztreeOrther);

            Integer merchantsId = merchants.getMerchantsId();
            List<Goods> list = goodsService.selectGoodsByMerchantsId(merchantsId);
            for (Goods goods : list) {
                Ztree ztree = new Ztree();
                ztree.setId(goods.getGoodsId());
                ztree.setName(goods.getGoodsName());
                ztree.setPId(0);
                if (goods.getGoodsTypeId() == 6) {
                    ztree.setPId(Integer.valueOf(merchants.getMerchantsId() + "6"));
                } else if (goods.getGoodsTypeId() == 7) {
                    ztree.setPId(Integer.valueOf(merchants.getMerchantsId() + "7"));
                } else {
                    ztree.setPId(Integer.valueOf(merchants.getMerchantsId() + "8"));
                }
                ztrees.add(ztree);
            }
        }

        String s = gson.toJson(ztrees);
        return s;
    }

    /**
     * 根据用户id查询到对应的商家数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserMerchants")
    public List<Merchants> getUserMerchants(@RequestParam("userId") String userId) {
        List<Merchants> merchants = merchantsService.selectByUserId(userId);
        return merchants;
    }

    /**
     * 根据用户id查询到对应的商家数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserMerchantsGoods")
    public List<Goods> getUserMerchantsGoods(@RequestParam("merchantsId") Integer merchantsId) {
        List<Goods> list = goodsService.selectGoodsByMerchantsId(merchantsId);
        return list;
    }

    @ResponseBody
    @RequestMapping("/findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId")
    public List<Goods> findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(String goodsNameValue, Integer goodsTypeId, Integer goodsMerchantsId) {
        return goodsSearchService.findByGoodsNameAndGoodsTypeIdAndGoodsMerchantsId(goodsNameValue,goodsTypeId,goodsMerchantsId);
    }

    @ResponseBody
    @RequestMapping("/findByGoodsNameAndGoodsMerchantsId")
    public List<Goods> findByGoodsNameAndGoodsMerchantsId(String goodsNameValue, Integer goodsMerchantsId) {
        return goodsSearchService.findByGoodsNameAndGoodsMerchantsId(goodsNameValue,goodsMerchantsId);
    }
}
