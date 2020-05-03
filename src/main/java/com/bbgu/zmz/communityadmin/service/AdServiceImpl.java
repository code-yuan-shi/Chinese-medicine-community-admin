package com.bbgu.zmz.communityadmin.service;

import com.bbgu.zmz.communityadmin.enums.MsgEnum;
import com.bbgu.zmz.communityadmin.enums.Position;
import com.bbgu.zmz.communityadmin.mapper.AdExtMapper;
import com.bbgu.zmz.communityadmin.mapper.AdMapper;
import com.bbgu.zmz.communityadmin.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private AdExtMapper adExtMapper;

    /*
    查询所有广告信息
     */
    @Override
    public List<Ad> findAd(String title) {
        List<Ad> adList = adExtMapper.findAllAd(title);
        for(Ad ad:adList){
            for(Position position:Position.values()){
                if(ad.getPos().equals(position.getPos())){
                    ad.setPos(position.getMessage());
                }
            }
        }
        return adList;
    }

    /*
    删除广告信息
     */
    @Override
    public void delAd(Integer id) {
        adMapper.deleteByPrimaryKey(id);
    }

    //查找广告信息
    @Override
    public Ad findAdById(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }

    /*
    更新广告信息
     */
    @Override
    public void updateAd(Ad ad) {
        adMapper.updateByPrimaryKeySelective(ad);
    }

    /*
    添加广告信息
     */
    @Override
    public int addAd(Ad ad) {
       return adMapper.insertSelective(ad);
    }


}
