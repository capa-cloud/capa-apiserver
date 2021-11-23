/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package group.rxcloud.capa.proxy.rpc.demo;

import group.rxcloud.capa.proxy.rpc.LocalRpcInvoker;
import group.rxcloud.capa.proxy.rpc.RemoteRpcInvoker;
import group.rxcloud.capa.proxy.rpc.domain.RpcRequest;
import group.rxcloud.cloudruntimes.utils.TypeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * The capa-proxy-gateway Aws API.
 */
@Controller
public class CapaRpcProxyApi {

    @Autowired
    private LocalRpcInvoker localRpcInvoker;
    @Autowired
    private RemoteRpcInvoker remoteRpcInvoker;

    @RequestMapping(value = "/vi/health")
    @ResponseBody
    public String checkhealth() {
        return "ok";
    }

    @RequestMapping(value = "/api/invokeRpc")
    @ResponseBody
    public String invokeRpc(RpcRequest request) throws Exception {
        String appId = localRpcInvoker.generateAppId(request);
        String methodName = localRpcInvoker.generateMethodName(request);
        Object data = localRpcInvoker.generateData(request);
        Mono<String> mono = localRpcInvoker.invokeMethod(appId, methodName, data, TypeRef.STRING);
        return mono.block();
    }
}
