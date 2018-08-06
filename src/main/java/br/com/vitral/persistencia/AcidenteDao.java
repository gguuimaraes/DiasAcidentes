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

	private Date getDataMaisRecente() {
		return (Date) Uteis.JpaEntityManager().createNamedQuery("Acidente.findDataMaisRecente").getSingleResult();
	}

	@SuppressWarnings("unchecked")
	private List<Date> listarDatasOrdenadas() {
		return Uteis.JpaEntityManager().createNamedQuery("Acidente.findDatasOrdenadas").getResultList();
	}

	public void remover(int id) {
		Uteis.JpaEntityManager().remove(Uteis.JpaEntityManager().find(Acidente.class, id));
	}

	public long getDiasSemAcidente() {
		return TimeUnit.DAYS.convert(new Date().getTime() - getDataMaisRecente().getTime(), TimeUnit.MILLISECONDS);
	}

	public long getRecorde() {
		List<Date> datas = listarDatasOrdenadas();
		if (datas.size() == 0)
			return 0;

		long maiorDiferenca = TimeUnit.DAYS.convert(new Date().getTime() - datas.get(datas.size() - 1).getTime(),
				TimeUnit.MILLISECONDS);

		for (int i = 0; i < datas.size() - 1; i++) {
			long diferenca = TimeUnit.DAYS.convert(datas.get(i + 1).getTime() - datas.get(i).getTime(),
					TimeUnit.MILLISECONDS);

			if (diferenca > maiorDiferenca) {
				maiorDiferenca = diferenca;
			}
		}

		return maiorDiferenca;
	}
}