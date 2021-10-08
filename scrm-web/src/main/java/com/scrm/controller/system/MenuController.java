package com.scrm.controller.system;

import com.scrm.service.common.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system/menu")
public class MenuController {

    @Autowired
    private LogService logService;

}
