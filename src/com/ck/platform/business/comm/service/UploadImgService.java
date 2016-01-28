package com.ck.platform.business.comm.service;

import java.util.Map;

import com.ck.platform.base.entity.RepObject;

@SuppressWarnings("rawtypes")
public interface UploadImgService
{
	RepObject uploadProductImg(Map paramMap);

	RepObject uploadCustomerPortraitImg(Map paramMap);
}
