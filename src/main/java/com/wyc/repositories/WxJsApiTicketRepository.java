package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.wx.domain.JsapiTicketBean;

public interface WxJsApiTicketRepository extends CrudRepository<JsapiTicketBean, String>{

}
