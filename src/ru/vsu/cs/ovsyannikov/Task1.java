package ru.vsu.cs.ovsyannikov;

import ru.vsu.cs.ovsyannikov.util.MyLinkedList;
import ru.vsu.cs.ovsyannikov.util.MyList;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Task1 extends JDialog {

    private JPanel contentPane;
    private JButton openButton;
    private JLabel listDataLabel;
    private JButton modifyListButton;
    private JLabel modifiedListDataLabel;

    private final MyList<Integer> list = new MyLinkedList<>();

    public Task1() {
        setContentPane(contentPane);
        setModal(true);

        openButton.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "/resources/task1");
            fileChooser.showOpenDialog(Task1.this);
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                System.out.println("File not selected: noop");
                return;
            }
            list.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                String[] stringNumbers = line.split(" ");
                for (String s : stringNumbers) {
                    list.add(Integer.parseInt(s));
                }
                listDataLabel.setText(list.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        modifyListButton.addActionListener(e -> {

            int minIndex = 0;
            int maxIndex = 0;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(list.size() - i) <= list.get(minIndex)) {
                    minIndex = list.size() - i;
                }
                if (list.get(i) >= list.get(maxIndex)) {
                    maxIndex = i;
                }
            }
            System.out.println("Min id:" + minIndex);
            System.out.println("Max id:" + maxIndex);

            list.swapNodes(minIndex, maxIndex);
            modifiedListDataLabel.setText(list.toString());
        });

        // Closed application when pressed X button
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Close application when pressed ESC
        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Task1 dialog = new Task1();
        dialog.pack();
        dialog.setTitle("Task 1");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
