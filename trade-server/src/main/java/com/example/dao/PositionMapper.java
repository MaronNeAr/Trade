package com.example.dao;

import com.example.pojo.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PositionMapper {
    List<String> selectAllCurrencyByAccount(@Param("account") String account);

    List<Position> selectPositionByAccount(@Param("account") String account);

    BigDecimal selectCurrencyInventoryByAccount(@Param("bc") String bc, @Param("account") String account);

    int insertCurrency(Position position);

    int updateCurrency(Position position);
}
