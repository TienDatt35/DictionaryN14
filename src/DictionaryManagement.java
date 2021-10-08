import java.io.*;
import java.util.*;


public class DictionaryManagement {

    public static void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập số lượng từ cần thêm:  ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Nhập từ tiếng Anh: ");
            String engWord = sc.nextLine();
            System.out.println("Nhập nghĩa tiếng Việt: ");
            String vieWord = sc.nextLine();
            Word word = new Word(engWord, vieWord);
            Dictionary.words.add(word);
        }

        Collections.sort(Dictionary.words);
        return;
    }

    private static final String url = "src/dictionaries.txt";

    public static void insertFromFile() {

        try {
            File file = new File(url);
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = reader.readLine()) != null) {
                int tmp = line.indexOf("\t");
                String Eng = line.substring(0, tmp);
                String Vie = line.substring(tmp + 1, line.length());
                Word newWord = new Word(Eng, Vie);
                Dictionary.words.add(newWord);
            }

            Collections.sort(Dictionary.words);
            reader.close();
        } catch (Exception e) {
        }
        return;
    }

    public static String dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ cần tìm:  ");
        String searchWord = sc.nextLine();

        //Collections.sort(Dictionary.words);
        int index = Collections.binarySearch(Dictionary.words, new Word(searchWord, null));
        if (index >= 0) {
            return Dictionary.words.get(index).getWord_explain();
        } else {
            return "Not Found!";
        }
    }

    public static void addWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ tiếng Anh muốn thêm: ");
        String engWord = sc.nextLine();

        int index = Collections.binarySearch(Dictionary.words, new Word(engWord, null));
        if (index >= 0) {
            System.out.println("Từ này đã tồn tại!");
            return;
        } else {
            System.out.println("Nhập nghĩa tiếng Việt: ");
            String vieWord = sc.nextLine();
            Word word = new Word(engWord, vieWord);
            Dictionary.words.add(word);
            Collections.sort(Dictionary.words);
            return;
        }
    }

    public static void modifiWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ muốn sửa: ");
        String engWord = sc.nextLine();
        //Collections.sort(Dictionary.words);

        int index = Collections.binarySearch(Dictionary.words, new Word(engWord, null));
        if (index >= 0) {
            System.out.println("Sửa nghĩa thành: ");
            String vieWord = sc.nextLine();
            Dictionary.words.get(index).setWord_explain(vieWord);
        } else {
            System.out.println("Not Found");
            return;
        }
    }

    public static void deleteWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ muốn xoá: ");
        String engWord = sc.nextLine();
        //Collections.sort(Dictionary.words);
        int index = Collections.binarySearch(Dictionary.words, new Word(engWord, null));
        if (index >= 0) {
            Dictionary.words.remove(Dictionary.words.get(index));
            System.out.println("Đã xoá!");
        } else {
            System.out.println("Not Found");
            return;
        }
    }

    public static void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Search Word: ");
        String searchWord = sc.nextLine();
        for (Word i : Dictionary.words) {
            if (i.getWord_target().indexOf(searchWord) == 0) {
                System.out.println(i.getWord_target());
            }
        }
    }

    public static void dictionaryExportToFile() {
        try {
            File file = new File(url);
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            for (int i = 0; i < Dictionary.words.size(); i++) {
                outputStreamWriter.write(Dictionary.words.get(i).getWord_target() + "\t" + Dictionary.words.get(i).getWord_explain() + "\n");
            }
            outputStreamWriter.flush();
        } catch (Exception e) {
        }
    }
}
