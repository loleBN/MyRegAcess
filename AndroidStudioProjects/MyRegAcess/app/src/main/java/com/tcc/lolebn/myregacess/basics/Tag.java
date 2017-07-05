package com.tcc.lolebn.myregacess.basics;

import java.util.ArrayList;
import java.util.Date;

public class Tag {
	private String tag_rfid;
	private String nome;
	private int frequencia_semanal;
	private int frequencia_mensal;
	private ArrayList<RegIN> registros;

	public Tag(String tag_rfid, String nome) {
		super();
		this.tag_rfid = tag_rfid;
		this.nome = nome;
		this.frequencia_semanal = 0;
		this.frequencia_mensal = 0;
		this.registros = new ArrayList<RegIN>();
	}

	public String getTag_rfid() {
		return tag_rfid;
	}
	public void setTaf_rfid(String taf_rfid) {
		this.tag_rfid = taf_rfid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getFrequencia_semanal() {
		return frequencia_semanal;
	}

	public void setFrequencia_semanal(ArrayList<String> datas) {
		double horas = 0.0;
		if ((datas.size()%2)==0){
			for (int i = 0; i < datas.size(); i=i+2) {
				horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}else{
			for (int i = 0; i < datas.size(); i=i+2) {
				if(i==datas.size()-1)
					horas=horas+Utils.getDiferencaData(datas.get(i), Utils.convertDateToStringBR(new Date()));
				else
					horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}
		this.frequencia_semanal = (int) horas;
	}

	public int getFrequencia_mensal() {
		return frequencia_mensal;
	}

	public void setFrequencia_mensal(ArrayList<String> datas) {
		double horas = 0.0;
		if ((datas.size()%2)==0){
			for (int i = 0; i < datas.size(); i=i+2) {
				horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}else{
			for (int i = 0; i < datas.size(); i=i+2) {
				if(i==datas.size()-1)
					horas=horas+Utils.getDiferencaData(datas.get(i), Utils.convertDateToStringBR(new Date()));
				else
					horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}

		this.frequencia_mensal = (int) horas;
	}

	public ArrayList<RegIN> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<RegIN> registros) {
		this.registros = registros;
	}

	@Override
	public String toString() {
		if (frequencia_semanal!=0){
			return "<html> &emsp " + nome+"&emsp&emsp&emsp&emsp CH: "+frequencia_semanal+"h<br>"
					+ " </span></html>";
		}else if (frequencia_mensal!=0){
			return "<html> &emsp " + nome+"&emsp&emsp&emsp&emsp CH: "+frequencia_mensal+"h<br>"
					+ " </span></html>";
		}
		return "sem registro de carga horaria";
	}
}
