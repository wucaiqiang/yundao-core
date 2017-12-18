package com.yundao.core.service.ticket;

import com.yundao.core.cache.redis.JedisUtils;
import com.yundao.core.code.Result;
import com.yundao.core.code.config.CoreCode;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.dto.login.TicketModel;
import com.yundao.core.dto.login.UserAccountModel;
import com.yundao.core.exception.BaseException;
import com.yundao.core.service.AbstractService;
import com.yundao.core.service.login.UserAccountService;
import com.yundao.core.utils.TicketGeneratorUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * Created by gjl on 2017/9/7.
 */
public abstract class AbstractTicketService extends AbstractService {

    protected abstract TicketModel selectByTicket(String ticket) throws  Exception;

    protected abstract int updateByPrimaryKeySelective(TicketModel model)throws Exception;

    protected abstract UserAccountModel selectUserById(Long accountId)throws Exception;

    public Result<Boolean> doValidate(String ticket) throws Exception {
        Object ticketRedis = JedisUtils.getObject(ticket);
        TicketModel model=null;
        if(ticketRedis == null){
        	model= this.selectByTicket(ticket);
             if(model == null){
                 throw new BaseException(CoreCode.CORE_1250003);
             }
             if((model.getActiveTime() != CommonConstant.NEGATIVE_ONE && System.currentTimeMillis() > model.getExpireTime().getTime())){
            	 throw new BaseException(CoreCode.CORE_1250004);
             }
        }
        updateExpireTime(ticket);
        return Result.newSuccessResult(true);
    }

    public Result<String> doRefresh(String ticket) throws Exception {
        TicketModel model = this.selectByTicket(ticket);
        if(model ==null){
            throw new BaseException(CoreCode.CORE_1250003);
        }
        if (model.getActiveTime() != CommonConstant.NEGATIVE_ONE && System.currentTimeMillis() > model.getExpireTime().getTime()) {
            throw new BaseException(CoreCode.CORE_1250004);
        }

        String newTicket= TicketGeneratorUtil.generateTicket("TGT", "cas.yundao.com");//生成新的ticket
        model.setTicket(newTicket);
        Date date=new Date();
        model.setExpireTime(DateUtils.addSeconds(date, model.getActiveTime()));//重新设置过期时间
        updateByPrimaryKeySelective(model);
        //清空旧的ticket，更新新的ticket
        JedisUtils.remove(ticket);
        JedisUtils.setObject(newTicket,model.getActiveTime(),newTicket);
        return Result.newSuccessResult(newTicket);
    }

    public Result<Integer> updateExpireTime(String ticket) throws Exception{
        //从redis中获取ticket
        Object objTicket = JedisUtils.getObject(ticket);
        TicketModel model = this.selectByTicket(ticket);
        //如果redis存在，增加redis的过期时间
        if(objTicket != null){
            JedisUtils.setObject(ticket,model.getActiveTime(),ticket);
        }else{
            //如果redis不存在去数据库验证是否过期
            if (model.getActiveTime() != CommonConstant.NEGATIVE_ONE && System.currentTimeMillis() > model.getExpireTime().getTime()) {
                throw new BaseException(CoreCode.CORE_1250004);
            }else{
                //如果数据库没有过期，更新过期时间，添加到redis过期中
                model.setExpireTime(DateUtils.addSeconds(new Date(), model.getActiveTime()));
                updateByPrimaryKeySelective(model);
                JedisUtils.setObject(ticket,model.getActiveTime(),ticket);
            }
        }
        return Result.newSuccessResult(1);
    }
}
