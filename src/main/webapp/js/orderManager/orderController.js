var OrderController = Ext.extend(Ext.util.Observable,{
	orderMainGrid:null,
	currentStatus:null,
	selectStatus:0,
	loadData:function(status){
		var obj = new Object();
		obj.status = status;
		this.orderMainGrid.getStore().load({params:obj});
	},
	constructor:function(orderMainGrid,sendFormPanel,signFormPanel,refundFormPanel,goodInfoFormPanel){
		var outThis = this;
		this.orderMainGrid = orderMainGrid;
		this.loadData(0);
		orderMainGrid.getTopToolbar().items.itemAt(1).disable();
		orderMainGrid.getTopToolbar().items.itemAt(3).disable();
		orderMainGrid.getTopToolbar().items.itemAt(5).disable();
		orderMainGrid.getTopToolbar().items.itemAt(9).disable();
		orderMainGrid.getTopToolbar().items.itemAt(11).disable();
		orderMainGrid.getTopToolbar().items.itemAt(13).disable();
		orderMainGrid.getTopToolbar().items.itemAt(15).disable();
		orderMainGrid.getSelectionModel().on("rowselect",function(selectModel,rowIndex,selectRecord){
			outThis.currentStatus = selectRecord.get("status");
			if(selectRecord.get("status")==1){
				orderMainGrid.getTopToolbar().items.itemAt(1).disable();
				orderMainGrid.getTopToolbar().items.itemAt(3).disable();
				orderMainGrid.getTopToolbar().items.itemAt(5).disable();
				orderMainGrid.getTopToolbar().items.itemAt(9).enable();
				orderMainGrid.getTopToolbar().items.itemAt(11).enable();
				orderMainGrid.getTopToolbar().items.itemAt(13).enable();
				orderMainGrid.getTopToolbar().items.itemAt(15).enable();
			}else if(selectRecord.get("status")==2){
				orderMainGrid.getTopToolbar().items.itemAt(1).enable();
				orderMainGrid.getTopToolbar().items.itemAt(3).disable();
				orderMainGrid.getTopToolbar().items.itemAt(5).disable();
				orderMainGrid.getTopToolbar().items.itemAt(9).enable();
				orderMainGrid.getTopToolbar().items.itemAt(11).enable();
				orderMainGrid.getTopToolbar().items.itemAt(13).enable();
				orderMainGrid.getTopToolbar().items.itemAt(15).enable();
			}else if(selectRecord.get("status")==3){
				orderMainGrid.getTopToolbar().items.itemAt(1).disable();
				orderMainGrid.getTopToolbar().items.itemAt(3).enable();
				orderMainGrid.getTopToolbar().items.itemAt(5).disable();
				orderMainGrid.getTopToolbar().items.itemAt(9).enable();
				orderMainGrid.getTopToolbar().items.itemAt(11).enable();
				orderMainGrid.getTopToolbar().items.itemAt(13).enable();
				orderMainGrid.getTopToolbar().items.itemAt(15).enable();
			}else if(selectRecord.get("status")==5){
				orderMainGrid.getTopToolbar().items.itemAt(1).disable();
				orderMainGrid.getTopToolbar().items.itemAt(3).disable();
				orderMainGrid.getTopToolbar().items.itemAt(5).enable();
				orderMainGrid.getTopToolbar().items.itemAt(9).enable();
				orderMainGrid.getTopToolbar().items.itemAt(11).enable();
				orderMainGrid.getTopToolbar().items.itemAt(13).enable();
				orderMainGrid.getTopToolbar().items.itemAt(15).enable();
			}else{
				orderMainGrid.getTopToolbar().items.itemAt(1).disable();
				orderMainGrid.getTopToolbar().items.itemAt(3).disable();
				orderMainGrid.getTopToolbar().items.itemAt(5).disable();
				orderMainGrid.getTopToolbar().items.itemAt(9).enable();
				orderMainGrid.getTopToolbar().items.itemAt(11).enable();
				orderMainGrid.getTopToolbar().items.itemAt(13).enable();
				orderMainGrid.getTopToolbar().items.itemAt(15).enable();
			}
		});
		var sendFormWin = new Ext.Window({
			closeAction:"hide",
			layout:"fit",
			width:300,
			height:200,
			title:"发货处理",
			items:[sendFormPanel]
		});
		
		var signFormWin = new Ext.Window({
			closeAction:"hide",
			layout:"fit",
			width:300,
			height:200,
			title:"签收",
			items:[signFormPanel]
		});
		var refundFormWin = new Ext.Window({
			closeAction:"hide",
			layout:"fit",
			width:300,
			height:200,
			title:"退款处理",
			items:[refundFormPanel]
		});
		
		var goodInfoFormWin = new Ext.Window({
			closeAction:"hide",
			layout:"fit",
			width:400,
			height:450,
			title:"商品信息",
			items:[goodInfoFormPanel]
		});
		sendFormPanel.buttons[0].on("click",function(){
			var selectionModel = orderMainGrid.getSelectionModel();
			var record = selectionModel.getSelected();
			var logisticsOrderTextField = sendFormPanel.logisticsOrderTextField;
			var deliveryTimeTextField= sendFormPanel.deliveryTimeTextField;
			var remarkTextField = sendFormPanel.remarkTextField;
		//	var url = "/manager/api/order_handle?id="+record.get("id")+"&way="+1+"&logistics_order="+logisticsOrderTextField.getValue()+"&delivery_time="+deliveryTimeTextField.getRawValue()+"&remark="+remarkTextField.getValue();
			var url = "/manager/api/order_handle?id="+record.get("id")+"&way="+1
			sendFormPanel.getForm().submit({
				url:url,
				method:"POST",
				success:function(form, action){
					if(action.result.success){
						outThis.loadData(outThis.selectStatus);
						sendFormWin.hide();
					}else{
						Ext.Msg.alert("系统提醒","处理错误");
					}
				},
				failure:function(form, action){
					Ext.Msg.alert("系统提醒","处理错误");
				}
			});
		});
		
		signFormPanel.buttons[0].on("click",function(){
			var selectionModel = orderMainGrid.getSelectionModel();
			var record = selectionModel.getSelected();
			var url = "/manager/api/order_handle?id="+record.get("id")+"&way="+2;
			signFormPanel.getForm().submit({
				url:url,
				method:"POST",
				success:function(form, action){
					if(action.result.success){
						outThis.loadData(outThis.selectStatus);
						signFormWin.hide();
					}else{
						Ext.Msg.alert("系统提醒","处理错误");
					}
				},
				failure:function(form, action){
					Ext.Msg.alert("系统提醒","处理错误");
				}
			});
		});
		
		
		refundFormPanel.buttons[0].on("click",function(){
			var selectionModel = orderMainGrid.getSelectionModel();
			var record = selectionModel.getSelected();
			var url = "/manager/api/order_handle?id="+record.get("id")+"&way="+3;
			refundFormPanel.getForm().submit({
				url:url,
				method:"POST",
				success:function(form, action){
					if(action.result.success){
						outThis.loadData(outThis.selectStatus);
						refundFormWin.hide();
					}else{
						Ext.Msg.alert("系统提醒","处理错误");
					}
				},
				failure:function(form, action){
					Ext.Msg.alert("系统提醒","处理错误");
				}
			});
		});
		goodInfoFormPanel.buttons[0].on("click",function(){
			goodInfoFormWin.hide();
		});
		orderMainGrid.on("statusSelected",function(status){
			outThis.selectStatus = status;
			outThis.loadData(status);
		});
		
		orderMainGrid.on("sendClick",function(record){
			sendFormWin.show();
		});
		
		orderMainGrid.on("signClick",function(){
			signFormWin.show();
		});
		orderMainGrid.on("refundClick",function(){
			refundFormWin.show();
		});
		orderMainGrid.on("goodInfoClick",function(){
			goodInfoFormWin.show();
			
			var selectionModel = orderMainGrid.getSelectionModel();
			if(selectionModel.hasSelection()){
				var record=selectionModel.getSelected();
				Ext.Ajax.request({
					url:"/manager/api/good_info?id="+record.get('good_id'),
					success:function(resp){
						var content = resp.responseText;
						var obj = eval("("+content+")");
						goodInfoFormPanel.idText.setValue(obj.id);
						goodInfoFormPanel.idText.disable();
						goodInfoFormPanel.aloneDiscountText.setValue(obj.alone_discount);
						goodInfoFormPanel.aloneDiscountText.disable();
						goodInfoFormPanel.aloneOriginalCostText.setValue(obj.alone_original_cost);
						goodInfoFormPanel.aloneOriginalCostText.disable();
						goodInfoFormPanel.couponCostText.setValue(obj.coupon_cost);
						goodInfoFormPanel.couponCostText.disable();
						goodInfoFormPanel.flowPriceText.setValue(obj.flow_price);
						goodInfoFormPanel.flowPriceText.disable();
						goodInfoFormPanel.groupDiscountText.setValue(obj.group_discount);
						goodInfoFormPanel.groupDiscountText.disable();
						goodInfoFormPanel.groupNumText.setValue(obj.group_num);
						goodInfoFormPanel.groupNumText.disable();
						goodInfoFormPanel.groupOriginalCostText.setValue(obj.group_original_cost);
						goodInfoFormPanel.groupOriginalCostText.disable();
						goodInfoFormPanel.instructionText.setValue(obj.instruction);
						goodInfoFormPanel.instructionText.disable();
						goodInfoFormPanel.marketPriceText.setValue(obj.market_price);
						goodInfoFormPanel.marketPriceText.disable();
						goodInfoFormPanel.nameText.setValue(obj.name);
						goodInfoFormPanel.nameText.disable();
						goodInfoFormPanel.statusPanel.setValue(obj.status);	
						goodInfoFormPanel.statusPanel.disable();
						goodInfoFormPanel.fileText.disable();
					}
				});
			}else{
				Ext.Msg.alert("系统提醒","请选中行");
			}
		});
		orderMainGrid.on("customerInfoClick",function(){
			alert("customerInfoClick");
		});
		
		orderMainGrid.on("groupInfoClick",function(){
			alert("groupInfoClick");
		});
		OrderController.superclass.constructor.call(this,{
			
		});
	}
});