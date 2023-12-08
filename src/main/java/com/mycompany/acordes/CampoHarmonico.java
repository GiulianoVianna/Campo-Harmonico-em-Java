package com.mycompany.acordes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A classe CampoHarmonico representa um programa que gera o campo harmônico de
 * uma tonalidade. O campo harmônico é uma sequência de acordes que são
 * construídos a partir de uma escala musical. O programa solicita ao usuário
 * que insira a tônica e o tipo de escala, e em seguida gera e imprime o campo
 * harmônico correspondente.
 */
public class CampoHarmonico {

    private static final Map<String, List<String>> escalasMaiores = new HashMap<>();
    private static final Map<String, String> relativasMaiores = new HashMap<>();
    private static final String[] tiposDeAcordesMaiores = {"M", "m", "m", "M", "M", "m", "dim"};
    private static final String[] tiposDeAcordesMenores = {"m", "dim", "M", "m", "m", "M", "M"};

    static {
        inicializarEscalasMaiores();
        inicializarRelativasMaiores();
    }

    /**
     * O método principal é o ponto de entrada do programa. Ele solicita ao
     * usuário que insira a tônica e o tipo de escala, gera o campo harmônico
     * com base na entrada e o imprime.
     *
     * @param args os argumentos da linha de comando
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a tônica (ex: C, D#, Ab): ");
        String tonica = normalizarNota(scanner.nextLine());

        System.out.print("Escolha a escala (Maior/Menor): ");
        String tipoDeEscala = scanner.nextLine().toLowerCase();

        List<String> campoHarmonico = gerarCampoHarmonico(tonica, tipoDeEscala);
        System.out.println("Campo Harmônico de " + tonica + " " + tipoDeEscala + ": " + campoHarmonico);
    }

    /**
     * Normaliza a nota para garantir consistência na representação das notas
     * bemóis.
     *
     * @param nota a nota a ser normalizada
     * @return a nota normalizada
     */
    private static String normalizarNota(String nota) {
        // Mapa de enarmonias
        Map<String, String> enarmonias = new HashMap<>();
        enarmonias.put("Cb", "B");
        enarmonias.put("Db", "C#");
        enarmonias.put("Eb", "D#");
        enarmonias.put("Fb", "E");
        enarmonias.put("Gb", "F#");
        enarmonias.put("Ab", "G#");
        enarmonias.put("Bb", "A#");
        enarmonias.put("E#", "F");
        enarmonias.put("B#", "C");

        String notaNormalizada = nota;

        // Verifica se a nota é uma enarmonia e a normaliza
        if (enarmonias.containsKey(nota)) {
            notaNormalizada = enarmonias.get(nota);
        } else if (nota.length() > 1 && nota.charAt(1) == 'b') {
            // Mantém o 'b' em minúsculas para notas com bemol.
            notaNormalizada = nota.substring(0, 1).toUpperCase() + "b";
        } else {
            // Converte toda a nota para maiúsculas se não for um bemol.
            notaNormalizada = nota.toUpperCase();
        }

        return notaNormalizada;
    }

    /**
     * Gera um campo harmônico com base na tônica e tipo de escala fornecidos.
     *
     * @param tonica A nota tônica do campo harmônico.
     * @param tipoDeEscala O tipo de escala (maior ou menor) para gerar o campo
     * harmônico.
     * @return A lista de acordes no campo harmônico gerado.
     */
    public static List<String> gerarCampoHarmonico(String tonica, String tipoDeEscala) {
        String relativaMaior = determinarRelativaMaior(tonica);
        List<String> escala = escalasMaiores.get(relativaMaior);

        int startIndex = escala.indexOf(tonica);
        List<String> escalaEscolhida = Arrays.asList(
                escala.get(startIndex % 7),
                escala.get((startIndex + 1) % 7),
                escala.get((startIndex + 2) % 7),
                escala.get((startIndex + 3) % 7),
                escala.get((startIndex + 4) % 7),
                escala.get((startIndex + 5) % 7),
                escala.get((startIndex + 6) % 7)
        );

        String[] tiposDeAcordes = tipoDeEscala.equals("menor") ? tiposDeAcordesMenores : tiposDeAcordesMaiores;

        for (int i = 0; i < escalaEscolhida.size(); i++) {
            escalaEscolhida.set(i, escalaEscolhida.get(i) + tiposDeAcordes[i]);
        }

        return escalaEscolhida;
    }

    /**
     * Determina a tonalidade relativa maior de uma tonalidade dada.
     *
     * @param tonica a tonalidade para a qual se deseja determinar a relativa
     * maior
     * @return a tonalidade relativa maior da tonalidade dada, ou "C" se não for
     * encontrada
     */
    private static String determinarRelativaMaior(String tonica) {
        return relativasMaiores.getOrDefault(tonica, "C");
    }

    /**
     * Inicializa as escalas maiores.
     *
     * Este método preenche o mapa 'escalasMaiores' com as escalas maiores e
     * suas respectivas notas. Cada escala é representada por uma chave (tom) e
     * uma lista de notas.
     */
    private static void inicializarEscalasMaiores() {
        escalasMaiores.put("C", Arrays.asList("C", "D", "E", "F", "G", "A", "B"));
        escalasMaiores.put("G", Arrays.asList("G", "A", "B", "C", "D", "E", "F#"));
        escalasMaiores.put("D", Arrays.asList("D", "E", "F#", "G", "A", "B", "C#"));
        escalasMaiores.put("A", Arrays.asList("A", "B", "C#", "D", "E", "F#", "G#"));
        escalasMaiores.put("E", Arrays.asList("E", "F#", "G#", "A", "B", "C#", "D#"));
        escalasMaiores.put("B", Arrays.asList("B", "C#", "D#", "E", "F#", "G#", "A#"));
        escalasMaiores.put("F#", Arrays.asList("F#", "G#", "A#", "B", "C#", "D#", "E#"));
        escalasMaiores.put("C#", Arrays.asList("C#", "D#", "E#", "F#", "G#", "A#", "B#"));
        escalasMaiores.put("F", Arrays.asList("F", "G", "A", "Bb", "C", "D", "E"));
        escalasMaiores.put("Bb", Arrays.asList("Bb", "C", "D", "Eb", "F", "G", "A"));
        escalasMaiores.put("Eb", Arrays.asList("Eb", "F", "G", "Ab", "Bb", "C", "D"));
        escalasMaiores.put("Ab", Arrays.asList("Ab", "Bb", "C", "Db", "Eb", "F", "G"));
        escalasMaiores.put("Db", Arrays.asList("Db", "Eb", "F", "Gb", "Ab", "Bb", "C"));
        escalasMaiores.put("Gb", Arrays.asList("Gb", "Ab", "Bb", "Cb", "Db", "Eb", "F"));
        escalasMaiores.put("Cb", Arrays.asList("Cb", "Db", "Eb", "Fb", "Gb", "Ab", "Bb"));
    }

    /**
     * Inicializa o mapa de relativas maiores. As relativas maiores são pares de
     * acordes onde um acorde é a relativa maior do outro. O mapa é preenchido
     * com as relativas maiores para cada acorde.
     */
    private static void inicializarRelativasMaiores() {
        relativasMaiores.put("A", "C");
        relativasMaiores.put("E", "G");
        relativasMaiores.put("B", "D");
        relativasMaiores.put("F#", "A");
        relativasMaiores.put("C#", "E");
        relativasMaiores.put("G#", "B");
        relativasMaiores.put("D#", "F#");
        relativasMaiores.put("A#", "C#");
        relativasMaiores.put("D", "F");
        relativasMaiores.put("G", "Bb");
        relativasMaiores.put("C", "Eb");
        relativasMaiores.put("F", "Ab");
        relativasMaiores.put("Bb", "Db");
        relativasMaiores.put("Eb", "Gb");
        relativasMaiores.put("Ab", "Cb");
    }

}
