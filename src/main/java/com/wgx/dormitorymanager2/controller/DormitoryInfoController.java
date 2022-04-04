package com.wgx.dormitorymanager2.controller;

import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.bean.ScoreAndLikesInfo;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.mapper.PaymentInfoMapper;
import com.wgx.dormitorymanager2.message.Message;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import com.wgx.dormitorymanager2.service.StudentService;
import com.wgx.dormitorymanager2.utils.IsNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * author:wgx
 * version:1.0
 */
@Controller
public class DormitoryInfoController {
    @Autowired
    private DormitoryInfoService dormitoryInfoService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/uploadDormitoryPhoto")
    @ResponseBody
    public Message uploadDormitoryPhoto(@RequestParam("photo") MultipartFile photo, @RequestParam("dormitoryPhotoName") String dormitoryPhotoName) throws IOException {
        return dormitoryInfoService.uploadDormitoryPhoto (photo, dormitoryPhotoName);
    }
    @GetMapping("/queryAllDormitory")
    @ResponseBody
    public Message queryAllDormitory() {
        List<DormitoryInfo> dormitoryInfoList = dormitoryInfoService.queryAllDormitory();
        if (dormitoryInfoList != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("dormitoryInfoList", dormitoryInfoList);
            return Message.success("成功", map);
        } else {
            return Message.fail("没有查询到宿舍", null);
        }
    }

    @GetMapping({"/dormitoryManage", "/sanitaryInspection", "/paymentInformation"})
    public ModelAndView dormitoryManage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        List<Integer> buildingIdList = dormitoryInfoService.queryAllBuildingId();
        mav.addObject("buildingIdList", buildingIdList);
        String URI = request.getRequestURI();
        if ("/dormitoryManage".equals(URI)) {
            mav.setViewName("dormitoryManage");
        } else if ("/sanitaryInspection".equals(URI)) {
            mav.setViewName("sanitaryInspection");
        } else if ("/paymentInformation".equals(URI)) {
            mav.setViewName("paymentInformation");
        }
        return mav;
    }
    @GetMapping("/queryAllFloorByBuildingId")
    @ResponseBody
    public Message queryAllFloorByBuildingId(@RequestParam("buildingId") Integer buildingId) {
        List<Object> floorIdList = dormitoryInfoService.queryAllFloorByBuildingId(buildingId);
        if (floorIdList != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("floorIdList", floorIdList);
            return Message.success("成功", map);
        } else {
            return Message.fail("未查询到宿舍号", null);
        }
    }
    @GetMapping("/queryAllStudentByBuildingAndFloor")
    public ModelAndView queryAllStudentByBuildingAndFloor(@RequestParam("buildingId") Integer buildingId, @RequestParam("floorId") Integer floorId) {
        DormitoryInfo dormitoryInfo = dormitoryInfoService.queryDormitoryByBuildingAndFloor(buildingId, floorId);
        List<Student> students = studentService.queryAllStudentByDormitoryId(dormitoryInfo.getDormitoryId());
        ModelAndView mav = new ModelAndView();
        mav.addObject("dormitoryInfo", dormitoryInfo);
        mav.addObject("students", students);
        mav.setViewName("forward:/dormitoryManage");
        return mav;
    }
    @GetMapping("/saveNewDormitory")
    @ResponseBody
    public Message saveNewDormitory(@RequestParam("buildingId") String buildingId, @RequestParam("floorId") String floorId){
        if (IsNumber.StringIsNumber(buildingId) && IsNumber.StringIsNumber(floorId)) {
            Integer dormitoryId = Integer.valueOf(buildingId + floorId);
            dormitoryInfoService.saveNewDormitory(dormitoryId, Integer.valueOf(buildingId), Integer.valueOf(floorId));
            return Message.success("成功", null);
        } else {
            return Message.fail("宿舍楼或宿舍号错误", null);
        }
    }
    @GetMapping("/queryDormitoryByStudentId")
    @ResponseBody
    public Message queryDormitoryByStudentId(@RequestParam("studentId") Integer studentId) {
        Student student = studentService.getStudentById(studentId);
        DormitoryInfo dormitoryInfo = dormitoryInfoService.queryDormitoryById(student.getDormitoryId());
        if (dormitoryInfo != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("dormitoryInfo", dormitoryInfo);
            return Message.success("成功", map);
        } else {
            return Message.fail("未查询到宿舍信息", null);
        }
    }
    @GetMapping("/queryAllDormitoryByBuildingId")
    public ModelAndView queryAllDormitoryByBuildingId(@RequestParam("buildingId") Integer buildingId, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        List<DormitoryInfo> dormitoryInfos = dormitoryInfoService.queryAllDormitoryByBuildingId(buildingId);
        //对dormitoryInfos处理,查询点赞数和分数情况
        List<ScoreAndLikesInfo> scoreAndLikesInfos = new ArrayList<>();
        if (student != null) {
            dormitoryInfos.stream().forEach(dormitoryInfo -> {
                Integer dormitoryId = dormitoryInfo.getDormitoryId();
                Integer score = (Integer) redisTemplate.opsForValue().get(dormitoryId + "-score");
                Integer likes = (Integer) redisTemplate.opsForValue().get(dormitoryId + "-likes");
                Boolean isMember = redisTemplate.opsForSet().isMember(dormitoryId + "-likes-members", student.getStudentId());
                dormitoryInfo.setScoreAndLikesInfo(new ScoreAndLikesInfo(score, likes, isMember));
            });
        } else {
            dormitoryInfos.stream().forEach(dormitoryInfo -> {
                Integer dormitoryId = dormitoryInfo.getDormitoryId();
                Integer score = (Integer) redisTemplate.opsForValue().get(dormitoryId + "-score");
                Integer likes = (Integer) redisTemplate.opsForValue().get(dormitoryId + "-likes");
                dormitoryInfo.setScoreAndLikesInfo(new ScoreAndLikesInfo(score, likes, null));
            });
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("dormitoryInfos", dormitoryInfos);
        mav.addObject("buildingId", buildingId);
        mav.setViewName("forward:/sanitaryInspection");
        return mav;
    }

    @ResponseBody
    @GetMapping("/clearAllLikes")
    public Message clearAllLikes() {
        List<DormitoryInfo> dormitoryInfos = dormitoryInfoService.queryAllDormitory();
        for (DormitoryInfo dormitoryInfo : dormitoryInfos) {
            String likes = dormitoryInfo.getDormitoryId() + "-likes";
            String likesMembers = dormitoryInfo.getDormitoryId() + "-likes-members";
            redisTemplate.opsForValue().set(likes, 0);
            redisTemplate.delete(likesMembers);
        }
        return Message.success("成功", null);
    }

    @GetMapping("/clearLikes")
    @ResponseBody
    public Message clearLikes(@RequestParam("dormitoryId") Integer dormitoryId) {
        String likes = dormitoryId + "-likes";
        String likesMembers = dormitoryId + "-likes-members";
        redisTemplate.opsForValue().set(likes, 0);
        redisTemplate.delete(likesMembers);
        return Message.success("成功", null);
    }

    @GetMapping("/changeScoreByDormitoryId")
    @ResponseBody
    public Message changeScoreByDormitoryId(@RequestParam("dormitoryId") Integer dormitoryId, @RequestParam("inputScore") Integer score) {
        redisTemplate.opsForValue().set(dormitoryId + "-score", score);
        return Message.success("成功", null);
    }

    @GetMapping("/likes")
    @ResponseBody
    public Message likes(@RequestParam("dormitoryId") Integer dormitoryId, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        String likesMembers = dormitoryId + "-likes-members";
        String likes = dormitoryId + "-likes";
        Boolean isMember = redisTemplate.opsForSet().isMember(likesMembers, student.getStudentId());
        if (isMember) {
            //开启事务
            redisTemplate.watch(Arrays.asList(likes, likesMembers));
            List<Object> exec = (List<Object>) redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    operations.opsForSet().remove(likesMembers, student.getStudentId());
                    operations.opsForValue().decrement(likes);
                    return operations.exec();
                }
            });
//            redisTemplate.watch(Arrays.asList(likes, likesMembers));
//            redisTemplate.multi();
//            redisTemplate.opsForSet().remove(likesMembers, student.getStudentId());
//            redisTemplate.opsForValue().decrement(likes);
//            List exec = redisTemplate.exec();
            if (exec == null || exec.isEmpty()) {
                return Message.fail("错误", null);
            }
            return Message.success("取消点赞", null);
        } else {
            redisTemplate.watch(Arrays.asList(likes, likesMembers));
            List<Object> exec = (List<Object>) redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    operations.opsForSet().add(likesMembers, student.getStudentId());
                    operations.opsForValue().increment(likes);
                    return operations.exec();
                }
            });

//            redisTemplate.watch(Arrays.asList(likes, likesMembers));
//            redisTemplate.multi();
//            redisTemplate.opsForSet().add(likesMembers, student.getStudentId());
//            redisTemplate.opsForValue().increment(likes);
//            List exec = redisTemplate.exec();
            if (exec == null || exec.isEmpty()) {
                return Message.fail("错误", null);
            }
            return Message.success("点赞成功", null);
        }
    }

}
