package com.haier.stock.services;


import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.HopStockSyncModel;
import com.haier.stock.service.HopStockSyncService;
import com.haier.stock.util.ErrorCode;
import com.haier.stock.util.MessageFormater;

public class HopStockSyncServiceImpl implements HopStockSyncService {
    private HopStockSyncModel hopStockSyncModel;

    @Override
    public ServiceResult<Boolean> syncStock(String sku, String secCode, Integer qty, String source) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (StringUtil.isEmpty(sku)) {
            result.setError(ErrorCode.STOCK_ERROR_SKU_NOTVALID,
                MessageFormater.formate(ErrorCode.STOCK_ERROR_SKU_NOTVALID, sku));
            return result;
        }
        if (StringUtil.isEmpty(secCode)) {
            result.setError(ErrorCode.STOCK_ERROR_SECCODE_NOTVALID,
                MessageFormater.formate(ErrorCode.STOCK_ERROR_SECCODE_NOTVALID, secCode));
            return result;
        }
        if (qty == null) {
            result.setError(ErrorCode.STOCK_ERROR_QTY_NOTVALID,
                MessageFormater.formate(ErrorCode.STOCK_ERROR_QTY_NOTVALID, qty));
            return result;
        }
        if (StringUtil.isEmpty(source)) {
            result.setError(ErrorCode.STOCK_ERROR_SOURCE_NOTVALID,
                MessageFormater.formate(ErrorCode.STOCK_ERROR_SOURCE_NOTVALID, source));
            return result;
        }

        try {
            String message = hopStockSyncModel.stockSync(sku, secCode, qty, source);
            result.setMessage(message);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            result.setError(ErrorCode.STOCK_ERROR_FAILD_SYNC_STOCK_FAILD, MessageFormater.formate(
                ErrorCode.STOCK_ERROR_FAILD_SYNC_STOCK_FAILD, sku, secCode, source, qty));
            result.setSuccess(false);
        }

        return result;
    }

    public HopStockSyncModel getHopStockSyncModel() {
        return hopStockSyncModel;
    }

    public void setHopStockSyncModel(HopStockSyncModel hopStockSyncModel) {
        this.hopStockSyncModel = hopStockSyncModel;
    }

}
