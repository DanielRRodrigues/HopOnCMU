package pt.ulisboa.tecnico.cmu.response;

import java.io.Serializable;

public interface Response extends Serializable {
	void handle(ResponseHandler rh);
}
