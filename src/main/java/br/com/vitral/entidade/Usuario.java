package br.com.vitral.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "usuario")
@Entity

@NamedQueries({
	 
	@NamedQuery(name = "Usuario.findUser", query = "SELECT u FROM Usuario u WHERE u.nome = :nome AND u.senha = :senha"),
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
 
})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome", unique = true)
	private String nome;

	@Column(name = "senha")
	private String senha;

	@Column(name = "tipo")
	private TipoUsuario tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public enum TipoUsuario {
		MESTRE, CONTROLADOR;
	}
}