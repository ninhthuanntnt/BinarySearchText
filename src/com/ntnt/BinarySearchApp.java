package com.ntnt;

import com.ntnt.models.DataFile;
import com.ntnt.search.BinarySearchTree;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class BinarySearchApp extends JFrame {

    private JFileChooser fileChooser;
    private JButton buttonFileChooser;
    private JButton buttonAddToBinaryTree;
    private JButton buttonSearchText;
    private JTextField searchTextInput;
    private JLabel choosedFile;
    private JLabel status;
    private File file;
    private JTextArea textAreaFileData;
    private Container container;
    private BinarySearchTree<DataFile> binarySearchTree;

    public BinarySearchApp(String title) {
        // init JFrame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(720, 480);
        this.setTitle(title);
        this.setLayout(new BorderLayout());

        // set window 10 theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        container = this.getContentPane();

        initUI();
        this.setVisible(true);
    }

    private void initUI() {
        JFrame frame = this;

        //init components
        JLabel labelFileChooser = new JLabel("Chọn file:");
        JPanel panel = new JPanel();
        JPanel searchPanel = new JPanel();

        status = new JLabel();
        choosedFile = new JLabel();
        textAreaFileData = new JTextArea();
        searchTextInput = new JTextField("", 13);
        buttonFileChooser = new JButton("Duyệt file...");
        buttonAddToBinaryTree = new JButton("Thêm vào cây nhị phân");
        buttonSearchText = new JButton("Tìm");

        //Config
        // search text field
        searchTextInput.setToolTipText("Nhập từ bạn muốn tìm kiếm ở đây");
        //textarea
        textAreaFileData.setEditable(false);
        JScrollPane scrollableTextArea = new JScrollPane(textAreaFileData);

        //scrollableTextArea
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //binding event
        buttonFileChooser.addActionListener(this::doLoadDataFromFile);
        buttonAddToBinaryTree.addActionListener(this::doAddToBinaryTree);
        buttonSearchText.addActionListener(this::doSearchText);
        //Status bar
        status.setText("Sẵn sàng nhận dữ liệu");
        status.setBorder(new EmptyBorder(10, 0, 0, 0));

        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        //Temp panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        // set component to layout
        panel.add(buttonAddToBinaryTree);
        panel.add(labelFileChooser);
        panel.add(buttonFileChooser);
        panel.add(choosedFile);

        searchPanel.add(searchTextInput);
        searchPanel.add(buttonSearchText);

        headerPanel.add(panel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.CENTER);

        container.add(headerPanel, BorderLayout.NORTH);
        container.add(scrollableTextArea, BorderLayout.CENTER);
        container.add(status, BorderLayout.SOUTH);
    }

    private void doLoadDataFromFile(ActionEvent e) {
        showStatus("Đang đọc xong dữ liệu từ file");

        JFileChooser fileChooser = new JFileChooser("D:");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            choosedFile.setText(file.getAbsolutePath());
        }

        StringBuilder datas = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bf.readLine()) != null) {
                datas.append(line).append("\n");
            }
            textAreaFileData.setText(datas.toString());

            showStatus("Đã đọc xong dữ liệu từ file");
        } catch (Exception ex) {
            ex.printStackTrace();

            showStatus("Lỗi khi đọc xong dữ liệu từ file");
        }

    }

    private void doAddToBinaryTree(ActionEvent e) {
        showStatus("Bắt đầu thêm vào cây nhị phân");

        binarySearchTree = new BinarySearchTree();
        try (
                BufferedReader br = new BufferedReader(new FileReader(this.file));
        ) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                String[] words = line.split("[$&+,:;=?@#|'<>.^*()%! ]");
                for (String word : words) {
                    if (!word.trim().isEmpty()) {
                        DataFile data = new DataFile(word, new int[]{row});
                        binarySearchTree.add(data);
                    }
                }
            }
            showStatus("Đã thêm vào cây nhị phân");
        } catch (Exception ex) {
            ex.printStackTrace();
            showStatus("Lỗi không thể thêm vào cây nhị phân");
        }

        binarySearchTree.show();
    }

    private void doSearchText(ActionEvent e){
        showStatus("Bắt đầu tìm kiếm");
        try{
            String text = this.searchTextInput.getText();
            DataFile searchedDataFile = binarySearchTree.search(new DataFile(text));
            showStatus(String.format("Đã tìm thấy từ \"%s\" xuất hiện %d lần", searchedDataFile.getWord(), searchedDataFile.getCount()));
        }catch (Exception ex){
            ex.printStackTrace();
            showStatus("Không tìm thấy từ này");
        }
    }

    private void showStatus(String message) {
        this.status.setText(message);
    }

    public static void main(String[] args) {
        BinarySearchApp binarySearchApp = new BinarySearchApp("NTNT");
        System.out.println("finish!!!");
    }

}
