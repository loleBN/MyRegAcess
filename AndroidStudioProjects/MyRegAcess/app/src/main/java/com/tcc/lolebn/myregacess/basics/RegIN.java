package com.tcc.lolebn.myregacess.basics;

public class RegIN {
	private String tag_rfid;
	private String data_hora;
	private int status;
	private String nome;
	private int regId;
		

	public RegIN(int regId,String taf_rfid, String nome, String data_hora, int status) {
		super();
		this.regId = regId;
		this.tag_rfid = taf_rfid;
		this.nome = nome;
		this.data_hora = data_hora;
		this.status = status;
	}
	
	public String getTag_rfid() {
		return tag_rfid;
	}
	public void setTag_rfid(String taf_rfid) {
		this.tag_rfid = taf_rfid;
	}
	public String getData_hora() {
		return data_hora;
	}
	public void setData_hora(String data_hora) {
		this.data_hora = data_hora;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getRegId() {
		return regId;
	}
	public void setRegId(int regId) {
		this.regId = regId;
	}
	
	@Override
	public String toString() {
		if (regId==-4){
			return "<html> &emsp Nome: " + nome+"&emsp&emsp&emsp&emsp&emsp entrada: "+Utils.getSoHorario(data_hora)+"<br>"
				+ "-----------------------------------------------------------------------" + " </span></html>";
		}
		if (regId == -1) {
			return "Nenhum registro realizado";
		} 
		if (regId == -3) {
			return "Erro ao carregar dados do servidor!";
		} else {
			if (status==1)
				return "<html>Nome: " + nome  + "<br>"
						+ Utils.getSoData(data_hora) + "<br>"
						+ "entrada: " + Utils.getSoHorario(data_hora) + "<br>" 
						+ "<br>" + " </span></html>";
						
			return "<html>Nome: " + nome  + "<br>"
					+ Utils.getSoData(data_hora) + "<br>"
					+ "saida: " + Utils.getSoHorario(data_hora) + "<br>" 
					+ "<br>" + " </span></html>";
		}
	}
	
}
