package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.RecordEntityMapper;
import cn.xuzilin.dao.RoomEntityMapper;
import cn.xuzilin.po.CustomerEntity;
import cn.xuzilin.po.MemberCardEntity;
import cn.xuzilin.po.RecordEntity;
import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.utils.BigDecimalUtil;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.SwitchUtil;
import cn.xuzilin.vo.DelayInfoVo;
import cn.xuzilin.vo.RecordVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RecordService {
    @Resource
    private RecordEntityMapper recordMapper;

    @Resource
    private RoomEntityMapper roomMapper;

    @Resource
    private RoomService roomService;

    @Resource
    private CustomerService customerService;

    @Resource
    private MemberService memberService;

    @Resource
    private CouponService couponService;

    public int insert(RecordEntity record){
        return recordMapper.insertSelective(record);
    }

    /**
     * 续住 判断续住时间是否比原来时间短
     * @param roomId
     * @param delayCheckOutTime
     * @return
     */
    public boolean delay(int roomId,String delayCheckOutTime){
        RecordEntity record = getByRoomId(roomId);
        if (record == null) return false;
        record.setCheckOutTime(DateUtil.strToDate(delayCheckOutTime));
        update(record);
        return true;
    }

    /**
     * 退房
     * @param roomId
     */
    public String checkOut(int roomId,boolean useMemberCard,
                         String memberCardId,String memberCardPass,
                         boolean useCoupon,String couponCode){
        String message = "退房成功！ ";

        //更新record记录
        RecordEntity record = recordMapper.getByRoomId(roomId);
        record.setCheckOutTime(DateUtil.getNowDate());
        record.setStatus(ConstPool.CHECK_OUT);
        //价格计算
        long days = DateUtil.subDateByDay(DateUtil.getNowDateStr(),DateUtil.dateToStr(record.getCheckInTime()));
        RoomEntity room = roomMapper.selectByPrimaryKey(roomId);
        BigDecimal paymentPerDay = SwitchUtil.switchTpyePayment(room.getRoomType());
        BigDecimal paymentTotal = BigDecimalUtil.multiply(paymentPerDay,days);

        if (useMemberCard){
            int mcardId =Integer.parseInt(memberCardId);
            if (!memberService.validate( mcardId,memberCardPass))return "会员卡卡号或密码错误";
            record.setMemberCardId( mcardId);
            MemberCardEntity memberCard = memberService.getMemberCardById( mcardId);
            paymentTotal = paymentTotal.multiply(SwitchUtil.switchMemberDiscount(memberCard.getType()))
                    .setScale(2,BigDecimal.ROUND_HALF_DOWN);
        }
        if (useCoupon){
            if (!couponService.validate(couponCode))return "优惠码不存在";
            BigDecimal discount = couponService.getDiscountByCode(couponCode);
            paymentTotal = paymentTotal.subtract(discount).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        }
        record.setPayment(paymentTotal);
        record.setDiscount(BigDecimalUtil.multiply(paymentPerDay,days).subtract(paymentTotal));
        //更新会员卡余额信息
        if (useMemberCard){
            int mcardId =Integer.parseInt(memberCardId);
            MemberCardEntity memberCard = memberService.getMemberCardById( mcardId );
            if (memberCard.getBalance().compareTo(paymentTotal)<0)return "用户会员卡余额不足，请充值或使用现金支付";
            //更新余额
            memberCard.setBalance(memberCard.getBalance().subtract(paymentTotal));
            //更新消费总额
            memberCard.setConsumption(memberCard.getBalance().add(paymentTotal));
            message+="会员卡级别为: " +SwitchUtil.switchMemberCardTypeName(memberCard.getType());
            //更新会员卡等级
            memberCard.setType(memberService.judgeLevel(memberCard.getConsumption()));
            message+=" ，本次消费:"+paymentTotal+"元,卡内余额:"+memberCard.getBalance()+"元";
            message+=" ，目前会员卡级别为："+SwitchUtil.switchMemberCardTypeName(memberCard.getType());
            memberService.update(memberCard);

        }
        //更新优惠码信息
        if (useCoupon){
            couponService.useCoupon(record.getId(),couponCode);
            message+="本次使用优惠券优惠："+couponService.getDiscountByCode(couponCode)+"元";
        }
        //更新记录
        update(record);
        //更新房间信息
        roomMapper.selectByPrimaryKey(roomId);
        roomService.checkOut(roomId);
        return message;
    }

    /**
     * 换房
     * @param newRoomId
     * @param oldRoomId
     */
    public void changeRoom(int newRoomId, int oldRoomId){
        RecordEntity oldRoomRecord = getByRoomId(oldRoomId);
        RecordEntity newRoomRecord = new RecordEntity();

        newRoomRecord.setCheckInTime(DateUtil.getNowDate());
        newRoomRecord.setCheckOutTime(oldRoomRecord.getCheckOutTime());
        newRoomRecord.setStatus(ConstPool.CHECK_IN);
        newRoomRecord.setRoomId(newRoomId);
        recordMapper.insertSelective(newRoomRecord);
        customerService.copyCustomerInfo(newRoomRecord.getId(),oldRoomRecord.getId());
        oldRoomRecord.setStatus(ConstPool.CHECK_OUT);
        recordMapper.updateByPrimaryKeySelective(oldRoomRecord);
        roomService.checkOut(oldRoomId);
        roomService.checkIn(newRoomId);
    }

    /**
     * 获取退房时的信息
     * @param roomId
     * @return
     */
    public JSONObject getCheckOutInfo(int roomId){
        JSONObject data = new JSONObject();
        RecordEntity record = recordMapper.getByRoomId(roomId);
        RoomEntity room = roomMapper.selectByPrimaryKey(roomId);
        BigDecimal paymentPerDay = SwitchUtil.switchTpyePayment(room.getRoomType());
        long days = DateUtil.subDateByDay(DateUtil.getNowDateStr(),DateUtil.dateToStr(record.getCheckInTime()));
        //todo 折扣
        double discount = 59.00;
        data.put("quitCheckInTime",DateUtil.formatDate(record.getCheckInTime()));
        data.put("quitCheckOutTime",DateUtil.getNowDateStr());
        data.put("paymentPerDay",paymentPerDay);
        data.put("paymentTotal",BigDecimalUtil.multiply(paymentPerDay,days));
        data.put("discount",discount);
        data.put("actualPayment",BigDecimalUtil.subtract(BigDecimalUtil.multiply(paymentPerDay,days),discount));
        return data;
    }

    public JSONObject getDelayInfo(int roomId){
        DelayInfoVo delayInfoVo = recordMapper.getDelayInfoByRoomId(roomId);
        if (delayInfoVo != null){
            delayInfoVo.setPayment(
                    BigDecimalUtil.getPayment(delayInfoVo.getCheckInTime(),
                            delayInfoVo.getCheckOutTime(),
                            delayInfoVo.getRoomType())
            );
            return JSON.parseObject(JSON.toJSONString(delayInfoVo));
        }

        return null;
    }

    public JSONArray getCompleteRecord(String page ,String roomId,String checkInTime ,String checkOutTime ,String customerName){
        roomId = judgeNull(roomId); checkInTime = judgeNull(checkInTime);checkOutTime =  judgeNull(checkOutTime); customerName = judgeNull(customerName);
        PageHelper.startPage(Integer.parseInt(page),15);
        List<RecordVo> list = recordMapper.getCompleteRecord(roomId,checkInTime,checkOutTime,customerName);
        return JSONArray.parseArray(JSON.toJSONString(list));
    }
    public int getRecordMaxPage(String roomId,String checkInTime ,String checkOutTime ,String customerName){
        roomId = judgeNull(roomId); checkInTime = judgeNull(checkInTime);checkOutTime =  judgeNull(checkOutTime); customerName = judgeNull(customerName);
        int res = recordMapper.getRecordMaxPage(roomId,checkInTime,checkOutTime,customerName);
        return (res+14)/15;
    }

    private String judgeNull(String s){
        if (s==null||s.length()<1)
            return null;
        return s;
    }
    public RecordEntity getByRoomId(int roomId){
        return recordMapper.getByRoomId(roomId);
    }
    public int update (RecordEntity record){
        return recordMapper.updateByPrimaryKeySelective(record);
    }





}
