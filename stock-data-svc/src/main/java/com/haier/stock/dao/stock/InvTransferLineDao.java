package com.haier.stock.dao.stock;

import com.haier.stock.model.InvTransferLine;

public interface InvTransferLineDao {
    InvTransferLine getTransferLine(String refDHNo);
}
