package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadEventManager
{
    public interface GamepadEventHandler<T> { void handleGamepadEvent(T currentValue, T previousValue); }

    public static class Configuration
    {
        // NOTE: Pression is defined by the Oxford Dictionaries and is thus a valid word, so I will use it even if it's in the bottom 30% of all English words ever. Deal with it.
        GamepadEventHandler<Boolean> dPadUpPressionHandler, dPadDownPressionHandler, dPadLeftPressionHandler, dPadRightPressionHandler, leftJoystickPressionHandler, rightJoystickPressionHandler, rightBumperPressionHandler, leftBumperPressionHandler;
        GamepadEventHandler<Float> leftJoystickXHandler, leftJoystickYHandler, rightJoystickXHandler, rightJoystickYHandler;
    }

    public Gamepad targetGamepad;
    public Configuration handlingConfiguration;
    public boolean tickBased = false;

    Boolean cachedDPadUpPression, cachedDPadDownPression, cachedDPadLeftPression, cachedDPadRightPression, cachedLeftJoystickPression, cachedRightJoystickPression, cachedLeftBumperPression, cachedRightBumperPression;
    Float cachedLeftJoystickX, cachedLeftJoystickY, cachedRightJoystickX, cachedRightJoystickY;

    /**
     * Initializes the GamepadEventManager. This must be done after waitForStart() has been called.
     * @param targetGamepad The gamepad that this instance of the GamepadEventManager class will be managing.
     * @param handlingConfiguration The configuration for the handling of events fired by the specified gamepad.
     */
    public GamepadEventManager(Gamepad targetGamepad, Configuration handlingConfiguration, boolean tickBased)
    {
        this.targetGamepad = targetGamepad;
        this.handlingConfiguration = handlingConfiguration;
        this.tickBased = tickBased;
        updateValueCache();
    }

    public void runEventHandlers() throws BrokenBarrierException, InterruptedException
    {
        CyclicBarrier barrier = new CyclicBarrier(10);
         new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedDPadUpPression != targetGamepad.dpad_up) handlingConfiguration.dPadUpPressionHandler.handleGamepadEvent(targetGamepad.dpad_up, cachedDPadUpPression);
            else if (tickBased || cachedDPadDownPression != targetGamepad.dpad_down) handlingConfiguration.dPadDownPressionHandler.handleGamepadEvent(targetGamepad.dpad_down, cachedDPadDownPression);
            else if (tickBased || cachedDPadLeftPression != targetGamepad.dpad_left) handlingConfiguration.dPadLeftPressionHandler.handleGamepadEvent(targetGamepad.dpad_left, cachedDPadLeftPression);
            else if (tickBased || cachedDPadRightPression != targetGamepad.dpad_right) handlingConfiguration.dPadRightPressionHandler.handleGamepadEvent(targetGamepad.dpad_right, cachedDPadRightPression);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedLeftJoystickPression != targetGamepad.left_stick_button) handlingConfiguration.leftJoystickPressionHandler.handleGamepadEvent(targetGamepad.left_stick_button, cachedLeftJoystickPression);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedRightJoystickPression != targetGamepad.right_stick_button) handlingConfiguration.rightJoystickPressionHandler.handleGamepadEvent(targetGamepad.right_stick_button, cachedRightJoystickPression);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedLeftBumperPression != targetGamepad.left_bumper) handlingConfiguration.leftBumperPressionHandler.handleGamepadEvent(targetGamepad.left_bumper, cachedLeftBumperPression);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedRightBumperPression != targetGamepad.right_bumper) handlingConfiguration.rightBumperPressionHandler.handleGamepadEvent(targetGamepad.right_bumper, cachedRightBumperPression);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedLeftJoystickX != targetGamepad.left_stick_x) handlingConfiguration.leftJoystickXHandler.handleGamepadEvent(targetGamepad.left_stick_x, cachedLeftJoystickX);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedLeftJoystickY != targetGamepad.left_stick_y) handlingConfiguration.leftJoystickYHandler.handleGamepadEvent(targetGamepad.left_stick_y, cachedLeftJoystickY);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedRightJoystickX != targetGamepad.right_stick_x) handlingConfiguration.rightJoystickXHandler.handleGamepadEvent(targetGamepad.right_stick_x, cachedRightJoystickX);
        }}).start();
        new Thread(new Runnable() { @Override public void run() {
            if (tickBased || cachedLeftJoystickY != targetGamepad.left_stick_y) handlingConfiguration.leftJoystickYHandler.handleGamepadEvent(targetGamepad.left_stick_y, cachedLeftJoystickY);
        }}).start();
        barrier.await();
    }

    public void updateValueCache()
    {
        cachedDPadUpPression = targetGamepad.dpad_up;
        cachedDPadDownPression = targetGamepad.dpad_down;
        cachedDPadLeftPression = targetGamepad.dpad_left;
        cachedDPadRightPression = targetGamepad.dpad_right;
        cachedLeftJoystickPression = targetGamepad.left_stick_button;
        cachedRightJoystickPression = targetGamepad.right_stick_button;
        cachedLeftBumperPression = targetGamepad.left_bumper;
        cachedRightBumperPression = targetGamepad.right_bumper;
        cachedLeftJoystickX = targetGamepad.left_stick_x;
        cachedLeftJoystickY = targetGamepad.left_stick_y;
        cachedRightJoystickX = targetGamepad.right_stick_x;
        cachedRightJoystickY = targetGamepad.right_stick_y;
    }
}
