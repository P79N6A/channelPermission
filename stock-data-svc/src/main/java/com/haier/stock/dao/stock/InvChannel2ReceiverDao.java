package com.haier.stock.dao.stock;


import com.haier.stock.model.InvChannel2Receiver;

public interface InvChannel2ReceiverDao {
    public InvChannel2Receiver getByReceiverCode(String receiverCode);
}
