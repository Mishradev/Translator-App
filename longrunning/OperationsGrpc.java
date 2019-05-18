package com.google.longrunning;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Manages long-running operations with an API service.
 * When an API method normally takes long time to complete, it can be designed
 * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
 * interface to receive the real response asynchronously by polling the
 * operation resource, or using `google.watcher.v1.Watcher` interface to watch
 * the response, or pass the operation resource to another API (such as Google
 * Cloud Pub/Sub API) to receive the response.  Any API service that returns
 * long-running operations should implement the `Operations` interface so
 * developers can have a consistent client experience.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.1)",
    comments = "Source: google/longrunning/operations.proto")
public final class OperationsGrpc {

  private OperationsGrpc() {}

  public static final String SERVICE_NAME = "google.longrunning.Operations";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> getGetOperationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOperation",
      requestType = com.google.longrunning.GetOperationRequest.class,
      responseType = com.google.longrunning.Operation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> getGetOperationMethod() {
    io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest, com.google.longrunning.Operation> getGetOperationMethod;
    if ((getGetOperationMethod = OperationsGrpc.getGetOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getGetOperationMethod = OperationsGrpc.getGetOperationMethod) == null) {
          OperationsGrpc.getGetOperationMethod = getGetOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.GetOperationRequest, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "GetOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.GetOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getGetOperationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> getListOperationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListOperations",
      requestType = com.google.longrunning.ListOperationsRequest.class,
      responseType = com.google.longrunning.ListOperationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> getListOperationsMethod() {
    io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest, com.google.longrunning.ListOperationsResponse> getListOperationsMethod;
    if ((getListOperationsMethod = OperationsGrpc.getListOperationsMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getListOperationsMethod = OperationsGrpc.getListOperationsMethod) == null) {
          OperationsGrpc.getListOperationsMethod = getListOperationsMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.ListOperationsRequest, com.google.longrunning.ListOperationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "ListOperations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.ListOperationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.ListOperationsResponse.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getListOperationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> getCancelOperationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelOperation",
      requestType = com.google.longrunning.CancelOperationRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> getCancelOperationMethod() {
    io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest, com.google.protobuf.Empty> getCancelOperationMethod;
    if ((getCancelOperationMethod = OperationsGrpc.getCancelOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getCancelOperationMethod = OperationsGrpc.getCancelOperationMethod) == null) {
          OperationsGrpc.getCancelOperationMethod = getCancelOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.CancelOperationRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "CancelOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.CancelOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getCancelOperationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> getDeleteOperationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteOperation",
      requestType = com.google.longrunning.DeleteOperationRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> getDeleteOperationMethod() {
    io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest, com.google.protobuf.Empty> getDeleteOperationMethod;
    if ((getDeleteOperationMethod = OperationsGrpc.getDeleteOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getDeleteOperationMethod = OperationsGrpc.getDeleteOperationMethod) == null) {
          OperationsGrpc.getDeleteOperationMethod = getDeleteOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.DeleteOperationRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "DeleteOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.longrunning.DeleteOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getDeleteOperationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OperationsStub newStub(io.grpc.Channel channel) {
    return new OperationsStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OperationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new OperationsBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OperationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new OperationsFutureStub(channel);
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or using `google.watcher.v1.Watcher` interface to watch
   * the response, or pass the operation resource to another API (such as Google
   * Cloud Pub/Sub API) to receive the response.  Any API service that returns
   * long-running operations should implement the `Operations` interface so
   * developers can have a consistent client experience.
   * </pre>
   */
  public static abstract class OperationsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients may use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public void getOperation(com.google.longrunning.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(getGetOperationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public void listOperations(com.google.longrunning.ListOperationsRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListOperationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients may use
     * [Operations.GetOperation] or other methods to check whether the
     * cancellation succeeded or the operation completed despite cancellation.
     * </pre>
     */
    public void cancelOperation(com.google.longrunning.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getCancelOperationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Deletes a long-running operation.  It indicates the client is no longer
     * interested in the operation result. It does not cancel the operation.
     * </pre>
     */
    public void deleteOperation(com.google.longrunning.DeleteOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteOperationMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetOperationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.GetOperationRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_GET_OPERATION)))
          .addMethod(
            getListOperationsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.ListOperationsRequest,
                com.google.longrunning.ListOperationsResponse>(
                  this, METHODID_LIST_OPERATIONS)))
          .addMethod(
            getCancelOperationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.CancelOperationRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_CANCEL_OPERATION)))
          .addMethod(
            getDeleteOperationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.DeleteOperationRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_OPERATION)))
          .build();
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or using `google.watcher.v1.Watcher` interface to watch
   * the response, or pass the operation resource to another API (such as Google
   * Cloud Pub/Sub API) to receive the response.  Any API service that returns
   * long-running operations should implement the `Operations` interface so
   * developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsStub extends io.grpc.stub.AbstractStub<OperationsStub> {
    private OperationsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients may use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public void getOperation(com.google.longrunning.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOperationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public void listOperations(com.google.longrunning.ListOperationsRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListOperationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients may use
     * [Operations.GetOperation] or other methods to check whether the
     * cancellation succeeded or the operation completed despite cancellation.
     * </pre>
     */
    public void cancelOperation(com.google.longrunning.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCancelOperationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deletes a long-running operation.  It indicates the client is no longer
     * interested in the operation result. It does not cancel the operation.
     * </pre>
     */
    public void deleteOperation(com.google.longrunning.DeleteOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteOperationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or using `google.watcher.v1.Watcher` interface to watch
   * the response, or pass the operation resource to another API (such as Google
   * Cloud Pub/Sub API) to receive the response.  Any API service that returns
   * long-running operations should implement the `Operations` interface so
   * developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsBlockingStub extends io.grpc.stub.AbstractStub<OperationsBlockingStub> {
    private OperationsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients may use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public com.google.longrunning.Operation getOperation(com.google.longrunning.GetOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOperationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public com.google.longrunning.ListOperationsResponse listOperations(com.google.longrunning.ListOperationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListOperationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients may use
     * [Operations.GetOperation] or other methods to check whether the
     * cancellation succeeded or the operation completed despite cancellation.
     * </pre>
     */
    public com.google.protobuf.Empty cancelOperation(com.google.longrunning.CancelOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getCancelOperationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deletes a long-running operation.  It indicates the client is no longer
     * interested in the operation result. It does not cancel the operation.
     * </pre>
     */
    public com.google.protobuf.Empty deleteOperation(com.google.longrunning.DeleteOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteOperationMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or using `google.watcher.v1.Watcher` interface to watch
   * the response, or pass the operation resource to another API (such as Google
   * Cloud Pub/Sub API) to receive the response.  Any API service that returns
   * long-running operations should implement the `Operations` interface so
   * developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsFutureStub extends io.grpc.stub.AbstractStub<OperationsFutureStub> {
    private OperationsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients may use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> getOperation(
        com.google.longrunning.GetOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOperationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.ListOperationsResponse> listOperations(
        com.google.longrunning.ListOperationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListOperationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients may use
     * [Operations.GetOperation] or other methods to check whether the
     * cancellation succeeded or the operation completed despite cancellation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> cancelOperation(
        com.google.longrunning.CancelOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCancelOperationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deletes a long-running operation.  It indicates the client is no longer
     * interested in the operation result. It does not cancel the operation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteOperation(
        com.google.longrunning.DeleteOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteOperationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OPERATION = 0;
  private static final int METHODID_LIST_OPERATIONS = 1;
  private static final int METHODID_CANCEL_OPERATION = 2;
  private static final int METHODID_DELETE_OPERATION = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OperationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OperationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OPERATION:
          serviceImpl.getOperation((com.google.longrunning.GetOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_LIST_OPERATIONS:
          serviceImpl.listOperations((com.google.longrunning.ListOperationsRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse>) responseObserver);
          break;
        case METHODID_CANCEL_OPERATION:
          serviceImpl.cancelOperation((com.google.longrunning.CancelOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_DELETE_OPERATION:
          serviceImpl.deleteOperation((com.google.longrunning.DeleteOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OperationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getGetOperationMethod())
              .addMethod(getListOperationsMethod())
              .addMethod(getCancelOperationMethod())
              .addMethod(getDeleteOperationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
