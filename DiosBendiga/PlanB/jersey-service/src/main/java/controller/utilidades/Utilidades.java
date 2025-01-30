package controller.utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import models.Sintetica;
public class Utilidades {

    public static Field getField(Class clazz, String attribte) {
        Field[] fields = clazz.getFields();
        Field resp = null;
        //System.out.println(fields.length);
        for (Field f : fields) {
            System.out.println("normal: " + f.getName() + " " + f.toString());
            if (attribte.equalsIgnoreCase(f.getName())) {
                resp = f;
            }
        }

        Field[] fields1 = clazz.getDeclaredFields();
        //System.out.println(fields1.length);
        for (Field f : fields1) {
            if (attribte.equalsIgnoreCase(f.getName())) {
                resp = f;
            }
        }
        return resp;
    }

    public static Object getData(Object clas, String attribte) throws InvocationTargetException, IllegalAccessException {
        Class clazz = clas.getClass();
        Field f = getField(clazz, attribte);
        Object obj = null;
        if (f != null) {
            String auxAttribute = "get" + capitalize(attribte);
            Method method = null;
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase(auxAttribute)) {
                    method = m;
                    break;
                }
            }
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.getName().equalsIgnoreCase(auxAttribute)) {
                    method = m;
                    break;
                }
            }
            if (method != null) {
                obj = method.invoke(clas);
            }
        }
        return obj;
    }

    public static String capitalize(String cdn) {
        char[] caracteres = cdn.toCharArray();
        String aux = String.valueOf(caracteres[0]).toUpperCase();
        caracteres[0] = aux.charAt(0);
        return new String(caracteres);
    }

    public static String getDirProject() {
        return System.getProperty("user.dir");
    }

    public static String getOs() {
        return System.getProperty("os.name");
    }

    public static void abrirNavegadorPredeterminadoWindows(String url) throws Exception {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        System.out.println(url);
    }

    public static void abrirNavegadorPredeterminadoLinux(String url) throws Exception {
        Runtime.getRuntime().exec("xdg-open " + url);
    }

    public static void abrirNavegadorPredeterminadoMacOS(String url) throws Exception {
        Runtime.getRuntime().exec("open " + url);
    }

    public static void copiarArchivo(File origen, File destino) throws Exception {
        Files.copy(origen.toPath(),
                (destino).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public static String extension(String s) throws Exception {
        String extension = "";
        int i = s.lastIndexOf('.');
        if (i > 0) {
            extension = s.substring(i + 1);
        }
        return extension;
    }

    public static Double distanciaEscuelas(Sintetica o, Sintetica d){
        return Utilidades.distancia2Puntos(o.getLatitud(), o.getLongitud(), d.getLatitud(), d.getLongitud());
    }
    public static Double distancia2Puntos(double lat1, double lon1, double lat2, double lon2) {
        double lat1rad = Math.toRadians(lat1);
        double lon1rad = Math.toRadians(lon1);
        double lat2rad = Math.toRadians(lat2);
        double lon2rad = Math.toRadians(lon2);

        double difLatitud = lat1rad - lat2rad;
        double difLongitud = lon1rad - lon2rad;

        double a = Math.pow(Math.sin(difLatitud / 2), 2)
                + Math.cos(lat1rad)
                * Math.cos(lat2rad)
                * Math.pow(Math.sin(difLongitud / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double radioTierraKm = 6378.0;
        double distancia = radioTierraKm * c;

        return distancia;
    }
    
    public static Double redondear(Double x){
        return Math.round(x * 100.0) / 100.0;
    }

    public static Integer generateRandomNumber(Integer max, Integer excluded) {
        Random rand = new Random();
        int randomNumber;
        
        do {
            randomNumber = rand.nextInt(max + 1);
        } while (randomNumber == excluded);
        
        return randomNumber;
    }
    public static void main(String[] args) {
        int maxNumber = 10;
        int excludedNumber = 1;
        
        int random = generateRandomNumber(maxNumber, excludedNumber);
        System.out.println("Número aleatorio generado (excluyendo " + excludedNumber + "): " + random);
    }
}
