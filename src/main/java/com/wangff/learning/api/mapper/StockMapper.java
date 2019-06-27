package com.wangff.learning.api.mapper;

import com.wangff.learning.api.model.Stock;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock> {
    int updateByOptimistic(Stock stock);
}