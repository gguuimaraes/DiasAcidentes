package br.com.vitral.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vitral.modelo.TelaModel;
import br.com.vitral.persistencia.TelaDao;

@Named(value = "indexController")
@SessionScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	List<TelaModel> telas;

	@Inject
	TelaDao telaDao;

	TelaModel telaAtual;
	int posicao;

	@PostConstruct
	private void init() {
		telas = telaDao.listar();
		if (telas.size() > 0) {
			telaAtual = telas.get(0);
			posicao = 0;
		}
	}

	public void listener() {
		telas = telaDao.listar();
		if (posicao + 1 < telas.size()) {
			posicao++;
		} else {
			posicao = 0;
		}
		if (telas.size() > 0) {
			telaAtual = telas.get(posicao);
		}
	}

	public TelaModel getTelaAtual() {
		return telaAtual;
	}

	public void setTelaAtual(TelaModel telaAtual) {
		this.telaAtual = telaAtual;
	}

}