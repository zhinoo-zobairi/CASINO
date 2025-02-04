import javax.swing.*;

public class CasinoMain {
    private static JFrame currentFrame; // Stores the active frame

    public static void showMainFrame() {
        switchFrame(new CasinoLoginUI());
    }

    public static void showSecondFrame(String username) {
        switchFrame(new Casino(username));
    }

    public static void showProfil(String username){
        switchFrame(new Profile(username));
    }

    public static void showInstructions(String username){
        switchFrame(new Instructions(username));
    }

    private static void switchFrame(JFrame newFrame) {
        if (currentFrame != null) {
            currentFrame.dispose(); // Close the previous frame
        }
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    public static void main(String[] args) {
        showMainFrame(); 
    }
}