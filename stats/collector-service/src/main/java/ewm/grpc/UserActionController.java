package ewm.grpc;

import com.google.protobuf.Empty;
import ewm.grpc.stats.action.UserActionProto;
import ewm.grpc.stats.collector.UserActionControllerGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserActionController extends UserActionControllerGrpc.UserActionControllerImplBase {
    @Override
    public void collectUserAction(UserActionProto request, StreamObserver<Empty> responseObserver) {
        super.collectUserAction(request, responseObserver);
    }

}
