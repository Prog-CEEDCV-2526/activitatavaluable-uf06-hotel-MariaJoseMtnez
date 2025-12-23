package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static final int CAPACITAT_DELUXE = 10;

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
                llistarReservesPerTipus(null, TIPUS_DELUXE);
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

        String tipoHabitacio = seleccionarTipusHabitacio();
        ArrayList<String> serveisList = seleccionarServeis();
        float preuTotal = calcularPreuTotal(tipoHabitacio, serveisList);
        int codiReserva = generarCodiReserva();

        /*
         * creem un array per memoritzar les dades de la reserva (tipo habitacio y
         * serveis)
         */
        ArrayList<String> dadesReserva = new ArrayList<>();
        dadesReserva.add(tipoHabitacio);
        dadesReserva.addAll(serveisList); // AÇI AFEGIM TOTES LES DADES
        /* añadim al hashmap el codi de reserva creat y les dades de reserva */
        reserves.put(codiReserva, dadesReserva);

        /* imprimim per visualitzar */

        System.out.println(reserves);
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        // TODO:
        int opcio = 0;
        System.out.println("Habitacions disponibles: ");
        System.out.println("1. Estàndar 50 e ");
        System.out.println("2. Suite 100 e ");
        System.out.println("3. Deluxe 150 e ");
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
        int opcio = 0;
        /*
         * mostrem tota la informacio de cada tipus d'habitacio
         * les ocupades, lliures, i preu (ja està posat en mostrarInfoTipus)
         */

        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_DELUXE);
        mostrarInfoTipus(TIPUS_SUITE);
        /*
         * Li diguem al usuari que seleccione tipus habitacio.
         * amb el llegir enter que ja est'a declarat validem la entrada.
         */
        System.out.println("Selecciona un tipus d'habitació disponible: ");
        System.out.println("1.ESTÀNDAR - 2.DELUXE - 3.SUITE: ");
        opcio = llegirEnter("Selecciona un tipus disponible (1-3)");

        /*
         * Switch case per a posar opcions i aixi comprobem si hi han disponibles (>0)
         * Retorna si hi ha, sino retorna null
         */
        switch (opcio) {
            case 1:
                if (disponibilitatHabitacions.get(TIPUS_ESTANDARD) > 0) {
                    return TIPUS_ESTANDARD;

                } else {
                    System.out.println("No n'hi ha han habitacions disponibles.");
                    return null;
                }

            case 2:
                if (disponibilitatHabitacions.get(TIPUS_DELUXE) > 0) {
                    return TIPUS_DELUXE;

                } else {
                    System.out.println("No n'hi ha han habitacions disponibles.");
                    return null;
                }

            case 3:
                if (disponibilitatHabitacions.get(TIPUS_SUITE) > 0) {
                    return TIPUS_SUITE;

                } else {
                    System.out.println("No n'hi ha han habitacions disponibles.");
                    return null;
                }

            default:
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
        ArrayList<String> serveisList = new ArrayList<>();

        System.out.println("Serveis adicionals: ");

        do {
            System.out.println("Vols afegir un servei? (s/n) ");
            resposta = sc.nextLine().toLowerCase().charAt(0);
            if (resposta == 'n') {
                respostaValida = true;
                break;
            }

            else if (resposta == 's') {
                respostaValida = true;
                System.out.println("Introdueix número del servei (1-4): ");
                System.out.println("1. Esmorçar 10 e ");
                System.out.println("2. Gimnàs 15 e ");
                System.out.println("3. Spa 20 e ");
                System.out.println("4. Piscina 25 e ");

                int opcio = sc.nextInt();
                sc.nextLine();

                String serveiTriat = null;

                switch (opcio) {
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

                if (serveisList.contains(serveiTriat)) {
                    System.out.println("Ja has seleccionat aquest servei. ");

                } else if (serviciValid == false) {
                    System.out.println("Servei no valid");

                }

                else {

                    serveisList.add(serveiTriat);
                    System.out.println("Servei afegit: " + serveiTriat);
                }

                for (int i = 0; i < serveisList.size(); i++) {

                    System.out.println((i + 1) + ". " + serveisList.get(i));

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
        System.out.println("Preu total (amb IVA): " + String.format("%.2f", preuTotal) + " euros");
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

        System.out.println("Codi de reserva: " + codiReserva);
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

        System.out.println("Introdueix codi de reserva: ");
        codiReserva = sc.nextInt();
        sc.nextLine();

        if (reserves.containsKey(codiReserva)) {
            /*
             * Creem altra vegada les variables i el arraylist de dadesreserva per poder accedir a les dades que ens
             * interesen, ja que estaben en altres métodes on no podem accedir.
             * a traves de estas noves variables, agafem les dades el hashmap global
             */
            ArrayList<String> dadesReserva = reserves.get(codiReserva); // EJEMPLO: dadesReserva = "Estandar", "Spa"
            String tipusHabitacio = dadesReserva.get(0); // tipusHabitacio= "Estandar"
            int disponible = disponibilitatHabitacions.get(tipusHabitacio); // disponible=X
            disponibilitatHabitacions.put(tipusHabitacio, disponible + 1); // añade 1 a la disponibilitat. "X+1"

            reserves.remove(codiReserva);
            System.out.println("Reserva cancel·lada ");

        } else {
            System.out.println("No existeix la reserva");
        }
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
        mostrarDisponibilitatTipus(TIPUS_SUITE);

    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // TODO: Implementar recursivitat
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
         * localitzem reserva amb la seua clau si el hashmap la conté
         */
        if (reserves.containsKey(codiReserva)) {
            System.out.println(reserves.get(codiReserva));
        } else
            System.out.println("No existeix la reserva");
    }

    /*
     * Mostra totes les reserves existents per a un tipus d'habitació específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
        String tipoHabitacio = seleccionarTipusHabitacio();

        // llistarReservesPerTipus(null, tipoHabitacio);
        disponibilitatHabitacions.get(tipoHabitacio);
        System.out.println();

    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
        // TODO: Imprimir tota la informació d'una reserva
        System.out.println("Tipus d'habitació seleccionat: " + seleccionarTipusHabitacio());
        System.out.println("Serveis seleccionats: " + seleccionarServeis());
        System.out.println(
                "El preu total amb IVA es: " + calcularPreuTotal(seleccionarTipusHabitacio(), seleccionarServeis()));

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
