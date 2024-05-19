import javax.swing.*;
public class Main{ public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            // Create the first window and show it
            FirstWindow f = new FirstWindow();
            f.setVisible(true);
            ImageIcon icon = new ImageIcon("IMG.png"); // Provide path to your icon image file
            f.setIconImage(icon.getImage());
       }
    });
}
}
