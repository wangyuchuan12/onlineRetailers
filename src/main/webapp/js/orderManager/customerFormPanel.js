var CustomerFormPanel = Ext.extend(Ext.form.FormPanel,{
	openidTextField:null,
	phonenumberTextField:null,
	defaultAddressTextField:null,
	cityTextField:null,
	countryTextField:null,
	groupIdTextField:null,
	headimgUrlTextField:null,
	languageTextField:null,
	nicknameTextField:null,
	provinceTextField:null,
	sexTextField:null,
	tokenTextField:null,
	invalidDateTextField:null,
	constructor:function(){
		var outThis = this;
		this.openidTextField = new Ext.form.TextField({
			fieldLabel:"openid"
		});
		this.phonenumberTextField = new Ext.form.TextField({
			fieldLabel:"电话号码"
		});
		this.defaultAddressTextField = new Ext.form.TextField({
			fieldLabel:"默认地址"
		});
		this.cityTextField = new Ext.form.TextField({
			fieldLabel:"所在城市"
		});
		this.countryTextField = new Ext.form.TextField({
			fieldLabel:"所在国家"
		});
		
		this.languageTextField = new Ext.form.TextField({
			fieldLabel:"语言"
		});
		this.groupIdTextField = new Ext.form.TextField({
			fieldLabel:"所属组"
		});
		this.headimgUrlTextField = new Ext.form.TextField({
			fieldLabel:"头像"
		});
		this.provinceTextField = new Ext.form.TextField({
			fieldLabel:"所在省"
		});
		this.sexTextField = new Ext.form.TextField({
			fieldLabel:"性别"
		});
		this.tokenTextField = new Ext.form.TextField({
			fieldLabel:"token"
		});
		this.invalidDateTextField = new Ext.form.TextField({
			fieldLabel:"token有效期"
		});
		CustomerFormPanel.superclass.constructor.call(this,{
			items:[outThis.openidTextField,outThis.phonenumberTextField,outThis.defaultAddressTextField,
			       outThis.cityTextField,outThis.countryTextField,outThis.groupIdTextField,
			       outThis.headimgUrlTextField,outThis.provinceTextField,outThis.sexTextField,outThis.languageTextField,
			       outThis.tokenTextField,outThis.invalidDateTextField],
			frame:true,
			buttons:[{text:"确定"}]
		});
	}
});