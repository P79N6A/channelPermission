package com.haier.svc.service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
public interface SequenceService {
    public void clearSequence(Map map);

    public Integer selectSequence(Map map);

    public void insertSequence(Map map);

    public void updateSequence(Map map);

    public void insertOrUpdateSequence(Map map);
}
