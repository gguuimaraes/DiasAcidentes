package br.com.vitral.controlador;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vitral.persistencia.AcidenteDao;

@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AcidenteDao acidenteDao;

	public long getDiasSemAcidentes() {
		return acidenteDao.getDiasSemAcidente();
	}

	public long getRecorde() {
		return acidenteDao.getRecorde();
	}

}