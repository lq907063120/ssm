package com.liuxn.service;

import com.liuxn.entity.TestUser;

/**
 * Created with IDEA by liuxn on 2018/5/16.
 *
 * @author liuxn
 */
public interface UserService {

    TestUser getByLoginName(TestUser user);
}
