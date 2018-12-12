package com.haier.stock.dao.stock;



import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStore;
import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvMachineSet;


public interface InvMachineSetDao {


    List<InvMachineSet> getByMainSku(@Param("main_sku") String mainSku);


    public List<Map<String,Object>> select_sku(List<Map<String,Object>> list);
    
    List<InvMachineSet> getBySubSku(@Param("sub_sku") String subSku);
    
    /**
     * 根据主sku和bom项目号获取
     * @param mainSku
     * @param bomNum
     * @return
     */
    InvMachineSet getByMainSkuAndBomNum(@Param("main_sku") String mainSku,
                                        @Param("posnr") String bomNum);
    
    Integer insert(InvMachineSet machineSet);

    Integer update(InvMachineSet machineSet);

    List<InvMachineSet> getPageByCondition(@Param("machineSet") InvMachineSet condition, @Param("start")int start, @Param("rows")int pageSize);

    long getPagerCount(@Param("machineSet")InvMachineSet condition);

    Integer updateSubSku(@Param("subSku") String sku, @Param("status") Integer s,@Param("optUser") String currentUser);

}
