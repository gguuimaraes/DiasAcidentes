package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.Acidente;
import br.com.vitral.modelo.TelaModel;
import br.com.vitral.util.Uteis;

public class TelaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	Acidente tela;
	EntityManager entityManager;

	public void salvar(TelaModel telaModel) {
		entityManager = Uteis.JpaEntityManager();
		if (telaModel.getId() == null) {
			tela = new Acidente();
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			entityManager.persist(tela);
		} else {
			tela = entityManager.find(Acidente.class, telaModel.getId());
			tela.setUrl(telaModel.getUrl());
			tela.setSegundos(telaModel.getSegundos());
			entityManager.merge(tela);
		}
	}

	public List<TelaModel> listar() {
		List<TelaModel> telasModel = new ArrayList<TelaModel>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("Tela.findAll");
		@SuppressWarnings("unchecked")
		Collection<Acidente> telas = (Collection<Acidente>) query.getResultList();
		TelaModel telaModel = null;
		for (Acidente t : telas) {
			telaModel = new TelaModel();
			telaModel.setId(t.getId());
			telaModel.setUrl(t.getUrl());
			telaModel.setSegundos(t.getSegundos());
			telasModel.add(telaModel);
		}
		return telasModel;
	}

	public void remover(int id) {
		entityManager = Uteis.JpaEntityManager();
		entityManager.remove(entityManager.find(Acidente.class, id));
	}
}