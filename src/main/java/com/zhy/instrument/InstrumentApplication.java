package com.zhy.instrument;

import com.zhy.instrument.view.IndexJFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class InstrumentApplication {
    public InstrumentApplication() {
        try {
            IndexJFrame.getInstance().initUI();
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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


        ApplicationContext ctx = new SpringApplicationBuilder(InstrumentApplication.class)
                .headless(false).run(args);
    }

}
