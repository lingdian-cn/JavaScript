package com.mictrl.leopard.platform.web.notice.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.qyvip.libra.kit.Rets;
import com.qyvip.libra.kit.StrKit;
import com.qyvip.libra.log.Logger;
import com.qyvip.libra.log.LoggerFactory;
import com.qyvip.libra.web.controller.LibraController;
import com.qyvip.libra.web.controller.annotation.RequestMapping;
import #(basePackage??"com.mictrl.leopard.platform").#(modelPackage??"model").#(modulePackage??).#(entityClassName??);
import #(basePackage??"com.mictrl.leopard.platform").#(servicePackage??"service").#(modulePackage??).#(serviceClassName??);

/**
 * @description #(description??)
 * @author #(author??)
 * @date #date()
 * @since jdk8
 */
@RequestMapping(value = "#(requestMappingValue??)", viewPath = "#(requestMappingViewPath??)")
public class #(controllerClassName??) extends LibraController {
    private static final Logger logger = LoggerFactory.get(#(controllerClassName??).class);
    
    /**
     * 通知公告管理列表页面
     */
    public void index() {
        render("#(indexHtmlName??'index.html')");
    }
    
    /**
     * 分页列表
     */
    public void paginateList() {
        JSONObject jsonObj = this.getRawObject();
        logger.debug("jsonObj:{}", jsonObj);
        
        Integer pageNumber = jsonObj.getInteger("pageNumber");
        Integer pageSize = jsonObj.getInteger("pageSize");
        if (jsonObj.getJSONObject("searchParams") != null) {
            jsonObj.putAll(jsonObj.getJSONObject("searchParams"));
        }
        logger.debug("params:{}", jsonObj);
        
        renderJson(Rets.okD(#(serviceClassName??).me().paginate(pageNumber, pageSize, jsonObj)));
    }
    
    /**
     * 根据id获取数据
     */
    @Before(POST.class)
    public void loadForm() {
        JSONObject jsonObj = this.getRawObject();
        logger.debug("logger:{}", jsonObj);
        Integer dataId = jsonObj.getInteger("#(paramKey??'dataId')");
        renderJson(Rets.okD(#(serviceClassName??).me().getRecordById(dataId)));
    }
    
    /**
     * 新增页面
     */
    public void add() {
        setAttr("_page_params_action", "add");
        render("#(addOrEditHtmlName??'edit.html')");
    }
    
    /**
     * 编辑页面
     */
    public void edit() {
        setAttr("_page_params_#(idField??'id')", getPara("#(idField??'id')"));
        setAttr("_page_params_action", "edit");
        render("#(addOrEditHtmlName??'edit.html')");
    }
    
    /**
     * 保存 （新增/修改）
     */
    @Before(POST.class)
    public void save() {
        String _page_params_action = getPara("_page_params_action");
        #(entityClassName??) entity = getBean(#(entityClassName??).class, "");
        renderJson(#(serviceClassName??).me().save(_page_params_action, entity));
    }
    
    ### 下面的删除方法先注释，放到methods中
    #--
    /**
     * 删除/批量删除
     */
    public void remove() {
        String id = getPara("#(idField??'id')");
        String ids = getPara("ids");
        logger.debug("id:{} -- ids:{}", id, ids);
        if (StrKit.notBlank(id)) {
            renderJson(#(serviceClassName??).me().remove(id));
        } else if (StrKit.notBlank((ids))) {
            renderJson(#(serviceClassName??).me().remove(ids.split(",")));
        } else {
            renderJson(Rets.failM("请选择一行或多行需要操作的数据"));
        }
    }
    --#
    
    #for(method:methods??)
    /**
     * #(method.description??)
     */
    public void #(method.cMethodName??)() {
        String id = getPara("#(idField??'id')");
        String ids = getPara("ids");
        logger.debug("id:{} -- ids:{}", id, ids);
        if (StrKit.notBlank(id)) {
            renderJson(#(serviceClassName??).me().#(method.sMethodName??)(id));
        } else if (StrKit.notBlank((ids))) {
            renderJson(#(serviceClassName??).me().#(method.sMethodName??)(ids.split(",")));
        } else {
            renderJson(Rets.failM("请选择一行或多行需要操作的数据"));
        }
        renderJson(Rets.ok());
    }
    
    #end
    
}
