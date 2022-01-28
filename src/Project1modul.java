
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Project1modul {
    static String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя., -?!—";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Здравствуйте!");
        System.out.println("Данная программа предназначена для шифровки/расшифровки файлов с использованием ключа, брута и статанализа");
        System.out.println("Выберете, что вы хотите сделать и нажмите соответствуюшую клавишу");
        System.out.println("1.Шифровка по ключу с использованием шифра Цезаря");
        System.out.println("2.Расшифровка по ключу с использованием шифра Цезаря");
        System.out.println("3.Брут-форс, с использованием шифра Цезаря, не зная ключа");
        System.out.println("4.Статистический анализ текста, на основе другого текста(желательно того же автора;)");
        int i = Integer.parseInt(scanner.nextLine());
        switch (i) {
            case 1: {
                System.out.println("Введите путь и имя файла, который хотите зашифровать:");
                String fileEncrypt = scanner.nextLine();
                System.out.println("Введите путь и имя файла, куда хотите сохранить зашифрованный текст:");
                String fileDecrypt = scanner.nextLine();
                System.out.println("Введите ключ:");
                int key = Integer.parseInt(scanner.nextLine());
                crypt(fileEncrypt, fileDecrypt, key);
                break;
            }
            case 2: {
                System.out.println("Введите путь и имя файла, который хотите расшифровать:");
                String fileEncrypt = scanner.nextLine();
                System.out.println("Введите путь и имя файла, куда хотите сохранить расшифрованный текст:");
                String fileDecrypt = scanner.nextLine();
                System.out.println("Введите ключ:");
                int key = Integer.parseInt(scanner.nextLine());
                if (key > alphabet.length()) {
                    key = key % alphabet.length();
                }
                int key2 = alphabet.length() - key;
                crypt(fileEncrypt, fileDecrypt, key2);
                break;
            }
            case 3: {
                System.out.println("Введите путь и имя файла, который хотите расшифровать:");
                String fileEncrypt = scanner.nextLine();
                System.out.println("Введите путь и имя файла, куда хотите сохранить расшифрованный текст:");
                String fileDecrypt = scanner.nextLine();
                char[] alp = alphabet.toCharArray();
                for (int j = 1; j < alp.length; j++) {
                    crypt(fileEncrypt, fileDecrypt, j);
                    if (brutForce(fileDecrypt)) {
                        break;
                    }
                }
            }
            case 4: {
                System.out.println("Введите путь и имя файла, который хотите расшифровать:");
                String fileEncrypt = scanner.nextLine();
                System.out.println("Введите путь и имя вспомогательного файла, на основе которого можно провести статистический анализ текста:");
                String fileForStatAnal = scanner.nextLine();
                System.out.println("Введите путь и имя файла, куда хотите сохранить расшифрованный текст:");
                String fileDecrypt = scanner.nextLine();
                cryptoAnal(fileEncrypt, fileForStatAnal, fileDecrypt);
            }
        }


    }

    public static void crypt(String encFile, String deFile, int key) {
        try (BufferedReader encFileBuf = new BufferedReader(new FileReader(encFile));
             BufferedWriter deFileBuf = new BufferedWriter(new FileWriter(deFile))) {
            char[] alphabetArray = alphabet.toCharArray();
            while (encFileBuf.ready()) {
                String st = encFileBuf.readLine().toLowerCase(Locale.ROOT);
                char[] chfile1 = st.toCharArray();
                char[] result = new char[chfile1.length];
                for (int i = 0; i < chfile1.length; i++) {
                    char cfile = chfile1[i];
                    for (int j = 0; j < alphabetArray.length; j++) {
                        char ch = alphabetArray[j];
                        if (ch == cfile) {
                            result[i] = alphabetArray[(j + key) % alphabetArray.length];
                        }
                    }
                }
                deFileBuf.write(result);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("По заданному адресу файла для чтения не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean brutForce(String encFile) {
        try (BufferedReader encFileBuf = new BufferedReader(new FileReader(encFile))) {
            double goodHits = 0;
            double totalHits = 0;
            while (encFileBuf.ready()) {
                char[] signs = {'.', ',', '?', '!'};
                String lineFromFile = encFileBuf.readLine();
                char[] lineSymbolArray = lineFromFile.toCharArray();
                for (int i = 0; i < lineSymbolArray.length - 1; i++) {
                    char iSymbol = lineSymbolArray[i];
                    for (int j = 0; j < signs.length; j++) {
                        if (iSymbol == signs[j]) {
                            totalHits++;
                            if (lineSymbolArray[i + 1] == ' ') {
                                goodHits++;
                            }
                        }
                    }
                }
            }
            double luck = goodHits / totalHits;
            if (luck > 0.8) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("По заданному адресу файла для чтения не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void cryptoAnal(String encFile, String statFile, String deFile) {
        HashMap<Character, Integer> encryptedMap = new HashMap<>();
        HashMap<Character, Integer> statAnalMap = new HashMap<>();
        try (BufferedReader encFileBuf = new BufferedReader(new FileReader(encFile));
             BufferedReader statFileBuf = new BufferedReader(new FileReader(statFile))) {
            while (encFileBuf.ready()) {
                String s1 = encFileBuf.readLine().toLowerCase(Locale.ROOT);
                char[] chf1 = s1.toCharArray();
                for (int i = 0; i < chf1.length; i++) {
                    if (encryptedMap.get(chf1[i]) != null) {
                        for (Map.Entry<Character, Integer> pair : encryptedMap.entrySet()) {
                            if (pair.getKey().equals(chf1[i])) {
                                encryptedMap.put(pair.getKey(), pair.getValue() + 1);
                            }
                        }
                    } else encryptedMap.put(chf1[i], 1);
                }
            }

            while (statFileBuf.ready()) {
                String s2 = statFileBuf.readLine().toLowerCase(Locale.ROOT);
                char[] chf2 = s2.toCharArray();
                for (int i = 0; i < chf2.length; i++) {
                    if (statAnalMap.get(chf2[i]) != null) {
                        for (Map.Entry<Character, Integer> pair : statAnalMap.entrySet()) {
                            if (pair.getKey().equals(chf2[i])) {
                                statAnalMap.put(pair.getKey(), pair.getValue() + 1);
                            }
                        }
                    } else statAnalMap.put(chf2[i], 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Character, Integer> sortEncMap = encryptedMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Map<Character, Integer> sortStatAnalMap = statAnalMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        LinkedHashMap<Character, Character> finalMap = new LinkedHashMap<>();

        Iterator<Map.Entry<Character, Integer>> it1 = sortEncMap.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> it2 = sortStatAnalMap.entrySet().iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Map.Entry<Character, Integer> pair1 = it1.next();
            Map.Entry<Character, Integer> pair2 = it2.next();
            finalMap.put(pair1.getKey(), pair2.getKey());
        }

        try (BufferedReader encryptedFile = new BufferedReader(new FileReader(encFile));
             BufferedWriter decryptedFile = new BufferedWriter(new FileWriter(deFile))) {
            while (encryptedFile.ready()) {
                String st = encryptedFile.readLine().toLowerCase(Locale.ROOT);
                char[] encryptedFileArray = st.toCharArray();
                char[] result = new char[encryptedFileArray.length];
                for (int i = 0; i < encryptedFileArray.length; i++) {
                    char iSymbol = encryptedFileArray[i];
                    for (Map.Entry<Character, Character> pair : finalMap.entrySet()) {
                        if (pair.getKey().equals(iSymbol)) {
                            result[i] = pair.getValue();
                        }
                    }

                }
                decryptedFile.write(result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("По заданному адресу файла для чтения не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
