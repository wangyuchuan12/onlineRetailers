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
			"customerInfoClick":true
		});
		var store = new Ext.data.SimpleStore({
			   fields : ['value', 'text'],
			   data : [['0',"未处理"],['1','全部']]
			});
		OrderMainGrid.superclass.constructor.call(this,{
			tbar:["-",{text:"发货",handler:function(){outThis.fireEvent("sendClick",outThis.getSelected());}},"-",{text:"签收"},"-",{text:"退款"},"-",{text:"退款签收"},"-",{xtype:"combo",store:store,valueField:"value",value:0,editable :false,triggerAction : 'all',displayField:"text",mode:"local"},"-",{text:"查看商品信息"},"-",{text:"查看顾客信息"},"-",{text:"查看组团情况"}],
																		columns:[{header:"id"},
																				 {header:"名称"},
																				 {header:"单价"},
																				 {header:"商品id"},
																				 {header:"数量"},
																				 {header:"顾客"},
																				 {header:"顾客id"},
																				 {header:"交易状态"},
																				 {header:"实收款"},
																				 {header:"发货时间"}],
																		width:1050,
																		autoScroll:true,
																		height:800,
																		store:new Ext.data.JsonStore({
																										url: "/manager/api/good_list",
																									 	root:"root",
																										fields:['id',"name","alone_original_cost","alone_discount",
																											"group_original_cost","group_discount","market_price","group_num","","source_address"]
																									 })
		});
	}	
});