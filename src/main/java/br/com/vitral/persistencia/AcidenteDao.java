package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.Acidente;
import br.com.vitral.modelo.AcidenteModel;
import br.com.vitral.util.Uteis;

public class AcidenteDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	Acidente acidente;
	EntityManager entityManager;

	public void salvar(AcidenteModel acidenteModel) {
		entityManager = Uteis.JpaEntityManager();
		if (acidenteModel.getId() == null) {
			acidente = new Acidente();
			acidente.setObs(acidenteModel.getObs());
			acidente.setData(acidenteModel.getData());
			entityManager.persist(acidente);
		} else {
			acidente = entityManager.find(Acidente.class, acidenteModel.getId());
			acidente.setObs(acidenteModel.getObs());
			acidente.setData(acidenteModel.getData());
			entityManager.merge(acidente);
		}
	}

	public List<AcidenteModel> listar() {
		List<AcidenteModel> acidentesModel = new ArrayList<AcidenteModel>();
		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("Acidente.findAll");
		@SuppressWarnings("unchecked")
		Collection<Acidente> acidentes = (Collection<Acidente>) query.getResultList();
		AcidenteModel acidenteModel = null;
		for (Acidente a : acidentes) {
			acidenteModel = new AcidenteModel();
			acidenteModel.setId(a.getId());
			acidenteModel.setObs(a.getObs());
			acidenteModel.setData(a.getData());
			acidentesModel.add(acidenteModel);
		}
		return acidentesModel;
	}

	public void remover(int id) {
		entityManager = Uteis.JpaEntityManager();
		entityManager.remove(entityManager.find(Acidente.class, id));
	}

	public long getDiasSemAcidente() {
		return TimeUnit.DAYS.convert(
				new Date().getTime() - ((Date) Uteis.JpaEntityManager()
						.createNamedQuery("Acidente.findAcidenteMaisRecente").getSingleResult()).getTime(),
				TimeUnit.MILLISECONDS);
	}
	
	public long getRecorde() {
		return TimeUnit.DAYS.convert(
				new Date().getTime() - ((Date) Uteis.JpaEntityManager()
						.createNamedQuery("Acidente.findAcidenteMaisAntigo").getSingleResult()).getTime(),
				TimeUnit.MILLISECONDS);
	}
}