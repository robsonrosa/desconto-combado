package br.com.desconto.combado.app;

public class Produto {

	private String nome;
	private String taxonomia;
	private double preco;

	public Produto(String nome, String taxonomia, double preco) {
		this.nome = nome;
		this.taxonomia = taxonomia;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public String getTaxonomia() {
		return taxonomia;
	}

	public double getPreco() {
		return preco;
	}
}