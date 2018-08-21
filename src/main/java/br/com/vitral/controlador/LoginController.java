package br.com.vitral.controlador;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.vitral.entidade.Usuario;
import br.com.vitral.entidade.Usuario.TipoUsuario;
import br.com.vitral.modelo.UsuarioModel;
import br.com.vitral.persistencia.UsuarioDao;
import br.com.vitral.util.Uteis;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioDao usuarioDao;

	public String login() {
		if (StringUtils.isEmpty(usuarioModel.getNome()) || StringUtils.isBlank(usuarioModel.getNome())) {
			Uteis.message("Favor informar o nome de usuário!");
			return null;
		} else if (StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())) {
			Uteis.message("Favor informar a senha!");
			return null;
		} else {
			Usuario usuario = usuarioDao.validaUsuario(usuarioModel);
			if (usuario != null) {
				usuarioModel.setId(usuario.getId());
				usuarioModel.setNome(usuario.getNome());
				usuarioModel.setTipo(usuario.getTipo());
				usuarioModel.setSenha(null);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);
				return "sistema/home?faces-redirect=true";
			} else {
				Uteis.message("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public UsuarioModel getUsuarioAutenticado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioModel) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	public TipoUsuario[] getTipos() {
		return TipoUsuario.values();
	}

	public UsuarioModel getUsuarioModel() {
		return this.usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
}