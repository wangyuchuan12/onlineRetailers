var OrderDetailGridPanel = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(){
		OrderDetailGridPanel.superclass.constructor.call(this,{
			
			columns:[{header:"id",width:250},
					 {header:"订单id",width:250},
					 {header:"物流id",width:250},
					 {header:"订单处理方式",renderer:function(type){
						 if(type==1){
							 return "发货";
						 }else if (type==2) {
							return "签收";
						}else if (type==3) {
							return "退款处理";
						}
					 }},
					 {header:"备注",width:650}],
			width:1100,
			autoScroll:true,
			height:800,
			store:new Ext.data.JsonStore({
				url: "/manager/api/get_orderdetail_by_order",
			 	root:"root",
				fields:['id',"orderId","logisticsOrder","way","remark"]
			 })
		});
	}
});