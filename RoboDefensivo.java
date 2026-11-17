public class RoboDefensivo extends Robo {

    public RoboDefensivo(int codigo, String nome, int ataque, int defesa) throws Exception {
        super(codigo, nome, ataque, defesa);
        //Método para definir qual o tipo do robo
        this.definirTipo("Defensivo");
    }

    //Aqui vamos aplicamos uma função para atribuir mais defesa ao robo
    // Polimorfismo
    @Override
    public int reduzirDano(int dano) {
        dano -= 3;

        if (dano < 2) {
            dano = 2;
        }

        return dano;
    }

    // Mensagem que mostra que o robo tem um bônus de defesa
    @Override 
    public String bonusDefesa(){
        return " Bonus defesa + 3";
    }

}
