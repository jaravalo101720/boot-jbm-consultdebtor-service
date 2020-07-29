package bootjbmconsultdebtorservice.dto;

public class AddInfoResponse {

	private String estadoDeuda="";
	
	public AddInfoResponse() {
		
	}

	public AddInfoResponse(String estadoDeuda) {
		super();
		this.estadoDeuda = estadoDeuda;
	}

	public String getEstadoDeuda() {
		return estadoDeuda;
	}

	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	
	
	
}
