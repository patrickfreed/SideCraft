package com.freedsuniverse.sidecraft.input;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.freedsuniverse.sidecraft.Settings;
import com.freedsuniverse.sidecraft.world.Location;

public class Mouse {
    private static boolean m1 = false, oldm1 = false, currentm1 = false, m2 = false, oldm2 = false, currentm2 = false, m3 = false, oldm3 = false, currentm3 = false;
    private static int x = 0, y = 0, w = 0;

    public static void modifyScrollWheelValue(int a) {
        Mouse.w += a;
    }

    public static int getScrollWheelValue() {
        return Mouse.w;
    }

    public static void setX(int x) {
        Mouse.x = x;
    }

    public static void setY(int y) {
        Mouse.y = y;
    }

    public static int getY() {
        return Mouse.y;
    }

    public static int getX() {
        return Mouse.x;
    }

    public static void toggle(int button, boolean toggleType) {
        if (button == MouseEvent.BUTTON1 && toggleType != m1) {
            m1 = !m1;
        } else if (button == MouseEvent.BUTTON2 && toggleType != m2) {
            m2 = !m2;
        } else if (button == MouseEvent.BUTTON3 && toggleType != m3) {
            m3 = !m3;
        }
    }

    public static boolean clicked(int button) {
        if (button == MouseEvent.BUTTON1)
            return m1 && !oldm1;
        else if (button == MouseEvent.BUTTON2)
            return m2 && !oldm2;
        else if (button == MouseEvent.BUTTON3)
            return m3 && !oldm3;
        else
            return false;
    }

    public static void update() {
        oldm1 = currentm1;
        currentm1 = m1;

        oldm2 = currentm2;
        currentm2 = m2;

        oldm3 = currentm3;
        currentm3 = m3;
    }

    public static boolean isDown(int button) {
        if (button == MouseEvent.BUTTON1)
            return m1;
        else if (button == MouseEvent.BUTTON2)
            return m2;
        else if (button == MouseEvent.BUTTON3)
            return m3;
        else
            return false;
    }

    public static Point getPoint() {
        return new Point(x, y);
    }

    public static Location getLocation() {
        return Location.valueOf(Mouse.x - Settings.BLOCK_SIZE / 2, Mouse.y - Settings.BLOCK_SIZE / 2);
    }
}