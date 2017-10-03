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
    public interface GamepadEventHandler<T> { enum TriggerMode { TICK, CHANGE, DYNAMIC } void handleGamepadEvent(T currentValue, T previousValue); }

    public class Configuration
    {
        // NOTE: Pression is defined by the Oxford Dictionaries and is thus a valid word, so I will use it even if it's in the bottom 30% of all English words ever. Deal with it.
        GamepadEventHandler<Boolean> dPadUpPressionHandler, dPadDownPressionHandler, dPadLeftPressionHandler, dPadRightPressionHandler, leftJoystickPressionHandler, rightJoystickPressionHandler, rightBumperPressionHandler, leftBumperPressionHandler;
        GamepadEventHandler<Integer> leftJoystickXHandler, leftJoystickYHandler, rightJoystickXHandler, rightJoystickYHandler;
    }

    public GamepadEventManager(Gamepad targetGamepad, Configuration handlingConfiguration) { }
    public void runEventLoop(GamepadEventHandler.TriggerMode handlingTriggerMode) { }
}
