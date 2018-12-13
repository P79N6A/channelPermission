package com.haier.svc.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.InvSection;

import java.util.List;

public interface InvSectionService {

    JSONObject getInvSectionList(InvSection invSection);

    List<InvSection> queryInvSectionExcel(InvSection invSection);
}
