package basics;

public class Tag {
	private String taf_rfid;
	private String nome;
	private int status;
		
	public Tag(String taf_rfid, String nome, int status) {
		super();
		this.taf_rfid = taf_rfid;
		this.nome = nome;
		this.status = status;
	}
	
	public String getTaf_rfid() {
		return taf_rfid;
	}
	public void setTaf_rfid(String taf_rfid) {
		this.taf_rfid = taf_rfid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
