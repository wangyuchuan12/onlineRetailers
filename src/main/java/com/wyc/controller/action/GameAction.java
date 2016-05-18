package com.wyc.controller.action;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.domain.LuckDrawGood;
import com.wyc.service.LuckDrawGoodService;
import com.wyc.service.LuckDrawRecordService;
import com.wyc.service.LuckDrawUserInfoService;

@Controller
public class GameAction {
    @Autowired
    private LuckDrawRecordService luckDrawRecordService;
    @Autowired
    private LuckDrawUserInfoService luckDrawUserInfoService;
    @Autowired
    private LuckDrawGoodService luckDrawGoodService;
    @RequestMapping("/game/luck_draw")
    public String luckDraw(HttpServletRequest httpServletRequest)throws Exception{
        Iterable<LuckDrawGood> luckDrawGoods = luckDrawGoodService.findAll();
        
        for(LuckDrawGood luckDrawGood:luckDrawGoods){
            if(luckDrawGood.getRecordIndex()==0){
                httpServletRequest.setAttribute("good0", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==1) {
                httpServletRequest.setAttribute("good1", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==2) {
                httpServletRequest.setAttribute("good2", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==3) {
                httpServletRequest.setAttribute("good3", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==4) {
                httpServletRequest.setAttribute("good4", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==5) {
                httpServletRequest.setAttribute("good5", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==6) {
                httpServletRequest.setAttribute("good6", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==7) {
                httpServletRequest.setAttribute("good7", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==8) {
                httpServletRequest.setAttribute("good8", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==9) {
                httpServletRequest.setAttribute("good9", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==10) {
                httpServletRequest.setAttribute("good10", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==11) {
                httpServletRequest.setAttribute("good11", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==12) {
                httpServletRequest.setAttribute("good12", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==13) {
                httpServletRequest.setAttribute("good13", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==14) {
                httpServletRequest.setAttribute("good14", luckDrawGood);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String goods = objectMapper.writeValueAsString(luckDrawGoods);
            httpServletRequest.setAttribute("goods", goods);
        }
        return "game/luckDraw";
    }
    
    @RequestMapping("/game/receive_good")
    public String receiveGood(HttpServletRequest httpServletRequest){
        return "game/receiveGood";
    }
}
