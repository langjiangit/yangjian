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

package com.yametech.yangjian.agent.plugin.dubbo.context;

import com.yametech.yangjian.agent.api.base.IContext;
import com.yametech.yangjian.agent.api.common.StringUtil;
import com.yametech.yangjian.agent.api.interceptor.IConstructorListener;
import com.yametech.yangjian.agent.api.log.ILogger;
import com.yametech.yangjian.agent.api.log.LoggerFactory;
import com.yametech.yangjian.agent.plugin.dubbo.util.DubboSpanUtil;
import org.apache.dubbo.common.URL;

import static com.yametech.yangjian.agent.plugin.dubbo.context.ContextConstants.DUBBO_GROUP;
import static com.yametech.yangjian.agent.plugin.dubbo.context.ContextConstants.DUBBO_INTERFACE;
import static com.yametech.yangjian.agent.plugin.dubbo.context.ContextConstants.IS_GENERIC;

/**
 * 增强类为了获取dubbo group以及是否泛化调用
 *
 * @author dengliming
 */
public class ApacheInvokerInvocationHandlerInterceptor implements IConstructorListener {

    private static final ILogger LOG = LoggerFactory.getLogger(ApacheInvokerInvocationHandlerInterceptor.class);

    @Override
    public void constructor(Object thisObj, Object[] allArguments) throws Throwable {
        if (!(thisObj instanceof IContext)) {
            return;
        }

        URL url = ((org.apache.dubbo.rpc.Invoker) allArguments[0]).getUrl();
        String group = url.getParameter("group");
        if (StringUtil.isEmpty(group)) {
            group = DubboSpanUtil.getDubboGroup(url.getParameterAndDecoded("refer"));
        }

        if (StringUtil.notEmpty(group)) {
            ((IContext) thisObj)._setAgentContext(DUBBO_GROUP, group);
        }

        String isGeneric = url.getParameter("generic");
        if (Boolean.parseBoolean(isGeneric)) {
            String interfaceName = url.getParameter("interface");
            if (StringUtil.notEmpty(interfaceName)) {
                ((IContext) thisObj)._setAgentContext(DUBBO_INTERFACE, interfaceName);
            }
        }
        ((IContext) thisObj)._setAgentContext(IS_GENERIC, isGeneric);
        LOG.info("ApacheInvokerInvocationHandlerInterceptor({})", url.toString());
    }

}
