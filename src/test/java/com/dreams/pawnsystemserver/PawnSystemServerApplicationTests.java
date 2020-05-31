package com.dreams.pawnsystemserver;

import com.dreams.channel.bo.ChannelBo;
import com.dreams.channel.dao.ChannelDao;
import com.dreams.channel.po.ChannelPo;
import com.dreams.goods.bo.GoodsBo;
import com.dreams.goods.dao.GoodsDao;
import com.dreams.goods.po.GoodsPo;
import com.dreams.sys.po.MenuPo;
import com.dreams.sys.service.MenuService;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PawnSystemServerApplicationTests {
    @Resource
    private GoodsDao goodsDao;

    @Test
    public void test(){
        List<GoodsPo> allGoodsInfo = this.goodsDao.getAllGoodsInfo(new GoodsBo());
        for (GoodsPo goodsPo : allGoodsInfo) {
            System.out.println(goodsPo);
        }
    }
}
