package bootjbmconsultdebtorservice.dto;

public class Response {

	private Data data;

	public Response() {
		super();
		
	}

	public Response(Data data) {
		super();
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	
	
	
	
}
