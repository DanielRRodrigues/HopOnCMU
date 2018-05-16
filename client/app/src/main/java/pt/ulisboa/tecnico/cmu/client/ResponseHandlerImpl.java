package pt.ulisboa.tecnico.cmu.client;

import pt.ulisboa.tecnico.cmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.response.ResponseHandler;

/**
 * Created by danif on 16-May-18.
 */

public class ResponseHandlerImpl implements ResponseHandler {
    @Override
    public void helloResponseHandle(HelloResponse hr) {
        String responseMessage = hr.getMessage();
        System.out.println("Response from server: " + responseMessage);
    }

    @Override
    public void signUpResponseHandle() {

    }
}
