package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Message;
import com.zj.entity.sm.dto.MessageDto;
import com.zj.entity.sm.form.MessageQueryForm;


public interface MessageMapper  extends BaseMapper{
	public List<Message> getMessagePageList(MessageQueryForm form);
	
	public List<MessageDto> getMessageDeatilPageList(MessageQueryForm form);
	
	public Long getUnReadMessageAmount(MessageQueryForm form);
}
