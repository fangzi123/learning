package com.wangff.learning.api.service;

import com.wangff.learning.api.constant.RedisKeysConstant;
import com.wangff.learning.api.mapper.StockMapper;
import com.wangff.learning.api.mapper.StockOrderMapper;
import com.wangff.learning.api.model.Stock;
import com.wangff.learning.api.model.StockOrder;
import com.wdcloud.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class KillServiceImpl implements KillService {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockOrderMapper stockOrderMapper;
    @Override
    public int kill(int sid) {
        //1校验库存 DB校验
        //Stock stock = checkStock(sid);
        //1校验库存 Redis缓存校验
        Stock stock = checkStockByRedis(sid);
        //2扣库存
        saleStock(stock);
        //3创建订单  可以优化为异步操作
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

        //自增 redis 校验
        redisService.incr(RedisKeysConstant.STOCK_SALE + stock.getId());
        redisService.incr(RedisKeysConstant.STOCK_VERSION + stock.getId());
    }

    private Stock checkStock(int sid) {
        Stock stock = stockMapper.selectOne(Stock.builder().id(sid).build());
        if (stock.getSale() == stock.getCount()) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    private Stock checkStockByRedis(int sid) {
        Integer count = Integer.parseInt(redisService.get(RedisKeysConstant.STOCK_COUNT + sid));
        Integer sale = Integer.parseInt(redisService.get(RedisKeysConstant.STOCK_SALE + sid));
        if (count.equals(sale)) {
            throw new RuntimeException("库存不足 Redis currentCount=" + sale);
        }
        Integer version = Integer.parseInt(redisService.get(RedisKeysConstant.STOCK_VERSION + sid));
        Stock stock = new Stock();
        stock.setId(sid);
        stock.setName("商品");
        stock.setCount(count);
        stock.setSale(sale);
        stock.setVersion(version);
        return stock;
    }



    @Override
    public void killInit(int sid,int count) {
        int sale = 0;
        int version = 0;
        redisService.set(RedisKeysConstant.STOCK_COUNT+sid,count+"");
        redisService.set(RedisKeysConstant.STOCK_SALE+sid,sale+"");
        redisService.set(RedisKeysConstant.STOCK_VERSION+sid,version+"");
        Stock stock = stockMapper.selectOne(Stock.builder().id(sid).build());
        stock.setCount(count);
        stock.setSale(sale);
        stock.setVersion(version);
        stockMapper.updateByPrimaryKeySelective(stock);
        stockOrderMapper.delete(StockOrder.builder().sid(sid).build());
    }
}
