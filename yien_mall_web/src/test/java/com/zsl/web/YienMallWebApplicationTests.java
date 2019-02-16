package com.zsl.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.Goods;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.fastdfs.FastDFSService;
import com.zsl.common.interfaces.management.GoodsService;
import com.zsl.common.utils.Msg;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YienMallWebApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(YienMallWebApplicationTests.class);

    @Reference(group = "management", version = "1.0.0")
    private GoodsService goodsService;

    @Autowired
    private FastDFSService fastDFSService;

    @Test
    public void contextLoads() throws IOException {

        List<Goods> list = goodsService.selectAllGoods();
        for (Goods goods : list) {
            try {
                MultipartFile multipartFile1 = getImsge.downLoadFromUrl(goods.getGoodsImage1(), goods.getGoodsId() + "image1.jpg", "E:\\images1");
                MultipartFile multipartFile2 = getImsge.downLoadFromUrl(goods.getGoodsImage2(), goods.getGoodsId() + "image2.jpg", "E:\\images1");
                MultipartFile multipartFile3 = getImsge.downLoadFromUrl(goods.getGoodsImage3(), goods.getGoodsId() + "image3.jpg", "E:\\images1");
                Msg msg1 = fastDFSService.fileUpload(multipartFile1);
                Msg msg2 = fastDFSService.fileUpload(multipartFile2);
                Msg msg3 = fastDFSService.fileUpload(multipartFile3);
                logger.info("msg1的数据为：" + msg1);
                logger.info("msg2的数据为：" + msg2);
                logger.info("msg3的数据为：" + msg3);
                goods.setGoodsImage1(msg1.getExtend().get("url").toString());
                goods.setGoodsImage2(msg2.getExtend().get("url").toString());
                goods.setGoodsImage3(msg3.getExtend().get("url").toString());
                goodsService.updateGoodsByGoodsId(goods);
            } catch (Exception ex) {
                logger.info("错误的对应的goods为：" + goods);
            }
        }
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test33() {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}


class getImsge {
    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static MultipartFile downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");

        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.IMAGE_JPEG.toString(), fileInputStream);
        return multipartFile;
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}

