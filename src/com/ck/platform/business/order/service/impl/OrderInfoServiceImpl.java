package com.ck.platform.business.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.RandomUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.DateUtil;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.order.constant.OrderStatus;
import com.ck.platform.business.order.constant.OrderType;
import com.ck.platform.business.order.dao.OrderInfoDAO;
import com.ck.platform.business.order.dao.OrderInvDAO;
import com.ck.platform.business.order.dao.OrderProductDAO;
import com.ck.platform.business.order.service.OrderInfoService;
import com.ck.platform.business.product.dao.ProductInfoDAO;

@Service("orderInfoService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	OrderInfoDAO orderInfoDAO;
	@Autowired
	OrderProductDAO orderProductDAO;
	@Autowired
	OrderInvDAO orderInvDAO;
	@Autowired
	ProductInfoDAO productInfoDAO;

	/**
	 * 验证订单参数是否有效
	 * 
	 * @param params
	 * @return
	 */
	private RepObject validOrderParams(Map params) {
		RepObject repObject = new RepObject(true,ResponseCode.GLOBAL_SUSSCESS, "");
		try {
			if (params != null) {
				String productIds = params.get("productIds") != null ? params.get("productIds").toString() : "";
				String customerId = params.get("order.customerId") != null ? params.get("order.customerId").toString() : "";
				String isInvoice = params.get("order.isInvoice") != null ? params.get("order.isInvoice").toString() : "";
				String siteId = params.get("order.siteId") != null ? params.get("order.siteId").toString() : "";
				String payMode = params.get("order.payMode") != null ? params.get("order.payMode").toString() : "";
				String receiveName = params.get("order.receiveName") != null ? params.get("order.receiveName").toString() : "";
				String receiveMobile = params.get("order.receiveMobile") != null ? params.get("order.receiveName").toString() : "";
				// 验证参数完整性
				if (StringUtils.isEmpty(productIds)
						|| StringUtils.isEmpty(customerId)
						|| StringUtils.isEmpty(payMode)
						|| StringUtils.isEmpty(receiveMobile)
						|| StringUtils.isEmpty(receiveName)
						|| StringUtils.isEmpty(siteId)) {
					repObject.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
					repObject.setMsg("订单数据不完整");
					repObject.setStatus(false);
				} else {
					repObject.setCode(ResponseCode.GLOBAL_SUSSCESS);
				}
				// 验证参数一致性
				if (ResponseCode.GLOBAL_SUSSCESS.equals(repObject.getCode())) {
					String[] productIdArr = productIds.split("&");
					// 验证参数结构
					for (int a = 0; a < productIdArr.length; a++) {
						String productO = productIdArr[a];
						if (productO.indexOf("-") <= 0) {
							repObject.setCode(ResponseCode.GLOBAL_FAILTURE);
							repObject.setMsg("参数格式不正确");
							repObject.setStatus(false);
						}
					}
					// 验证订单-发票
					repObject = validInvParams(params);
				}
			}

		} catch (Exception e) {
			repObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObject.setStatus(false);
			e.printStackTrace();

		}
		return repObject;
	}

	/**
	 * 验证订单-发票
	 * 
	 * @param params
	 * @param index
	 *            第index+1张发票
	 * @return
	 */
	public RepObject validInvParams(Map params) {
		RepObject repObject = new RepObject(true,ResponseCode.GLOBAL_SUSSCESS, "");
		try {
			if (params != null) {
				String invTitle =StringTool.getMapString(params,"inv.invTitle");
				String invContent = StringTool.getMapString(params,"inv.invContent");
				// 验证参数完整性
				if (StringUtils.isEmpty(invTitle)
						|| StringUtils.isEmpty(invContent)) {
					repObject.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
					repObject.setMsg("发票数据不完整");
					repObject.setStatus(false);
				} else {
					repObject.setCode(ResponseCode.GLOBAL_SUSSCESS);
				}
			}

		} catch (Exception e) {
			repObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObject.setStatus(false);
			e.printStackTrace();

		}
		return repObject;
	}


	/**
	 * 创建订单 多商品格式 productIds：1000001-1&1000002-2
	 */
	public synchronized RepObject createOrder(Map paramMap)throws Exception {
		RepObject repObject = new RepObject(false, "","");
		try {
			// 判断订单是否有效
			RepObject validObject = validOrderParams(paramMap);
			if (validObject.getStatus()) {
				String[] productIds = StringTool.getMapString(paramMap, "productIds").split("&");
				String orderId=RandomUtil.generateTimeMillNum(12);
				String orderName="";
				String defaultProductId="";
				double payMoney=0.00;
				double totalMoney=0.00;
				//创建订单商品
				boolean isCreateOrderPro=false;
				for (int i = 0; i < productIds.length; i++) {
					String productId = productIds[i].split("-")[0];
					String productNumber = productIds[i].split("-")[1];
					String productDiscountMoney ="0.00";
					Map orderProduct=new HashMap();
					orderProduct.put("productNumber", productNumber);
					//创建订单-商品
					RepObject proRepObj= createOrderProduct(orderProduct,productId,orderId);
					if(proRepObj.getStatus()){
						Map proData=(HashMap)proRepObj.getData();
						double pMoney=0.00;
						pMoney=Double.valueOf(StringTool.getMapString(proData, "payMoney"));
						payMoney+=pMoney;
						double tMoney=0.00;
						tMoney=Double.valueOf(StringTool.getMapString(proData, "totalMoney"));
						totalMoney+=tMoney;
						double dMoney=0.00;
						if(StringUtils.isEmpty(orderName))
							orderName=StringTool.getMapString(proData, "productName");
						if(StringUtils.isEmpty(defaultProductId))
							defaultProductId=productId;
						//创建成功
						if(i==productIds.length-1){
							isCreateOrderPro=true;
						}
					}else{
						repObject=proRepObj;
					}
				}
				
				// 创建订单
				Map<String,Object> order_param_map = this.getOrderMap(paramMap);
				boolean isCreateOrder=false;
				if (isCreateOrderPro && order_param_map != null) {
					String freight=StringTool.getMapString(paramMap, "order.freight");
					String couponId=StringTool.getMapString(paramMap, "order.couponId");  //预留优惠券
					double couponMoney=0.00;
					order_param_map.put("order.orderType",OrderType.COMMON_PRODUCT);	
					order_param_map.put("orderId", orderId);
					order_param_map.put("orderStatus",OrderStatus.WAITING_PAYMENT);
					if(StringUtils.isNotEmpty(freight) && Integer.valueOf(freight)>0){
						payMoney=payMoney+Double.valueOf(freight);
					}
					order_param_map.put("payMoney", payMoney);
					order_param_map.put("totalMoney", totalMoney);
					order_param_map.put("couponMoney", couponMoney);
					order_param_map.put("validTime", DateUtil.getDateToString(DateUtil.getDateAfter(new Date(), 15),
							"yyyy-MM-dd HH:mm:ss"));
					if (order_param_map.get("orderName") == null) {
						order_param_map.put("orderName",orderName);
					}
					order_param_map.put("defaultProductId", defaultProductId);
					orderInfoDAO.create(order_param_map);
					repObject.setData(order_param_map);
					repObject.setStatus(true);
					isCreateOrder=true;
				}

				// 创建订单-发票
				Map order_inv_param_map = this.getInvMap(paramMap);
				if (isCreateOrder && repObject.getStatus() && order_inv_param_map != null) {
					order_inv_param_map.put("orderId", orderId);
					order_inv_param_map.put("invMoney", payMoney);
					if (order_inv_param_map.get("invType") == null) {
						order_inv_param_map.put("invType", 1);
					}
					orderInvDAO.create(order_inv_param_map);
				}
				repObject.setData(order_param_map);
				repObject.setPk(Long.valueOf(orderId));

			}else{
				repObject=validObject;
			}

		} catch (Exception ex) {
			repObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObject.setStatus(false);
			throw new Exception("创建订单出错",ex);
			//ex.printStackTrace();
		}
		return repObject;

	}

	private String getMapString(Map paramMap, String mapStr) {
		String value = paramMap.get(mapStr) != null ? paramMap.get(mapStr)
				.toString() : "";

		return value;
	}

	private Float getInvMoney(Long orderId) {

		Float f = 0f;

		return f;

	}


	/**
	 * 创建订单-单品
	 * 
	 * @param paramMap 所需参数
	 * @param productId 商品ID
	 * @param orderId 商品数量
	 * @return
	 */
	private RepObject createOrderProduct(Map paramMap,String productId,String orderId)throws Exception {
		RepObject resObject = new RepObject(false, "", "");
		try {
			HashMap productInfo = productInfoDAO.getById(productId);
			if (productInfo != null) {
				int productNumber=Integer.valueOf(StringTool.getMapString(paramMap, "productNumber"));
				String isValid=StringTool.getMapString(productInfo, "is_valid");
				int stock=Integer.valueOf(StringTool.getMapString(productInfo, "stock"));
				String isOnSale=StringTool.getMapString(productInfo, "is_on_sale");
				
				String productPrice=StringTool.getMapString(productInfo, "product_price");
				if("1".equals(isValid)  && "1".equals(isOnSale) 
						&& stock> 0 && productNumber>0
						 && stock>=productNumber){
					int pNumber=Integer.valueOf(productNumber);
					Map orderform_sub_param_map = new HashMap();
					orderform_sub_param_map.put("orderId", orderId);
					orderform_sub_param_map.put("productId", productId);
					orderform_sub_param_map.put("productNumber", productNumber);
					orderform_sub_param_map.put("productName",StringTool.getMapString(productInfo, "product_name"));
					orderform_sub_param_map.put("productPrice",productPrice);
					double totalMoney = 0.00;
					totalMoney = Double.valueOf(productPrice)* pNumber;
					orderform_sub_param_map.put("totalMoney",totalMoney);
					double payMoney=0.00;
					payMoney=totalMoney;
					orderform_sub_param_map.put("payMoney", payMoney);
					orderProductDAO.create(orderform_sub_param_map);
					Map<String,Object> dataMap=new HashMap<String,Object>();
					dataMap.put("productName", StringTool.getMapString(productInfo, "product_name"));
					dataMap.put("productPrice", Double.valueOf(productPrice));
					dataMap.put("payMoney", payMoney);
					dataMap.put("totalMoney", totalMoney);
					resObject.setStatus(true);
					resObject.setData(dataMap);
				}else{
					resObject.setCode(ResponseCode.GLOBAL_FAILTURE);
					resObject.setMsg("商品无效或库存不足：" + productId);
				}
				
			} else {
				resObject.setCode(ResponseCode.GLOBAL_FAILTURE);
				resObject.setMsg("不存在的商品：" + productId);
			}
		} catch (Exception e) {
			resObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			resObject.setStatus(false);
			throw e;
		}
		return resObject;
	}

	/**
	 * 获取发票信息
	 * 
	 * @param paramMap
	 * @param index
	 * @return
	 */
	private Map getInvMap(Map paramMap) {
		Map invMap = null;
		String isInvoice = "0";
		if (paramMap != null && paramMap.get("order.isInvoice") != null) {
			isInvoice = StringTool.getMapString(paramMap, "order.isInvoice");
		}
		if (paramMap != null && "1".equals(isInvoice)) {
			invMap = new HashMap();
			Iterator it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String invProperty = it.next().toString();

				if (invProperty.startsWith("inv.")) {
					String key = invProperty.substring(invProperty.indexOf(".") + 1, invProperty.length());
					String value = paramMap.get(invProperty).toString();
					invMap.put(key, value);
				}
			}
		}
		return invMap;
	}

	/**
	 * 获取订单信息
	 * 
	 * @param paramMap
	 * @param index
	 * @return
	 */
	private Map getOrderMap(Map paramMap) {
		Map orderMap = null;
		if (paramMap != null) {
			orderMap = new HashMap();
			Iterator it = paramMap.keySet().iterator();
			while (it.hasNext()) {
				String orderProperty = it.next().toString();
				if (orderProperty.startsWith("order.")) {
					String key = orderProperty.substring(
							orderProperty.indexOf(".") + 1,
							orderProperty.length());
					orderMap.put(key, paramMap.get(orderProperty).toString());
				}
			}
		}
		return orderMap;
	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject repObject = new RepObject(false,"","");
		String id=StringTool.getMapString(paramMap, "id");
		String orderId=StringTool.getMapString(paramMap, "orderId");
		try{
			if(StringUtils.isEmpty(id) && StringUtils.isEmpty(orderId)){
				repObject.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObject.setMsg("参数错误");
			}else{
				orderInfoDAO.update(paramMap);
				repObject.setStatus(Boolean.TRUE);
			}
		} catch (Exception ex){
			repObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObject.setMsg("update order_info error");
			ex.printStackTrace();
		}
		return repObject;
	}

	public RepObject executeCancel(Map paramMap) {
		RepObject repObject = new RepObject(false, "","");
		try {
			String orderId = StringTool.getMapString(paramMap, "orderId");
			if (StringUtils.isEmpty(orderId)) {
				repObject.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
				repObject.setMsg("数据不完整");
				return repObject;
			}
			HashMap orderMap=orderInfoDAO.getById(orderId); 
			// 取消订单
			if (orderMap!=null && orderId.equals(orderMap.get("order_id").toString())) {
				String orderStatus = StringTool.getMapString(paramMap, "order_status");
				//只有待发货的时候才可以取消订单
				if(OrderStatus.WAITING_PAYMENT.equals(orderStatus)){
					Map cancelMap = new HashMap();
					cancelMap.put("orderId", orderId);
					cancelMap.put("orderStatus", OrderStatus.CANCEL);
					cancelMap.put("remark", StringTool.getMapString(paramMap, "remark"));
					orderInfoDAO.update(cancelMap);
					repObject.setStatus(true);
				}else {
							repObject.setCode(ResponseCode.GLOBAL_FAILTURE);
							repObject.setMsg("不能取消的订单状态");
				}
			} else {
				repObject.setCode(ResponseCode.GLOBAL_SYSTEM_NONEXIST);
				repObject.setMsg("订单不存在");
			}

		} catch (Exception ex) {
			repObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObject.setStatus(false);
			// ex.printStackTrace();
		}
		return repObject;

	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return orderInfoDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return orderInfoDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return orderInfoDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return orderInfoDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return orderInfoDAO.getByParam(paramMap);
	}

}
