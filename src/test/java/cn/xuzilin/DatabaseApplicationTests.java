package cn.xuzilin;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.MemberCardEntityMapper;
import cn.xuzilin.dao.RecordEntityMapper;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.service.*;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseApplicationTests {
	@Resource
	private RoomEntityMapper roomMapper;
	@Resource
	private ReserveService reserveService;
	@Resource
	private RoomService roomService;
	@Resource
	private RecordService recordService;
	@Resource
	private MemberService memberService;
	@Resource
	private FeedbackService feedbackService;
	@Resource
	private CouponService couponService;
	@Resource
	private RecordEntityMapper recordMapper;
	@Test
	public void contextLoads() {
		couponService.create(2, BigDecimal.TEN,DateUtil.getNowDate());
//		String delayCheckOutTime = "2018-9-19";
//		System.out.println(DateUtil.getNowDate().after(DateUtil.strToDate(delayCheckOutTime))) ;
	}

}
