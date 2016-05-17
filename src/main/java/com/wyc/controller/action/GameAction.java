package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameAction {
    @RequestMapping("/game/luck_draw")
    public String luckDraw(HttpServletRequest httpServletRequest){
        return "game/luckDraw";
    }
}
