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
package com.yametech.yangjian.agent.plugin.dubbo;

import com.yametech.yangjian.agent.plugin.dubbo.base.RpcClient;
import com.yametech.yangjian.agent.plugin.dubbo.base.RpcServer;
import org.junit.After;
import org.junit.Before;
import zipkin2.Endpoint;
import zipkin2.Span;

import java.time.Instant;

public class DubboTest {
//	private RpcServer rpcServer;
//	private RpcClient client;
	
	@org.junit.Test
	public void server() throws InterruptedException {
		RpcServer rpcServer = initDubboServer();
		Thread.sleep(1000000);
		rpcServer.stop();
	}
	
	@org.junit.Test
	public void client() throws InterruptedException {
		initDubboClient();
		IDubboService service = RpcClient.getService(IDubboService.class);
		System.err.println(service.hello("aaa"));
		System.err.println(service.hello("aaa", 15));
		System.err.println(service.heart());
//		Thread.sleep(5000);
	}

	@org.junit.Test
	public void test() throws InterruptedException {
		Span span = Span.newBuilder()
				.traceId(1L, 2L)
				.id(1L)
				.parentId(111L)
				.kind(Span.Kind.CLIENT)
				.name("name")
				.putTag("error", "参数异常")
				.putTag("tag-1", "tag-value-1")
				.putTag("tag-2", "tag-value-2")
				.localEndpoint(Endpoint.newBuilder().ip("10.10.1.1").serviceName("test-server").build())
				.remoteEndpoint(Endpoint.newBuilder().ip("10.10.1.1").port(8080).build())
				.timestamp(1589188711915000L)
				.duration(100)
				.addAnnotation(System.currentTimeMillis(), "annotation-1")
				.addAnnotation(System.currentTimeMillis(), "annotation-2").build();
		System.err.println(span);
	}

	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
	}
	
	public static RpcServer initDubboServer() {
		return initDubboServer("1.0.0");
	}
	public static RpcServer initDubboServer(String version) {
		return RpcServer.instance()
				.appName("javaagent-test")
				.addProtocol(20901, 3)
				.addRegistry("zookeeper://10.1.0.64:2181")
				.version(version)
				.start(IDubboService.class, new DubboService());
//		return rpcServer;
	}
	
	public static void initDubboClient() {
		initDubboClient("1.0.0");
	}
	public static void initDubboClient(String version) {
		RpcClient.instance()
				.appName("javaagent-test")
				.addRegistry("zookeeper://10.1.0.64:2181")
				.version(version)
				.timeout(10000);
	}
}
