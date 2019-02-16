package com.zsl.search;

import com.zsl.common.entity.Goods;
import com.zsl.common.interfaces.search.GoodsSearchService;
import com.zsl.common.utils.Msg;
import com.zsl.search.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YienMallSearchApplicationTests {

    @Autowired
    private GoodsSearchService goodsSearchService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void contextLoads() {
        Msg msg1 = goodsSearchService.deleteAllGoodsFromES();
        Msg msg = goodsSearchService.insertAllGoodsToES();
        System.out.println(msg);
    }

    @Test
    public void testGoodsName() {
        goodsSearchService.findByGoodsName("日本");
    }
//
//    @Test
//    public void test2() {
//        Iterable<Goods> goodsNowAmount = goodsSearchService
//                .findByGoodsNowMoney("goodsNowAmount", new BigDecimal("30"), new BigDecimal("100"));
//        Iterator<Goods> iterator = goodsNowAmount.iterator();
//        while (iterator.hasNext()) {
//            Goods next = iterator.next();
//            System.out.println(next);
//        }
//    }

    @Test
    public void test3() {
        Iterable<Goods> goods = goodsSearchService.selectAllGoods();
        Iterator<Goods> iterator = goods.iterator();
        while (iterator.hasNext()) {
            Goods next = iterator.next();
            System.out.println(next);
        }
    }

//    @Autowired
//    private TestQueryBuilderService queryBuilderService;
//
//    @Test
//    public void test4() {
//        queryBuilderService.searchPrefixQueryBuilder("goodsName", "日本");
//    }
//
//    @Test
//    public void test5() {
//        String[] strs = { "日本", "德国", "法国" };
//        Iterable<Goods> list = queryBuilderService.searchTermsQueryBuilder("goodsName", strs);
//        Iterator<Goods> iterator = list.iterator();
//        while(iterator.hasNext()) {
//            Goods next = iterator.next();
//            System.out.println(next);
//        }
//    }
//
//    @Test
//    public void test6() {
//        Iterable<Goods> list = queryBuilderService
//                .searchRangeQueryBuilder("goodsNowAmount", 30.0, 100.0);
//        Iterator<Goods> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            Goods next = iterator.next();
//            System.out.println(next);
//        }
//    }
//
//    @Test
//    public void test7() {
//        Iterable<Goods> goods = queryBuilderService.searchMatchAllQueryBuilder();
//        Iterator<Goods> iterator = goods.iterator();
//        while (iterator.hasNext()) {
//            Goods next = iterator.next();
//            System.out.println(next);
//        }
//    }

    @Test
    public void test10() {
        goodsRepository.deleteByGoodsId("113969473121145");
    }

    @Test
    public void test11() {
        goodsSearchService.insertAllGoodsToES();

    }

}

