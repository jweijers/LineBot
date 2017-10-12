package nl.ocs.lejos;

import ev3dev.actuators.LCD;
import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.sensors.Button;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Font;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;

/**
 * Hello Lego!
 */
public class App {

    //EV3 display -- make sure to kill brickman!
    private static final GraphicsLCD lcd = LCD.getInstance();
    
    //Engines for driving
    private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

    //Sensors
    private static final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        LOG.info("Starting robot");
        setupShutdownHooks();

        lcd.clear();
        lcd.setFont(new Font(MONOSPACED, PLAIN, 10));
        lcd.setColor(Color.BLACK);
        lcd.drawString("Hello Lego!", 10, 10, 0);
        lcd.refresh();
        LOG.info("Hello Lego!");

        Button.waitForAnyPress();

        setupRobotControlListeners();

        leftMotor.setSpeed(-200);
        rightMotor.setSpeed(-200);
        leftMotor.forward();
        rightMotor.forward();
        SampleProvider colorSampler = colorSensor.getColorIDMode();
        float[] sample = new float[colorSampler.sampleSize()];
        do {
            colorSampler.fetchSample(sample, 0);
            LOG.debug("Color sample: " + sample[0]);
        } while(Color.RED != (int) sample[0]);

        leftMotor.stop();
        rightMotor.stop();

        lcd.clear();
        LOG.info("Shutting down.");

    }

    public static void setupRobotControlListeners() {
        Button.ESCAPE.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(final Key key) {
                lcd.clear();
                LOG.info("Stopping program");
                System.exit(1);
            }

            @Override
            public void keyReleased(final Key key) {

            }
        });
    }

    public static void setupShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.debug("ShutdownHook - Stopping motors.");
            leftMotor.stop();
            rightMotor.stop();
            LOG.debug("ShutdownHook - Motors stopped.");
        }));
    }
}
