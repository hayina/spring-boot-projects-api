package api.enums;

public enum RoleEnum {
	
	
	CHARGE_SUIVI("ROLE_CHARGE_SUIVI", 1), 
	ADMIN("ROLE_ADMIN", 2), 
	SUPERVISOR_DIV("ROLE_SUPERVISOR_DIV", 3), 
	GUEST("ROLE_GUEST", 4), 
	VIP_GUEST("ROLE_VIP_GUEST", 5), 
	USER("ROLE_USER", 6);
	
	private final String key;
	private final Integer value;
	
	RoleEnum(String key, Integer value) {
	    this.key = key;
	    this.value = value;
	}
	
	public String getKey() {
	    return key;
	}
	public Integer getValue() {
	    return value;
	}

	

}
