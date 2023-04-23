import org.junit.Test;
import org.sikuli.script.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSpotify {

    final static String PATH = "C:\\Users\\Milo\\AppData\\Roaming\\Spotify\\Spotify.exe";
    private static final Pattern searchBox = new Pattern(TestSpotify.class.getResource("SearchBox.png"));
    private static final Pattern searchIcon = new Pattern(TestSpotify.class.getResource("SearchIcon.png"));
    private static final Pattern playButtonType1 = new Pattern(TestSpotify.class.getResource("PlayButtonType1.png"));
    private static final Pattern playButtonType2 = new Pattern(TestSpotify.class.getResource("PlayButtonType2.png"));
    private static final Pattern pauseButton = new Pattern(TestSpotify.class.getResource("PauseButton.png"));

    private final App app = new App(PATH);

    @Test
    public void testSearchUI() throws FindFailed {
        app.open();
        Screen screen = new Screen();

        List<String> keys = new ArrayList<>();
        String key;
        // Lấy đối tượng ClassLoader để truy cập tài nguyên trong thư mục resources
        ClassLoader classLoader = TestSpotify.class.getClassLoader();

        // Lấy InputStream của tệp Key.txt
        InputStream inputStream = classLoader.getResourceAsStream("Key.txt");

        // Kiểm tra xem InputStream có tồn tại không
        if (inputStream != null) {
            try {
                // Tạo BufferedReader để đọc từng dòng từ InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                
                // Đọc từng dòng và in ra màn hình
                while ((key = reader.readLine()) != null) {
                    keys.add(key);
                }

                // Đóng BufferedReader
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không thể tìm thấy tệp Key.txt");
        }

        // Lấy 1 key ngẫu nhiên
        key = keys.get(Math.round(Math.round(Math.random() * (keys.size() - 1))));

        screen.click(searchIcon);
        screen.wait(searchBox, 3);
        screen.type(searchBox, key + Key.ENTER);

        Region result1 = new Region(395, 224, 360, 231);
        Region result2 = new Region(789, 222, 340, 45);
        Region result3 = new Region(789, 287, 340, 45);
        Region result4 = new Region(789, 344, 342, 45);
        Region result5 = new Region(789, 399, 342, 45);

        result1.hover();
        assertNotNull(result1.exists(playButtonType1.similar(0.9), 2));

        result2.hover();
        assertNotNull(result2.exists(playButtonType2.similar(0.9), 2));

        result3.hover();
        assertNotNull(result3.exists(playButtonType2.similar(0.9), 2));

        result4.hover();
        assertNotNull(result4.exists(playButtonType2.similar(0.9), 2));

        result5.hover();
        assertNotNull(result5.exists(playButtonType2.similar(0.9), 2));

        result1.hover();
        result1.click(playButtonType1);
        assertNotNull(result1.exists(pauseButton, 2));
    }
}
