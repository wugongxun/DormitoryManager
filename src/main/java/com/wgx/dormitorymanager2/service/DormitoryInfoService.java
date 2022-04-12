package com.wgx.dormitorymanager2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.mapper.DormitoryInfoMapper;
import com.wgx.dormitorymanager2.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * author:wgx
 * version:1.0
 */
@Service
public class DormitoryInfoService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Autowired
    private DormitoryInfoMapper dormitoryInfoMapper;

    public DormitoryInfo queryDormitoryById(Integer dormitoryId) {
        return dormitoryInfoMapper.selectById(dormitoryId);
    }
    public List<DormitoryInfo> queryAllDormitory() {
        return dormitoryInfoMapper.selectList(null);
    }

    public Message uploadDormitoryPhoto(MultipartFile photo, Integer dormitoryId) throws IOException {
        if (photo.isEmpty()) {
            return Message.fail("文件为空", null);
        }
        String originalFilename = photo.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀
        UUID uuid = UUID.randomUUID();
        String prefix = uuid.toString().replaceAll("-", "");//随机生成前缀
        String dormitoryPhotoName = prefix + suffix;
        //将数据库中的对应宿舍的dormitoryPhotoName修改为上传后的名字
        DormitoryInfo dormitoryInfo = dormitoryInfoMapper.selectById(dormitoryId);
        String oldPhotoName = dormitoryInfo.getPhotoName();
        dormitoryInfo.setPhotoName(dormitoryPhotoName);
        dormitoryInfoMapper.updateById(dormitoryInfo);
        File file = new File(uploadFolder);
        if(!file.exists()){
            file.mkdir();
        }
        //删除旧图片
        File originalFile = new File(uploadFolder + File.separator + oldPhotoName);
        originalFile.delete();
        System.out.println(originalFile);
        String finalPath = uploadFolder + File.separator + dormitoryPhotoName;
        photo.transferTo(new File(finalPath));
        return Message.success("上传成功", null);
    }

    public List<Integer> queryAllBuildingId() {
        return dormitoryInfoMapper.queryAllBuildingId();
    }
    public List<Object> queryAllFloorByBuildingId(Integer buildingId) {
        QueryWrapper<DormitoryInfo> wrapper = new QueryWrapper<>();
        wrapper.select("floor_id").eq("building_id", buildingId);
        List<Object> floorIdList = dormitoryInfoMapper.selectObjs(wrapper);
        return floorIdList;
    }
    public DormitoryInfo queryDormitoryByBuildingAndFloor(Integer buildingId, Integer floorId) {
        QueryWrapper<DormitoryInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id", buildingId).eq("floor_id", floorId);
        DormitoryInfo dormitoryInfo = dormitoryInfoMapper.selectOne(wrapper);
        return dormitoryInfo;
    }
    public void saveNewDormitory(Integer dormitoryId, Integer buildingId, Integer floorId) {
        dormitoryInfoMapper.insert(new DormitoryInfo(dormitoryId, buildingId, floorId, buildingId + "-" + floorId + ".jpg", null));
    }
    public List<DormitoryInfo> queryAllDormitoryByBuildingId(Integer buildingId) {
        QueryWrapper<DormitoryInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id", buildingId);
        return dormitoryInfoMapper.selectList(wrapper);
    }

}
