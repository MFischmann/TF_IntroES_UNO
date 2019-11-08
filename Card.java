public class Card{
    private String valor;
    private String cor;

    public Card(String valor, String cor){
        this.valor = valor;
        this.cor = cor;
    }

    /**
     * @return valor
     */
    public String getValor(){
        return valor;
    }

    /**
     * @return cor
     */
    public String getCor() {
        return cor;
    }
    /**
     * Metodo toString para printar resultados
     */
    @Override
    public String toString() {
        return valor+" "+cor;
    }
}