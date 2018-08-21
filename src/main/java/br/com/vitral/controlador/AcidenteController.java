package br.com.vitral.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import br.com.vitral.modelo.AcidenteModel;
import br.com.vitral.persistencia.AcidenteDao;
import br.com.vitral.util.Uteis;

@Named(value = "acidenteController")
@SessionScoped
public class AcidenteController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AcidenteModel acidenteModel;

	@Inject
	private AcidenteDao acidenteDao;

	@Produces
	private List<AcidenteModel> acidentes;

	@PostConstruct
	public void init() {
		acidentes = acidenteDao.listar();
	}

	public void cadastrar() {
		acidenteModel = new AcidenteModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').show();");
	}

	public void salvar() {
		acidenteDao.salvar(acidenteModel);
		init();
		this.acidenteModel = new AcidenteModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').hide();");
		Uteis.messageInformation("Acidente cadastrado com sucesso");
	}

	public void excluir(AcidenteModel acidenteModel) {
		acidenteDao.remover(acidenteModel.getId());
		acidentes.remove(acidenteModel);
	}

	public List<AcidenteModel> getAcidentes() {
		return acidentes;
	}

	public void onRowEdit(RowEditEvent event) {
		acidenteDao.salvar((AcidenteModel) event.getObject());
		Uteis.messageInformation("Acidente alterado com sucesso");
	}

	public void onRowCancel() {
		Uteis.messageInformation("Operação cancelada");
	}

	public AcidenteModel getAcidenteModel() {
		return this.acidenteModel;
	}

	public void setAcidenteModel(AcidenteModel acidenteModel) {
		this.acidenteModel = acidenteModel;
	}
}