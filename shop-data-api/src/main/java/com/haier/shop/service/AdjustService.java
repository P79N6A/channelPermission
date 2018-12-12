package com.haier.shop.service;


import com.haier.shop.model.Adjust;
import com.haier.shop.model.Invoices;

import java.util.List;


 
public interface AdjustService {

    String getVehicleAdjustNo( String begin);

    int updateSelectiveByAdjustNo( Adjust entity);

	List<Invoices> getAdjustByTiming(long startTime, long endTime);

}
