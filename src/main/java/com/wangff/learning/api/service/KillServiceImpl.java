package com.wangff.learning.api.service;

import com.wangff.learning.api.mapper.StockMapper;
import com.wangff.learning.api.mapper.StockOrderMapper;
import com.wangff.learning.api.model.Stock;
import com.wangff.learning.api.model.StockOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class KillServiceImpl implements KillService {
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockOrderMapper stockOrderMapper;
    @Override
    public int kill(int sid) {
        //1校验库存
        Stock stock = checkStock(sid);
        //2扣库存
        saleStock(stock);
        //3创建订单
        int id = createOrder(stock);
        return id;
    }

    private int createOrder(Stock stock) {
        StockOrder stockOrder = StockOrder.builder()
                .sid(stock.getId()).name(stock.getName()).createTime(new Date()).build();
        stockOrderMapper.insert(stockOrder);
        return stockOrder.getId();
    }

    private void saleStock(Stock stock) {
//        stock.setSale(stock.getSale()+1);
//        stockMapper.updateByPrimaryKeySelective(stock);

        //乐观锁更新
        Example example = new Example(Stock.class);
        example.createCriteria()
                .andEqualTo("id",stock.getId())
                .andEqualTo("version",stock.getVersion());
        stock.setSale(stock.getSale()+1);
        stock.setVersion(stock.getVersion()+1);
        int count=stockMapper.updateByExample(stock,example);
//        int count=stockMapper.updateByOptimistic(stock);
        if (count == 0) {
            throw new RuntimeException("并发更新库存失败");
        }
    }

    private Stock checkStock(int sid) {
        Stock stock = stockMapper.selectOne(Stock.builder().id(sid).build());
        if (stock.getSale() == stock.getCount()) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }
}
