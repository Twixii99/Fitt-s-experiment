package alexu.edu.eg.fetts.app;

import org.jfree.ui.RefineryUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Designer implements ActionListener {

    // random numbers generator
    private final Random random_generator = new Random();

    // frame that contains the the whole app
    private final JFrame FRAME;
    // panel for the components
    private final JPanel Bg_PANEL;
    // panel for the left buttons
    private final JPanel LEFT_BUTTONS_CONTAINER;
    // panel for the right buttons
    private final JPanel RIGHT_BUTTONS_CONTAINER;
    // left button
    private final JButton LEFT_BUTTON;
    // right button
    private final JButton RIGHT_BUTTON;
    // plot button
    private final JButton PLOT;

    // frame width
    private final int FRAME_WIDTH = 1026;
    // frame height
    private final int FRAME_HEIGHT = 460;
    // program name
    private final String FRAME_NAME = "Fitt's experimant";

    private final int[] LEFT_BUTTONS_CONTAINER_PADDINGS = {29, 176, 484, 108};
    private final int[] RIGHT_BUTTONS_CONTAINER_PADDINGS = {513, 176, 484, 108};

    // counter and time for changing the buttons states
    private int cnt = 0;
    private long startTime;
    private long elapsedTime;
    private int no_of_experiments;

    // information of buttons
    private int commonWidth;
    private int leftButtonPosition;
    private int rightButtonPosition;

    public Designer() {
        this.FRAME = new JFrame(this.FRAME_NAME);
        this.Bg_PANEL = new JPanel();
        this.LEFT_BUTTONS_CONTAINER = new JPanel();
        this.RIGHT_BUTTONS_CONTAINER = new JPanel();
        this.LEFT_BUTTON = new JButton("L");
        this.RIGHT_BUTTON = new JButton("R");
        this.PLOT = new JButton("More Data");
        this.makeComponents();
        this.makeBackGroundPanel();
        this.makeFrame();
    }

    private void makeComponents() {
        // set colors of the buttons and the panel contains them .
        this.LEFT_BUTTONS_CONTAINER.setBackground(new Color(190, 158, 102));
        this.RIGHT_BUTTONS_CONTAINER.setBackground(new Color(190, 158, 102));
        this.LEFT_BUTTON.setBackground(Color.BLACK);
        this.LEFT_BUTTON.setOpaque(true);
        this.RIGHT_BUTTON.setBackground(Color.BLACK);
        this.RIGHT_BUTTON.setOpaque(true);

        // add actions to buttons
        this.LEFT_BUTTON.addActionListener(this);
        this.RIGHT_BUTTON.addActionListener(this);
        this.PLOT.addActionListener(this);

        // set PLOT button position
        this.PLOT.setBounds(443, 320, 140, 80);
        this.PLOT.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.PLOT.setFont(new Font("Serif", Font.BOLD, 24));
        this.PLOT.setEnabled(false);

        // set position of the buttons and the panel contains them .
        this.LEFT_BUTTONS_CONTAINER.setBounds(this.LEFT_BUTTONS_CONTAINER_PADDINGS[0], this.LEFT_BUTTONS_CONTAINER_PADDINGS[1],
                this.LEFT_BUTTONS_CONTAINER_PADDINGS[2], this.LEFT_BUTTONS_CONTAINER_PADDINGS[3]);
        this.RIGHT_BUTTONS_CONTAINER.setBounds(this.RIGHT_BUTTONS_CONTAINER_PADDINGS[0], this.RIGHT_BUTTONS_CONTAINER_PADDINGS[1],
                this.RIGHT_BUTTONS_CONTAINER_PADDINGS[2], this.RIGHT_BUTTONS_CONTAINER_PADDINGS[3]);
        this.setLeftRightButtonsPositions();

        // set layouts for the containers
        this.LEFT_BUTTONS_CONTAINER.setLayout(null);
        this.RIGHT_BUTTONS_CONTAINER.setLayout(null);

        // adding the buttons to the panel .
        this.LEFT_BUTTONS_CONTAINER.add(this.LEFT_BUTTON);
        this.RIGHT_BUTTONS_CONTAINER.add(this.RIGHT_BUTTON);
    }

    private void setLeftRightButtonsPositions() {
        this.commonWidth = this.random_generator.nextInt(484);
        this.leftButtonPosition = this.random_generator.nextInt(484 - this.commonWidth);
        this.rightButtonPosition = this.random_generator.nextInt(484 - this.commonWidth);

        this.LEFT_BUTTON.setBounds(this.leftButtonPosition, 3, this.commonWidth, 100);
        this.RIGHT_BUTTON.setBounds(this.rightButtonPosition, 3, this.commonWidth, 100);
        this.LEFT_BUTTONS_CONTAINER.repaint();
        this.RIGHT_BUTTONS_CONTAINER.repaint();
    }

    private void makeBackGroundPanel() {
        this.Bg_PANEL.setBackground(new Color(230, 216, 190));
        this.Bg_PANEL.setLayout(null);
        this.Bg_PANEL.add(this.LEFT_BUTTONS_CONTAINER);
        this.Bg_PANEL.add(this.RIGHT_BUTTONS_CONTAINER);
        this.Bg_PANEL.add(this.PLOT);
    }

    private void makeFrame() {
        this.FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.FRAME.add(this.Bg_PANEL);
        this.FRAME.pack();
        this.FRAME.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
        this.FRAME.setResizable(false);
        this.FRAME.setLocationRelativeTo(null);
        this.FRAME.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("L") || action.equals("R")) {
            if(action.equals("L")) {
                this.LEFT_BUTTON.setEnabled(false);
                this.RIGHT_BUTTON.setEnabled(true);
            } else {
                this.LEFT_BUTTON.setEnabled(true);
                this.RIGHT_BUTTON.setEnabled(false);
            }
            if (cnt == 1) {
                this.elapsedTime = System.currentTimeMillis() - this.startTime;
                try {
                    DataCollector.enterData(484 - this.leftButtonPosition - this.commonWidth + this.rightButtonPosition,
                            this.commonWidth, this.elapsedTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.no_of_experiments++;
                if (this.no_of_experiments > 1) {
                    this.PLOT.setEnabled(true);
                    this.PLOT.setText("PLOT");
                }
                this.setLeftRightButtonsPositions();
            } else
                this.startTime = System.currentTimeMillis();
            cnt ^= 1;
        } else if (action.equals("PLOT")) {
            this.no_of_experiments = 0;
            try {
                DataCollector.myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PlotterInvoker plotterInvoker =
                    new PlotterInvoker("fitt's experimant");

            SwingUtilities.invokeLater(() -> {
                plotterInvoker.setSize(1400, 800);
                plotterInvoker.setLocationRelativeTo(null);
                plotterInvoker.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                plotterInvoker.setVisible(true);
            });
        }
    }
}
