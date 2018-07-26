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

import br.com.vitral.modelo.TelaModel;
import br.com.vitral.persistencia.TelaDao;
import br.com.vitral.util.Uteis;

@Named(value = "telaController")
@SessionScoped
public class TelaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TelaModel telaModel;

	@Inject
	private TelaDao telaDao;

	@Produces
	private List<TelaModel> telas;

	@PostConstruct
	public void init() {
		telas = telaDao.listar();
	}

	public void cadastrar() {
		telaModel = new TelaModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').show();");
	}

	public void salvar() {
		telaDao.salvar(telaModel);
		init();
		this.telaModel = new TelaModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').hide();");
		Uteis.MensagemInfo("Tela cadastrada com sucesso");
	}

	public void excluir(TelaModel telaModel) {
		telaDao.remover(telaModel.getId());
		telas.remove(telaModel);
	}

	public List<TelaModel> getTelas() {
		return telas;
	}

	public void onRowEdit(RowEditEvent event) {
		telaDao.salvar((TelaModel) event.getObject());
		Uteis.MensagemInfo("Tela alterada com sucesso");
	}

	public void onRowCancel(RowEditEvent event) {
		Uteis.MensagemInfo("Operação cancelada");
	}

	public TelaModel getTelaModel() {
		return this.telaModel;
	}

	public void setTelaModel(TelaModel telaModel) {
		this.telaModel = telaModel;
	}
}