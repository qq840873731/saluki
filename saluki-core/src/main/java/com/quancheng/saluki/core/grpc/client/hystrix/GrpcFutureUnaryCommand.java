/*
 * Copyright 1999-2012 DianRong.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quancheng.saluki.core.grpc.client.hystrix;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.Message;
import com.quancheng.saluki.core.common.GrpcURL;
import com.quancheng.saluki.core.grpc.client.GrpcAsyncCall;

import io.grpc.MethodDescriptor;

/**
 * @author liushiming 2017年4月26日 下午5:21:49
 * @version $Id: UnaryCommand.java, v 0.0.1 2017年4月26日 下午5:21:49 liushiming
 */
public class GrpcFutureUnaryCommand extends GrpcHystrixCommand {

    private final GrpcAsyncCall                      grpcAsyncCall;

    private final Message                            request;

    private final MethodDescriptor<Message, Message> methodDesc;

    private final int                                timeOut;

    public GrpcFutureUnaryCommand(GrpcAsyncCall grpcAsyncCall, GrpcURL refUrl,
                                  MethodDescriptor<Message, Message> methodDesc, Message request, int timeOut){
        super(refUrl, methodDesc);
        this.grpcAsyncCall = grpcAsyncCall;
        this.methodDesc = methodDesc;
        this.request = request;
        this.timeOut = timeOut;
    }

    @Override
    protected Message run() throws Exception {
        return grpcAsyncCall.unaryFuture(request, methodDesc).get(timeOut, TimeUnit.MILLISECONDS);
    }
}