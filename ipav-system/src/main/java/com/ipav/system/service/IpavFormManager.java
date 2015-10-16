package com.ipav.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ipav.system.entity.IpavForm;

/**
 * creater Jerry All right reserved. Created on 2014年11月14日 下午1:57:42
 * 上海天道启科电子有限公司
 */
@Service
public class IpavFormManager {
	private static final String SESSION_KEY_OF_FROMS = "_forms_in_session";
	/** 表单最大个数 */
	private int maxFormNum = 7;

	/**
	 * 销毁一个表单
	 */
	public void destroyToken(HttpServletRequest request, String token) {
		getForms(request).remove(token);
	}

	/**
	 * 打印表单信息。
	 */
	public String dumpForm(HttpServletRequest request, String token) {
		IpavForm form = getForms(request).get(token);
		if (form == null) {
			return "null";
		}
		return form.toString();
	}

	/**
	 * 判断表单是否存在。如果token为null，直接返回false。
	 * 
	 * @see #getForms(HttpServletRequest)
	 */
	public boolean hasForm(HttpServletRequest request, String token) {
		if (token == null) {
			return false;
		}
		return getForms(request).containsKey(token);
	}

	/**
	 * 访问参数中是否存在表单Token。
	 */
	public boolean hasFormToken(HttpServletRequest request) {
		String formToken = request
				.getParameter(IpavForm.FORM_UNIQ_ID_FIELD_NAME);
		return StringUtils.isNotBlank(formToken);
	}

	/**
	 * 生成一个新的表单，如果目前表单个数大于设定的最大表单数则先删除最早的一个表单。<br>
	 * 新表单用RandomStringUtils.randomAlphanumeric(32)生成Token。
	 * 
	 * @return 创建的新表单
	 * @see #removeOldestForm(HttpServletRequest)
	 * @see org.apache.commons.lang.RandomStringUtils#random(int)
	 */
	public IpavForm newForm(HttpServletRequest request) {
		IpavForm form = new IpavForm(RandomStringUtils.randomAlphanumeric(32));
		Map<String, IpavForm> forms = getForms(request);
		synchronized (forms) {
			// 如果目前表单个数大于等于最大表单数，那么删除最老的表单，添加新表单。
			if (forms.size() >= maxFormNum) {
				removeOldestForm(request);
			}
			forms.put(form.getToken(), form);
		}
		return form;
	}

	/**
	 * 获得目前session中的表单列表。
	 * 
	 * @return 返回的Map中以表单的token为键，Form对象为值
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, IpavForm> getForms(HttpServletRequest request) {
		Map<String, IpavForm> formsInSession = null;
		HttpSession session = request.getSession();
		synchronized (session) {
			formsInSession = (Map<String, IpavForm>) session
					.getAttribute(SESSION_KEY_OF_FROMS);
			if (formsInSession == null) {
				formsInSession = new HashMap<String, IpavForm>();
				session.setAttribute(SESSION_KEY_OF_FROMS, formsInSession);
			}
		}
		return formsInSession;
	}

	/**
	 * 删除最老的Form
	 * 
	 * @see #destroyToken(HttpServletRequest, String)
	 */
	protected void removeOldestForm(HttpServletRequest request) {
		List<IpavForm> forms = new ArrayList<IpavForm>(getForms(request)
				.values());
		if (!forms.isEmpty()) {
			IpavForm oldestForm = forms.get(0);
			for (IpavForm form : forms) {
				if (form.getCreateTime().before(oldestForm.getCreateTime())) {
					oldestForm = form;
				}
			}
			destroyToken(request, oldestForm.getToken());
		}
	}

	public void setMaxFormNum(int maxFormNum) {
		this.maxFormNum = maxFormNum;
	}

}
