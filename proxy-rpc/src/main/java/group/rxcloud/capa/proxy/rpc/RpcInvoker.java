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
import group.rxcloud.cloudruntimes.domain.core.invocation.HttpExtension;
import group.rxcloud.cloudruntimes.utils.TypeRef;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class RpcInvoker {

    private final CapaRpcClient client;
    private final HttpExtension httpType;

    public RpcInvoker(CapaRpcClient client) {
        this.client = Objects.requireNonNull(client);
        this.httpType = HttpExtension.POST;
    }

    public abstract String generateAppId(RpcRequest rpcRequest);

    public abstract String generateMethodName(RpcRequest rpcRequest);

    public abstract Object generateData(RpcRequest rpcRequest);

    public <T> Mono<T> invokeMethod(String appId, String methodName, Object data, Map<String, String> metadata, TypeRef<T> type) {
        return invoke(appId, methodName, data, metadata, type);
    }

    public <T> Mono<T> invokeMethod(String appId, String methodName, Object data, TypeRef<T> type) {
        return invoke(appId, methodName, data, new HashMap<>(2, 1), type);
    }

    public <T> Mono<T> invokeMethod(String appId, String methodName, Map<String, String> metadata, TypeRef<T> type) {
        return invoke(appId, methodName, null, metadata, type);
    }

    public Mono<Void> invokeMethod(String appId, String methodName, Object data, Map<String, String> metadata) {
        return invoke(appId, methodName, data, metadata, TypeRef.VOID);
    }

    public Mono<Void> invokeMethod(String appId, String methodName, Object data) {
        return invoke(appId, methodName, data, new HashMap<>(2, 1), TypeRef.VOID);
    }

    public Mono<Void> invokeMethod(String appId, String methodName, Map<String, String> metadata) {
        return invoke(appId, methodName, null, metadata, TypeRef.VOID);
    }

    public Mono<byte[]> invokeMethod(String appId, String methodName, byte[] data, Map<String, String> metadata) {
        return invoke(appId, methodName, data, metadata, TypeRef.BYTE_ARRAY);
    }

    public Mono<byte[]> invokeMethod(String appId, String methodName, byte[] data) {
        return invoke(appId, methodName, data, new HashMap<>(2, 1), TypeRef.BYTE_ARRAY);
    }

    public Mono<String> invokeMethod(String appId, String methodName, String data, Map<String, String> metadata) {
        return invoke(appId, methodName, data, metadata, TypeRef.STRING);
    }

    public Mono<String> invokeMethod(String appId, String methodName, String data) {
        return invoke(appId, methodName, data, new HashMap<>(2, 1), TypeRef.STRING);
    }

    private <T> Mono<T> invoke(String appId, String methodName, Object data, Map<String, String> metadata, TypeRef<T> type) {
        return client.invokeMethod(appId, methodName, data, httpType, metadata, type);
    }
}
