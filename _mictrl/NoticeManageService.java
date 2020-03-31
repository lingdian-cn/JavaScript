package com.mictrl.leopard.platform.service.notice;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mictrl.leopard.platform.kit.SqlKit;
import com.mictrl.leopard.platform.shiro.LpShiroKit;
import com.mictrl.leopard.platform.shiro.LpUserAccount;
import com.qyvip.libra.kit.Kvs;
import com.qyvip.libra.kit.Rets;
import com.qyvip.libra.kit.StrKit;
import com.qyvip.libra.log.Logger;
import com.qyvip.libra.log.LoggerFactory;
import java.util.Date;
import #(basePackage??"com.mictrl.leopard.platform").#(modelPackage??"model").#(modulePackage??).#(entityClassName??);

/**
 * @description #(description??)
 * @author #(author??)
 * @date #date()
 * @since jdk8
 */
public class #(serviceClassName??) {
    private static final Logger logger = LoggerFactory.get(#(serviceClassName??).class);
    
    private static #(serviceClassName??) me;
    
    public static #(serviceClassName??) me() {
        if (me == null) {
            return new #(serviceClassName??)();
        }
        return me;
    }
    
    public Rets save(String opt, #(entityClassName??) entity) {
        if (entity == null) return Rets.failM("无效的数据");
        if ("add".equals(opt)) {
            LpUserAccount user = LpShiroKit.me.getUser();
            entity.setCreater(user.getId());
            entity.setCreateTime(new Date());
            entity.setStatus(1);
            entity.setUpdater(user.getId());
            entity.setUpdateTime(new Date());
            if (entity.save()) {
                return Rets.okM("新增保存成功");
            } else {
                return Rets.failM("新增保存失败");
            }
        } else if ("edit".equals(opt)) {
            entity.setUpdater(LpShiroKit.me.getUser().getId());
            entity.setUpdateTime(new Date());
            if (entity.update()) {
                return Rets.okM("编辑保存成功");
            } else {
                return Rets.failM("编辑保存失败");
            }
        }
        return Rets.failM("不存在的操作类型");
    }
    
    /**
     * 分页查询列表
     * @param pageNumber 第几页
     * @param pageSize 每页数量
     * @param params 参数
     * @return
     */
    public Page<Record> paginate(Integer pageNumber, Integer pageSize, JSONObject params) {
        return SqlKit.me().getDsTemplate("#(methods.paginate??)", params).paginate(pageNumber, pageSize);
    }
    
    /**
     * 根据id获取数据
     * @param id 要查询的数据id
     * @return Record
     */
    public Record getRecordById(Integer id) {
        return SqlKit.me().getDsTemplate("#(methods.getRecordById??)", Kvs.by("id", id)).findFirst();
    }
    
    ### 下面的删除方法先注释，放到methods.others中
    #--
    /**
     * 删除一条数据
     * @param id 要删除的数据id
     * @return Rets
     */
    public Rets remove(String id) {
        if(SqlKit.me().getDsTemplate("#(methods.remove??)", Kvs.by("id", id)).update()>0) {
            return Rets.okM("删除操作成功");
        } else {
            return Rets.failM("删除操作失败");
        }
    }
    --#
    
    #for(method:methods.others??)
    /**
     * #(method.description??)
     * @param #(method.paramName??) #(method.paramText??)
     * @return Rets
     */
    public Rets #(method.name??)(#(method.paramType??) #(method.paramName??)) {
        if(SqlKit.me().getDsTemplate("#(method.template??)", Kvs.by("#(method.paramName??)", #(method.paramName??))).update()>0) {
            return Rets.okM("#(method.okText??)");
        } else {
            return Rets.failM("#(method.failText??)");
        }
    }
    
    #end
    
}
