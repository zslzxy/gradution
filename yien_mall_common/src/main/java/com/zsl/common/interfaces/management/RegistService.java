package com.zsl.common.interfaces.management;

import com.zsl.common.entity.User;
import com.zsl.common.utils.Msg;

public interface RegistService {
    Msg insertOne(User user);
}
