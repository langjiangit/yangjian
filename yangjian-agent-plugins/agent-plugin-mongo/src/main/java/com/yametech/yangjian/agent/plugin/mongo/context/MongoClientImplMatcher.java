/*
 * Copyright 2020 yametech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yametech.yangjian.agent.plugin.mongo.context;

import com.yametech.yangjian.agent.api.IEnhanceClassMatch;
import com.yametech.yangjian.agent.api.InterceptorMatcher;
import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.bean.MethodDefined;
import com.yametech.yangjian.agent.api.configmatch.*;

import java.util.Arrays;

/**
 * for 3.7.x
 *
 * @author dengliming
 * @date 2020/6/23
 */
public class MongoClientImplMatcher implements IEnhanceClassMatch, InterceptorMatcher {
    @Override
    public IConfigMatch classMatch() {
        return new ClassMatch("com.mongodb.client.MongoClientImpl");
    }

    @Override
    public IConfigMatch match() {
        return new CombineAndMatch(Arrays.asList(
                classMatch(),
                new MethodConstructorMatch(),
                new MethodArgumentIndexMatch(0, "com.mongodb.MongoClientSettings")
        ));
    }

    @Override
    public LoadClassKey loadClass(MethodType type, MethodDefined methodDefined) {
        return new LoadClassKey("com.yametech.yangjian.agent.plugin.mongo.context.MongoClientImplInterceptor");
    }
}
