package nl.ocs.lejos;

import ev3dev.actuators.LCD;
import ev3dev.hardware.EV3DevDevice;
import ev3dev.sensors.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Font;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;

public class App extends EV3DevDevice {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    //EV3 display -- make sure to kill brickman!
    private static final GraphicsLCD lcd = LCD.getInstance();

    public static void main(final String[] args) {
        LOG.info("Starting robot");
        clearLCD();
        lcd.setFont(new Font(MONOSPACED, PLAIN, 10));
        lcd.setColor(Color.BLACK);
        lcd.drawString("Hello Lego!", 10, 10, 0);
        lcd.refresh();
        LOG.info("Hello Lego!");
        Button.waitForAnyPress();
        LOG.info("Shutting down.");

    }

    public static void clearLCD() {
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0, 0, lcd.getWidth(), lcd.getHeight());
        lcd.refresh();
    }
}
