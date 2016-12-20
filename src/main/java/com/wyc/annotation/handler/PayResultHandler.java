package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wyc.annotation.handler.pay.PayHandler;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GoodStyle;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.MyResource;
import com.wyc.domain.NewsArticleItem;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.PushArticle;
import com.wyc.domain.SystemPayActivity;
import com.wyc.domain.TempGroupOrder;
import com.wyc.domain.TemporaryData;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodStyleService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.NewsArticleItemService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.PayActivityService;
import com.wyc.service.PayHandlerService;
import com.wyc.service.PushArticleService;
import com.wyc.service.TempGroupOrderService;
import com.wyc.service.TemporaryDataService;
public class PayResultHandler implements Handler{
    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private TempGroupOrderService tempGroupOrderService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    @Autowired
    private PayActivityService payActivityService;
    @Autowired
    private AutowireCapableBeanFactory factory;
    @Autowired
    private PayHandlerService payHandlerService;
    @Autowired
    private GoodStyleService goodStyleService;
    @Autowired
    private PushArticleService pushArticleService;
    @Autowired
    private NewsArticleItemService newsArticleItemService;
    @Autowired
    private MyResourceService myResourceService;
    final static Logger logger = LoggerFactory.getLogger(PayResultHandler.class);
    @Override
    @Transactional
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
    	
        String outTradeNo = httpServletRequest.getAttribute("outTradeNo").toString();
        TempGroupOrder tempGroupOrder = tempGroupOrderService.findByOutTradeNo(outTradeNo);
        Customer customer = null;
        GoodGroup goodGroup = null;
        GoodOrder goodOrder = null;
        if(tempGroupOrder!=null){
            customer = customerService.findByOpenId(tempGroupOrder.getOpenid());
        }
        if(tempGroupOrder!=null&&(tempGroupOrder.getGoodOrderType()==0||tempGroupOrder.getGoodOrderType()==2)){
            OpenGroupCoupon openGroupCoupon = null;
            if(tempGroupOrder.getGoodOrderType()==2){
                openGroupCoupon = openGroupCouponService.getFirstRecord(customer.getId(), tempGroupOrder.getGoodId(), new DateTime(), 1);
                if(openGroupCoupon==null){
                    return null;
                }
            }
            Good good = goodService.findOne(tempGroupOrder.getGoodId());
            float cost = tempGroupOrder.getCost();
            String openid = tempGroupOrder.getOpenid();
            goodOrder = new GoodOrder();
            goodOrder.setAddress(tempGroupOrder.getAddress());
            goodOrder.setAddressId(tempGroupOrder.getAddressId());
            goodOrder.setCode(tempGroupOrder.getCode());
            goodOrder.setCost(cost);
            goodOrder.setDeliveryTime(new DateTime());
            goodOrder.setFlowPrice(tempGroupOrder.getFlowPrice());
            goodOrder.setGoodId(tempGroupOrder.getGoodId());
            goodOrder.setGoodPrice(tempGroupOrder.getGoodPrice());
            goodOrder.setPaymentTime(new DateTime());
            goodOrder.setStatus(2);
            goodOrder.setType(0);
            goodOrder.setCreateTime(new DateTime());
            goodOrder.setAdminId(tempGroupOrder.getAdminId());
           
            goodOrder = goodOrderService.add(goodOrder);
            
            
            goodGroup = new GoodGroup();
            goodGroup.setGoodId(tempGroupOrder.getGoodId());
            goodGroup.setGroupHead(customer.getId());
            goodGroup.setNum(tempGroupOrder.getNum());
            goodGroup.setResult(1);
            goodGroup.setStartTime(new DateTime());
            goodGroup.setTimeLong(tempGroupOrder.getTimeLong());
            goodGroup.setTotalPrice(cost);
            goodGroup.setReliefType(tempGroupOrder.getReliefType());
            goodGroup.setAdminId(tempGroupOrder.getAdminId());
            goodGroup.setReliefValue(tempGroupOrder.getReliefValue());
            goodGroup = goodGroupService.add(goodGroup);
            goodOrder.setGroupId(goodGroup.getId());
            goodOrder = goodOrderService.save(goodOrder);
            GroupPartake groupPartake = new GroupPartake();
            groupPartake.setCustomerAddress(tempGroupOrder.getAddress()+","+tempGroupOrder.getPersonName()+","+tempGroupOrder.getPhonenumber());
            groupPartake.setAddressContent(tempGroupOrder.getAddress());
            groupPartake.setPersonName(tempGroupOrder.getPersonName());
            groupPartake.setPhonenumber(tempGroupOrder.getPhonenumber());
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setGroupId(goodGroup.getId());
            groupPartake.setOrderId(goodOrder.getId());
            groupPartake.setRole(1);
            groupPartake.setType(0);
           
            groupPartake.setGoodStyleId(tempGroupOrder.getGoodStyleId());
            if(tempGroupOrder.getGoodStyleId()!=null){
                GoodStyle goodStyle = goodStyleService.findOne(tempGroupOrder.getGoodStyleId());
                if(goodStyle!=null){
                    groupPartake.setGoodStyleName(goodStyle.getName());
                }
            }
            groupPartake.setIsDel(0);
            groupPartake = groupPartakeService.add(groupPartake);
            
            GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
            groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
            
            groupPartakeDeliverService.add(groupPartakeDeliver);
            GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
            groupPartakePayment.setCost(tempGroupOrder.getCost());
            groupPartakePayment.setPayTime(new DateTime());
            groupPartakePayment.setGroupPartakeId(groupPartake.getId());
            groupPartakePayment.setOutTradeNo(outTradeNo);
            groupPartakePayment.setStatus(1);
            groupPartakePaymentService.add(groupPartakePayment);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCustomerId(customer.getId());
            orderDetail.setGoodId(tempGroupOrder.getGoodId());
            orderDetail.setGroupId(goodGroup.getId());
            orderDetail.setNum(tempGroupOrder.getNum());
            orderDetail.setOrderId(goodOrder.getId());
            orderDetail.setOutTradeNo(outTradeNo);
            orderDetail.setStatus(2);
            orderDetail = orderDetailService.add(orderDetail);
            
            
            
            if(openGroupCoupon!=null){
            	openGroupCoupon.setStatus(0);
            	openGroupCouponService.save(openGroupCoupon);
            }
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(openid, "lastGroupId",1);
            if(temporaryData==null){
                temporaryData = new TemporaryData();
                temporaryData.setMykey(openid);
                temporaryData.setKeyName("openid");
                temporaryData.setName("lastGroupId");
                temporaryData.setValue(goodGroup.getId());
                temporaryDataService.add(temporaryData);
            }else{
                temporaryData.setMykey(openid);
                temporaryData.setName("lastGroupId");
                temporaryData.setValue(goodGroup.getId());
                temporaryDataService.save(temporaryData);
            }
            
            
        
        }else if (tempGroupOrder!=null&&tempGroupOrder.getGoodOrderType()==3) {
            
            String groupId = tempGroupOrder.getGroupId();
            String openid = tempGroupOrder.getOpenid();
            int partNum = groupPartakeService.countByGroupId(groupId);
            goodGroup = goodGroupService.findOne(groupId);
            int groupNum = goodGroup.getNum();
            if(goodGroup!=null){
                GroupPartake groupPartake = new GroupPartake();
                groupPartake.setCustomerid(customer.getId());
                OrderDetail orderDetail = orderDetailService.findByGruopId(goodGroup.getId());
                groupPartake.setOrderId(orderDetail.getOrderId());
                groupPartake.setDateTime(new DateTime());
                groupPartake.setGroupId(goodGroup.getId());
                groupPartake.setCustomerAddress(tempGroupOrder.getAddress()+","+tempGroupOrder.getPersonName()+","+tempGroupOrder.getPhonenumber());
                groupPartake.setAddressContent(tempGroupOrder.getAddress());
                groupPartake.setPersonName(tempGroupOrder.getPersonName());
                groupPartake.setPhonenumber(tempGroupOrder.getPhonenumber());
                groupPartake.setGoodStyleId(tempGroupOrder.getGoodStyleId());
                if(tempGroupOrder.getGoodStyleId()!=null){
                    GoodStyle goodStyle = goodStyleService.findOne(tempGroupOrder.getGoodStyleId());
                    if(goodStyle!=null){
                        groupPartake.setGoodStyleName(goodStyle.getName());
                    }
                }
                groupPartake.setIsDel(0);
                goodOrder = goodOrderService.findOne(orderDetail.getOrderId());
                
                if(partNum==1){
                    groupPartake.setRole(2);
                }else{
                    groupPartake.setRole(3);
                }
                if(partNum+1==groupNum){
                   goodGroup.setResult(2);
                   goodGroupService.save(goodGroup);
                   Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByRoleAsc(groupId);
                   Good good = goodService.findOne(goodGroup.getGoodId());
                   MyResource myResource = myResourceService.findOne(good.getGoodInfoHeadImg());
                   for(GroupPartake groupPartake2:groupPartakes){
                       groupPartake2.setStatus(GroupPartake.BEGIN_STATUS);
                       groupPartakeService.save(groupPartake2);
                       
                       	try {
                       	 Customer entryCustomer = customerService.findOne(groupPartake2.getCustomerid());
                       	 PushArticle pushArticle = new PushArticle();
                         pushArticle.setFromUser("System:group success");
                         pushArticle.setMsgtype(PushArticle.NEWS_TYPE);
                         pushArticle.setPushTime(new DateTime());
                         pushArticle.setStatus(PushArticle.NOT_SENT_STATUS);
                         pushArticle.setTouser(entryCustomer.getOpenId());
                         
                         pushArticle = pushArticleService.add(pushArticle);
                         
                         NewsArticleItem newsArticleItem = new NewsArticleItem();
                         newsArticleItem.setArticleId(pushArticle.getId());
                         newsArticleItem.setDescription("恭喜你，你参加的团组团成功啦");
                         newsArticleItem.setPicurl(myResource.getUrl());
                         newsArticleItem.setUrl("http://www.chengxihome.com/info/group_info2?id="+goodGroup.getId());
                         newsArticleItem.setTitle("您收到一条消息");
                         newsArticleItem = newsArticleItemService.add(newsArticleItem);
						} catch (Exception e) {
							// TODO: handle exception
						}
                   }
                   groupPartake.setStatus(GroupPartake.BEGIN_STATUS);
                }
                groupPartake.setType(0);
                groupPartake = groupPartakeService.add(groupPartake);
                
                
                GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
                groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
                groupPartakeDeliverService.add(groupPartakeDeliver);
                GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
                groupPartakePayment.setCost(tempGroupOrder.getCost());
                groupPartakePayment.setPayTime(new DateTime());
                groupPartakePayment.setGroupPartakeId(groupPartake.getId());
                groupPartakePayment.setStatus(1);
                groupPartakePayment.setOutTradeNo(outTradeNo);
                groupPartakePaymentService.add(groupPartakePayment);
                
                TemporaryData temporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(openid, "lastGroupId",1);
                if(temporaryData==null){
                    temporaryData = new TemporaryData();
                    temporaryData.setMykey(openid);
                    temporaryData.setName("lastGroupId");
                    temporaryData.setKeyName("openid");
                    temporaryData.setValue(goodGroup.getId());
                    temporaryDataService.add(temporaryData);
                }else{
                    temporaryData.setMykey(openid);
                    temporaryData.setName("lastGroupId");
                    temporaryData.setValue(goodGroup.getId());
                    temporaryDataService.save(temporaryData);
                }
            }
        }else if (tempGroupOrder!=null&&tempGroupOrder.getGoodOrderType()==1) {
            goodOrder = new GoodOrder();
            goodOrder.setAddress(tempGroupOrder.getAddress());
            goodOrder.setAddressId(tempGroupOrder.getAddressId());
            goodOrder.setCode(tempGroupOrder.getCode());
            goodOrder.setCost(tempGroupOrder.getCost());
            goodOrder.setCreateTime(new DateTime());
            goodOrder.setFlowPrice(tempGroupOrder.getFlowPrice());
            goodOrder.setAdminId(tempGroupOrder.getAdminId());
            goodOrder.setGoodId(tempGroupOrder.getGoodId());
            goodOrder.setGoodPrice(tempGroupOrder.getGoodPrice());
            goodOrder.setStatus(2);
            goodOrder.setType(tempGroupOrder.getGoodOrderType());
            goodOrder = goodOrderService.add(goodOrder);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCustomerId(customer.getId());
            orderDetail.setGoodId(tempGroupOrder.getGoodId());
            orderDetail.setNum(tempGroupOrder.getNum());
            orderDetail.setOrderId(goodOrder.getId());
            orderDetail.setOutTradeNo(tempGroupOrder.getOutTradeNo());
            orderDetail.setStatus(1);
            orderDetail = orderDetailService.add(orderDetail);
            
            GroupPartake groupPartake = new GroupPartake();
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setOrderId(goodOrder.getId());
            groupPartake.setType(1);
            groupPartake.setStatus(GroupPartake.BEGIN_STATUS);
            groupPartake.setCustomerAddress(tempGroupOrder.getAddress()+","+tempGroupOrder.getPersonName()+","+tempGroupOrder.getPhonenumber());
            groupPartake.setAddressContent(tempGroupOrder.getAddress());
            groupPartake.setPersonName(tempGroupOrder.getPersonName());
            groupPartake.setPhonenumber(tempGroupOrder.getPhonenumber());
            groupPartake.setGoodStyleId(tempGroupOrder.getGoodStyleId());
            if(tempGroupOrder.getGoodStyleId()!=null){
                GoodStyle goodStyle = goodStyleService.findOne(tempGroupOrder.getGoodStyleId());
                if(goodStyle!=null){
                    groupPartake.setGoodStyleName(goodStyle.getName());
                }
            }
            groupPartake.setIsDel(0);
            groupPartakeService.add(groupPartake);
            
            
            GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
            groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
            groupPartakeDeliverService.add(groupPartakeDeliver);
            GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
            groupPartakePayment.setCost(tempGroupOrder.getCost());
            groupPartakePayment.setPayTime(new DateTime());
            groupPartakePayment.setGroupPartakeId(groupPartake.getId());
            groupPartakePayment.setStatus(1);
            groupPartakePayment.setOutTradeNo(outTradeNo);
            groupPartakePaymentService.add(groupPartakePayment);
            
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(tempGroupOrder.getOpenid(), "lastOrder");
            if(temporaryData!=null){
                temporaryData.setMykey(tempGroupOrder.getOpenid());
                temporaryData.setName("lastOrder");
                temporaryData.setValue(goodOrder.getId());
                temporaryData.setStatus(1);
                temporaryDataService.save(temporaryData);
            }else{
                temporaryData = new TemporaryData();
                temporaryData.setMykey(tempGroupOrder.getOpenid());
                temporaryData.setName("lastOrder");
                temporaryData.setKeyName("openid");
                temporaryData.setValue(goodOrder.getId());
                temporaryData.setStatus(1);
                temporaryDataService.add(temporaryData);
            }
            
        }
        
        
        List<SystemPayActivity> payActivities = payActivityService.findAllByGoodIdAndPayType(tempGroupOrder.getGoodId(),tempGroupOrder.getGoodOrderType());
        List<SystemPayActivity> payActivities2 = payActivityService.findAllByGoodIdAndPayType(tempGroupOrder.getGoodId(), 7);
        List<SystemPayActivity> payActivitiesAll = new ArrayList<SystemPayActivity>();
        payActivitiesAll.addAll(payActivities);
        payActivitiesAll.addAll(payActivities2);
        logger.debug("payActivities:{}",payActivities);
        for(SystemPayActivity payActivity:payActivitiesAll){
            boolean flag = false;
            if(payActivity.getUserOpenIds().equals("*")){
                flag = true;
            }
            for(String userOpenId:payActivity.getUserOpenIds().split(",")){
               if(userOpenId.equals(tempGroupOrder.getOpenid())){
                   flag = true;
                   break;
               }
            }
            if(!flag){
                continue;
            }
            String handlersStr = payActivity.getHandlers();
            String[] handlersArrayStr = handlersStr.split(",");
            List<String> arrayIteratorStr = new ArrayList<String>();
            for(String str:handlersArrayStr){
                arrayIteratorStr.add(str);
            }
            Iterable<com.wyc.domain.SystemPayHandler> payHandlerEntitys = payHandlerService.findAll(arrayIteratorStr);
            logger.debug("payHandlerEntitys:{}",payHandlerEntitys);
            for(com.wyc.domain.SystemPayHandler payHandlerEntity:payHandlerEntitys){
                try {
                    Class<PayHandler> handlerClass = (Class<PayHandler>) Class.forName(payHandlerEntity.getClassPath());
                    PayHandler payHandler = handlerClass.newInstance();
                    factory.autowireBean(payHandler);
                    payHandler.handler(tempGroupOrder.getOpenid(), tempGroupOrder.getGoodId(), goodGroup.getId(), goodOrder.getId(), outTradeNo, tempGroupOrder.getGoodOrderType(),payActivity.getId());
                    
                } catch (Exception e) {
                    logger.error("has an error:{}",e);
                }
                
            }
        }
        return null;
       
        
    }

    @Override
    public Class<? extends Handler>[] extendHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
        // TODO Auto-generated method stub
        
    }

}
