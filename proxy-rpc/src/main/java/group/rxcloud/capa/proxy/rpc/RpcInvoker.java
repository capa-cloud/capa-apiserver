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

import java.util.Map;
import java.util.Objects;

/**
 * The Proxy Rpc invoker.
 */
public abstract class RpcInvoker {

    private final CapaRpcClient client;
    private final HttpExtension httpType;

    /**
     * Instantiates a new Rpc invoker.
     *
     * @param client the client
     */
    RpcInvoker(CapaRpcClient client) {
        this.client = Objects.requireNonNull(client);
        this.httpType = HttpExtension.POST;
    }

    /**
     * Invoke rpc method.
     *
     * @param <T>        the type parameter
     * @param rpcRequest the rpc request
     * @param type       the type
     */
    public <T> Mono<T> invokeMethod(RpcRequest rpcRequest, TypeRef<T> type) {
        String appId = generateAppId(rpcRequest);
        String methodName = generateMethodName(rpcRequest);
        Object data = generateData(rpcRequest);
        return invokeMethod(appId, methodName, data, rpcRequest.getMetadata(), type);
    }

    /**
     * Generate app id.
     *
     * @param rpcRequest the rpc request
     */
    protected abstract String generateAppId(RpcRequest rpcRequest);

    /**
     * Generate method name.
     *
     * @param rpcRequest the rpc request
     */
    protected abstract String generateMethodName(RpcRequest rpcRequest);

    /**
     * Generate data object.
     *
     * @param rpcRequest the rpc request
     * @return the object
     */
    protected abstract Object generateData(RpcRequest rpcRequest);

    /**
     * Invoke rpc method.
     *
     * @param <T>        the type parameter
     * @param appId      the app id
     * @param methodName the method name
     * @param data       the data
     * @param metadata   the metadata
     * @param type       the type
     */
    protected <T> Mono<T> invokeMethod(String appId, String methodName, Object data, Map<String, String> metadata, TypeRef<T> type) {
        return client.invokeMethod(appId, methodName, data, httpType, metadata, type);
    }
}
