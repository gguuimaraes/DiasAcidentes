package br.com.vitral.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vitral.modelo.AcidenteModel;
import br.com.vitral.persistencia.AcidenteDao;

@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * @Produces List<AcidenteModel> telas;
	 * 
	 * @Inject AcidenteDao telaDao;
	 * 
	 * AcidenteModel telaAtual;
	 */
	@Inject
	AcidenteDao acidenteDao;

	@PostConstruct
	private void init() {
	}

	public void listener() {
		
	}

	public long getDiasSemAcidentes() {
		return acidenteDao.getDiasSemAcidente();
	}

	public long getRecorde() {
		return acidenteDao.getRecorde();
	}

}