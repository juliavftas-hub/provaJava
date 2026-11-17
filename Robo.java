
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Robo {
    
    /*Transformamos os dados que antes eram publicos em dados privados
    que fazem parte do encapsulamento */
    private int codigo;
    private String nome;
    private int ataque;
    private int defesa;
    private int energia;
    private int vitorias;
    private int derrotas;
    private ArrayList<Combate> combates;
    private int pontos;
    private String tipo;

    private static final ArrayList<Robo> robos = new ArrayList<Robo>();

    public Robo(int codigo, String nome, int ataque, int defesa) throws Exception {
        if (codigo <= 0) {
            throw new Exception("Código deve ser positivo");
        }

        for (Robo r : robos) {
            if (r.codigo == codigo) {
                throw new Exception("Código já existe, digite outro");
            }
        }

        if (nome.trim().equals("")) {
            throw new Exception("Nome não pode ser vazio");
        }

        // o ataque deverá estar entre 10 e 30;
        if (ataque < 10 || ataque > 30) {
            throw new Exception("Ataque deve ser entre 10 e 30");
        }
        //  e a defesa, entre 0 e 20
        if (defesa < 0 || defesa > 20) {
            throw new Exception("Defesa deve ser entre 0 e 20");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.energia = 100;
        this.vitorias = 0;
        this.derrotas = 0;
        this.pontos = 0;
        this.combates = new ArrayList<>();

        robos.add(this);
    }

    public boolean estaDisponivel() {
        return this.energia > 30;
    }

    public String imprimirRobo() {
        return "Codigo: " + getCodigo() + " | Nome: " + getNome() + " | Energia: " + getEnergia()
                + " | Ataque: " + getAtaque() + bonusAtaque() + " | Defesa: " + getDefesa() + bonusDefesa() + " | Pontos: " + getPontos()
                + " | Vitorias: " + getVitorias() + " | Derrotas: " + getDerrotas()
                + " | Disponível: " + (this.estaDisponivel() ? "Sim" : "Em recuperação")
                + " | Tipo: " + getTipo();
    }

    public static Robo buscarRobo(Scanner s) throws Exception {
        try {
            System.out.println("Digite o código que deseja buscar");
            int codigo = s.nextInt();

            for (Robo robo : Robo.robos) {
                if (codigo == robo.codigo) {
                    return robo;
                }
            }
            throw new Exception("Robo " + codigo + " não encontrado");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int aproveitamento() {
        //  O aproveitamento será calculado dividindo as vitórias pelo total de combates realizados e multiplicando por 100.
        return this.combates.size() != 0
                ? (this.vitorias * 100) / this.combates.size()
                : 0;
    }

    public static ArrayList<Robo> ordenarRobos() {
        ArrayList<Robo> ordenado = Robo.robos;

        int n = ordenado.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ordenado.get(j).pontos == ordenado.get(j + 1).pontos) {
                    // nesta sequência: maior número de vitórias, maior energia restante e menor código
                    if (ordenado.get(j).vitorias == ordenado.get(j + 1).vitorias) {
                        if (ordenado.get(j).energia == ordenado.get(j + 1).energia) {
                            if (ordenado.get(j).codigo == ordenado.get(j + 1).codigo) {
                                // no-op
                            }
                            if (ordenado.get(j).codigo < ordenado.get(j + 1).codigo) {
                                Robo temp = ordenado.get(j);
                                ordenado.set(j, ordenado.get(j + 1));
                                ordenado.set(j + 1, temp);
                            }
                        } else {
                            if (ordenado.get(j).energia < ordenado.get(j + 1).energia) {
                                Robo temp = ordenado.get(j);
                                ordenado.set(j, ordenado.get(j + 1));
                                ordenado.set(j + 1, temp);
                            }
                        }
                    } else {
                        if (ordenado.get(j).vitorias < ordenado.get(j + 1).vitorias) {
                            Robo temp = ordenado.get(j);
                            ordenado.set(j, ordenado.get(j + 1));
                            ordenado.set(j + 1, temp);
                        }
                    }
                } else if (ordenado.get(j).pontos < ordenado.get(j + 1).pontos) {
                    Robo temp = ordenado.get(j);
                    ordenado.set(j, ordenado.get(j + 1));
                    ordenado.set(j + 1, temp);
                }
            }
        }

        return ordenado;
    }

    /*Os métodos public servem para permitir que outras classes consultem
    os atributos que são private, também fazem parte do encapsulamento*/
 
    public int calcularAtaque() {
        return this.ataque;
    }

    public int reduzirDano(int dano) {
        return dano;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public int getAtaque() {
        return this.ataque;
    }

    public int getDefesa() {
        return this.defesa;
    }

    public int getEnergia() {
        return this.energia;
    }

    public int getPontos() {
        return this.pontos;
    }

    public int getVitorias() {
        return this.vitorias;
    }

    public int getDerrotas() {
        return this.derrotas;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void registrarVitoria() {
        this.vitorias++;
        this.pontos += 3;
    }

    public void registrarDerrota() {
        this.derrotas++;
    }

    public void registrarEmpate() {
        this.pontos++;
    }

    public String bonusDefesa() {
        return "";
    }

    public String bonusAtaque(){
        return "";
    }

    public void recuperarEnergia(int energia) throws Exception {
        if (energia <= 0 || energia % 10 !=  0) {
            throw new Exception("Energia deve ser múltipla de 10");
        }

        int quantidadePontos = energia / 10;

        if (quantidadePontos > this.pontos) {
            throw new Exception("Não há pontos suficientes para a recuperação");
        }

        if ((this.energia + energia) > 100) {
            throw new Exception("Energia do robo não pode passar 100");
        }

        this.energia += energia;
        this.pontos -=quantidadePontos;
    }

    protected void definirTipo(String tipo) {
        this.tipo = tipo;
    }

    public static ArrayList<Robo> listarRobos() {
        return new ArrayList<Robo>(robos);
    }

    public void registrarCombate(Robo adversario) {
        this.combates.add(new Combate(adversario));
    }

    public void excluirRobo() throws Exception {
        if (this.combates.size() != 0) {
            throw new Exception(this.nome + " já participou de combates");
        }

        robos.remove(this);
    }

    public void receberDano(int dano) {
        if (dano > 0) {
            this.energia -= dano;

            if (this.energia < 0) {
                this.energia = 0;
            }
        }
    }
}
