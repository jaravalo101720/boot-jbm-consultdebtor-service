package bootjbmconsultdebtorservice.dto;

public class Data {

	private GeneralInfoResponse generalInfo;
	private AddInfoResponse addInfo;
	
	public Data() {
		super();
	}

	public Data(GeneralInfoResponse generalInfo, AddInfoResponse addInfo) {
		super();
		this.generalInfo = generalInfo;
		this.addInfo = addInfo;
	}

	public GeneralInfoResponse getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfoResponse generalInfo) {
		this.generalInfo = generalInfo;
	}

	public AddInfoResponse getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(AddInfoResponse addInfo) {
		this.addInfo = addInfo;
	}
	
	
}
