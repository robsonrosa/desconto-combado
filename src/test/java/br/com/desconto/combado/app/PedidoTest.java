package br.com.desconto.combado.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PedidoTest {

	private static final Produto calca = new Produto("Calça Jeans", "Vestuário", 300.00);
	private static final Produto blusinha = new Produto("Blusinha", "Vestuário", 50.00);
	private static final Produto notebook = new Produto("Notebook Azer", "Eletrônicos", 2750.00);
	private static final Produto celular = new Produto("Celular Xaiane", "Eletrônicos", 1999.00);
	private static final Produto raspberry = new Produto("Raspberry", "Eletrônicos", 999.00);
	private static final Produto quadro = new Produto("Quadro Decorativo", "Casa", 39.00);
	private static final Produto prendedor = new Produto("Prendedor de Roupa", "Casa", 9.00);
	private static final Produto cabide = new Produto("Cabide", "Casa", 1.00);
	private static final Produto cerveja = new Produto("Cerveja", "Bebidas", 5.00);
	private static final Produto vinho = new Produto("Vinho", "Bebidas", 79.00);
	private static final Produto dixit = new Produto("Dixit", "Jogos", 179.00);
	private static final Produto coup = new Produto("Coup", "Jogos", 99.00);

	@Test
	void pedidoSemDescontoAlgum() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(cerveja, vinho);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco() + vinho.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 99,00
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoFrete() {

		String formaPagamento = "crédito";
		List<Produto> produtos = List.of(calca);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = calca.getPreco();

		double descontoFrete = Pedido.VALOR_FRETE;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 300,00
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@ParameterizedTest
	@ValueSource(strings = { "boleto", "pix" })
	void pedidoComDescontoParaPagamentoBoletoOuPix(String formaPagamento) {
		;
		List<Produto> produtos = List.of(cerveja, vinho);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco()
				+ vinho.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = (valorProdutos + valorFrete - descontoFrete) * 0.05;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 94,05
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoLojista() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = Collections.nCopies(101, cabide);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = 101 * cabide.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = valorProdutos * 0.02;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 113,98
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoSemDescontoVolumePedidoCincoPorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = Collections.nCopies(1, raspberry);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = raspberry.getPreco();

		double descontoFrete = Pedido.VALOR_FRETE;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 999,00
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoVolumePedidoCincoPorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = Collections.nCopies(1, celular);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = celular.getPreco();

		double descontoFrete = Pedido.VALOR_FRETE;
		double descontoVolumePedido = valorProdutos * 0.05;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 1.899,05
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoVolumePedidoDezPorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(celular, prendedor);
		List<String> cupons = List.of();
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = celular.getPreco()
				+ prendedor.getPreco();

		double descontoFrete = Pedido.VALOR_FRETE;
		double descontoVolumePedido = valorProdutos * 0.1;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 1.807,02
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoPrimeiraCompra() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(cerveja, vinho);
		List<String> cupons = List.of("PrimeiraCompra");
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco()
				+ vinho.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = 0;
		double descontoCupomPrimeiraCompra = (valorProdutos + valorFrete) * 0.1;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 89,10
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoTaxonomiaDezPorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(cerveja, blusinha);
		List<String> cupons = List.of("BEB1");
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco()
				+ blusinha.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = cerveja.getPreco() * 0.1;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 69,50
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoTaxonomiaVintePorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(cerveja, blusinha);
		List<String> cupons = List.of("BEB1", "BEB2");
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco()
				+ blusinha.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = cerveja.getPreco() * 0.2;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 69,00
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComDescontoTaxonomiaTrintaPorcento() {
		String formaPagamento = "bitcoin";
		List<Produto> produtos = List.of(cerveja, blusinha);
		List<String> cupons = List.of("BEB1", "BEB2", "BEB3");
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = cerveja.getPreco()
				+ blusinha.getPreco();

		double descontoFrete = 0;
		double descontoVolumePedido = 0;
		double descontoLojista = 0;
		double descontoFormaPagamento = 0;
		double descontoCupomTaxonomia = cerveja.getPreco() * 0.3;
		double descontoCupomPrimeiraCompra = 0;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		// R$ 68,50
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	@Test
	void pedidoComTodosDescontosAoMesmoTempo() {
		String formaPagamento = "pix";
		List<Produto> produtos = new ArrayList<>();
		produtos.add(calca);
		produtos.add(quadro);
		produtos.add(notebook);
		produtos.addAll(Collections.nCopies(101, coup));
		produtos.addAll(Collections.nCopies(101, dixit));
		List<String> cupons = List.of("ELE1", "CAS2", "JOG3", "PrimeiraCompra");
		Pedido pedido = new Pedido(formaPagamento, produtos, cupons);

		double valorFrete = Pedido.VALOR_FRETE;
		double valorProdutos = // 31167
				  calca.getPreco()
				+ quadro.getPreco()
				+ notebook.getPreco()
				+ 101 * coup.getPreco()
				+ 101 * dixit.getPreco();

		double descontoFrete = Pedido.VALOR_FRETE;
		double descontoLojista = // 561.56
				  101 * coup.getPreco() * 0.02
				+ 101 * dixit.getPreco() * 0.02;
		double descontoCupomTaxonomia = // 8706.2
				  notebook.getPreco() * 0.1
			  	+ quadro.getPreco() * 0.2
			    + 101 * coup.getPreco() * 0.3
				+ 101 * dixit.getPreco() * 0.3;
		double descontoVolumePedido = // 2189.924
				 (valorProdutos
				- descontoLojista
				- descontoCupomTaxonomia)
				* 0.1;
		double descontoCupomPrimeiraCompra = // 1970.9316
				( valorProdutos
				- descontoLojista
				- descontoCupomTaxonomia
				- descontoVolumePedido)
				* 0.1;
		double descontoFormaPagamento = // 886.91922
				( valorProdutos
				- descontoLojista
				- descontoCupomTaxonomia
				- descontoVolumePedido
				- descontoCupomPrimeiraCompra)
				* 0.05;
		double totalEsperado = valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;

		assertValorMonetario(31167, valorProdutos);
		assertValorMonetario(15, valorFrete);
		assertValorMonetario(15, descontoFrete);
		assertValorMonetario(561.56, descontoLojista);
		assertValorMonetario(8706.2, descontoCupomTaxonomia);
		assertValorMonetario(2189.92, descontoVolumePedido);
		assertValorMonetario(1970.93, descontoCupomPrimeiraCompra);
		assertValorMonetario(886.92, descontoFormaPagamento);

		// R$ 16.851,47 | 16851.46518
		assertValorMonetario(totalEsperado, pedido.calcularValorTotal());
	}

	private static void assertValorMonetario(double esperado, double atual) {
		double esperadoArredondado = Math.round(esperado * 100.0) / 100.0;
		double atualArredondado = Math.round(atual * 100.0) / 100.0;
		Assertions.assertEquals(esperadoArredondado, atualArredondado);
	}
}