import java.io.File;
import java.util.Comparator;

public class FilesComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        return Integer.parseInt(o1.getName().split("_")[2]) - Integer.parseInt(o2.getName().split("_")[2]);
    }
}
