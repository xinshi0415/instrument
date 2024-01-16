package com.zhy.instrument;

import com.zhy.instrument.view.IndexJFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class InstrumentApplication {
    public InstrumentApplication() {
        //初始化主页
        IndexJFrame.getInstance().initUI();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        try {
//            //加载beutilful样式
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
//            UIManager.put("RootPane.setupButtonVisible", false);
//        } catch(Exception e) {
//            //TODO exception
//        }
        //添加风格(跟随系统)
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        ApplicationContext ctx = new SpringApplicationBuilder(InstrumentApplication.class)
                .headless(false).run(args);
    }

}
