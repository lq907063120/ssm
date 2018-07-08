package com.liuxn.service.impl;

import com.liuxn.entity.TestUser;
import com.liuxn.entity.TestUserExample;
import com.liuxn.mapper.TestUserMapper;
import com.liuxn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA by liuxn on 2018/5/16.
 *
 * @author liuxn
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TestUserMapper testUserMapper;

    @Override
    public TestUser getByLoginName(TestUser user) {
        TestUserExample example = new TestUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        return testUserMapper.selectByExample(example).get(0);
    }
}
