package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ProdutoRepositorio;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private CategoriaServico categoriaServico;

	public List<Produto> listarTodos(Long codigoCategoria) {
		return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
	}

	public Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria) {
		return produtoRepositorio.findByCodigoAndCategoriaCodigo(codigo, codigoCategoria);
	}
	
	public void deletar(Long codigoCategoria, Long codigoProduto) {
		Produto produto = validarProdutoExistente(codigoProduto, codigoCategoria);
		produtoRepositorio.delete(produto);
		
	}

	public Produto salvar(Long codigoCategoria, Produto produto) {
		verificarCategoriaExistente(codigoCategoria);
		verificarProdutoDuplicado(produto);
		return produtoRepositorio.save(produto);
	}
	
	

	private void verificarProdutoDuplicado(Produto produtoAtualizado) {
		Optional<Produto> produtoAtual = produtoRepositorio.findByCategoriaCodigoAndDescricao(
				produtoAtualizado.getCategoria().getCodigo(), produtoAtualizado.getDescricao());
		if (produtoAtual.isPresent() && produtoAtual.get().getCodigo() != produtoAtualizado.getCodigo()) {
			throw new RegraNegocioException(
					String.format("O produto %s informado já está cadastrado", produtoAtualizado.getDescricao()));

		}
	}

	public Produto atualizar(Long codigoCategoria, Long codigo, Produto produtoAtualizado) {
		Produto produtoSalvar = validarProdutoExistente(codigo, codigoCategoria);
		verificarCategoriaExistente(produtoAtualizado.getCategoria().getCodigo());
		verificarProdutoDuplicado(produtoAtualizado);
		BeanUtils.copyProperties(produtoAtualizado, produtoSalvar, "codigo");

		return produtoRepositorio.save(produtoAtualizado);
	}

	private Produto validarProdutoExistente(Long codigo, Long codigoCategoria) {
		Optional<Produto> produto = buscarPorCodigo(codigo, codigoCategoria);

		if (produto.isEmpty() || produto == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}
	
	protected Produto validarProdutoExistente(Long codigo) {
		Optional<Produto> produto = produtoRepositorio.findById(codigo);
		if (produto.isEmpty() || produto == null) {
			throw new RegraNegocioException(String.format("Produto de código %s não encontrado", codigo));
		}
		return produto.get();
	}
	
	protected void atualizarQuantidadeEmEstoque(Produto produto) {
		produtoRepositorio.save(produto);
	}
	

	private void verificarCategoriaExistente(Long codigo) {
		if (codigo == null) {
			throw new RegraNegocioException("A categoria não pode ser vazia");
		}

		if (categoriaServico.buscarPorCodigo(codigo).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de código %s informado não existe", codigo));
		}
	}

}
