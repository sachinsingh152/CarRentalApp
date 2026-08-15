package application.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final KeyCombination SCREENSHOT_KEY = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);

    public static void attachScreenshotListener(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (SCREENSHOT_KEY.match(event)) {
                captureScreenshot(scene);
            }
        });
    }

    private static void captureScreenshot(Scene scene) {
        WritableImage image = scene.snapshot(null);
        File dir = new File("screenshots");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File file = new File(dir, "screenshot_" + timestamp + ".png");
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Screenshot saved successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot.");
            e.printStackTrace();
        }
    }
}
