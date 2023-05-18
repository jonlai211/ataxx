package ataxx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

class GUI implements View, CommandSource, Reporter {
    private Game game;
    private Board board;
    private JFrame frame;
    private Consumer<Void> playConsumer;
    private JButton[][] buttons;
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
        initBoard();


        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 60));
        bottomPanel.setLayout(null);
        String[] options = {"AI", "Manual"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(100, 30));
        String[] options2 = {"AI", "Manual"};
        JComboBox<String> comboBox2 = new JComboBox<>(options2);
        comboBox2.setPreferredSize(new Dimension(100, 30));
        JLabel label1 = new JLabel("Red: ");
        label1.setForeground(Color.RED);
        label1.setPreferredSize(new Dimension(50, 30));
        JLabel label2 = new JLabel("Blue: ");
        label2.setForeground(Color.BLUE);
        label2.setPreferredSize(new Dimension(50, 30));
        bottomPanel.add(label1);
        JButton startButton = new JButton("Play");
        startButton.setBackground(Color.GREEN);
        startButton.setPreferredSize(new Dimension(100, 30));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        label1.setBounds(10, 15, label1.getPreferredSize().width, label1.getPreferredSize().height);
        bottomPanel.add(comboBox);
        comboBox.setBounds(50, 15, comboBox.getPreferredSize().width, comboBox.getPreferredSize().height);
        bottomPanel.add(label2);
        label2.setBounds(200, 15, label2.getPreferredSize().width, label2.getPreferredSize().height);
        bottomPanel.add(comboBox2);
        comboBox2.setBounds(240, 15, comboBox2.getPreferredSize().width, comboBox2.getPreferredSize().height);
        bottomPanel.add(startButton);
        startButton.setBounds(680, 15, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
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
    public void initBoard() {
        buttons = new JButton[7][7];
        JPanel boardPanel = new JPanel(new GridLayout(7, 7));

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j] = new JButton();
                if (i == 0 && j == 0 || i == 6 && j == 6) {
                    buttons[i][j].setBackground(Color.BLUE);
                } else if (i == 0 && j == 6 || i == 6 && j == 0) {
                    buttons[i][j].setBackground(Color.RED);
                }
                boardPanel.add(buttons[i][j]);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);
    }

    private ActionListener handleButtonClick(int i, int j, Game game) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (lastClickedButtonPoint != null) {
//                    PieceState state = game.getAtaxxBoard().getContent(toColumn(lastClickedButtonPoint.y), toRow(lastClickedButtonPoint.x));
//                    buttons[lastClickedButtonPoint.x][lastClickedButtonPoint.y].setBackground(getColor(state));
//                    for (int x = -1; x <= 1; x++) {
//                        for (int y = -1; y <= 1; y++) {
//                            if (lastClickedSurroundingPoints[x+1][y+1] != null) {
//                                Point p = lastClickedSurroundingPoints[x+1][y+1];
//                                state = game.getAtaxxBoard().getContent(toColumn(p.y), toRow(p.x));
//                                buttons[p.x][p.y].setBackground(getColor(state));
//                                lastClickedSurroundingPoints[x+1][y+1] = null;
//                            }
//                        }
//                    }
//                }

                JButton button = buttons[i][j];
                button.setBackground(Color.GREEN);
                lastClickedButton = button;

//                for (int x = -1; x <= 1; x++) {
//                    for (int y = -1; y <= 1; y++) {
//                        if (i+x >= 0 && i+x < 7 && j+y >= 0 && j+y < 7) {
//                            buttons[i+x][j+y].setBackground(Color.YELLOW);
//                            lastClickedSurroundingButtons[x+1][y+1] = buttons[i+x][j+y];
//                        }
//                    }
//                }
            }
        };
    }

    private Color getColor(PieceState state) {
        if (state == PieceState.RED) {
            return Color.RED;
        } else if (state == PieceState.BLUE) {
            return Color.BLUE;
        } else if (state == PieceState.BLOCKED) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    private char toRow(int i) {
        return (char) ('1' + i);
    }

    private char toColumn(int j) {
        return (char) ('a' + j);
    }

    public void startGame() {
        if (game == null) {
            game = new Game(this, this, this);
            System.exit(game.play());
        }
    }

    // These methods could be modified

    @Override
    public void update(Board board) {
        board = new Board();
        buttons = new JButton[Board.ONESIDE][Board.ONESIDE];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                char r = (char) ('1' + i);
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
            }
        }
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
