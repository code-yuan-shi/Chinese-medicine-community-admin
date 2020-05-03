package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.model.Ad;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



public interface AdService {
    List<Ad> findAd(String title);
    void delAd(Integer id);
    Ad findAdById(Integer id);
    void updateAd(Ad ad);
    int addAd(Ad ad);
}
