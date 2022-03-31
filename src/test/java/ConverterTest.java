import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConverterTest {
    @Test
    public void whenCompare() {
        File file = new File("C:\\Users\\DashaPC\\Desktop\\test\\Новая папка\\itog.log");
        File file1 = new File("C:\\Users\\DashaPC\\Desktop\\test\\Новая папка\\rightitog.log");
        assertThat(read(file).toString().equals(read(file1).toString()), is(true));
    }

    private StringBuilder read(File file) {
        StringBuilder res = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines().forEach(el -> res.append(el + System.lineSeparator()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
