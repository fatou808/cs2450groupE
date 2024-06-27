package main.java.org.example.controller;
import main.java.org.example.business.CPU;
import main.java.org.example.business.IOHandler;
import org.example.data.Memory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class UVSimGUI extends JFrame {
    private Memory memory;
    private JFrame frame;
    private CPU cpu;
    private JPanel mainPanel;
    private JButton loadProgramButton;
    private JButton runProgram;
    private JTextArea outputArea;
    private IOHandler ioHandler;
    private JPanel panelTop;
    private JButton resetProgramButton;

    public UVSimGUI() {
        memory = new Memory();
        frame = new JFrame("UV Sim");
        ioHandler = new IOHandler(memory, this);
        cpu = new CPU(memory, ioHandler);
        resetProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgram();
            }
        });
    }

    private void resetProgram() {
        memory.clear();
        cpu.reset();
        outputArea.setText("");
        appendOutput("Program reset.\n");
    }

    public void createAndShowGUI() {
        JOptionPane.showMessageDialog(null,
                "Welcome to the UV Sim!\n" +
                        "Click the Load program button to load a program file from your local machine.\n" +
                        "Click the run program button to run the program from the file\n" +
                        "When prompted enter a 4 digit instruction.\n\n" +
                        "To reset the simulator and run a new program, click the reset button");
        outputArea.setEditable(false);
        frame.setContentPane(mainPanel);
        frame.setTitle("UVSIM");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        runProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runProgram();
            }
        });
        loadProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProgram();
            }
        });
    }

    private void loadProgram() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(selectedFile)) {
                int i = 0;
                while (scanner.hasNextInt() && i < 100) {
                    int instruction = scanner.nextInt();
                    memory.setData(i, instruction);
                    i++;
                }
                appendOutput("Program loaded successfully.\n");
            } catch (Exception e) {
                appendOutput("Error loading program: " + e.getMessage() + "\n");
            }
        }
    }

    private void runProgram() {
        cpu.execute();
        appendOutput("Program executed.\n");
    }

    public void appendOutput(String message) {
        outputArea.append(message + "\n");
    }

    public int getInputField() {
        String inputText = JOptionPane.showInputDialog("Please enter a four digit instruction: ");
        while(!inputText.matches("^[-]?\\d{4}$")) {
           inputText = JOptionPane.showInputDialog("Please enter a four digit instruction: ");
        }

        return Integer.parseInt(inputText);
    }
    public void setInputField(int inputField) {

    }

}
