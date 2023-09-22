package kk.soft.co.jp.sys.impl;

import kk.soft.co.jp.sys.mapper.MessageInfoMstMapper;
import kk.soft.co.jp.sys.model.MessageInfoMstModel;
import kk.soft.co.jp.sys.service.MessageInfoMstService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageInfoMstServiceImpl implements MessageInfoMstService {

    @Resource
    private MessageInfoMstMapper mapper;

    /**
     * 新規作成
     *
     * @param messageInfoMst
     */
    @Override
    public Object insert(MessageInfoMstModel messageInfoMst) {

        return mapper.insert(messageInfoMst);
    }
}
