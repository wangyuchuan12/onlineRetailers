package com.wyc.annotation.handler.pay;


public class PayTestHandler implements PayHandler{

    @Override
    public void handler(String openId, String goodId, String groupId,
            String orderId, String outTradeNo, int payHandlerType) {
        System.out.println(".......................");
        System.out.println(openId);
        System.out.println(goodId);
        System.out.println(groupId);
        System.out.println(orderId);
        System.out.println(outTradeNo);
        System.out.println(payHandlerType);
        
    }

}
