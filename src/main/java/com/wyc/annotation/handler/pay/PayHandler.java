package com.wyc.annotation.handler.pay;

public interface PayHandler {
  //付款方式 0表示组团购买，1表示单买，2表示开团劵购买 3参加组团
    public final static int GROUP_TYPE = 0;
    public final static int SINGLE_TYPE = 1;
    public final static int OPEN_GROUP_TYPE = 2;
    public final static int PART_TAKE_TYPE = 3;
    public void handler(String openId , String goodId , String groupId , String orderId , String outTradeNo , int payHandlerType);
}
