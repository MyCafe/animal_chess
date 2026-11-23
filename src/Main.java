import com.mingye.components.GameFrame;
import com.mingye.service.GameService;

import javax.swing.*;


/**
 * 程序启动类
 */
public class Main {

    /**
     * Main方法
     *
     * @param args
     */
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