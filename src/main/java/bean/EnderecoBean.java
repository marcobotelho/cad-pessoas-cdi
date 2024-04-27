package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.DaoEndereco;
import dao.DaoPessoa;
import model.Endereco;
import model.Pessoa;
import model.ViaCep;
import util.Mensagem;
import util.ViaCepService;

@Named
@ViewScoped
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pessoaId = null;

	@Inject
	private Pessoa pessoa;

	@Inject
	private DaoPessoa daoPessoa;

	@Inject
	private Endereco endereco;

	@Inject
	private DaoEndereco daoEndereco;

	private List<Endereco> enderecos = new ArrayList<>();

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	@PostConstruct
	private void init() {

		if (pessoaId == null) {
			pessoaId = Long.parseLong(
					FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pessoaId"));
		}

		pessoa = daoPessoa.buscarId(Pessoa.class, pessoaId);

		endereco.setPessoa(pessoa);

		enderecos = daoEndereco.pesquisarPorPessoaId(pessoaId);
	}

	public String salvar() {
		try {
			// endereco.setPessoa(pessoa);
			if (endereco.getId() == null) {
				daoEndereco.salvar(endereco);
			} else {
				daoEndereco.atualizar(endereco);
			}
			endereco = new Endereco();
			endereco.setPessoa(pessoa);
			this.enderecos = daoEndereco.pesquisarPorPessoaId(pessoaId);
			Mensagem.info("Endereço salvo com sucesso!");
		} catch (Exception e) {
			Mensagem.error("Erro ao salvar endereço! " + e.getMessage());
		}
		return "";
	}

	public String limpar() {
		endereco = new Endereco();
		endereco.setPessoa(pessoa);
		return "endereco.jsf?pessoaId=" + pessoaId + "&faces-redirect=true";
	}

	public void excluir() {
		try {
			if (daoEndereco.buscarId(Endereco.class, endereco.getId()) == null) {
				throw new Exception("Endereço não encontrado");
			}
			daoEndereco.excluir(endereco);
			endereco = new Endereco();
			endereco.setPessoa(pessoa);
			enderecos = daoEndereco.pesquisarPorPessoaId(pessoaId);
			Mensagem.info("Endereço excluído com sucesso!");
		} catch (Exception e) {
			Mensagem.error("Erro ao excluír endereço! " + e.getMessage());
		}
	}

	public void excluir(Long id) {
		try {
			endereco = daoEndereco.buscarId(Endereco.class, id);
			if (endereco == null) {
				throw new Exception("Endereço não encontrado");
			}
			daoEndereco.excluir(endereco);
			endereco = new Endereco();
			endereco.setPessoa(pessoa);
			enderecos = daoEndereco.pesquisarPorPessoaId(pessoaId);
			Mensagem.info("Endereço excluído com sucesso!");
		} catch (Exception e) {
			Mensagem.error("Erro ao excluír endereço! " + e.getMessage());
		}
	}

	public String editar(Endereco endereco) {
		this.endereco = endereco;
		return "";
	}

	public void buscaEnderecoByCep() {
		ViaCep viaCep = ViaCepService.buscaCep(endereco.getCep());

		if (viaCep != null) {
			endereco.setCep(viaCep.getCep());
			endereco.setLogradouro(viaCep.getLogradouro());
			endereco.setEstado(viaCep.getUf());
			endereco.setCidade(viaCep.getLocalidade());
			endereco.setBairro(viaCep.getBairro());
		}
	}

}
