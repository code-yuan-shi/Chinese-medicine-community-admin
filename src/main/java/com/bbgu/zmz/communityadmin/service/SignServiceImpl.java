package com.bbgu.zmz.communityadmin.service;


import com.bbgu.zmz.communityadmin.mapper.QiandaoExtMapper;
import com.bbgu.zmz.communityadmin.mapper.QiandaoMapper;
import com.bbgu.zmz.communityadmin.model.Qiandao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignServiceImpl implements SignService {
    @Autowired
    private QiandaoMapper qiandaoMapper;
    @Autowired
    private QiandaoExtMapper qiandaoExtMapper;


    @Override
    public List<Qiandao> findSign(Long userId) {
        return qiandaoExtMapper.findAllSign(userId);
    }

    @Override
    public void delSign(Long id) {
        qiandaoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Qiandao findSignById(Long id) {
        return qiandaoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateSign(Qiandao qiandao) {
        qiandaoMapper.updateByPrimaryKeySelective(qiandao);
    }

    @Override
    public int addSign(Qiandao qiandao) {
        return qiandaoMapper.insertSelective(qiandao);
    }
}
