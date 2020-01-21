package notasutpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Scanner;

public class UsernameEVA_GestionFilesCSV {

    /**
     * Clase principal
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Estudiante> estudiantes = Operaciones.generarEstudiantes(20);

        Informe informe = new Informe(Operaciones.ENCABEZADO, estudiantes);

        EscribirArchivo salida = new EscribirArchivo("datos.csv");

        salida.escribir(informe.toString());
        salida.cerrar();

        LeerArchivo entrada = new LeerArchivo("datos.csv");
        estudiantes = entrada.obtenerEstudiantes();

        informe = new Informe(Operaciones.ENCABEZADO, estudiantes);
        informe.calcular();

        Collections.sort(estudiantes, new ComparaNombre());
        salida = new EscribirArchivo("usernameBdEst_OrdenNomb.csv");
        salida.escribir(informe.toString());
        salida.cerrar();
        System.out.println("Informe ordenado por NOMBRE\n=======================================");
        System.out.println(informe.toString());

        Collections.sort(estudiantes, new ComparaTotal());
        salida = new EscribirArchivo("usernameBdEst_OrdenTOTAL.csv");
        salida.escribir(informe.toString());
        salida.cerrar();
        System.out.println("Informe ordenado por TOTAL\n=======================================");
        System.out.println(informe.toString());

        Collections.sort(estudiantes, new ComparaPromocion());
        salida = new EscribirArchivo("usernameBdEst_OrdenPromo.csv");
        salida.escribir(informe.toString());
        salida.cerrar();
        System.out.println("Informe ordenado por PROMOCION\n=======================================");
        System.out.println(informe.toString());
    }
}

// Clases Estudiante e Informe
/**
 * Clase Estudiante
 *
 */
class Estudiante {

    private String name;
    private double for1, cha1, vid1, tra1, pre1, for2, cha2, vid2, tra2, pre2, fin1, fin2, total;
    private String alerta, promocion;
    private double total1Bim, total2Bim;

    /**
     * Constructor de clase Estudiante
     *
     * @param name nombre del estudiante
     * @param for1 foro 1Bim
     * @param cha1 chat 1Bim
     * @param vid1 videocolaboracion 1Bim
     * @param tra1 trabajos 1Bim
     * @param pre1 examen presencial 1Bim
     * @param for2 foro 2Bim
     * @param cha2 chat 2Bim
     * @param vid2 videocolaboracion 2Bim
     * @param tra2 trabajos 2Bim
     * @param pre2 examen presencial 2Bim
     */
    public Estudiante(String name, double for1, double cha1, double vid1, double tra1, double pre1, double for2, double cha2, double vid2, double tra2, double pre2) {
        this.name = name;
        this.for1 = for1;
        this.cha1 = cha1;
        this.vid1 = vid1;
        this.tra1 = tra1;
        this.pre1 = pre1;
        this.for2 = for2;
        this.cha2 = cha2;
        this.vid2 = vid2;
        this.tra2 = tra2;
        this.pre2 = pre2;
        this.fin1 = 0;
        this.fin2 = 0;
        this.total = 0;
        this.alerta = "";
        this.promocion = "";
        this.total1Bim = 0;
        this.total2Bim = 0;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setFor1(double for1) {
        this.for1 = for1;
    }

    public void setCha1(double cha1) {
        this.cha1 = cha1;
    }

    public void setVid1(double vid1) {
        this.vid1 = vid1;
    }

    public void setTra1(double tra1) {
        this.tra1 = tra1;
    }

    public void setPre1(double pre1) {
        this.pre1 = pre1;
    }

    public void setFor2(double for2) {
        this.for2 = for2;
    }

    public void setCha2(double cha2) {
        this.cha2 = cha2;
    }

    public void setVid2(double vid2) {
        this.vid2 = vid2;
    }

    public void setTra2(double tra2) {
        this.tra2 = tra2;
    }

    public void setPre2(double pre2) {
        this.pre2 = pre2;
    }

    public void setFin1(double fin1) {
        this.fin1 = fin1;
    }

    public void setFin2(double fin2) {
        this.fin2 = fin2;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getFor1() {
        return for1;
    }

    public double getCha1() {
        return cha1;
    }

    public double getVid1() {
        return vid1;
    }

    public double getTra1() {
        return tra1;
    }

    public double getPre1() {
        return pre1;
    }

    public double getFor2() {
        return for2;
    }

    public double getCha2() {
        return cha2;
    }

    public double getVid2() {
        return vid2;
    }

    public double getTra2() {
        return tra2;
    }

    public double getPre2() {
        return pre2;
    }

    public double getFin1() {
        return fin1;
    }

    public double getFin2() {
        return fin2;
    }

    public double getTotal() {
        return total;
    }

    public String getAlerta() {
        return alerta;
    }

    public String getPromocion() {
        return promocion;
    }

    public int calcAlerta() {
        int opcion = 0;

        if (total1Bim + total2Bim == 0) {
            opcion = 4;
        } else {
            if (pre1 < 8 && pre2 < 8) {
                opcion = 3;
            } else if (pre1 < 8) {
                opcion = 1;
            } else if (pre2 < 8) {
                opcion = 2;
            }

            if (pre1 == pre2 && total1Bim + total2Bim < 28) {
                opcion = 2;
            } else if (pre1 > 8 && total1Bim < 28) {
                opcion = 1;
            } else if (pre2 > 8 && total2Bim < 28) {
                opcion = 2;
            }
        }

        return opcion;
    }

    public void calcTotalesBim() {
        total1Bim = for1 + cha1 + vid1 + tra1 + pre1;
        total2Bim = for2 + cha2 + vid2 + tra2 + pre2;
    }

    public void calcPromocion() {
        if (total >= 28) {
            promocion = "APROBADO";
        } else {
            promocion = "REPROBADO";
        }
    }

    public void calcular() {
        calcTotalesBim();
        switch (calcAlerta()) {
            case 0:
                total = total1Bim + total2Bim;
                break;
            case 1:
                fin1 = Operaciones.nota(20);
                total = fin1 + total2Bim;
                alerta = "Rendir Final 1";
                break;
            case 2:
                fin2 = Operaciones.nota(20);
                total = total1Bim + fin2;
                alerta = "Rendir Final 2";
                break;
            case 3:
                fin1 = Operaciones.nota(20);
                fin2 = Operaciones.nota(20);
                total = fin1 + fin2;
                alerta = "Rendir Final 1 y 2";
                break;
            case 4:
                total = 0;
                alerta = "Reprobado falta trabajo";
                break;
        }
        if (total > 40) {
            total = 40;
        }
        calcPromocion();
    }

    @Override
    public String toString() {
        return String.format("%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%s;%s", name, for1, cha1, vid1, tra1, pre1, for2, cha2, vid2, tra2, pre2, fin1, fin2, total, alerta, promocion);
    }
}

/**
 * Clase Informe
 *
 */
class Informe {

    private String encabezado;
    private ArrayList<Estudiante> estudiantes;

    /**
     * Constructor de Informe
     *
     * @param encabezado
     * @param estudiantes
     */
    public Informe(String encabezado, ArrayList<Estudiante> estudiantes) {
        this.encabezado = encabezado;
        this.estudiantes = estudiantes;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void calcular() {
        for (Estudiante estudiante : estudiantes) {
            estudiante.calcular();
        }
    }

    @Override
    public String toString() {
        String reporte = String.format("%s\n", encabezado);
        for (Estudiante estudiante : estudiantes) {
            reporte += String.format("%s\n", estudiante);
        }
        return reporte;
    }
}

// Clases para administrar los archivos de lectura y escritura
/**
 * Clase para leer archivos
 *
 */
class LeerArchivo {

    private Scanner archivo;

    public LeerArchivo(String archivo) {
        try {
            this.archivo = new Scanner(new File(archivo));
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo.");
        }
    }

    public ArrayList<Estudiante> obtenerEstudiantes() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        int numLinea = 0;

        while (archivo.hasNext()) {
            String[] linea = archivo.nextLine().split(";");
            if (numLinea != 0) {
                estudiantes.add(new Estudiante(linea[0], Operaciones.decimal(linea[1]), Operaciones.decimal(linea[2]), Operaciones.decimal(linea[3]), Operaciones.decimal(linea[4]), Operaciones.decimal(linea[5]), Operaciones.decimal(linea[6]), Operaciones.decimal(linea[7]), Operaciones.decimal(linea[8]), Operaciones.decimal(linea[9]), Operaciones.decimal(linea[10])));
            }
            numLinea++;
        }
        cerrar();
        return estudiantes;
    }

    public void cerrar() {
        if (archivo != null) {
            archivo.close();
        }
    }
}

/**
 * Clase para escribir archivos
 *
 */
class EscribirArchivo {

    private Formatter archivo;

    public EscribirArchivo(String archivo) {
        try {
            this.archivo = new Formatter(archivo, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Error al abrir el archivo.");
        }
    }

    public void escribir(String linea) {
        archivo.format("%s\n", linea);
    }

    public void cerrar() {
        archivo.close();
    }
}

// Clases auxiliares
/**
 * Clase que contiene metodos auxiliares
 *
 */
final class Operaciones {

    public static final String NOMBRE = "Estudiante";
    public static final String ENCABEZADO = "NOMBRES;FOR1;CHA1;VID1;TRA1;PRE1;FOR2;CHA2;VID2;TRA2;PRE2;FIN1;FIN2;TOTAL;ALERTA;PROMOCIÓN";
    public static final double MAXPRE = 14;
    public static final double MAXACTLINEA = 1;
    public static final double MAXTRABAJOS = 6;

    /**
     * Metodo que genera una nota en entre 0 a n valor.
     *
     * @param maxNum numero maximo que devuelve
     * @return double
     */
    public static double nota(double maxNum) {
        return Math.random() * maxNum;
//        return notaAlta(maxNum);
    }

    public static double nota(double minNum, double maxNum) {
        return Math.random() * minNum + maxNum;
    }

    public static double notaAlta(double maxNum) {
        int accion = (int) nota(100);
        int minNum = 0;
        if (maxNum == MAXPRE) {
            minNum = 10;
        } else if (maxNum == MAXTRABAJOS) {
            minNum = 4;
        }

        if (accion >= 70) {
            return nota(maxNum);
        } else if (accion >= 20) {
            return nota(minNum, maxNum);
        } else {
            return 0;
        }
    }

    public static double decimal(String numero) {
        double num = 0;
        numero = numero.replace(",", ".");
        try {
            num = Double.parseDouble(numero);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir decimal.");
        }

        return num;
    }

    public static ArrayList<Estudiante> generarEstudiantes(int numEstudiantes) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        for (int i = 0; i < numEstudiantes; i++) {
            estudiantes.add(new Estudiante(String.format("%s %d", NOMBRE, i + 1), nota(MAXACTLINEA), nota(MAXACTLINEA), nota(MAXACTLINEA), nota(MAXTRABAJOS), nota(MAXPRE), nota(MAXACTLINEA), nota(MAXACTLINEA), nota(MAXACTLINEA), nota(MAXTRABAJOS), nota(MAXPRE)));
        }
        return estudiantes;
    }
}

// Clases para ordenamiento de Estudiantes
/**
 * Clase que implementa los metodos astractos de Comparator, clase usada para
 * ordenar por NOMBRE.
 *
 */
class ComparaNombre implements Comparator<Estudiante> {

    @Override
    public int compare(Estudiante o1, Estudiante o2) {

        Integer a = Integer.parseInt(o1.getName().split(" ")[1]);
        Integer b = Integer.parseInt(o2.getName().split(" ")[1]);
        return a.compareTo(b);
    }
}

/**
 * Clase que implementa los metodos astractos de Comparator, clase usada para
 * ordenar por PROMOCION.
 *
 */
class ComparaPromocion implements Comparator<Estudiante> {

    @Override
    public int compare(Estudiante o1, Estudiante o2) {
        String a = o1.getPromocion();
        String b = o2.getPromocion();
        return a.compareTo(b);
    }
}

/**
 * Clase que implementa los metodos astractos de Comparator, clase usada para
 * ordenar por TOTAL.
 *
 */
class ComparaTotal implements Comparator<Estudiante> {

    @Override
    public int compare(Estudiante o1, Estudiante o2) {
        Double a = o1.getTotal();
        Double b = o2.getTotal();
        return a.compareTo(b);
    }
}