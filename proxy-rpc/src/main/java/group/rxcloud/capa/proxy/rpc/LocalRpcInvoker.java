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
package group.rxcloud.capa.proxy.rpc;

import group.rxcloud.capa.proxy.rpc.domain.RpcRequest;
import group.rxcloud.capa.rpc.CapaRpcClient;
import org.springframework.stereotype.Component;

@Component
public class LocalRpcInvoker extends RpcInvoker {

    public LocalRpcInvoker(CapaRpcClient client) {
        super(client);
    }

    @Override
    public String generateAppId(RpcRequest rpcRequest) {
        return rpcRequest.getAppId();
    }

    @Override
    public String generateMethodName(RpcRequest rpcRequest) {
        return rpcRequest.getMethodName();
    }

    @Override
    public Object generateData(RpcRequest rpcRequest) {
        return rpcRequest.getRequestData();
    }
}
