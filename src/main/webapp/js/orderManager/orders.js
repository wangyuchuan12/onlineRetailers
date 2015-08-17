var OrderMainGrid = Ext.extend(Ext.grid.GridPanel,{
	getSelected:function(){
		var selectionModel = this.getSelectionModel();
		return selectionModel.getSelected();
	},
	constructor:function(){
		var outThis = this;
		outThis.addEvents({
			"sendClick":true,
			"signClick":true,
			"refundClick":true,
			"refundSignClick":true,
			"goodInfoClick":true,
			"customerInfoClick":true,
			"statusSelected":true,
			"groupInfoClick":true
		});
		//1表示未付款 2表示已付款 3表示未发货 4表示已发货但未签收 5已签收 6退款中 7退款成功
		var store = new Ext.data.SimpleStore({
			   fields : ['value', 'text'],
			   data : [['0',"全部"],['1',"未付款"],['2','已付款'],['3','未发货'],['4','已发货但未签收'],['5','已签收'],['6','退款中'],['7','退款成功']]
			});
		OrderMainGrid.superclass.constructor.call(this,{
			tbar:["-",{text:"发货",handler:function(){outThis.fireEvent("sendClick",outThis.getSelected());}},"-",
			      {text:"签收",handler:function(){outThis.fireEvent("signClick",outThis.getSelected());}},"-",
			      {text:"退款",handler:function(){outThis.fireEvent("refundClick",outThis.getSelected());}},"-",
			      {text:"退款签收",handler:function(){outThis.fireEvent("refundSignClick",outThis.getSelected());}},"-",
			      {xtype:"combo",store:store,
			    	  valueField:"value",value:0,
			    	  listeners:{"select":function(combo,record,index){
			    		  outThis.fireEvent("statusSelected",record.get("value"));
			    	  }},
			    	  editable :false,triggerAction:'all',
			    	  displayField:"text",mode:"local"},"-",
			      {text:"查看商品信息",handler:function(){outThis.fireEvent("goodInfoClick",outThis.getSelected());}},"-",
			      {text:"查看顾客信息",handler:function(){outThis.fireEvent("customerInfoClick",outThis.getSelected());}},"-",
			      {text:"查看组团情况",handler:function(){outThis.fireEvent("groupInfoClick",outThis.getSelected());}},"-"],
																		columns:[{header:"id"},
																				 {header:"货物id"},
																				 {header:"货物名称"},
																				 {header:"物流费用"},
																				 {header:"商品单价"},
																				 {header:"已付款"},
																				 {header:"付款时间"},
																				 {header:"签收时间"},
																				 {header:"发货时间"},
																				 {header:"退款时间"},
																				 {header:"退款发货时间"},
																				 {header:"退款签收时间"},
																				 {header:"订单创建时间",width:150},
																				//1表示未付款 2表示已付款 3表示未发货 4表示已发货但未签收 5已签收 6退款中 7退款成功
																				 {header:"状态",renderer:function(value){
																					 if(value==1){
																						 return "未付款 ";
																					 }else if(value==2){
																						 return "已付款";
																					 }else if(value==3){
																						 return "未发货";
																					 }else if(value==4){
																						 return "已发货但未签收";
																					 }else if(value==5){
																						 return "已签收";
																					 }else if(value==6){
																						 return "退款中";
																					 }else if(value==7){
																						 return "退款成功";
																					 }
																					 
																				 }}],
																		width:1500,
																		autoScroll:true,
																		height:800,
																		store:new Ext.data.JsonStore({
																										url: "/manager/api/order_list",
																									 	root:"root",
																										fields:['id',"good_id","good_name","flow_price","good_price","cost","payment_time","sign_time","devivery_time","refund_time","refund_devivery_time","refund_sign_time","created_at","status"]
																									 })
		});
	}	
});