var OrderController = Ext.extend(Ext.util.Observable,{
	orderMainGrid:null,
	loadData:function(status){
		var obj = new Object();
		obj.status = status;
		this.orderMainGrid.getStore().load({params:obj});
	},
	constructor:function(orderMainGrid){
		this.orderMainGrid = orderMainGrid;
		this.loadData(0);
		var outThis = this;
		orderMainGrid.on("statusSelected",function(status){
			outThis.loadData(status);
		});
		
		orderMainGrid.on("sendClick",function(){
			alert("sendClick");
		});
		
		orderMainGrid.on("signClick",function(){
			alert("signClick");
		});
		orderMainGrid.on("refundClick",function(){
			alert("refundClick");
		});
		orderMainGrid.on("refundSignClick",function(){
			alert("refundSignClick");
		});
		orderMainGrid.on("goodInfoClick",function(){
			alert("goodInfoClick");
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