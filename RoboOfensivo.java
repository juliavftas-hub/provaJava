
public class RoboOfensivo extends Robo {

    public RoboOfensivo(int codigo, String nome, int ataque, int defesa) throws Exception {
        super(codigo, nome, ataque, defesa);
        this.definirTipo("Ofensivo");
    }
    
    //Aqui vamos aplicamos uma função para atribuir mais ataque ao robo
    // polimorfismo
    @Override
    public int calcularAtaque() {
        return this.getAtaque() + 5;
    }

    // Mensagem que mostra que o robo tem um bônus de ataque
    @Override
    public String bonusAtaque(){
        return " bonus ataque + 5";
    }
}
