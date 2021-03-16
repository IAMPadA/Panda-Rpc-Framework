package com.panda.rpc.test;

import com.panda.rpc.annotation.Service;
import com.panda.rpc.api.ByeService;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-16 13:12]
 * @description 服务实现类
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye," + name;
    }

}
