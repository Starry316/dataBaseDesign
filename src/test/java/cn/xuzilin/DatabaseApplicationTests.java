package cn.xuzilin;

import cn.xuzilin.common.dao.RoomEntityMapper;
import cn.xuzilin.common.po.ReserveEntity;
import cn.xuzilin.common.po.RoomEntity;
import cn.xuzilin.common.service.ReserveService;
import cn.xuzilin.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseApplicationTests {
	@Resource
	private RoomEntityMapper roomMapper;
	@Resource
	private ReserveService reserveService;
	@Test
	public void contextLoads() {
		Integer[] res =roomMapper.getRoomIdListByTypeAndCheckInTime(Byte.parseByte(4+""), DateUtil.strToDate("2018-08-14"));
		for (Integer i: res)
			System.out.println(i);

	}

}
