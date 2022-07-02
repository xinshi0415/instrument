package com.gjx.instrument;

import com.gjx.instrument.entity.GjxJFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class InstrumentApplication {
    public InstrumentApplication() {
        GjxJFrame.getInstance().initUI();
    }

    public static void main(String[] args) {
        try {
            //加载beutilful样式
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch(Exception e) {
            //TODO exception
        }
        ApplicationContext ctx = new SpringApplicationBuilder(InstrumentApplication.class)
                .headless(false).run(args);
    }

}
