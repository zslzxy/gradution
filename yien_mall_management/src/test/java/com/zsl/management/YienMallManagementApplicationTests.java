package com.zsl.management;

import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zsl.common.entity.*;
import com.zsl.common.entity.myenum.KillState;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.common.interfaces.management.KillGoodsService;
import com.zsl.common.interfaces.management.MerchantsService;
import com.zsl.common.myenum.Level;
import com.zsl.common.myenum.State;
import com.zsl.management.mapper.GoodsMapper;
import com.zsl.management.mapper.MerchantsMapper;
import com.zsl.management.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YienMallManagementApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        User user1 = userMapper.selectByMobileNo(new BigInteger("123"));
        System.out.println(user1);
        User user = new User();
        user.setId(1);
        user.setUserId("12346");
        user.setUserBirth(new Date());
        user.setUserState(State.Cerdibility_General);
        user.setUserLevel(Level.admin);
        int integer = userMapper.updateOne(user);

        System.out.println(userMapper.selectUserIdCount("123456"));
    }

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void test40() throws ParseException {
        List<KillTime> list = killTimeService.selectKillTimeByState(KillTimeState.WK);
        Collections.sort(list);
        for (KillTime killTime : list) {
            System.out.println(killTime);
        }
    }

//    @Autowired
//    private PaperMapper paperMapper;
//
//    @Test
//    public void test1() {
//        List<Paper> papers = paperMapper.selectAllGoods();
//        for (Paper paper : papers) {
//			System.out.println(paper);
//
//            //修改金额
//			Goods goods = new Goods();
//			goods.setGoodsNowAmount(paper.getPaperNowMoney());
//			goods.setGoodsDefaultAmount(paper.getPaperNowMoney().add(new BigDecimal(22)));
//			goods.setGoodsName(paper.getPaperName());
//
//			StringBuilder str = new StringBuilder();
//            str.append(paper.getPaperDesc());
////            str.append(paper.getPaperPress());
//			String value = str.toString();
//			goods.setGoodsDesc(value);
//			goods.setGoodsId(String.valueOf(System.nanoTime()));
//			goods.setGoodsDetail1(paper.getPaperDetail1());
//			goods.setGoodsDetail2(paper.getPaperDetail2());
//			goods.setGoodsDetail3(paper.getPaperDetail3());
//
//            //设置图片的地址
//            String bookImage1 = paper.getPaperImage1();
//            String bookImage2 = paper.getPaperImage2();
//            String bookImage3 = paper.getPaperImage3();
//            goods.setGoodsImage1(bookImage1.replace("com/n5/","com/n1/"));
//            goods.setGoodsImage2(bookImage2.replace("com/n5/","com/n1/"));
//            goods.setGoodsImage3(bookImage3.replace("com/n5/","com/n1/"));
////            //设置商品的类型id
//            goods.setGoodsTypeId(8);
//
////            //设置商品销售状态
//            goods.setGoodsState("selling_now");
////            //设置商品销售时间
//            goods.setGoodsSellTime(new Date());
////            //设置商品的库存
//            goods.setGoodsInventory(aa());
////            //新增时间
//            goods.setRawAddTime(new Date());
////            //设置商家的id
//            goods.setGoodsMerchantsId(aa());
////            //设置商品的生产时间
//            goods.setGoodsProdPlace("成都");
//            goods.setGoodsProdTime(new Date());
//            goods.setGoodsShelfLife("九个月");
//
//            Integer integer = goodsMapper.insertGoods(goods);
//            System.out.println(integer);
//        }
//    }

    public Integer aa() {

        int begin = 1;
        int end = 100;
        int count = begin + end;
        int[] codes = new int[count + 1];
        for (int i = begin; i <= end; i++) {
            codes[i] = i;
        }
        //随机交换数据
        int index = 0;
        int tempCode = 0;
        Random random = new Random();
        for (int i = begin; i <= end; i++) {
            index = random.nextInt(count + 1);
            tempCode = codes[index];
            codes[index] = codes[i];
            codes[i] = tempCode;
        }
        return tempCode;
    }

    @Test
    public void test6() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        Collections.shuffle(goods);
        for (Goods good : goods) {
            goodsMapper.deleteGoodsByGoodsId(good.getGoodsId());
            goodsMapper.insertGoods(good);
        }
    }

    public Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        gson.serializeNulls();
        return gson;
    }

    @Test
    public void test7() {
        Goods goods = goodsMapper.selectGoodsByGoodsId("113966776876527");
        System.out.println(getGson().toJson(goods));
        Goods goods1 = goodsMapper.selectGoodsByGoodsId("4401094600620");
        System.out.println(getGson().toJson(goods1));
        Goods goods2 = goodsMapper.selectGoodsByGoodsId("4401800778233");
        System.out.println(getGson().toJson(goods2));
    }

    @Test
    public void test() {
        GoodsType goodsType2 = new GoodsType(3, "345", "33", "33", new Date(2018, 11, 23), new Date(2018, 11, 23));
        GoodsType goodsType1 = new GoodsType(2, "234", "22", "22", new Date(2018, 11, 22), new Date(2018, 11, 22));
        GoodsType goodsType = new GoodsType(1, "123", "11", "11", new Date(2018, 11, 21), new Date(2018, 11, 21));
        GoodsType goodsType_1 = new GoodsType(1, "123", "111", "111", new Date(2018, 11, 21), new Date(2018, 11, 21));
        ArrayList<GoodsType> goodsTypes = new ArrayList<>();
        goodsTypes.add(goodsType2);
        goodsTypes.add(goodsType1);
        goodsTypes.add(goodsType_1);
        goodsTypes.add(goodsType);

        ListIterator<GoodsType> iterator = goodsTypes.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        HashMap<String, List<GoodsType>> map = new LinkedHashMap<>();

        for (GoodsType type : goodsTypes) {
            if (map.containsKey(type.getRawAddTime().toString())) {
                map.get(type.getRawAddTime().toString()).add(type);
            } else {
                ArrayList<GoodsType> list = new ArrayList<>();
                list.add(type);
                map.put(type.getRawAddTime().toString(), list);
            }
            iterator.previous();
        }

        System.out.println(getGson().toJson(map));

    }


    @Autowired
    private MerchantsMapper merchantsMapper;

    @Test
    public void test14() {
        Merchants merchants1 = new Merchants(null, 1, "123456", "惊鸿小店", "重庆市巴南区", "张世林", new BigInteger("18875150531"), "1083837617@qq.com", new Date(), new Date(), null);
        Merchants merchants2 = new Merchants(null, 2, "12345", "小爱小店", "垫江", "世平", new BigInteger("18185166484"), "128762126@qq.com", new Date(), new Date(), null);
        merchantsMapper.insertOne(merchants1);
        merchantsMapper.insertOne(merchants2);
    }

    @Autowired
    private KillGoodsService killGoodsService;

    @Test
    public void test15() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        List<Goods> goods1 = goods.subList(0, 20);
        for (Goods good : goods1) {
            KillGoods killGoods = new KillGoods();
            killGoods.setKillGoodsId(good.getGoodsId());
            killGoods.setKillId(String.valueOf(System.nanoTime()));
            killGoods.setKillGoodsName(good.getGoodsName());
            killGoods.setKillDefaultAmount(good.getGoodsDefaultAmount());
            killGoods.setKillGoodsAmount(good.getGoodsNowAmount().subtract(new BigDecimal(2)));
            killGoods.setKillGoodsState(KillState.NK);
            killGoods.setKillGoodsTime(new Date(2019, 01, 03));
            killGoods.setRawAddTime(new Date());
            Integer integer = killGoodsService.insertOne(killGoods);
        }
    }

    @Test
    public void test17() {
        List<Goods> list = goodsMapper.selectAllGoods();
        Collections.shuffle(list);
        for (Goods goods : list) {
            goods.setGoodsName(goods.getGoodsName().replaceAll(" ", ""));
            goodsMapper.updateGoodsByGoodsId(goods);
        }
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test18() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void test19() {
        long start = System.currentTimeMillis();
        List<Goods> list = goodsMapper.selectAllGoods();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void test20() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void test21() {
        //Merchants merchants1 = new Merchants(null, "123456");
    }

    @Test
    public void test22() {
        for (int i=0;i<=1000;i++) {
            User user1 = new User(null, listss(8),
                    NameBuilder.build(3), NameBuilder.build(2),
                    "a43bfc9f4a5cebdd1a292c2915d61b92", RandomStringUtil.returnSex(),
                    listss(18), listss(10) + "@qq.com",
                    new BigInteger(listss(11)), 0, new BigDecimal("0"),
                    new Date(), null, State.Cerdibility_Optimal,
                    "重庆市巴南区李家沱街道", Level.user, new Date(),
                    null);

            Integer integer = userMapper.insertOne(user1);
        }
    }

    /**
     * 返回字符串
     * @param length
     * @return
     */
    public static String listss(int length) {
        RandomStringUtil randomStringUtil = new RandomStringUtil();
        List<String> list = Arrays.asList(randomStringUtil.getRandomCode(30).split(""));
        Collections.shuffle(list);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : list) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString().substring(0,length);
    }

    @Test
    public void test24() {
//        for (int i = 3; i<=130;i++) {
//            User user = userMapper.selectById(25 + i );
//            user.setUserLevel(Level.merchant);
//            userMapper.updateOne(user);
//            Merchants merchants = new Merchants(null,i,user.getUserId(),NameBuilder.build(2),"重庆市",
//                    user.getUserName(),user.getUserMobileNo(),user.getUserEmail(),new Date(),new Date(),null);
//            merchantsMapper.insertOne(merchants);
//        }
        List<Merchants> merchants = merchantsMapper.selectAll();
        for (Merchants merchant : merchants) {
            merchant.setMerchantsStoreName(NameBuilder.build(2) + "小店");
            merchantsMapper.updateOne(merchant);
        }
    }

    @Test
    public void test25() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        for (Goods good : goods) {
            Merchants merchants = merchantsMapper.selectOne(good.getGoodsMerchantsId());
            good.setGoodsMerchantsName(merchants.getMerchantsStoreName());
            goodsMapper.updateGoodsByGoodsId(good);
        }
    }
    @Test
    public void test26() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        for (Goods good : goods) {
            if (good.getGoodsMerchantsId() == 0) {
                good.setGoodsMerchantsId(aa());
            }
            Merchants merchants = merchantsMapper.selectOne(good.getGoodsMerchantsId());
            good.setGoodsMerchantsName(merchants.getMerchantsStoreName());
            goodsMapper.updateGoodsByGoodsId(good);
        }
    }

    @Test
    public void test27() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        Collections.shuffle(goods);
        for (Goods good : goods) {
            goodsMapper.deleteGoodsByGoodsId(good.getGoodsId());
            goodsMapper.insertGoods(good);
        }
    }

    @Autowired
    private KillTimeService killTimeService;
//    @Test
//    public void test30() throws ParseException {
//        killTimeService.addKillTimeAuto();
//    }


}


/**
 * @author lnexin@aliyun.com
 * @Name RandomStringUtil
 * @Descr 生成随机字符串
 * @date 2015年10月15日下午2:36:05
 */
class RandomStringUtil {
    /**
     * @param passLength : 要生成多少长度的字符串
     * @return 根据传入的type来判定
     */

    public String getRandomCode(int passLength) {
        StringBuffer buffer = null;
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        r.setSeed(new Date().getTime());
        buffer = new StringBuffer("123456789");
        int range = buffer.length();
        for (int i = 0; i < passLength; ++i) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 返回性别
     *
     * @return
     */
    public static String returnSex() {
        double random = returndandom();
        if (random >= 0.5) {
            return "boy";
        } else {
            return "girl";
        }
    }

    public static double returndandom() {
        double random = Math.random();
        return random;
    }

}
