package com.scrm.controller.system;

import com.scrm.common.service.LogService;
import com.scrm.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private LogService logService;

}
