package com.yundao.core.service.login;

import com.yundao.core.code.Result;
import com.yundao.core.dto.login.TicketModel;
import com.yundao.core.dto.login.UserAccountModel;

public interface TicketService {
    public Result<String> insert(TicketModel ticketModel) throws Exception;

    public Result<UserAccountModel> validate(String ticket) throws Exception;

    public Result<Integer> deleteByTicket(String ticket) throws Exception;


    public Result<TicketModel> selectByAccountId(Long accountId) throws Exception;
}
