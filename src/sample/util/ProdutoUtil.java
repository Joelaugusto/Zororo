package sample.util;

import sample.controller.model.ProdutoController;
import sample.model.modelo.Produto;

public class ProdutoUtil {

    public ProdutoUtil(){

    }
  private final ProdutoController produtoController = new ProdutoController();

  public float lucro(Produto produto){
      return lucro(produto.getValor(),produto.getPrecoCaixa(),produto.getUnidadesPorCaixa());
  }

  public float lucroUnidade(Produto produto){
      return lucro(produto)/produto.getUnidadesPorCaixa();
  }

  public float lucro(float preco, float precoCaixa, int quantidade){
      return preco*quantidade - precoCaixa;
  }
}
