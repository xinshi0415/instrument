package com.zhy.instrument.view;

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
public class IndexJFrame extends JFrame {
    private static IndexJFrame instance = null;
    private JProgressBar progressBar;
    private IndexJFrame() {
    }
    /**
     * @Author zhy
     * @Desc 实例化Jframe
     * @Date 2024/1/12 17:25
     * @Param
     * @return
     */
    public static IndexJFrame getInstance() {
        if (null == instance) {
            synchronized (IndexJFrame.class) {
                if (null == instance) {
                    instance = new IndexJFrame();
                }
            }
        }
        return instance;
    }

    /** @Author zhy
     * @Description 初始化JFrame
     * @Date 2024/1/12 15:45
     * @Param
     * @return
     */
    public void initUI() {
        this.setTitle("小小小小工具");
        //窗口是否可以调节大小
        this.setResizable(false);
        //客户点击关闭同时关闭程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setBounds(100, 100, 700, 540);
        //设置窗体可见
        this.setVisible(true);
        //设置窗口大小
        this.setSize(700,540);
        //本语句实现窗口居屏幕中央
        this.setLocationRelativeTo(null);


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
