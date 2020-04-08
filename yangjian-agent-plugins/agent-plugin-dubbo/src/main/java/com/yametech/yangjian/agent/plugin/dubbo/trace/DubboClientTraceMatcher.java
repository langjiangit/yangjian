package com.yametech.yangjian.agent.plugin.dubbo.trace;

import java.util.Arrays;

import com.yametech.yangjian.agent.api.base.IConfigMatch;
import com.yametech.yangjian.agent.api.base.MethodType;
import com.yametech.yangjian.agent.api.bean.LoadClassKey;
import com.yametech.yangjian.agent.api.configmatch.ClassMatch;
import com.yametech.yangjian.agent.api.configmatch.CombineAndMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodArgumentNumMatch;
import com.yametech.yangjian.agent.api.configmatch.MethodNameMatch;
import com.yametech.yangjian.agent.api.trace.ITraceMatcher;
import com.yametech.yangjian.agent.api.trace.TraceType;

public class DubboClientTraceMatcher implements ITraceMatcher {

	@Override
	public IConfigMatch match() {
		return new CombineAndMatch(Arrays.asList(
        		new ClassMatch("org.apache.dubbo.rpc.protocol.dubbo.filter.TraceFilter"),
        		new MethodNameMatch("invoke"),
                new MethodArgumentNumMatch(2)
        ));
	}

	@Override
	public TraceType type() {
		return TraceType.DUBBO_CLIENT;
	}
	
	@Override
	public LoadClassKey loadClass(MethodType type) {
		return new LoadClassKey("com.yametech.yangjian.agent.plugin.dubbo.trace.DubboClientSpanCreater");
	}

}
