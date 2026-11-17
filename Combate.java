public class Combate {

    Robo adversario;

    public Combate(Robo adversario) {
        this.adversario = adversario;
    }

    public static boolean realizarAtaque(Robo atacante, Robo defende, int rodada) {
        int dano = atacante.calcularAtaque() - defende.getDefesa();

        if (dano < 5) {
            dano = 5;
        }

        if (rodada % 2 == 0) {
            dano += 5;
        }

        dano = defende.reduzirDano(dano);

        defende.receberDano(dano);

        if (defende.getEnergia() == 0) {
            return false;
        }

        return true;
    }

    public static void realizarCombate(Robo primeiro, Robo segundo) {
        for (int i = 0; i < 5; i++) {
            System.out.println("Rodada " + (i + 1));
            // Primeiro Ataca
            if (!realizarAtaque(segundo, primeiro, i)) {
                break;
            }
            if (!realizarAtaque(primeiro, segundo, i)) {
                break;
            }
            System.out.println(primeiro.imprimirRobo());
            System.out.println(segundo.imprimirRobo());
        }

        if (primeiro.getEnergia() == segundo.getEnergia()) {
            System.out.println("Empate");
            primeiro.registrarEmpate();
            segundo.registrarEmpate();
        } else if (primeiro.getEnergia() > segundo.getEnergia()) {
            System.out.println(primeiro.getNome() + " venceu!");
            primeiro.registrarVitoria();
            segundo.registrarDerrota();
        } else {
            System.out.println(segundo.getNome() + " venceu!");
            segundo.registrarVitoria();
            primeiro.registrarDerrota();
        }
    }
}