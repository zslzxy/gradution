package com.zsl.web.kill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zsl.common.entity.KillGoods;
import com.zsl.common.interfaces.kill.KillService;
import com.zsl.common.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @Filename KillManagementController.java
 *
 * @Description 商品秒杀的管理的Controller
 *
 * @Version 1.0
 *
 * @Author 10838
 *
 * @Email 10838@yiji.com
 *
 * @History
 *<li>Author: 10838</li>
 *<li>Date: 2019年02月05日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Controller
@RequestMapping("/kill")
public class KillManagementController {

    @Reference(group = "yien_mall_kill", version = "1.0.0")
    private KillService killService;

    @ResponseBody
    @RequestMapping("/selectKillGoods")
    public Msg selectKillGoods() {
        List<KillGoods> list = killService.selectKillGoods();
        if (list.size() > 0) {
            return Msg.success().addInfo("可以进行操作").addMap("list",list);
        }
        return Msg.fail().addInfo("秒杀已经结束，请下次参与");
    }

}
