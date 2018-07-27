package br.com.vitral.modelo;

import java.io.Serializable;
import java.util.Date;

public class AcidenteModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String obs;
	private Date data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AcidenteModel [id=" + id + ", obs=" + obs + ", data=" + data + "]";
	}
}