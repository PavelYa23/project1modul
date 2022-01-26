
import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class project1modul {

    public static void main(String[] args) {
        String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя., -?!—";
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
                // while () {

                // }
            }
        }


    }

    public static void crypt(String file1, String file2, int key) {
        try (BufferedReader f1 = new BufferedReader(new FileReader(file1));
             BufferedWriter f2 = new BufferedWriter(new FileWriter(file2))) {
            String alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя'., -?!—";
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
//            while (br.ready()) {
//
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
