import javax.swing.*;

public class CasinoMain {
    private static JFrame currentFrame; // Stores the active frame

    public static void showMainFrame() {
        switchFrame(new CasinoLoginUI());
    }

    public static void showSecondFrame() {
        switchFrame(new Casino());
    }

    public static void showProfil(){
        switchFrame(new Profile());
    }

    private static void switchFrame(JFrame newFrame) {
        if (currentFrame != null) {
            currentFrame.dispose(); // Close the previous frame
        }
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    public static void main(String[] args) {
        showMainFrame(); // Start the application with MainFrame
    }
}