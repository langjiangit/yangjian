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
package com.yametech.yangjian.agent.plugin.dubbo.util;

import org.springframework.aop.TargetClassAware;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author dengliming
 */
public class ClassUtil {

    private static final String CGLIB_CLASS_SEPARATOR = "$$";
    private static final String JDK_PROXY_CLASS_PREFIX = "$Proxy";

    /**
     * 获取应用原始类名
     * <p>
     * 如：AccountInfoServiceImpl$$EnhancerBySpringCGLIB$$88abb036 return AccountInfoServiceImpl
     * 如：com.sun.proxy.$Proxy97 return target class
     *
     * @param proxy
     * @return 返回代理前的class类型
     */
    public static Class<?> getOriginalClass(Object proxy) {
        Class clazz = proxy.getClass();
        // CGLIB代理
        if (clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                return superclass;
            }
        }
        // JDK代理
        else if (clazz.getName().contains(JDK_PROXY_CLASS_PREFIX)) {
            try {
                Class<?> result = getJDKTargetClass(proxy);
                if (result != null) {
                    return result;
                }
            } catch (Throwable e) {
                // ignore
            }
        }

        return clazz;
    }

    /**
     * 参考spring-aop
     *
     * @param candidate
     * @return
     */
    public static Class<?> getJDKTargetClass(Object candidate) {
        Object current = candidate;
        Class<?> result = null;
        while (current instanceof TargetClassAware) {
            result = ((TargetClassAware) current).getTargetClass();
            Object nested = null;
            if (current instanceof Advised) {
                TargetSource targetSource = ((Advised) current).getTargetSource();
                if (targetSource instanceof SingletonTargetSource) {
                    nested = ((SingletonTargetSource) targetSource).getTarget();
                }
            }
            current = nested;
        }
        return result;
    }
}
