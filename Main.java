import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int opt = -1;
        Scanner s = new Scanner(System.in);

        try {
            new RoboOfensivo(1, "Metabee", 20, 10);
            new RoboDefensivo(2, "Optimus Prime", 30, 10);
            new RoboEquilibrado(3, "Robozao", 10, 10);
        } catch (Exception e) {

        }

        do {
            System.out.println("Campeonato de Robos");
            System.out.println("1 - Cadastrar robô");
            System.out.println("2 - Listar Robos");
            System.out.println("3 - Procurar Robo");
            System.out.println("4 - Realizar combate");
            System.out.println("5 - Recuperar energia");
            System.out.println("6 - Rodada geral");
            System.out.println("7 - Classificação");
            System.out.println("8 - Estatísticas");
            System.out.println("9 - Excluir participante");
            System.out.println("0 - Sair");
            try {
                opt = s.nextInt();
            } catch (Exception e) {
                opt = -1;
            }
            switch (opt) {
                case 0:
                    System.out.println("Adeus!");
                    break;
                case 1:
                    try {
                        System.out.println("Cadastro de Robo");
                        System.out.println("Digite o código");
                        int codigo = s.nextInt();
                        System.out.println("Digite o nome");
                        String nome = s.next();
                        System.out.println("Digite o ataque");
                        int ataque = s.nextInt();
                        System.out.println("Digite a defesa");
                        int defesa = s.nextInt();
                        System.out.println("Escolha o tipo de robô:");
                        System.out.println("1 - Ofensivo");
                        System.out.println("2 - Defensivo");
                        System.out.println("3 - Equilibrado");
                        int tipo = s.nextInt();
                        if (tipo == 1) {
                            new RoboOfensivo(codigo, nome, ataque, defesa);
                        } else if (tipo == 2) {
                            new RoboDefensivo(codigo, nome, ataque, defesa);
                        } else if (tipo == 3) {
                            new RoboEquilibrado(codigo, nome, ataque, defesa);
                        } else {
                            throw new Exception(" Tipo de robô inválido!");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao cadastrar um robo: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Lista de Robos: ");
                    for (Robo robo : Robo.listarRobos()) {
                        System.out.println(robo.imprimirRobo());
                    }
                    break;
                case 3:
                    try {
                        System.out.println(Robo.buscarRobo(s).imprimirRobo());
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar robo: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Combate!");
                    try {
                        Robo robo1 = Robo.buscarRobo(s);
                        Robo robo2 = Robo.buscarRobo(s);

                        if (!robo1.estaDisponivel() || !robo2.estaDisponivel()) {
                            throw new Exception("Um ou mais robos não estão disponíveis para a luta!");
                        }
                        robo1.registrarCombate(robo2);
                        robo2.registrarCombate(robo1);

                        Robo primeiro = null;
                        Robo segundo = null;
                        if (robo1.getPontos() == robo2.getPontos()) {
                            if (robo1.getCodigo() > robo2.getCodigo()) {
                                primeiro = robo2;
                                segundo = robo1;
                            } else {
                                primeiro = robo1;
                                segundo = robo2;
                            }
                        } else if (robo1.getPontos() > robo2.getPontos()) {
                            primeiro = robo2;
                            segundo = robo1;
                        } else {
                            primeiro = robo1;
                            segundo = robo2;
                        }

                        Combate.realizarCombate(primeiro, segundo);
                    } catch (Exception e) {
                        System.out.println("Erro no combate: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        Robo robo = Robo.buscarRobo(s);
                        System.out.println("Digite a energia a recuperar");
                        int energia = s.nextInt();

                        robo.recuperarEnergia(energia);

                        System.out.println("Energia restaurada");
                        System.out.println(robo.imprimirRobo());
                    } catch (Exception e) {
                        System.out.println("Erro ao recuperar energia: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Rodada geral");
                    ArrayList<Robo> robosOrdenados = Robo.ordenarRobos();
                    boolean ehPar = robosOrdenados.size() % 2 == 0;
                    int quantidade = ehPar ? robosOrdenados.size() : robosOrdenados.size() - 1;

                    for (int j = 0; j < quantidade; j += 2) {
                        Robo primeiro = robosOrdenados.get(j);
                        Robo segundo = robosOrdenados.get(j + 1);
                        Combate.realizarCombate(primeiro, segundo);
                    }
                    break;
                case 7:
                    System.out.println("Classificação");
                    for (Robo robo : Robo.ordenarRobos()) {
                        System.out.println(robo.imprimirRobo());
                    }
                    break;
                case 8:
                    System.out.println("Estatísticas");
                    System.out.println("Quantidade de Robos: " + Robo.listarRobos().size());
                    int totalEnergia = 0;
                    int maiorAproveitamento = 0;
                    int emRecuperacao = 0;
                    for (Robo robo : Robo.listarRobos()) {
                        totalEnergia += robo.getEnergia();
                        if (robo.aproveitamento() > maiorAproveitamento) {
                            maiorAproveitamento = robo.aproveitamento();
                        }
                        if (!robo.estaDisponivel()) {
                            emRecuperacao++;
                        }
                    }
                    System.out.println("Média de energia: " + (totalEnergia / Robo.listarRobos().size()));
                    System.out.println("Quantidade de Robos em recuperação: " + emRecuperacao);
                    System.out.println("Maior aproveitamento: " + maiorAproveitamento);
                    for (Robo robo : Robo.listarRobos()) {
                        if (robo.aproveitamento() == maiorAproveitamento) {
                            System.out.println(robo.imprimirRobo());
                        }
                    }
                    break;
                case 9:
                    try {
                        Robo robo = Robo.buscarRobo(s);
                        robo.excluirRobo();
                    } catch (Exception e) {
                        System.out.println("Erro ao remover o robo: " + e.getMessage());
                    }
                    break;
            }
        } while (opt != 0);
        s.close();
    }
}