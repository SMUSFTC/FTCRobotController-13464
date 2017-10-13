package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadValueMonitor
{
    public interface Fetchable<T> { T fetch(); }
    public static class MonitoredValue<T> { public enum UpdateMode { TICK, CHANGE } public void updateValue() { previousValue = currentValue; currentValue = value.fetch(); if (tickBased || previousValue != currentValue) updateInformer.run(); } public boolean tickBased = true; public Fetchable<T> value; public Runnable updateInformer; T currentValue, previousValue; UpdateMode activeUpdateMode = UpdateMode.TICK; }
    public static class MonitoredGamepadValue<T> extends MonitoredValue<T> { public boolean monitorValue = false; }

    final MonitoredGamepadValue<Boolean>
            leftStickButton = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.left_stick_button; } }; }},
            rightStickButton = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.right_stick_button; } }; }},
            leftBumper = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.left_bumper; } }; }},
            rightBumper = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.right_bumper; } }; }},
            a = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.a; } }; }},
            b = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.b; } }; }},
            x = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.x; } }; }},
            y = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.y; } }; }},
            start = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.start; } }; }},
            back = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.back; } }; }},
            dPadUp = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.dpad_up; } }; }},
            dPadDown = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.dpad_down; } }; }},
            dPadLeft = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.dpad_left; } }; }},
            dPadRight = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable() { @Override public Boolean fetch() { return targetGamepad.dpad_right; } }; }};
    final MonitoredGamepadValue<Float> leftStickX = new MonitoredGamepadValue(), leftStickY = new MonitoredGamepadValue(), rightStickX = new MonitoredGamepadValue(), rightStickY = new MonitoredGamepadValue(), leftTrigger = new MonitoredGamepadValue();

    List<MonitoredValue> monitoredValues = new ArrayList<MonitoredValue>();
    Gamepad targetGamepad;

    public GamepadValueMonitor(Gamepad targetGamepad) {  }

    public GamepadValueMonitor(GamepadValueMonitor other) { ; } // Needs implementation.

    public void initialize()
    {

    }
}
