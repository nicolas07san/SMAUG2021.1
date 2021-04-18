package com.neterusgames.game.desktop;

import com.neterusgames.game.SplashWorker;

import java.awt.*;

public class DesktopSplashWorker implements SplashWorker {
    public void closeSplashScreen() {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if(splashScreen != null){
            splashScreen.close();
        }
    }
}
