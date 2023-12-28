package com.tjoeun.picok_project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjoeun.service.alarm.AlarmCountService;
import com.tjoeun.service.alarm.AlarmDeleteAllService;
import com.tjoeun.service.alarm.AlarmDeleteService;
import com.tjoeun.service.alarm.AlarmService;
import com.tjoeun.vo.AlarmVO;

@Controller
public class AlarmController {
	
	//알람 수
	@ResponseBody
	@RequestMapping(value = "/alarmCount", method=RequestMethod.GET)
	public Map<String, Integer> alramCount(String to_id) {
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    AlarmCountService service = ctx.getBean("alarmCount", AlarmCountService.class);             
	    int count = service.execute(to_id);
	    
	    Map<String, Integer> response = new HashMap<>();
	    response.put("count", count);
	    
	    return response;
	}

	
	
	//알람목록
	@ResponseBody
	@RequestMapping(value = "/alarmList", method=RequestMethod.GET)
	public List<AlarmVO> alarmList(String memberId) {

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmService service = ctx.getBean("alarm", AlarmService.class);				
		System.out.println(service.execute(memberId));
		return service.execute(memberId);
	}	
	
	
	//알람개별삭제
	@ResponseBody
	@RequestMapping(value = "/alarmDelete", method=RequestMethod.POST)
	public String alarmDelete(int alarm_idx) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDeleteService service = ctx.getBean("alarmDelete", AlarmDeleteService.class);				
		service.execute(alarm_idx);
		
	    return "";
	}
	
	//알람전체삭제
	@ResponseBody
	@RequestMapping(value = "/alarmDeleteAll", method=RequestMethod.POST)
	public String alarmDeleteAll(String from_id) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDeleteAllService service = ctx.getBean("alarmDeleteAll", AlarmDeleteAllService.class);				
		service.execute(from_id);
		
		return "";
	}
	

}
