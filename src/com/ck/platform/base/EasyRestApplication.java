package com.ck.platform.base;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ck.platform.base.util.spring.SpringUtil;
import com.ck.platform.business.cms.resource.CmsColumnProductResource;
import com.ck.platform.business.comm.resource.UploadImgResource;
import com.ck.platform.business.customer.resource.CustomerInfoResource;
import com.ck.platform.business.customer.resource.CustomerReceiveAddressResource;
import com.ck.platform.business.customer.resource.CustomerUniteResource;
import com.ck.platform.business.order.resource.OrderInfoResource;
import com.ck.platform.business.order.resource.OrderProductResource;
import com.ck.platform.business.product.resource.ProductInfoResource;
import com.ck.platform.business.system.resource.SystemMenuResource;
import com.ck.platform.business.weixin.resource.WeixinInteractResource;

public class EasyRestApplication extends Application {

	HashSet<Object> singletons = new HashSet<Object>();

	public EasyRestApplication() {
		singletons.add((CustomerUniteResource) SpringUtil.getBean("customerUniteResource"));
		singletons.add((CustomerInfoResource) SpringUtil.getBean("customerInfoResource"));
		singletons.add((CustomerReceiveAddressResource) SpringUtil.getBean("customerReceiveAddressResource"));
		
		singletons.add((WeixinInteractResource) SpringUtil.getBean("weixinInteractResource"));
		singletons.add((SystemMenuResource) SpringUtil.getBean("systemMenuResource"));
		
		singletons.add((ProductInfoResource) SpringUtil.getBean("productInfoResource"));
		singletons.add((OrderInfoResource) SpringUtil.getBean("orderInfoResource"));
		singletons.add((OrderProductResource) SpringUtil.getBean("orderProductResource"));
		
		
		singletons.add((CmsColumnProductResource) SpringUtil.getBean("cmsColumnProductResource"));
		
		singletons.add((UploadImgResource) SpringUtil.getBean("uploadImgResource"));
		
		System.out.println("EasyRestApplication init end ");
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
