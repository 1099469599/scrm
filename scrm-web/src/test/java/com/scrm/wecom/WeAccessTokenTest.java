package com.scrm.wecom;

import com.scrm.BaseTest;
import com.scrm.context.BaseContextHandler;
import com.scrm.dto.wecom.WeAccessTokenDTO;
import com.scrm.service.wecom.AccessTokenClient;
import com.scrm.service.wecom.customer.CustomerClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuKevin
 * @date 2021年10月13日 15:54
 */
@Slf4j
public class WeAccessTokenTest extends BaseTest {

    @Autowired
    private AccessTokenClient accessTokenClient;
    @Autowired
    private CustomerClient customerClient;

    @Test
    public void getAccessToken(){
        WeAccessTokenDTO wwb7bc0ee558e60842 = accessTokenClient.getAccessToken("wwb7bc0ee558e60842", "TjOdsTpxx3PyAyuNvnnhXe-CP1YgNIGWf32T6RTrt6c");
        log.info("sss");
        BaseContextHandler.setCorpId("wwb7bc0ee558e60842");
        String list = customerClient.list();
    }
}
