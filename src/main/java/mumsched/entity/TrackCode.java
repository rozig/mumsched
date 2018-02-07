package mumsched.entity;

public enum TrackCode {
	MPP("CS401"),FPP("CS390");
	
	String code = "";
	
	TrackCode(String code){
		this.code = code;
	}
	
	public String getCode() {
        return code;
    }
}
