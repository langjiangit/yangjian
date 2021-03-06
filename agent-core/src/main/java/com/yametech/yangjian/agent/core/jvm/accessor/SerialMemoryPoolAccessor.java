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
package com.yametech.yangjian.agent.core.jvm.accessor;

import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * @author zcn
 * @date: 2019-10-25
 */
public class SerialMemoryPoolAccessor extends BaseMemoryPoolAccessor {
    public SerialMemoryPoolAccessor(List<MemoryPoolMXBean> beans) {
        super(beans);
    }

    @Override
    protected String[] getPermNames() {
        return new String[]{"Perm Gen", "Compressed Class Space"};
    }

    @Override
    protected String[] getCodeCacheNames() {
        return new String[]{"Code Cache"};
    }

    @Override
    protected String[] getEdenNames() {
        return new String[]{"Eden Space"};
    }

    @Override
    protected String[] getOldNames() {
        return new String[]{"Tenured Gen"};
    }

    @Override
    protected String[] getSurvivorNames() {
        return new String[]{"Survivor Space"};
    }

    @Override
    protected String[] getMetaspaceNames() {
        return new String[]{"Metaspace"};
    }
}
