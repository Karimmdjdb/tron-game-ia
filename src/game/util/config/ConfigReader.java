package game.util.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe pour la gestion de la récupération des variables de configuration depuis un fichier texte.
 */
public class ConfigReader {

    private static Map<String, String> configs;

    static {
        configs = new HashMap<>();
        try {
            File gameconf = new File("config.txt");
            Scanner scanner = new Scanner(gameconf);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty() || line.charAt(0) == '#') continue; // on ignore les lignes vides et les commentaires
                String[] data = line.split("=");
                String param = data[0].trim(), value = data[1].trim();
                configs.put(param, value);
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Récupére la configuration extraite du fichier texte.
     * @return une table associative de régles -> valeurs.
     */
    public static Map<String, String> getConfig() {
        return configs;
    }

    /**
     * Récupére la valeur qui corréspond a une clé dans la configuration.
     * @param param la clé qu'on recherche
     * @return la valeur qui corréspond à la clé
     */
    public static String get(String param) {
        return configs.get(param);
    }
}

