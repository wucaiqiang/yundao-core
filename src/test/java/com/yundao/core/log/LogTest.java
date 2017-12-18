package com.yundao.core.log;

import com.yundao.core.base.service.BaseService;
import com.yundao.core.dto.log.LogInsertDto;
import org.junit.Assert;
import org.junit.Test;

/**
 * 日志测试类
 *
 * @author wupengfei wupf86@126.com
 */
public class LogTest {

    private static Log log = LogFactory.getLog(LogTest.class);

    @Test
    public void logBusiness() {
        LogInsertDto logDto = new LogInsertDto();
        logDto.setIp("192.168.1.13");
        logDto.setSystemCode("core");
        logDto.setBusinessName("插入客户");
        logDto.setBusinessId(3);
        logDto.setTableName("customer");
        logDto.setActiveUserId(6);

        logDto.putField("sex", "1");
        logDto.putField("name", "客户1");
        logDto.putField("type", "A");
        BaseService.getBaseService().logBusiness(logDto);
    }

    @Test
    public void testLog() {
        Person person = new Person();
        person.setId(1);
        person.setName("log");
        log.begin("ssss", "dddd", person);
        log.debug("log debug");
        log.info("log info");
        log.warn("log warn");
        log.error("log error");
        log.error("log error");
        log.info("warn count=" + LogFactory.getWarnCount() + ", error count=" + LogFactory.getErrorCount());
        LogFactory.resetCount();
        log.info("warn count=" + LogFactory.getWarnCount() + ", error count=" + LogFactory.getErrorCount());
        log.end();
    }

    @Test
    public void testConfigLog() {
        log.info("before testConfigLog info");
        boolean config = LogFactory.configCommonsLog();
        Assert.assertEquals(config, true);
        log = LogFactory.getLog(LogTest.class);
        log.info("after testConfigLog info");
    }
}

class Person {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id : " + this.id;
    }
}
