import ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        // Start the application by opening Login screen
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });

    }
}
