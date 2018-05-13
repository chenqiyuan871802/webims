package webims;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.plugins.Page;
import com.ims.WebimsApplication;
import com.ims.common.matatype.Dto;
import com.ims.common.matatype.Dtos;
import com.ims.common.util.JsonUtil;
import com.ims.common.util.PageDto;
import com.ims.common.util.Query;
import com.ims.system.model.Param;
import com.ims.system.service.ParamService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebimsApplication.class)
public class RedisTest {
	

	
	
	@Autowired 
	private ParamService paramService;

	@Test
	public void test() throws Exception {
	    Map<String,Object> columnMap=new HashMap<String,Object>();
	    columnMap.put("paramkey", "order_ip");
		List<Param> paramList=paramService.selectByMap(columnMap);
		
		System.out.println(JsonUtil.toJson(paramList));

	}

}
