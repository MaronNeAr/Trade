package com.example.dao;

import com.example.pojo.TradeDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeDetailMapper {
    List<TradeDetail> selectAllTradeDetailsByAccount(@Param("account") String account);

    int insertTradeDetail(TradeDetail tradeDetail);
}
