package com.haier.stock.services.helper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.stock.model.InvStockAge.StockAgeData;
import com.haier.common.util.JsonUtil;

public class StockAgeJSONHandler implements TypeHandler<List<StockAgeData>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<StockAgeData> parameter,
                             JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtil.toJson(parameter));
    }

    @Override
    public List<StockAgeData> getResult(ResultSet rs, String columnName) throws SQLException {
        return parseStockAgeData(rs.getString(columnName));
    }

    @Override
    public List<StockAgeData> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseStockAgeData(rs.getString(columnIndex));
    }

    @Override
    public List<StockAgeData> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseStockAgeData(cs.getString(columnIndex));
    }

    private List<StockAgeData> parseStockAgeData(String ageData) {
        Gson gson = new Gson();
        List<StockAgeData> l = gson.fromJson(ageData, new TypeToken<List<StockAgeData>>() {
        }.getType());
        return l;
    }
}
