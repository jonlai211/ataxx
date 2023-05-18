package ataxx;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

class GUI implements View, CommandSource, Reporter {
    private Board board;
    private JFrame frame;
    private Consumer<Void> playConsumer;
    private JButton[][] buttons = new JButton[7][7];
    private JButton lastClickedButton = null;
    private JButton[][] lastClickedSurroundingButtons = new JButton[3][3];
    private Point lastClickedButtonPoint = null;
    private Point[][] lastClickedSurroundingPoints = new Point[3][3];

    GUI(String ataxx) {
        board = new Board();
        frame = new JFrame(ataxx);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 100));
        JLabel infoLabel = new JLabel("Game Time");
        infoLabel.setFont(infoLabel.getFont().deriveFont(60.0f));
        topPanel.add(infoLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // center panel

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 60));
        bottomPanel.setLayout(null);
        String[] options = {"AI", "Manual"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(100, 30));
        String[] options2 = {"Manual"};
        JComboBox<String> comboBox2 = new JComboBox<>(options2);
        comboBox2.setPreferredSize(new Dimension(100, 30));
        JLabel label1 = new JLabel("BLUE: ");
        label1.setForeground(Color.BLUE);
        label1.setPreferredSize(new Dimension(50, 30));
        JLabel label2 = new JLabel("Red: ");
        label2.setForeground(Color.RED);
        label2.setPreferredSize(new Dimension(50, 30));
        bottomPanel.add(label1);
        label1.setBounds(10, 15, label1.getPreferredSize().width, label1.getPreferredSize().height);
        bottomPanel.add(comboBox);
        comboBox.setBounds(50, 15, comboBox.getPreferredSize().width, comboBox.getPreferredSize().height);
        bottomPanel.add(label2);
        label2.setBounds(200, 15, label2.getPreferredSize().width, label2.getPreferredSize().height);
        bottomPanel.add(comboBox2);
        comboBox2.setBounds(240, 15, comboBox2.getPreferredSize().width, comboBox2.getPreferredSize().height);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(800, 1000);
        frame.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        frame.setLocation((width - frame.getWidth()) / 2, (height - frame.getHeight()) / 2);
        frame.setVisible(true);
    }

    // Add some codes here

    // These methods could be modified

    @Override
    public void update(Board board) {
        JPanel boardPanel = new JPanel(new GridLayout(7, 7));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

                char r = (char) ('1' + Math.abs(6 - i));
                char c = (char) ('a' + j);
                PieceState state = board.getContent(c, r);
                buttons[i][j] = new JButton();

                if (state == PieceState.RED) {
                    buttons[i][j].setBackground(Color.RED);
                } else if (state == PieceState.BLUE) {
                    buttons[i][j].setBackground(Color.BLUE);
                } else if (state == PieceState.BLOCKED) {
                    buttons[i][j].setBackground(Color.BLACK);
                }
                boardPanel.add(buttons[i][j]);
            }
        }

        frame.add(boardPanel);
        frame.validate();
    }

    @Override
    public String getCommand(String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }

    @Override
    public void announceWinner(PieceState state) {
        JOptionPane.showMessageDialog(frame, "The winner is: " + state);
    }

    @Override
    public void announceMove(Move move, PieceState player) {
        System.out.println("Player " + player + " made move: " + move);
    }

    @Override
    public void message(String format, Object... args) {
        JOptionPane.showMessageDialog(frame, String.format(format, args));
    }

    @Override
    public void error(String format, Object... args) {
        JOptionPane.showMessageDialog(frame, "Error: " + String.format(format, args), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void pack() {
    }

}
