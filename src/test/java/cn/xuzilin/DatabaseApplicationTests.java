package cn.xuzilin;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.ReserveEntity;
import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.service.ReserveService;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
		System.out.println(PasswordUtil.createHash("316289"));
		List<RoomEntity> list = roomMapper.getRoomList();
		for (RoomEntity i :list){
			if (i.getCheckIn() == 2){
				i.setCheckIn(ConstPool.EMPTY);
				roomMapper.updateByPrimaryKeySelective(i);
			}
		}
	}

}
