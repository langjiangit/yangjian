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
package com.yametech.yangjian.agent.api.configmatch;

import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.bean.MethodDefined;

/**
 * 方法注解匹配
 * 
 * @author liuzhao
 */
public class MethodAnnotationMatch implements IConfigMatch {
	private String annotation;
	
	public MethodAnnotationMatch(String annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public boolean isMatch(MethodDefined methodDefined) {
		return methodDefined.getMethodAnnotations() != null && methodDefined.getMethodAnnotations().stream().anyMatch(ann -> ann.getName().equals(annotation));
	}
	
	@Override
	public String toString() {
		return annotation;
	}
	
}
