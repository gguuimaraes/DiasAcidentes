package br.com.vitral.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitral.entidade.Usuario;
import br.com.vitral.modelo.UsuarioModel;
import br.com.vitral.util.Uteis;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	Usuario usuario;
	transient EntityManager em;

	public Usuario validaUsuario(UsuarioModel usuarioModel) {
		try {
			Query query = Uteis.getEntityManager().createNamedQuery("Usuario.findUser");
			query.setParameter("nome", usuarioModel.getNome());
			query.setParameter("senha", usuarioModel.getSenha());
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public void salvar(UsuarioModel usuarioModel) {
		em = Uteis.getEntityManager();
		if (usuarioModel.getId() == null) {
			usuario = new Usuario();
			usuario.setNome(usuarioModel.getNome());
			usuario.setSenha(usuarioModel.getSenha());
			usuario.setTipo(usuarioModel.getTipo());
			em.persist(usuario);
		} else {
			usuario = em.find(Usuario.class, usuarioModel.getId());
			usuario.setNome(usuarioModel.getNome());
			usuario.setSenha(usuarioModel.getSenha());
			usuario.setTipo(usuarioModel.getTipo());
			em.merge(usuario);
		}
	}

	public List<UsuarioModel> listar() {
		List<UsuarioModel> usuariosModel = new ArrayList<>();
		em = Uteis.getEntityManager();
		Query query = em.createNamedQuery("Usuario.findAll");
		@SuppressWarnings("unchecked")
		Collection<Usuario> usuarios = (Collection<Usuario>) query.getResultList();
		UsuarioModel usuarioModel = null;
		for (Usuario u : usuarios) {
			usuarioModel = new UsuarioModel();
			usuarioModel.setId(u.getId());
			usuarioModel.setNome(u.getNome());
			usuarioModel.setSenha(u.getSenha());
			usuarioModel.setTipo(u.getTipo());
			usuariosModel.add(usuarioModel);
		}
		return usuariosModel;
	}
	
	public void remover(int id) {
		em = Uteis.getEntityManager();
		em.remove(em.find(Usuario.class, id));
	}
}