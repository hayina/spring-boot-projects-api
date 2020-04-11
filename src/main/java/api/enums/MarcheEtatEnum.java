package api.enums;

public enum MarcheEtatEnum {

	
	adjudication(1), approbation(2), realisation(3), resilie(4), acheve(5);
	
	public Integer value;
	
	private MarcheEtatEnum(Integer value) {
		this.value = value;
	}
	

}
