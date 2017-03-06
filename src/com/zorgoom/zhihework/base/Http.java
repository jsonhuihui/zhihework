package com.zorgoom.zhihework.base;

/**
 * Created by hx on 2015/7/21.
 */
public class Http {

	/**
	 * 服务器测试地址
	 * 
	 */
	 public static final String SERVLET_URL ="http://120.25.232.104:9000/zhshh/";
	 
	 
//	public static final String SERVLET_URL = "http://192.168.0.102:8080/zhshh/";

	// public static final String SERVLET_URL =
	// "http://120.25.232.104:9000/hxcloud/";

	/**
	 * 服务器生产地址
	 */
	// public static final String SERVLET_URL = "http://120.25.227.4:9000/";

	// 用注册接口
	public static final String REGISTER = SERVLET_URL + "appcity/register.do";
	// 登录接口
	public static final String LOGIN = SERVLET_URL + "appcity/login.do";
	// 获取城市/公司
	public static final String GETCOMPANY = SERVLET_URL + "appcity/getCompany.do";
	// 获取小区/部門
	public static final String GETCOMMUNITY = SERVLET_URL + "appcity/getDept.do?";
	// 获取楼栋
	public static final String GETBLOCK = SERVLET_URL + "appcity/getBlock.do?";
	// 获取房间
	public static final String GETUNIT = SERVLET_URL + "appcity/getUnit.do?";
	// 提交房屋认证
	public static final String AUTHENTICATION = SERVLET_URL + "appcity/authentication.do?";
	// 获取房屋列表
	public static final String GETMYUNIT = SERVLET_URL + "appcity/getMyUnit.do?";
	// 获取付款二维码
	public static final String QRCODE = SERVLET_URL + "appcity/getQRCode.do?";
	// 获取考勤二维码
	public static final String KQQRCODE = SERVLET_URL + "appcity/getattenceQRCode.do?";
	// 微信消费成功跳转界面
	public static final String CODEISCONSUME = SERVLET_URL + "appcity/codeisConsume.do?";
	// 微信支付
	public static final String WEIXINPAY = SERVLET_URL + "appPay/eAppWinXinPay.do?";
	// 金额
	public static final String EMP = SERVLET_URL + "appcity/getEmp.do?";
	// 打卡记录
	public static final String ATTENCEDETAIL = SERVLET_URL + "appcity/getattencedetail.do?";
	// 充值查询
	public static final String CHARGEDETAIL = SERVLET_URL + "appcity/getrechargedetail.do?";
	// 消费记录
	public static final String CONSUMEDETAIL = SERVLET_URL + "appcity/getconsumedetail.do?";
	// 获取钥匙列表接口
	public static final String GETLOCK = SERVLET_URL + "appcity/getLock.do?";
	// 联系物业接口
	public static final String GETCONTACT = SERVLET_URL + "appcity/getContact.do?";
	// 获取通知接口
	public static final String GETNOTICE = SERVLET_URL + "appcity/getNotice.do?";
	// 缴费列表信息查询接口
	public static final String PAYMENT_LIST = SERVLET_URL + "appcity/getMyBill.do?";
	// 缴费详情信息查询接口
	public static final String PAYMENT_DETAIL = SERVLET_URL + "appcity/getMyBillDetail.do?";
	// 访客通行查询接口
	public static final String GETMYGUEST = SERVLET_URL + "appcity/getMyGuest.do?";
	// 访客通行新建接口
	public static final String REDUCEGUESTPASSWORD = SERVLET_URL + "appcity/reduceGuestPassword.do";
	// 维修申报接口
	public static final String ADDTROUBLE = SERVLET_URL + "appcity/addTrouble.do";
	// 维修申报列表接口
	public static final String GETMYTROUBLE = SERVLET_URL + "appcity/getMyTrouble.do?";
	// 注册极光推送接口
	public static final String REGISTERJPUSH = SERVLET_URL + "appDevice/registerJPush.do?";
	// 室内机机初始化接口
	public static final String SAVEINDOORUNIT = SERVLET_URL + "appcity/saveIndoorUnit.do";
	// 室内机机密码重置接口
	public static final String UPDATEINDOORUNITPASSWORD = SERVLET_URL + "appcity/updateIndoorUnitPassword.do";
	// 修改密码接口
	public static final String EDITPASSWORD = SERVLET_URL + "appcity/editPassword.do";
	// 升级接口
	public static final String GETUPGRADE = SERVLET_URL + "appcity/getUpgrade.do?";
	// 意见反馈接口
	public static final String ADDSUGGESTION = SERVLET_URL + "appcity/addSuggestion.do";
	// 上传头像接口
	public static final String ADDUSERHEADER = SERVLET_URL + "appcity/addUserHeader.do";
	// 房屋删除
	public static final String DELETEMYUNIT = SERVLET_URL + "appcity/deleteMyUnit.do?";
	// 获取我的投诉建议列表
	public static final String GETMYSUGGESTION = SERVLET_URL + "appcity/getMySuggestion.do?";
	// 获取我的车位申请列表
	public static final String GETMYCARAPPLY = SERVLET_URL + "appcity/getMyCarApply.do?";
	// 获取我的车位申请列表
	public static final String ADDCARAPPLY = SERVLET_URL + "appcity/addCarApply.do";
	// 获取房屋租赁列表
	public static final String GETALLLEASEHOUSE = SERVLET_URL + "appcity/getAllLeaseHouse.do?";
	// 获取我的房屋租赁列表
	public static final String GETMYLEASEHOUSE = SERVLET_URL + "appcity/getMyLeaseHouse.do?";
	// 提交房屋租赁
	public static final String ADDLEASEHOUSE = SERVLET_URL + "appcity/addLeaseHouse.do";
	// 获取广告
	public static final String GETADBYPOSITION = SERVLET_URL + "appcity/getAdByPosition.do?";
	// 取消访客密钥
	public static final String UPDATEGUESTSTATUS = SERVLET_URL + "appcity/updateGuestStatus.do?";
	// 业主物业缴费支付
	public static final String EAPPWINXINPAY = SERVLET_URL + "appBillPay/eAppWinXinPay.do";
	// 手机户户通呼叫判断房屋是否存在
	public static final String FINDUNIT = SERVLET_URL + "appcity/findUnit.do?";
	// 开门记录
	public static final String FINDACCESS = SERVLET_URL + "appcity/findAccess.do?";

	/***************************************** 商圈o2o ********************************************************************/

	// 商圈列表
	public static final String SHOPLIST = SERVLET_URL + "appOto/shopList.do?";
	// 商圈商家分类
	public static final String GETSHOPPORDCATEGORY = SERVLET_URL + "appOto/getShopPordCategory.do?";
	// 商圈商家查询商品
	public static final String GETPROD = SERVLET_URL + "appOto/getProd.do?";
	// 业主获取收货地址
	public static final String GETADRRBYUSERID = SERVLET_URL + "appOto/getAdrrByUserId.do?";
	// 业主增加收货地址
	public static final String ADDRECEVIEADRR = SERVLET_URL + "appOto/addRecevieAdrr.do";
	// 业主修改收货地址
	public static final String UPDATERECEVIEADRR = SERVLET_URL + "appOto/updateRecevieAdrr.do";
	// 业主删除收货地址
	public static final String DELETERECEVIEADRR = SERVLET_URL + "appOto/deleteRecevieAdrr.do?";
	// 业主提交订单
	public static final String ADDORDER = SERVLET_URL + "appPay/eAppWinXinPay.do";
	// 查询订单
	public static final String GETORDERBYUSERID = SERVLET_URL + "appOto/getOrderByUserId.do?";
	// 获取首页的商家推荐
	public static final String PRIORITYSHOPLIST = SERVLET_URL + "appOto/priorityShopList.do?";
	// 删除订单
	public static final String DELETEORDER = SERVLET_URL + "appOto/updateOrderState.do?";
	// 上传app错误日志接口
	public static final String APPLOGUPLOAD = SERVLET_URL + "appLog/addLog.do?";
	
	/***************************************** 邻里圈 ********************************************************************/
	// 查询邻里圈
	public static final String GETUFRIENDDATAS = SERVLET_URL + "appcf/getUfrienddatas.do?";

}
