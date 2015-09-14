package com.wyc.wx.response.domain;

import javax.persistence.Column;

public class PaySuccess {
    @Column
    private String appid;
    @Column
    private String attach;
    @Column(name="bank_type")
    private String bankType;
    @Column(name="cash_fee")
    private String cashFee;
    @Column(name="is_subscribe")
    private String isSubscribe;
    @Column(name="mch_id")
    private String mchId;
    @Column(name="nonce_str")
    private String nonceStr;
    @Column
    private String openid;
    @Column(name="out_trade_no")
    private String outTradeNo;
    @Column(name="result_code")
    private String resultCode;
    @Column(name="return_code")
    private String returnCode;
    @Column
    private String sign;
    @Column(name="time_end")
    private String timeEnd;
    @Column(name="total_fee")
    private String totalFee;
    @Column(name="trade_type")
    private String tradeType;
    @Column(name="transaction_id")
    private String transactionId;
}
