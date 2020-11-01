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
package com.yametech.yangjian.agent.api;

import com.yametech.yangjian.agent.api.base.IReportData;
import com.yametech.yangjian.agent.api.base.SPI;

public interface IReport extends IReportData, SPI {
	
	/**
	 * 上报类型，注册后可被其他上报使用者配置选择使用哪种上报方式
	 * @return	上报类型
	 */
	String type();
	
}