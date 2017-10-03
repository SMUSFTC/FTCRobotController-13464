package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

    /*
    Class Diagram:

        RobotController(targetGamepad)
        void hook()

        static ArrayList<Gamepad> getAvailableGamepads()

        Gamepad targetGamepad
        BiConsumer<Boolean, Boolean> handleDPadUpActivated, handleDPadDownActivated, handleDPadLeftActivated, handleDPadRightActivated, JoystickLeftActivated, JoystickRightActivated, BumperLeftActivated, BumperRightActivated
        Consumer<Integer, Integer> JoystickLeftXChanged, JoyStickLeftYChanged, JoystickRightXChanged, JoystickRightYChanged, TriggerLeftPressureChanged

    */

public class GamepadEventManager
{
    public interface GamepadEventHandler<T> { void handleGamepadEvent(T currentValue, T previousValue); }
    public enum HandlerTriggerMode { TICK, CHANGE, DYNAMIC }

    public GamepadEventManager(Gamepad targetGamepad) { }
    public void runEventLoop(HandlerTriggerMode triggerMode) { }

    GamepadEventHandler<Boolean> dPadUpHandler, dPadDownHandler, dPadLeftHandler, dPadRightHandler, leftJoystickZHandler, rightJoystickZHandler;
    GamepadEventHandler<Integer> leftJoystickXHandler, leftJoystickYHandler, rightJoystickXHandler, rightJoystickYHandler;
}
