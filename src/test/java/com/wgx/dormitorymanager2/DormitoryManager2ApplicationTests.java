package com.wgx.dormitorymanager2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wgx.dormitorymanager2.bean.*;
import com.wgx.dormitorymanager2.mapper.PaymentInfoMapper;
import com.wgx.dormitorymanager2.mapper.RepairInfoMapper;
import com.wgx.dormitorymanager2.mapper.StudentMapper;
import com.wgx.dormitorymanager2.service.DormitoryAnnouncementService;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import com.wgx.dormitorymanager2.service.RepairInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class DormitoryManager2ApplicationTests {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private DormitoryInfoService dormitoryInfoService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PaymentInfoMapper paymentInfoMapper;
	@Autowired
	private RepairInfoMapper repairInfoMapper;
	@Autowired
	private RepairInfoService repairInfoService;
	@Autowired
	private DormitoryAnnouncementService dormitoryAnnouncementService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testMapper() {
//		List<Student> students = studentMapper.queryStudentsWithDormitoryNameByClassId(10011802);
//		for (Student student : students) {
//			System.out.println(student);
//		}
//		DormitoryInfo dormitoryInfo = dormitoryInfoService.queryDormitoryById(219204);
//		System.out.println(dormitoryInfo);
		List<Object> objects = dormitoryInfoService.queryAllFloorByBuildingId(219);
		System.out.println(objects);
	}

	@Test
	public void testRedis() {
		Object score = redisTemplate.opsForValue().get("219201-score");
		redisTemplate.opsForValue().set("219204-score", 91);
		Boolean aBoolean = redisTemplate.opsForSet().isMember("219204-likes-members", 2018011389);
		System.out.println(score);
		System.out.println(aBoolean);
	}

	@Test
	public void testPaymentInfo() {
		PaymentInfo paymentInfo = paymentInfoMapper.queryPaymentInfoByDormitoryId(219204);
		List<PaymentRecord> paymentRecords = paymentInfo.getPaymentRecords();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//		paymentRecords.stream().forEach(paymentRecord -> {
//			System.out.println(sdf.format(paymentRecord.getPaymentDate()));
//		});
		paymentRecords.stream().forEach(System.out :: println);
	}

	@Test
	public void testRepairMapper() {
		QueryWrapper<RepairInfo> wrapper = new QueryWrapper<>();
		wrapper.eq("repair_dormitory", 219204);
		List<RepairInfo> repairInfos = repairInfoMapper.selectList(wrapper);
		System.out.println(repairInfos);
	}

	@Test
	public void testRepairService() {
//		Integer integer = repairInfoService.queryLastRepairId("2022-04-07");
//		System.out.println(integer);
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String repairDate = sdf.format(now);
//		repairDate += "00";
//		Integer integer1 = Integer.valueOf(repairDate.replaceAll("-", ""));
//		System.out.println(integer1);
		Page<RepairInfo> page = repairInfoService.queryAllRepairInfoAndDormitoryByPage(1, "situation", true);
		System.out.println(page.getCurrent());
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		List<RepairInfo> records = page.getRecords();
		for (RepairInfo record : records) {
			System.out.println(record);
		}
	}

	@Test
	public void testRepairInfoMapper() {
//		List<RepairInfo> repairInfos = repairInfoMapper.queryAllRepairInfoAndDormitory();
//		for (RepairInfo repairInfo : repairInfos) {
//			System.out.println(repairInfo);
//		}
	}

	@Test
	public void testDormitoryAnnouncementService() {
//		Page<DormitoryAnnouncement> dormitoryAnnouncementPage = dormitoryAnnouncementService.queryDormitoryAnnouncementPageByAnnouncementId(1);
//		System.out.println(dormitoryAnnouncementPage.getCurrent());
//		System.out.println(dormitoryAnnouncementPage.getTotal());
//		System.out.println(dormitoryAnnouncementPage.getPages());
//		System.out.println(dormitoryAnnouncementPage.getRecords());
		DormitoryAnnouncement dormitoryAnnouncement = dormitoryAnnouncementService.queryDormitoryAnnouncementById(1);
		System.out.println(dormitoryAnnouncement);
		System.out.println(dormitoryAnnouncement != null);
	}

	@Test
	public void testUUID() {
		UUID uuid = UUID.randomUUID();
		String photoName = uuid.toString().replaceAll("-", "");
		System.out.println(photoName);
	}
}
