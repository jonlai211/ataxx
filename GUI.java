package ataxx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI implements View, CommandSource, Reporter {
    private JFrame frame;
    private JButton[][] buttons;

    GUI(String ataxx) {
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
        buttons = new JButton[7][7];
        JPanel boardPanel = new JPanel(new GridLayout(7, 7));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j] = new JButton();
                boardPanel.add(buttons[i][j]);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 60));
        bottomPanel.setLayout(null);
        String[] options = { "AI", "Manual"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(100, 30));
        String[] options2 = { "AI", "Manual"};
        JComboBox<String> comboBox2 = new JComboBox<>(options2);
        comboBox2.setPreferredSize(new Dimension(100, 30));
        JLabel label1 = new JLabel("Red: ");
        label1.setForeground(Color.RED);
        label1.setPreferredSize(new Dimension(50, 30));
        JLabel label2 = new JLabel("Blue: ");
        label2.setForeground(Color.BLUE);
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
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        frame.setLocation((width-frame.getWidth())/2, (height-frame.getHeight())/2);
        frame.setVisible(true);
    }

    // Add some codes here






    // These methods could be modified
	
    @Override
    public void update(Board board) {

    }

    @Override
    public String getCommand(String prompt) {
        return null;
    }

    @Override
    public void announceWinner(PieceState state) {

    }

    @Override
    public void announceMove(Move move, PieceState player) {

    }

    @Override
    public void message(String format, Object... args) {

    }

    @Override
    public void error(String format, Object... args) {

    }

    public void setVisible(boolean b) {
		
    }

    public void pack() {
		
    }
	
}
