package com.haier.shop.services;

import com.haier.shop.dao.shopread.StorageCitiesReadDao;
import com.haier.shop.dao.shopread.StoragesReadDao;
import com.haier.shop.dao.shopread.StoragesWwwRelasReadDao;
import com.haier.shop.dao.shopwrite.StoragesWriteDao;
import com.haier.shop.model.Storages;
import com.haier.shop.model.StoragesVo;
import com.haier.shop.model.StoragesWwwRelas;
import com.haier.shop.service.StoragesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoragesServiceImpl implements StoragesService{
    @Autowired
    private StoragesReadDao         storagesReadDao;
    @Autowired
    private StoragesWwwRelasReadDao storagesWwwRelasReadDao;
    @Autowired
    private StorageCitiesReadDao    storageCitiesReadDao;
    @Autowired
    private StoragesWriteDao storagesWriteDao;

    /**
     * 根据编码获取库位信息
     * @param code 库位编码
     * @return
     */
   public Storages getByCode(String code){
       return storagesReadDao.getByCode(code);
   }

    /**
     * 根据id获取库位信息
     * @param id 库位id
     * @return
     */
    public Storages get(Integer id){
        return storagesReadDao.get(id);
    }

	@Override
	public String queryCenterCode(String netPointN8) {
		// TODO Auto-generated method stub
		return storagesReadDao.queryCenterCode(netPointN8);
	}

	@Override
	public String queryCode(String centerCode) {
		// TODO Auto-generated method stub
		return storagesReadDao.queryCode(centerCode);
	}

    @Override
    public String getBywwwCode(String wwwCode) {
       return storagesReadDao.getBywwwCode(wwwCode);
    }

    @Override
    public StoragesWwwRelas findForGbCode(String wwwCode) {
        return storagesWwwRelasReadDao.findForGbCode(wwwCode);
    }

    @Override
    public StoragesWwwRelas findByCainiaoStoreCode(String caiNiaoCode) {
        return storagesWwwRelasReadDao.findByCainiaoStoreCode(caiNiaoCode);
    }

  @Override
  public List<Map<String, String>> getInfoByType(Integer type) {
    return storagesReadDao.getInfoByType(type);
  }


    @Override
    public Map<String, Object> getStoragesList(Map<String, Object> params) {

        List<StoragesVo> netPointListList =storagesReadDao.queryStoragesList(params);
        //查询总条数
        int resultCount = storagesReadDao.getRowCnts();

        //查询所属中心(城市ID)对用的城市名字
      /*  if(netPointListList != null && netPointListList.size() > 0){
            for (StoragesVo storagesVo : netPointListList) {
                Integer centerCityId = storagesVo.getCenterCity();
                List<String>  cityNameList = storageCitiesReadDao.getCityNameByCityId(centerCityId);
                storagesVo.setCityName(cityNameList.get(0));
            }
        }*/
        //查询总条数
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("total", resultCount);
        retMap.put("rows", netPointListList);
        return retMap;
    }


    /**
     * 导出库位列表查询
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getExportStoragesListByParams(Map<String, Object> params) {

        List<Map<String, Object>> exportStoragesList = storagesReadDao.getExportStoragesListByParams(params);

        return exportStoragesList;

    }

    @Override
    public int insert(Storages storages, String cityName) {
        return storagesWriteDao.insert(storages);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return storagesWriteDao.deleteByPrimaryKey(id);
    }

}
