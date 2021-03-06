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
package com.yametech.yangjian.agent.api.bean;

import java.util.Set;

public class MethodDefined {
	private ClassDefined classDefined;// 类定义
	private String methodDes;// 方法描述
	private Set<Annotation> methodAnnotations;// 方法注解
	private String methodName;// 方法名
	private String[] params;// 方法参数类型
	private String methodRet;// 方法返回值类型
	private boolean staticMethod = false;// 是否为静态方法
	private boolean constructorMethod = false;// 是否为构造方法
	private boolean instanceMethod = false;// 是否为实例方法

	public MethodDefined(ClassDefined classDefined) {
		this.classDefined = classDefined;
	}
	
	public MethodDefined(ClassDefined classDefined, Set<Annotation> methodAnnotations,
			String methodDes, String methodName, String[] params, String methodRet, 
			boolean staticMethod, boolean constructorMethod, boolean instanceMethod) {
		this.classDefined = classDefined;
		this.methodDes = methodDes;
		this.methodAnnotations = methodAnnotations;
		this.methodName = methodName;
		this.params = params;
		this.methodRet = methodRet;
		this.staticMethod = staticMethod;
		this.constructorMethod = constructorMethod;
		this.instanceMethod = instanceMethod;
	}

	public ClassDefined getClassDefined() {
		return classDefined;
	}
	
	public Set<Annotation> getMethodAnnotations() {
		return methodAnnotations;
	}
	
	public String getMethodDes() {
		return methodDes;
	}

	public String getMethodName() {
		return methodName;
	}

	public String[] getParams() {
		return params;
	}

	public String getMethodRet() {
		return methodRet;
	}
	
	public boolean isConstructorMethod() {
		return constructorMethod;
	}
	
	public boolean isInstanceMethod() {
		return instanceMethod;
	}
	
	public boolean isStaticMethod() {
		return staticMethod;
	}

	public String getMethodIdentify() {
		return classDefined.getClassName() + "." + methodName + "(" + String.join(",", params) + ")";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append('\t').append(methodDes);
		if(methodAnnotations != null && !methodAnnotations.isEmpty()) {
			builder.append(" annotations ");
			methodAnnotations.forEach(cls -> builder.append(cls).append(','));
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
	
}
