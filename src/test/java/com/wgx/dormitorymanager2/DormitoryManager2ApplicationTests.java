package com.wgx.dormitorymanager2;

import com.wgx.dormitorymanager2.bean.DormitoryInfo;
import com.wgx.dormitorymanager2.bean.PaymentInfo;
import com.wgx.dormitorymanager2.bean.PaymentRecord;
import com.wgx.dormitorymanager2.bean.Student;
import com.wgx.dormitorymanager2.mapper.PaymentInfoMapper;
import com.wgx.dormitorymanager2.mapper.StudentMapper;
import com.wgx.dormitorymanager2.service.DormitoryInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

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

}
