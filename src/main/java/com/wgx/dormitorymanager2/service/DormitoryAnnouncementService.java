package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.DormitoryAnnouncement;
import com.wgx.dormitorymanager2.mapper.DormitoryAnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
        List<DormitoryAnnouncement> dormitoryAnnouncements = dormitoryAnnouncementMapper.selectList(null);
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
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = new Page<>(pageNum, 1);
        dormitoryAnnouncementPage.setTotal(dormitoryAnnouncements.size());
        dormitoryAnnouncementPage.setRecords(Arrays.asList(dormitoryAnnouncements.get(pageNum - 1)));
        dormitoryAnnouncementPage.setPages(dormitoryAnnouncements.size());
        return dormitoryAnnouncementPage;
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
    public Page<DormitoryAnnouncement> queryDormitoryAnnouncementPageByAnnouncementId(Integer announcementId) {
        List<DormitoryAnnouncement> dormitoryAnnouncements = dormitoryAnnouncementMapper.selectList(null);
        int current = 0;
        ArrayList<DormitoryAnnouncement> records = new ArrayList<>();
        for (int i = 0; i < dormitoryAnnouncements.size(); i++) {
            if (dormitoryAnnouncements.get(i).getAnnouncementId() == announcementId) {
                current = i + 1;
                records.add(dormitoryAnnouncements.get(i));
                break;
            }
        }
        Page<DormitoryAnnouncement> dormitoryAnnouncementPage = new Page<>(current, 1);
        dormitoryAnnouncementPage.setRecords(records);
        dormitoryAnnouncementPage.setPages(dormitoryAnnouncements.size());
        dormitoryAnnouncementPage.setTotal(dormitoryAnnouncements.size());
        return dormitoryAnnouncementPage;
    }
    public Page<DormitoryAnnouncement> queryDormitoryAnnouncementPage(Integer pageNum) {
        Page<DormitoryAnnouncement> page = new Page<>(pageNum, 1);
        return dormitoryAnnouncementMapper.selectPage(page, null);
    }
    public DormitoryAnnouncement queryDormitoryAnnouncementById(Integer announcementId) {
        return dormitoryAnnouncementMapper.selectById(announcementId);
    }
    public void changeAnnouncement(DormitoryAnnouncement dormitoryAnnouncement) {
        dormitoryAnnouncementMapper.updateById(dormitoryAnnouncement);
    }

}
