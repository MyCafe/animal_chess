import com.mingye.components.GameFrame;
import com.mingye.service.GameService;

import javax.swing.*;


/**
 * 主类
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            GameService service = new GameService();
            new GameFrame(service).setVisible(true);
        });
    }
}