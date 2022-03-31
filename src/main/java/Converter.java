import java.io.*;
import java.util.*;

public class Converter {
    private List<File> files;
     private File dir;
    // сделать джарник
    //
    public Converter (File dir) {
        this.dir = dir;
    }
    /**
     * Метод предназначен для получения списка файлов из папки
     * @param
     */
    public void listOfFiles() {
        if (dir.isDirectory()) {
            files = Arrays.stream(dir.listFiles()).toList().stream().sorted(new FilesComparator()).toList();
        }
    }

    /**
     * Считывание отсортированных файлов из списка и запись в результирующий файл
     * @param
     */
    public String readAndWrite() {
        String resultFileName = makeResultFileName();
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.lines().forEach(el -> {
                    StringBuilder str = analize(el);
                    stringBuilder.append(str + System.lineSeparator());
                   // stringBuilder.append(el + System.lineSeparator());
                } );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(dir.toString() + "\\" + resultFileName)))) {
                out.write(stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultFileName;
    }

    private StringBuilder analize(String string) {
        StringBuilder res = new StringBuilder();
        String[] mas = string.split(" ");
        mas[0] = rowChangeRules(mas[0]);
        mas[3] = rowChangeRules(mas[3]);
        if (mas[3].contains("A")) {
            mas[3] = "B" + mas[3].substring(1);
        }
        StringBuilder firstLine = makeFirstLine(mas);
        StringBuilder secondLine =makeSecondLine(mas);
        res.append(firstLine).append(secondLine);

        return res;
    }

    private StringBuilder makeFirstLine(String[] mas) {
        StringBuilder res = new StringBuilder();
        if (mas[0].startsWith("C127") & !mas[3].startsWith("C127")) {
            mas[2] = mas[0].substring(1);
        }
        for (String str : mas) {
            res.append(str + " ");
        }
        res.append(System.lineSeparator());
        return res;
    }

    private StringBuilder makeSecondLine(String[] mas) {
        StringBuilder res = new StringBuilder();
        mas[0] = mas[3];
        if (mas[3].startsWith("C127")) {
            mas[1] = mas[3].substring(1);
        }
        for (String str : mas) {
            res.append(str + " ");
        }

        return res;
    }

    private String rowChangeRules(String row) {
        if (row.startsWith("A44")) {
            row = "A44";
        } else if (row.startsWith("A45")) {
            row = "A45";
        } else if (row.startsWith("A95")) {
            row = "A95";
        } else if (row.startsWith("C127") | row.startsWith("C000")) {
            row = row.substring(0, row.length() - 2);
        }
        return row;
    }
    public String makeResultFileName() {
        String res = "";
        String start = files.get(0).getName().split("\\.")[0];
        String end = files.get(files.size() - 1).getName().split("\\.")[0];
        res = start + "_" + end + ".log";
        return res;
    }
    public static void main(String[] args) {
        String inputDir;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите полный путь к папке в формате C:\\Users\\DashaPC\\Desktop\\test: ");
            inputDir = scanner.nextLine();
            if (new File(inputDir).isDirectory()) {
                break;
            } else {
                System.out.println("Указанный путь не является директорией. Введите полный путь к папке в формате C:\\Users\\DashaPC\\Desktop\\test: ");
            }
        }
        Converter converter = new Converter(new File(inputDir));
        converter.listOfFiles();
        String resultFileName = converter.readAndWrite();
        System.out.println("Файл " + resultFileName + " создан в папке " + inputDir + " проверяйте :)!");
    }
}
