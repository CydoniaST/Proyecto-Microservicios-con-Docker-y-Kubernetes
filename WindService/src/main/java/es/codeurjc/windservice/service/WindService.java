
package es.codeurjc.windservice.service;

import es.codeurjc.WindService.proto.WindRequest;
import es.codeurjc.WindService.proto.WindResponse;
import io.grpc.stub.StreamObserver;

public interface WindService {
    void getWindSpeed (WindRequest request, StreamObserver<WindResponse> response);
}
