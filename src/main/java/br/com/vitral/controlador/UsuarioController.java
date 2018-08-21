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

import br.com.vitral.entidade.Usuario.TipoUsuario;
import br.com.vitral.modelo.UsuarioModel;
import br.com.vitral.persistencia.UsuarioDao;
import br.com.vitral.util.Uteis;

@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioDao usuarioDao;

	@Produces
	private List<UsuarioModel> usuarios;

	@PostConstruct
	public void init() {
		usuarios = usuarioDao.listar();
	}

	public void cadastrar() {
		usuarioModel = new UsuarioModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').show();");
	}

	public void salvar() {
		usuarioDao.salvar(usuarioModel);
		init();
		this.usuarioModel = new UsuarioModel();
		PrimeFaces.current().executeScript("PF('dialogCadastro').hide();");
		Uteis.messageInformation("Usuário cadastrado com sucesso");
	}

	public void excluir(UsuarioModel usuarioModel) {
		usuarioDao.remover(usuarioModel.getId());
		usuarios.remove(usuarioModel);
	}

	public UsuarioModel getUsuarioModel() {
		return this.usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	public TipoUsuario[] getTipos() {
		return TipoUsuario.values();
	}

	public List<UsuarioModel> getUsuarios() {
		return usuarios;
	}

	public void onRowEdit(RowEditEvent event) {
		usuarioDao.salvar((UsuarioModel) event.getObject());
		Uteis.messageInformation("Usuário alterado com sucesso");
	}

	public void onRowCancel() {
		Uteis.messageInformation("Operação cancelada");
	}
}