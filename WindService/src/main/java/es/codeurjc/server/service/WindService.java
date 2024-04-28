
package es.codeurjc.server.service;


import es.codeurjc.ProtoService.proto.WindRequest;
import es.codeurjc.ProtoService.proto.WindResponse;
import io.grpc.stub.StreamObserver;

public interface WindService {
    void getWindSpeed (WindRequest request, StreamObserver<WindResponse> response);
}
