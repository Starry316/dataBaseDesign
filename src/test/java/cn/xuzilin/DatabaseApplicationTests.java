package cn.xuzilin;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.MemberCardEntityMapper;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.service.MemberService;
import cn.xuzilin.service.RecordService;
import cn.xuzilin.service.ReserveService;
import cn.xuzilin.service.RoomService;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
	@Test
	public void contextLoads() {
		//recordService.changeRoom(305,105);
		memberService.recharge(1,ConstPool.DIAMONDCONSUMPTION);
		//System.out.println(memberService.getData(1,"","","","")) ;
	}

}
