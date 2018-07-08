package com.liuxn.controller;

import com.liuxn.common.base.log.Log;
import com.liuxn.common.base.log.LogFactory;
import com.liuxn.entity.TestUser;
import com.liuxn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IDEA by liuxn on 2018/5/16.
 *
 * @author liuxn
 */
@Controller
public class UserConnroller {
    private static final Log logger = LogFactory.getLog(UserConnroller.class);


    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "getUser")
    public void getUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            TestUser user = new TestUser();
            user.setUsername("admin");
            TestUser userdb = userService.getByLoginName(user);
            System.out.println(userdb.toString());
            PrintWriter writer = response.getWriter();
            writer.write(userdb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("", e);
        }

    }

}
