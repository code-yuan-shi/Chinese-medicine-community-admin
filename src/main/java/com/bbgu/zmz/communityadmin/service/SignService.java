package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.model.Ad;
import com.bbgu.zmz.communityadmin.model.Qiandao;

import java.util.List;

public interface SignService {
    List<Qiandao> findSign(Long userId);
    void delSign(Long id);
    Qiandao  findSignById(Long id);
    void updateSign(Qiandao qiandao);
    int addSign(Qiandao qiandao);
}
