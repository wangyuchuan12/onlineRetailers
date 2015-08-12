var GoodInfo = Ext.extend(Ext.form.FormPanel,{
										idText:null,
										aloneDiscountText:null,
										aloneOriginalCostText:null,
										couponCostText:null,
										flowPriceText:null,
										groupDiscountText:null,
										groupNumText:null,
										groupOriginalCostText:null,
										instructionText:null,
										marketPriceText:null,
										nameText:null,
										constructor:function(){
												
												this.idText = new Ext.form.TextField({
																				  		fieldLabel:"id",
																						readOnly:true,
																						name:"id"
																				  });
												this.aloneDiscountText = new Ext.form.TextField({
																						fieldLabel:"单卖折扣",
																						name:"alone_discount"
																					});
												this.aloneOriginalCostText = new Ext.form.TextField({
																						fieldLabel:"单卖原价",
																						name:"alone_original_cost"

																					});
												this.couponCostText = new Ext.form.TextField({
																						fieldLabel:"需要开团劵",
																						name:"coupon_cost"
																					});
												this.flowPriceText = new Ext.form.TextField({
																						fieldLabel:"物流费",
																						name:"flow_price"
																					});
												this.groupDiscountText = new Ext.form.TextField({
																						fieldLabel:"团购折扣",
																						name:"group_discount"
																					});
												this.groupNumText = new Ext.form.TextField({
																						fieldLabel:"需要组团数量",
																						name:"group_num"
																					});
												this.groupOriginalCostText = new Ext.form.TextField({
																						fieldLabel:"团购原价",
																						name:"group_original_cost"
																					});
												this.instructionText = new Ext.form.TextField({
																						fieldLabel:"介绍",
																						name:"instruction"
																					});
												this.fileText = new Ext.form.TextField({
																						fieldLabel:"商品图片",
																						inputType:"file",
																						name:"head_img"
																					});
												this.marketPriceText = new Ext.form.TextField({
																						fieldLabel:"市场价",
																						name:"market_price"		
																					});
												this.nameText = new Ext.form.TextField({
																						fieldLabel:"商品名称",
																						name:"name",
																						value:"123"
																					});
												GoodInfo.superclass.constructor.call(this,{
																							   		frame:true,
																									width:350,
																									fileUpload:true,
																							   		items:[this.idText,this.nameText,this.aloneDiscountText,this.aloneOriginalCostText,
																										this.couponCostText,this.flowPriceText,this.groupDiscountText,
																										this.groupNumText,this.groupOriginalCostText,this.instructionText,this.marketPriceText,this.fileText],
																									buttons:[{text:"确定",scope:this,handler:function(){
																										
																									}}]
																							   
																							   });
											}
									});