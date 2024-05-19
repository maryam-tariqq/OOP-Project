import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class FirstWindow extends JFrame {
    JButton b;
    JLabel L;
    JProgressBar progressBar;
    JLabel background;

    FirstWindow() {
        try {
            Image backgroundImage = ImageIO.read(new File("image_10.jpg"));
            background = new JLabel(new ImageIcon(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
            background = new JLabel("Background Image Not Found");
        }
        setContentPane(background);
        setLayout(null);
        getContentPane().setBackground(new Color(173, 216, 230)); // Light Blue

        b = new JButton("PRESS TO CONTINUE");
        L = new JLabel("WEATHER CLUES", SwingConstants.CENTER);
        progressBar = new JProgressBar();

        add(b);
        add(L);
        setLayout(null);

        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonX = (getWidth() - buttonWidth) / 2;
        int buttonY = (getHeight() - buttonHeight) / 2;
        b.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        L.setFont(new Font("Cascadia Code", Font.BOLD, 42));
        b.setFont(new Font("Cascadia Code", Font.BOLD, 16));
        b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.setBackground(new Color(70, 130, 180));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(70, 130, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(new Color(70, 130, 180));
            }
        });

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressBar.setVisible(true);
                b.setEnabled(false);

                // Simulate a task
                Timer timer = new Timer(5000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                dispose(); // Dispose the FirstWindow
                                SecondWindow w2;
                                w2 = new SecondWindow();
                                w2.setVisible(true);

                                try {
                                    SecondWindow window = new SecondWindow();
                                    window.setVisible(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });


        L.setBounds(0, 100, getWidth(), 50);
        progressBar.setIndeterminate(true);
        progressBar.setString("Loading...");
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 0, 139));
        progressBar.setBackground(new Color(173, 216, 230));
        progressBar.setBounds((getWidth() - buttonWidth) / 2, buttonY + buttonHeight + 20, buttonWidth, 30);
        progressBar.setVisible(false);
        add(progressBar);

        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resizeButton();
                resizeLabel();
                resizeProgressBar();
            }
        });
    }

    private void resizeButton() {
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonX = (getWidth() - buttonWidth) / 2;
        int buttonY = (getHeight() - buttonHeight) / 2;
        b.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    }

    private void resizeLabel() {
        L.setBounds(0, 100, getWidth(), 50);
    }

    private void resizeProgressBar() {
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonX = (getWidth() - buttonWidth) / 2;
        int buttonY = (getHeight() - buttonHeight) / 2;
        progressBar.setBounds(buttonX, buttonY + buttonHeight + 20, buttonWidth, 30);
    }
}
