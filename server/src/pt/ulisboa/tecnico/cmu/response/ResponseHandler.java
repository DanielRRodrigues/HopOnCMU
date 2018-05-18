package pt.ulisboa.tecnico.cmu.response;

public interface ResponseHandler {

	void handle(SignUpResponse sur);

	void handle(LoginResponse sur);
}
