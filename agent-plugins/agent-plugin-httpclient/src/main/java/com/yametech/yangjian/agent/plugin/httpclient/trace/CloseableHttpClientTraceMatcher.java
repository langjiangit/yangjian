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
package com.yametech.yangjian.agent.plugin.httpclient.trace;

import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.bean.MethodDefined;
import com.yametech.yangjian.agent.api.configmatch.*;
import com.yametech.yangjian.agent.api.trace.ITraceMatcher;
import com.yametech.yangjian.agent.api.trace.TraceType;

import java.util.Arrays;

/**
 * @author dengliming
 * @date 2020/4/25
 */
public class CloseableHttpClientTraceMatcher implements ITraceMatcher {

    @Override
    public TraceType type() {
        return TraceType.HTTP_CLIENT;
    }

    @Override
    public IConfigMatch match() {
        return new CombineOrMatch(Arrays.asList(
                // 主要是兼容4.0.x-4.2.x版本，4.3版本该类已弃用
                new CombineAndMatch(Arrays.asList(
                        new ClassMatch("org.apache.http.impl.client.DefaultRequestDirector"),
                        new NotMatch(new ClassAnnotationMatch("java.lang.Deprecated")),
                        new MethodNameMatch("execute"),
                        new MethodArgumentNumMatch(3))),

                new CombineAndMatch(Arrays.asList(
                        new SuperClassMatch("org.apache.http.impl.client.CloseableHttpClient"),
                        new MethodNameMatch("doExecute"),
                        new MethodArgumentNumMatch(3),
                        new MethodReturnMatch("org.apache.http.client.methods.CloseableHttpResponse")))
        ));
    }

    @Override
    public LoadClassKey loadClass(MethodType type, MethodDefined methodDefined) {
        return new LoadClassKey("com.yametech.yangjian.agent.plugin.httpclient.trace.CloseableHttpClientSpanCreater");
    }
}
