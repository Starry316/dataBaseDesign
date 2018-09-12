package cn.xuzilin.service;

import cn.xuzilin.dao.CustomerEntityMapper;
import cn.xuzilin.po.CustomerEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {
    @Resource
    private CustomerEntityMapper customerMapper;

    public JSONArray getCustomerInfoJson(int recordId){
        List<CustomerEntity> list = getCustomerInfoByRecordId(recordId);
        JSONArray data = JSONArray.parseArray(JSON.toJSONString(list));
        return data;
    }

    public List<CustomerEntity> getCustomerInfoByRecordId(int recordId){
        return customerMapper.getCustomerByRecordId(recordId);
    }

    public void copyCustomerInfo(int newRecordId,int oldRecordId){
        List<CustomerEntity> list = getCustomerInfoByRecordId(oldRecordId);
        for (CustomerEntity i : list){
            saveCustomer(i.getName(),i.getIdcardNo(),i.getPhoneNum(),newRecordId);
        }
    }

    public int saveCustomer(String name,String idcardNo,String phoneNum,int recordId){
        CustomerEntity customer = customerBuilder(name,idcardNo,phoneNum,recordId);
        return customerMapper.insertSelective(customer);
    }

    public CustomerEntity customerBuilder(String name,String idcardNo,String phoneNum,int recordId){
        CustomerEntity customer = new CustomerEntity();
        customer.setIdcardNo(idcardNo);
        customer.setName(name);
        customer.setPhoneNum(phoneNum);
        customer.setRecordId(recordId);
        return customer;
    }
}
