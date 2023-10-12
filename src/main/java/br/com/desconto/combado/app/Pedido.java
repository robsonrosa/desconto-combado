package br.com.desconto.combado.app;

import java.util.List;

public class Pedido {

	public static final double VALOR_FRETE = 15;

	private String formaPagamento;
	private List<Produto> produtos;
	private List<String> cupons;

	public Pedido(String formaPagamento, List<Produto> produtos, List<String> cupons) {
		this.formaPagamento = formaPagamento;
		this.produtos = produtos;
		this.cupons = cupons;
	}

	public double calcularValorTotal() {
		return 0;
	}

}