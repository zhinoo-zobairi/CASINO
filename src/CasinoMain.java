import javax.swing.*;

public class CasinoMain {
    private static JFrame currentFrame; // Speichert das aktuelle Fenster

    // Zeigt das Login-Fenster an
    public static void showMainFrame() {
        switchFrame(new CasinoLoginUI());
    }

    // Öffnet das Casino-Hauptfenster mit Benutzername
    public static void showSecondFrame(String username) {
        switchFrame(new Casino(username));
    }

    // Öffnet das Profil-Fenster
    public static void showProfil(String username){
        switchFrame(new Profile(username));
    }

    // Öffnet die Anleitungen
    public static void showInstructions(String username){
        switchFrame(new Instructions(username));
    }

    // Wechselt das aktuelle Fenster
    private static void switchFrame(JFrame newFrame) {
        if (currentFrame != null) {
            currentFrame.dispose(); // Schließt das alte Fenster
        }
        currentFrame = newFrame;
        currentFrame.setVisible(true);
    }

    // Startet das Programm mit dem Login-Fenster
    public static void main(String[] args) {
        showMainFrame(); 
    }
}