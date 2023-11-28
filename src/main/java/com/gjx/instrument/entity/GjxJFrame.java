package com.gjx.instrument.entity;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhanghongyu
 * @create 2022-07-02 17:06
 * @desc 样式类
 **/
public class GjxJFrame extends JFrame {
    private static GjxJFrame instance = null;
    private JProgressBar progressBar;
    private GjxJFrame() {
    }
    public static GjxJFrame getInstance() {
        if (null == instance) {
            synchronized (GjxJFrame.class) {
                if (null == instance) {
                    instance = new GjxJFrame();
                }
            }
        }
        return instance;
    }
    public void initUI() {
        this.setTitle("小小小小工具");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 700, 540);
        this.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setContentPane(panel);
        JLabel fileFild = new JLabel("无");
        fileFild.setBounds(200,150,500,30);
        AtomicReference<JFileChooser> file = new AtomicReference<>(new JFileChooser());
        JButton openBtn = new JButton("选择文件");
        openBtn.addActionListener(e -> file.set(showFileOpenDialog(this, fileFild)));
        openBtn.setBounds(160,100,100,30);
        openBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        openBtn.setFont(new Font("宋体", Font.BOLD,15));
        openBtn.setForeground(Color.white);//字体颜色
        panel.add(openBtn);
        panel.add(fileFild);
    }

    private JFileChooser showFileOpenDialog(Component parent, JLabel textField) {
//        if (progressBar.getValue() != 0 && progressBar.getValue() != 100) {
//            JOptionPane.showMessageDialog(null, "计算过程中，不可更改文件！╮(╯▽╰)╭", "警告！", JOptionPane.WARNING_MESSAGE);
//            return null;
//        }

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setMultiSelectionEnabled(false);
        // 设置默认使用的文件过滤器
        jFileChooser.setFileFilter(new FileNameExtensionFilter("excel(*.xlsx, *.xls)", "xls", "xlsx"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = jFileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = jFileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            textField.setText("");
            textField.setText(file.getName() + "\n\n");
        }
        //进度条归零
        //progressBar.setValue(0);
        return jFileChooser;
    }
}
