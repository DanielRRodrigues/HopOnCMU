package pt.ulisboa.tecnico.cmu.response;

public interface ResponseHandler {
    void handle(HelloResponse hr);
    void handle(SignUpResponse sur);
}

