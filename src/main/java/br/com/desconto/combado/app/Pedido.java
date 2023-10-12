package br.com.desconto.combado.app;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

		double valorProdutos = 0;
		for (Produto produto: produtos) {
			valorProdutos += produto.getPreco();
		}

		double valorFrete = VALOR_FRETE;
		double descontoFrete = 0;
		if (valorProdutos > 299) {
			descontoFrete = VALOR_FRETE;
		}

		double descontoLojista = 0;
		Collection<List<Produto>> grupos = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getNome))
				.values();
		for (List<Produto> grupo : grupos) {
			if (grupo.size() > 100) {
				descontoLojista += grupo.get(0).getPreco() * 0.02 * grupo.size();
			}
		}

		double descontoVestuario = 0;
		List<Produto> vestuario = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getTaxonomia))
				.get("Vestuário");
		double descontoEletronicos = 0;
		List<Produto> eletronicos = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getTaxonomia))
				.get("Eletrônicos");
		double descontoCasa = 0;
		List<Produto> casa = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getTaxonomia))
				.get("Casa");
		double descontoBebidas = 0;
		List<Produto> bebidas = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getTaxonomia))
				.get("Bebidas");
		double descontoJogos = 0;
		List<Produto> jogos = produtos.stream()
				.collect(Collectors.groupingBy(Produto::getTaxonomia))
				.get("Jogos");

		List<String> cuponsOrdenados = cupons.stream().sorted().toList();
		for (String cupom : cuponsOrdenados) {
			if (cupom.equals("VES1")) {
				for (Produto produto : vestuario) {
					descontoVestuario += produto.getPreco() * 0.1;
				}
			}
			if (cupom.equals("VES2")) {
				descontoVestuario = 0;
				for (Produto produto : vestuario) {
					descontoVestuario += produto.getPreco() * 0.2;
				}
			}
			if (cupom.equals("VES3")) {
				descontoVestuario = 0;
				for (Produto produto : vestuario) {
					descontoVestuario += produto.getPreco() * 0.3;
				}
			}

			if (cupom.equals("ELE1")) {
				for (Produto produto : eletronicos) {
					descontoEletronicos += produto.getPreco() * 0.1;
				}
			}
			if (cupom.equals("ELE2")) {
				descontoEletronicos = 0;
				for (Produto produto : eletronicos) {
					descontoEletronicos += produto.getPreco() * 0.2;
				}
			}
			if (cupom.equals("ELE3")) {
				descontoEletronicos = 0;
				for (Produto produto : eletronicos) {
					descontoEletronicos += produto.getPreco() * 0.3;
				}
			}

			if (cupom.equals("CAS1")) {
				for (Produto produto : casa) {
					descontoCasa += produto.getPreco() * 0.1;
				}
			}
			if (cupom.equals("CAS2")) {
				descontoCasa = 0;
				for (Produto produto : casa) {
					descontoCasa += produto.getPreco() * 0.2;
				}
			}
			if (cupom.equals("CAS3")) {
				descontoCasa = 0;
				for (Produto produto : casa) {
					descontoCasa += produto.getPreco() * 0.3;
				}
			}

			if (cupom.equals("BEB1")) {
				for (Produto produto : bebidas) {
					descontoBebidas += produto.getPreco() * 0.1;
				}
			}
			if (cupom.equals("BEB2")) {
				descontoBebidas = 0;
				for (Produto produto : bebidas) {
					descontoBebidas += produto.getPreco() * 0.2;
				}
			}
			if (cupom.equals("BEB3")) {
				descontoBebidas = 0;
				for (Produto produto : bebidas) {
					descontoBebidas += produto.getPreco() * 0.3;
				}
			}

			if (cupom.equals("JOG1")) {
				for (Produto produto : jogos) {
					descontoJogos += produto.getPreco() * 0.1;
				}
			}
			if (cupom.equals("JOG2")) {
				descontoJogos = 0;
				for (Produto produto : jogos) {
					descontoJogos += produto.getPreco() * 0.2;
				}
			}
			if (cupom.equals("JOG3")) {
				descontoJogos = 0;
				for (Produto produto : jogos) {
					descontoJogos += produto.getPreco() * 0.3;
				}
			}
		}

		double descontoCupomTaxonomia = descontoVestuario
				+ descontoEletronicos
				+ descontoCasa
				+ descontoBebidas
				+ descontoJogos;

		double descontoVolumePedido = 0;
		double totalAntesDescontoVolumePedido = valorProdutos - descontoLojista - descontoCupomTaxonomia;
		if (totalAntesDescontoVolumePedido > 999) {
			descontoVolumePedido = totalAntesDescontoVolumePedido * 0.05;
		}
		if (totalAntesDescontoVolumePedido > 1999) {
			descontoVolumePedido = totalAntesDescontoVolumePedido * 0.1;
		}

		double descontoCupomPrimeiraCompra = 0;
		double totalAntesDescontoPrimeiraCompra = totalAntesDescontoVolumePedido - descontoVolumePedido + valorFrete - descontoFrete;
		if (cupons.contains("PrimeiraCompra")) {
			descontoCupomPrimeiraCompra = totalAntesDescontoPrimeiraCompra * 0.1;
		}

		double descontoFormaPagamento = 0;
		double totalAntesDescontoFormaPagamento = totalAntesDescontoPrimeiraCompra - descontoCupomPrimeiraCompra;
		if (formaPagamento.equals("pix") || formaPagamento.equals("boleto")) {
			descontoFormaPagamento = totalAntesDescontoFormaPagamento * 0.05;
		}

		return valorProdutos
				+ valorFrete
				- descontoFrete
				- descontoVolumePedido
				- descontoLojista
				- descontoFormaPagamento
				- descontoCupomTaxonomia
				- descontoCupomPrimeiraCompra;
	}
}