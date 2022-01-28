
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Project1modul {
    static String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя., -?!—";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число");
        int i = Integer.parseInt(scanner.nextLine());
        switch (i) {
            case 1: {
                System.out.println("Введите файл1");
                String file1 = scanner.nextLine();
                System.out.println("Введите файл 2");
                String file2 = scanner.nextLine();
                System.out.println("Введите ключ");
                int key = Integer.parseInt(scanner.nextLine());
                crypt(file1, file2, key);
                break;

            }
            case 2: {
                System.out.println("Введите файл1");
                String file1 = scanner.nextLine();
                System.out.println("Введите файл 2");
                String file2 = scanner.nextLine();
                System.out.println("Введите ключ");
                int key = Integer.parseInt(scanner.nextLine());
                if (key > alphabet.length()) {
                    key = key % alphabet.length();
                }
                int key2 = alphabet.length() - key;
                crypt(file1, file2, key2);
                break;

            }
            case 3: {
                System.out.println("Введите файл1");
                String file1 = scanner.nextLine();
                System.out.println("Введите файл 2");
                String file2 = scanner.nextLine();
                char[] alp = alphabet.toCharArray();
                for (int j = 1; j < alp.length; j++) {
                    crypt(file1, file2, j);
                    if (space(file2)) {
                        break;
                    }
                }
            }
            case 4: {
                System.out.println("Введите файл1");
                String file1 = scanner.nextLine();
                System.out.println("Введите файл 2");
                String file2 = scanner.nextLine();
                System.out.println("Введите файл 3");
                String file3 = scanner.nextLine();
                cryptoAnal(file1, file2, file3);
            }
        }


    }

    public static void crypt(String file1, String file2, int key) {
        try (BufferedReader f1 = new BufferedReader(new FileReader(file1));
             BufferedWriter f2 = new BufferedWriter(new FileWriter(file2))) {
            char[] alp = alphabet.toCharArray();
            while (f1.ready()) {
                String st = f1.readLine().toLowerCase(Locale.ROOT);
                char[] chfile1 = st.toCharArray();
                char[] result = new char[chfile1.length];
                for (int i = 0; i < chfile1.length; i++) {
                    char cfile = chfile1[i];
                    for (int j = 0; j < alp.length; j++) {
                        char ch = alp[j];
                        if (ch == cfile) {
                            result[i] = alp[(j + key) % alp.length];
                        }
                    }
                }
                f2.write(result);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("По заданному адресу файла для чтения не существует");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static boolean space(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            double good = 0;
            double vsego = 0;
            while (br.ready()) {

                char[] znaki = {'.', ',', '?', '!'};
                String st = br.readLine();
                char[] chfile1 = st.toCharArray();
                for (int i = 0; i < chfile1.length - 1; i++) {
                    char z = chfile1[i];
                    for (int j = 0; j < znaki.length; j++) {
                        if (z == znaki[j]) {
                            vsego++;
                            if (chfile1[i + 1] == ' ') {
                                good++;
                            }
                        }
                    }

                }

            }
            double pi = good / vsego;
            if (pi > 0.8) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void cryptoAnal(String file1, String file2, String file3) {
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        try (BufferedReader f1 = new BufferedReader(new FileReader(file1));
             BufferedReader f2 = new BufferedReader(new FileReader(file2))) {
            while (f1.ready()) {
                String s1 = f1.readLine().toLowerCase(Locale.ROOT);
                char[] chf1 = s1.toCharArray();
                for (int i = 0; i < chf1.length; i++) {
                    if (map1.get(chf1[i]) != null) {
                        for (Map.Entry<Character, Integer> pair : map1.entrySet()) {
                            if (pair.getKey().equals(chf1[i])) {
                                map1.put(pair.getKey(), pair.getValue() + 1);
                            }
                        }
                    } else map1.put(chf1[i], 1);
                }
            }

            while (f2.ready()) {
                String s2 = f2.readLine().toLowerCase(Locale.ROOT);
                char[] chf2 = s2.toCharArray();
                for (int i = 0; i < chf2.length; i++) {
                    if (map2.get(chf2[i]) != null) {
                        for (Map.Entry<Character, Integer> pair : map2.entrySet()) {
                            if (pair.getKey().equals(chf2[i])) {
                                map2.put(pair.getKey(), pair.getValue() + 1);
                            }
                        }
                    } else map2.put(chf2[i], 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Character, Integer> resultmap1 = map1.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Map<Character, Integer> resultmap2 = map2.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        LinkedHashMap<Character, Character> itog = new LinkedHashMap<>();

        Iterator<Map.Entry<Character, Integer>> it1 = resultmap1.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> it2 = resultmap2.entrySet().iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Map.Entry<Character, Integer> pair1 = it1.next();
            Map.Entry<Character, Integer> pair2 = it2.next();
            itog.put(pair1.getKey(), pair2.getKey());
        }
        itog.entrySet().forEach(System.out::println);

        try (BufferedReader fileCrypted = new BufferedReader(new FileReader(file1));
             BufferedWriter fileUncrypted = new BufferedWriter(new FileWriter(file3))) {
            while (fileCrypted.ready()) {
                String st = fileCrypted.readLine().toLowerCase(Locale.ROOT);
                char[] chfile1 = st.toCharArray();
                char[] result = new char[chfile1.length];
                for (int i = 0; i < chfile1.length; i++) {
                    char cfile = chfile1[i];
                    for (Map.Entry<Character, Character> pair : itog.entrySet()) {
                        if (pair.getKey().equals(cfile)) {
                            result[i] = pair.getValue();
                        }
                    }

                }
                fileUncrypted.write(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
