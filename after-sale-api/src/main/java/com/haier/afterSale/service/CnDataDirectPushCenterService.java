package com.haier.afterSale.service;
import com.haier.afterSale.model.AfterSale;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.ExchangeGoods;
import com.haier.purchase.data.model.ReturnTable;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

public interface CnDataDirectPushCenterService {

    ServiceResult<List<ExchangeGoods>> searchList(ExchangeGoods vo);

    ServiceResult<List<ReturnTable>> findReturnGoods(ReturnTable vo);

    String returnGoodsPushSAP(String id) throws MalformedURLException, ParseException;
}
