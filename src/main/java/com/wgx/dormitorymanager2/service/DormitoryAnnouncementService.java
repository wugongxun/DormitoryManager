package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.DormitoryAnnouncement;
import com.wgx.dormitorymanager2.mapper.DormitoryAnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class DormitoryAnnouncementService {
    @Autowired
    DormitoryAnnouncementMapper dormitoryAnnouncementMapper;
    public Page<DormitoryAnnouncement> queryDormitoryAnnouncementNotExpired(Integer pageNum) throws ParseException {
        Page<DormitoryAnnouncement> page = new Page<>(pageNum, 1);
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = dormitoryAnnouncementMapper.selectPage(page, null);
        List<DormitoryAnnouncement> dormitoryAnnouncements = dormitoryAnnouncementPage.getRecords();
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //过滤过期的公告
        dormitoryAnnouncements = dormitoryAnnouncements.stream().filter(dormitoryAnnouncement -> {
            try {
                Date expirationDate = sdf.parse(dormitoryAnnouncement.getExpirationDate());
                return expirationDate.after(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                //不处理，抛出异常
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return dormitoryAnnouncementPage.setRecords(dormitoryAnnouncements);
    }

    public List<DormitoryAnnouncement> dormitoryAnnouncement() {
        return dormitoryAnnouncementMapper.selectList(null);
    }
    public void releaseAnnouncement(DormitoryAnnouncement dormitoryAnnouncement) {
        dormitoryAnnouncementMapper.insert(dormitoryAnnouncement);
    }
    public void deleteAnnouncement(Integer announcementId) {
        dormitoryAnnouncementMapper.deleteById(announcementId);
    }
}
