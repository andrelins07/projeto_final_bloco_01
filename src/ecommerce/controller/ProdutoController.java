package ecommerce.controller;

import java.util.List;
import ecommerce.exception.RegraDeNegocioException;
import ecommerce.model.produto.DadosAtualizacaoProduto;
import ecommerce.model.produto.Produto;
import ecommerce.service.ProdutoService;
import ecommerce.util.Cores;
import ecommerce.util.Leitura;

public class ProdutoController {

	private ProdutoService produtoService;
	private Produto produto;
	
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public void buscarProdutos() {

		String nome = Leitura.lerString("Digite o nome do produto que deseja buscar: ");

		List<Produto> produtosFiltrados = produtoService.filtrarProdutos(nome);

		produtosFiltrados.forEach(p -> p.visualizarProduto());
	}

	public void listarTodosProdutos() {

		produtoService.listarTodosProdutos().forEach(produto -> produto.visualizarProduto());
	}

	public void cadastrarProduto() {

		System.out.println("Cadastrando Produto: \n");

		String nomeProduto = Leitura.lerString("Digite o nome do produto: ");
		float preco = Leitura.lerFloat("Digite o preco do produto: ");
		int estoque = Leitura.lerInteiro("Digite a quantidade do estoque: ");
		int restricao = Leitura.lerInteiro("Produto é para +18 ?\n1 - SIM | 0 - NAO: ");

		boolean restricaoIdade;

		switch (restricao) {
		case 1 -> restricaoIdade = true;
		case 0 -> restricaoIdade = false;
		default -> throw new RegraDeNegocioException("Opcao invalida");
		}

		produtoService.cadastrarProduto(new Produto(nomeProduto, preco, estoque, restricaoIdade));

		System.out.println(Cores.TEXT_GREEN + "Produto cadastrado com sucesso!");
	}

	public void atualizarProduto() {

		int codigo = Leitura.lerInteiro("Digite o codigo do produto: ");
		produto = produtoService.buscarProdutoPorCodigo(codigo);

		produto.visualizarProduto();

		System.out.println("\nAtualizando produto: \n");

		String novoNome = Leitura.lerString("Alterando o nome do produto: ");
		float novoPreco = Leitura.lerFloat("Alterando o preco do produto: ");
		int novoEstoque = Leitura.lerInteiro("Alterando a quantidade do estoque: ");

		DadosAtualizacaoProduto dadosAtualizacao = new DadosAtualizacaoProduto(novoNome, novoPreco, novoEstoque);

		produtoService.atualizarProduto(produto, dadosAtualizacao);

		System.out.println(Cores.TEXT_GREEN + "Dados atualizados com sucesso!");
	}

	public void deletarProduto() {

		int codigo = Leitura.lerInteiro("Digite o codigo do produto que deseja deletar: ");

		produto = produtoService.buscarProdutoPorCodigo(codigo);

		produto.visualizarProduto();

		System.out.println(Cores.TEXT_RED + "\nTem certeza que deseja excluir o produto?\n" + Cores.TEXT_RESET);

		int opcao = Leitura.lerInteiro("Digite '1 - SIM' ou '2 - NAO': ");

		switch (opcao) {
		case 1 -> {
			produtoService.deletarProduto(produto);
			System.out.println(Cores.TEXT_GREEN + "\nProduto excluido com sucesso!");
		}
		case 2 -> System.out.println("Operacao cancelada!");

		default -> System.out.println(Cores.TEXT_RED + "Opcao invalida! Operacao cancelada");
		}
	}
}
