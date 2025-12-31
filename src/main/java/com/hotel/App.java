package com.hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 1;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
        // TODO:
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2:
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReservaPerTipus();
                break;
            case 5:
                obtindreReserva();
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        // TODO:

        String tipoHabitacio = seleccionarTipusHabitacioDisponible(); // creo variable tipohabitacio per accedir a la
                                                                      // habitacio
        // elegida.
        ArrayList<String> serveisList = seleccionarServeis(); // arrayList de serveis. Per accedir als serveis elegits
        float preuTotal = calcularPreuTotal(tipoHabitacio, serveisList); // es crida a calcularPreu amb les variables
                                                                         // creades. (tipoHabitacio +
                                                                         // arraylist(serveis))
        int codiReserva = generarCodiReserva(); // Arrepleguem açi també el codiReserva generat.
        /*
         * Com ja tenim les dades que necessitem, creem ArrayList per
         * memoritzarles i poder treballar amb elles.
         * (tipo habitacio, serveis i preutotal)
         */
        ArrayList<String> dadesReserva = new ArrayList<>();
        // Afegim: habitacio + serveis + preu
        dadesReserva.add(tipoHabitacio);
        dadesReserva.addAll(serveisList); // afegim tots el serveis amb el addAll
        String preuFormateat = String.format("%.2f", preuTotal); // preu formateat amb 2decimals
        dadesReserva.add(preuFormateat);

        /* Afegim al hashmap global: integer codiReserva + ArrayList dadesReserva */
        reserves.put(codiReserva, dadesReserva);
        // restem una habitació a disponible quan es reserva
        int disponible = disponibilitatHabitacions.get(tipoHabitacio);
        disponibilitatHabitacions.put(tipoHabitacio, disponible - 1);
        float impostos = preuTotal * IVA;

        System.out.println("Calculem el total... ");
        System.out.println("\nPreu habitació " + tipoHabitacio + ": " + preusHabitacions.get(tipoHabitacio) + "euros");
        System.out.println("\nServeis: ");
        for (int i = 0; i < serveisList.size(); i++) {

            System.out.println((i + 1) + ". " + serveisList.get(i));

        }
        System.out.println("\nIVA (21%) " + String.format("%.2f", impostos) + " euros");
        System.out.println("\nPreu total (amb IVA): " + preuFormateat + " euros");

        System.out.println("\nReserva realitzada!");
        System.out.println("\nCodi de reserva: " + codiReserva);

    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        // TODO:
        int opcio = 0;
        System.out.println("1. Estàndar ");
        System.out.println("2. Suite ");
        System.out.println("3. Deluxe ");
        System.out.println("Seleccione tipus d'habitació: ");

        opcio = sc.nextInt();
        sc.nextLine();

        switch (opcio) {
            case 1:
                return TIPUS_ESTANDARD;
            case 2:
                return TIPUS_SUITE;
            case 3:
                return TIPUS_DELUXE;
            default:
                System.out.println("Opció no vàlida. S'assignarà Estàndard per defecte.");
                return TIPUS_ESTANDARD;
        }

    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        // TODO:
        /*
         * mostrem tota la informacio de cada tipus d'habitacio
         * les ocupades, lliures, i preu (ja està posat en mostrarInfoTipus)
         */
        System.out.print("1. ");
        mostrarInfoTipus(TIPUS_ESTANDARD);
        System.out.print("2. ");
        mostrarInfoTipus(TIPUS_SUITE);
        System.out.print("3. ");
        mostrarInfoTipus(TIPUS_DELUXE);
        /*
         * Li diguem al usuari que seleccione tipus habitacio.
         * amb el llegir enter que ja est'a declarat validem la entrada.
         */
        System.out.println();
        int opcio = llegirEnter("Seleccione tipus d'habitació: ");
        sc.nextLine();

        /*
         * Switch per a posar opcions i aixi comprobem si hi han disponibles (>0)
         * Retorna si hi ha, sino retorna null
         */
        switch (opcio) {
            case 1:
                if (disponibilitatHabitacions.get(TIPUS_ESTANDARD) > 0) {
                    return TIPUS_ESTANDARD;

                } else {
                    System.out.println("No n'hi han habitacions disponibles.");
                    return null;
                }

            case 2:
                if (disponibilitatHabitacions.get(TIPUS_SUITE) > 0) {
                    return TIPUS_SUITE;

                } else {
                    System.out.println("No n'hi ha han habitacions disponibles.");
                    return null;
                }

            case 3:
                if (disponibilitatHabitacions.get(TIPUS_DELUXE) > 0) {
                    return TIPUS_DELUXE;

                } else {
                    System.out.println("No n'hi ha han habitacions disponibles.");
                    return null;
                }

            default:
                System.out.println("No has elegit una opció vàlida.");
                return null;
        }

    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        // TODO:
        char resposta;
        boolean respostaValida = true;
        boolean serviciValid = true;
        boolean eixir = false;

        ArrayList<String> serveisList = new ArrayList<>();
        System.out.println();
        System.out.println("Serveis adicionals (0-4):\n ");
        System.out.println("0. Finalitzar ");
        System.out.println("1. Esmorçar (10 e) ");
        System.out.println("2. Gimnàs   (15 e) ");
        System.out.println("3. Spa      (20 e) ");
        System.out.println("4. Piscina  (25 e) ");
        do {

            System.out.print("\nVol afegir un servei? (s/n): ");
            resposta = sc.nextLine().toLowerCase().charAt(0);
            System.out.println();

            if (resposta == 'n') {
                respostaValida = true;
                break;
            }

            else if (resposta == 's') {

                respostaValida = true;
                System.out.print("Seleccione servei: ");

                int opcio = sc.nextInt();
                sc.nextLine();
                System.out.println();

                String serveiTriat = null;

                switch (opcio) {
                    case 0:
                        eixir = true;
                        break;
                    case 1:
                        serviciValid = true;
                        serveiTriat = SERVEI_ESMORZAR;
                        break;
                    case 2:
                        serviciValid = true;
                        serveiTriat = SERVEI_GIMNAS;
                        break;
                    case 3:
                        serviciValid = true;
                        serveiTriat = SERVEI_SPA;
                        break;
                    case 4:
                        serviciValid = true;
                        serveiTriat = SERVEI_PISCINA;
                        break;
                    default:
                        serviciValid = false;
                        break;

                }
                if (eixir) {
                    break;
                }

                if (serveisList.contains(serveiTriat)) {
                    System.out.println("Ja has seleccionat aquest servei. ");
                    System.out.println();

                } else if (serviciValid == false) {
                    System.out.println("Servei no valid");
                    System.out.println();

                }

                else {

                    serveisList.add(serveiTriat);
                    System.out.println("Servei afegit: " + serveiTriat);
                    System.out.println();
                }

            }

            else {
                respostaValida = false;
                System.out.println("Resposta no valida");
            }

        } while ((resposta == 's' && serveisList.size() < 4) || (respostaValida == false));

        return serveisList;

    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipoHabitacio, ArrayList<String> serveisList) {
        // TODO:

        float preuTotal = preusHabitacions.get(tipoHabitacio); // preu habitacio

        for (String servei : serveisList) {
            preuTotal += preusServeis.get(servei);
        }

        preuTotal = preuTotal * (1 + IVA);

        return preuTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        // TODO:
        int codiReserva;
        boolean codiRepetit;
        do {
            codiReserva = 100 + random.nextInt(900);

            codiRepetit = reserves.containsKey(codiReserva);
        } while (codiRepetit);

        return codiReserva;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        // TODO: Demanar codi, tornar habitació i eliminar reserva
        int codiReserva;
        boolean codiTrobat = false;
        System.out.println("Introdueix codi de reserva: ");
        codiReserva = sc.nextInt();
        sc.nextLine();

        if (reserves.containsKey(codiReserva)) {
            codiTrobat = true;
            /*
             * Creem altra vegada les variables i el arraylist de dadesreserva per poder
             * accedir a les dades que ens
             * interesen, ja que estaben en altres métodes on no podem accedir.
             * a traves de estas noves variables, agafem les dades el hashmap global
             */

            ArrayList<String> dadesReserva = reserves.get(codiReserva); // EJEMPLO: dadesReserva = "Estandar", "Spa"
            String tipusHabitacio = dadesReserva.get(0); // tipusHabitacio= "Estandar"
            int disponible = disponibilitatHabitacions.get(tipusHabitacio); // disponible=X
            disponibilitatHabitacions.put(tipusHabitacio, disponible + 1); // añade 1 a la disponibilitat. "X+1"

            reserves.remove(codiReserva);
            System.out.println("Habitació alliberada correctament. ");
            System.out.println("Disponibilitat actualitzada.");
        }

        else {
            System.out.println("No existeix la reserva");
        }
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
        System.out.println("===== DISPONIBILITAT D'HABITACIONS =====");

        System.out.println("\nTIPUS\t\tLLIURES\tOCUPADES");
        System.out.println("------\t\t-------\t--------");

        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);

    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // TODO: Implementar recursivitat
        // Açi hi ha que llegir un tipus de habitacio y imprimir totes les reserves que
        // hi han de eixe tipus
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");

        // TODO: Mostrar dades d'una reserva concreta

        System.out.println("Introdueix codi de la reserva: ");
        int codiReserva = sc.nextInt();
        sc.nextLine();

        /*
         * localitzem reserva amb la seua clau si el hashmap la conté.
         * Si la té, accedim a la funció de mostrarDadesReserva.
         */

        if (reserves.containsKey(codiReserva)) {

            mostrarDadesReserva(codiReserva);

        } else
            System.out.println("No existeix la reserva amb aquest codi " + codiReserva);

    }

    /*
     * Mostra totes les reserves existents per a un tipus d'habitació específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
        String tipoHabitacio = seleccionarTipusHabitacio();
        String tipoBuscat;

        System.out.println("Reserves de tipus: " + tipoHabitacio);
        System.out.println();

        for (Map.Entry<Integer, ArrayList<String>> entrada : reserves.entrySet()) {
            ArrayList<String> dadesReserva = new ArrayList<>();
            int codiReserva = entrada.getKey();
            dadesReserva = entrada.getValue();
            tipoBuscat = dadesReserva.get(0);
            if (tipoBuscat.equals(tipoHabitacio)) {
                mostrarDadesReserva(codiReserva);
                System.out.println();
            }
        }

    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
        // TODO: Imprimir tota la informació d'una reserva
        ArrayList<String> dadesReserva = reserves.get(codi);
        String tipoHabitacio = dadesReserva.get(0);
        System.out.println("==== RESERVA: " + codi + " ====");
        System.out.println("Habitació: ");
        System.out.println(tipoHabitacio);
        System.out.println();
        System.out.println("Serveis: ");
        if (dadesReserva.size() <= 2) {
            System.out.println("cap");
        } else {
            for (int i = 1; i < dadesReserva.size() - 1; i++) {
                System.out.println(i + ". " + dadesReserva.get(i));
            }
            System.out.println();
        }
        String preu = dadesReserva.get(dadesReserva.size() - 1);
        System.out.println("Preu total: ");
        System.out.println(preu + " euros");

    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
            System.out.print(missatge);
            valor = sc.nextInt();
            correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }

}
