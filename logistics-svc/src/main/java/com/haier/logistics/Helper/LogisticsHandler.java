package com.haier.logistics.Helper;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisVomShippingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interface to implemented by the Logistics Handler to process the {@code VomShippingStatus}
 * Created by 钊 on 2014/7/29.
 */
@Service
public abstract class LogisticsHandler {
    private LogisticsHandler     successor;
    @Autowired
    private EisVomShippingStatusService  eisVomShippingStatusService;

    protected LogisticsHandler getSuccessor() {
        return successor;
    }

    private String dealMsg(String msg) {
        if (StringUtil.isEmpty(msg)) {
            msg = "";
        }
        if (msg.length() > 255) {
            msg = msg.substring(0, 255);
        }
        return msg;
    }

    protected int setProcessStatus(Integer id, Integer statusUpdateTo, Integer statusUpdateFrom,
                                   String msg) {
        msg = dealMsg(msg);
        return eisVomShippingStatusService.updateProcessStatus(id, statusUpdateTo, statusUpdateFrom, msg);
    }

    protected int setProcessSuccess(Integer id, String msg) {

        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_DOWN,
            VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }

    protected int setProcessFailed(Integer id, String msg) {
        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_WAIT,
            VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }

    protected int setProcessError(Integer id, String msg) {
        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_ERROR,
            VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }

    protected EisVomShippingStatusService getVomShippingStatusDao() {
        return eisVomShippingStatusService;
    }

    /**
     * 处理物流状态信息
     * @param shippingStatus
     * @return Return @{code true} if handler can process and {@code false} if can not .
     * @throws BusinessException Throws BusinessException when exception occurred
     */
    abstract public void process(VomShippingStatus shippingStatus) throws BusinessException;

    public void setVomShippingStatusDao(EisVomShippingStatusService eisVomShippingStatusService) {
        this.eisVomShippingStatusService = eisVomShippingStatusService;
    }

    public void setSuccessor(LogisticsHandler successor) {
        this.successor = successor;
    }

}
