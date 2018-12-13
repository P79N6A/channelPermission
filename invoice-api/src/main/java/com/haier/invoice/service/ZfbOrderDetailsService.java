package com.haier.invoice.service;


import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetails;

public interface ZfbOrderDetailsService {

	ServiceResult<List<ZfbOrderMatchingDto>> selectAllByParam(QueryZFBOrderParameter queryOrder);

	ServiceResult<List<ZfbOrdersDetails>> selectZfbOrdersByParam(QueryZFBOrderParameter queryOrder);

	ServiceResult<List<ZfbOrderMatchingDto>> exportMatchingOrderList(QueryZFBOrderParameter queryOrder);

	ServiceResult<List<ZfbOrdersDetails>> selectReportFormByParam(QueryZFBOrderParameter queryOrder);
}
