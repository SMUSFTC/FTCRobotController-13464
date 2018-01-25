package org.firstinspires.ftc.teamcode.utils;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadValueMonitor
{
    public interface Fetchable<T> { T fetch(); }

    public static class MonitoredValue<T>
    {
        public enum UpdateMode { TICK, CHANGE, CUSTOM}

        public void updateValue()
        {
            previousValue = currentValue;
            currentValue = value.fetch();
            if (activeUpdateMode == UpdateMode.TICK || (activeUpdateMode == UpdateMode.CUSTOM && customUpdateCondition != null && customUpdateCondition.fetch()) || (activeUpdateMode == UpdateMode.CHANGE && previousValue != null && !currentValue.equals(previousValue))) updateInformer.run();
        }

        @NonNull public Fetchable<T> value;
        @NonNull public Fetchable<Boolean> customUpdateCondition;
        @NonNull public Runnable updateInformer = () -> { };

        public T currentValue, previousValue;
        public UpdateMode activeUpdateMode = UpdateMode.TICK;
    }

    public static class MonitoredGamepadValue<T> extends MonitoredValue<T> { public boolean active = false; public String identifier = "MonitoredGamepadValue"; }

    @NonNull public Fetchable<Boolean> opModeIsActive;
    @NonNull public Fetchable<Telemetry> activeTelemetry;
    @NonNull public List<MonitoredValue> monitoredValues;
    @NonNull public Runnable loop;
    public final MonitoredGamepadValue<Boolean>
            leftStickButton = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.left_stick_button; identifier = "leftStickButton"; }},
            rightStickButton = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.right_stick_button; identifier = "rightStickButton"; }},
            leftBumper = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.left_bumper; identifier = "leftBumper"; }},
            rightBumper = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.right_bumper; identifier = "rightBumper"; }},
            a = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.a; identifier = "a"; }},
            b = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.b; identifier = "b"; }},
            x = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.x; identifier = "x"; }},
            y = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.y; identifier = "y"; }},
            start = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.start; identifier = "start"; }},
            back = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.back; identifier = "back"; }},
            dPadUp = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.dpad_up; identifier = "dPadUp"; }},
            dPadDown = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.dpad_down; identifier = "dPadDown"; }},
            dPadLeft = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.dpad_left; identifier = "dPadLeft"; }},
            dPadRight = new MonitoredGamepadValue<Boolean>() {{ value = () -> targetGamepad.dpad_right; identifier = "dPadRight"; }};
    public final MonitoredGamepadValue<Float>
            leftStickX = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.left_stick_x; identifier = "leftStickX"; }},
            leftStickY = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.left_stick_y; identifier = "leftStickY"; }},
            rightStickX = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.right_stick_x; identifier = "rightStickX"; }},
            rightStickY = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.right_stick_y; identifier = "rightStickY"; }},
            leftTrigger = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.left_trigger; identifier = "leftTrigger"; }},
            rightTrigger = new MonitoredGamepadValue<Float>() {{ value = () -> targetGamepad.right_trigger; identifier = "rightTrigger"; }};

    Gamepad targetGamepad;

    public GamepadValueMonitor(Gamepad targetGamepad, @NonNull Fetchable<Boolean> opModeIsActive, @NonNull Fetchable<Telemetry> activeTelemetry)
    {
        this.targetGamepad = targetGamepad;
        this.opModeIsActive = opModeIsActive;
        this.activeTelemetry = activeTelemetry;
    }

    public GamepadValueMonitor(@NonNull GamepadValueMonitor other)
    {
        other.initialize();
        monitoredValues = new ArrayList<MonitoredValue>(other.monitoredValues);
        targetGamepad = other.targetGamepad;
        opModeIsActive = other.opModeIsActive;
        activeTelemetry = other.activeTelemetry;
    }

    public void initialize()
    {
        monitoredValues = new ArrayList<MonitoredValue>()
        {{
            if (leftStickButton.active) add(leftStickButton);
            if (rightStickButton.active) add(rightStickButton);
            if (leftBumper.active) add(leftBumper);
            if (rightBumper.active) add(rightBumper);
            if (a.active) add(a);
            if (b.active) add(b);
            if (x.active) add(x);
            if (y.active) add(y);
            if (start.active) add(start);
            if (back.active) add(back);
            if (dPadUp.active) add(dPadUp);
            if (dPadDown.active) add(dPadDown);
            if (dPadLeft.active) add(dPadLeft);
            if (dPadRight.active) add(dPadRight);

            if (leftStickX.active) add(leftStickX);
            if (leftStickY.active) add(leftStickY);
            if (rightStickX.active) add(rightStickX);
            if (rightStickY.active) add(rightStickY);
            if (leftTrigger.active) add(leftTrigger);
            if (rightTrigger.active) add(rightTrigger);
        }};
    }

    public void startMonitoring(boolean initialize)
    {
        if (initialize) initialize();
        // TODO: Create thread pool and spread out handlers.
        activeTelemetry.fetch().addLine("The value monitoring will soon commence;");
        activeTelemetry.fetch().update();
        for (int i = 0; i < 4; i++)
        new Thread(() ->
        {
            try
            {
                while (opModeIsActive.fetch())
                {
                    for (MonitoredValue monitoredValue : monitoredValues)
                    {
                        monitoredValue.updateValue();
                        activeTelemetry.fetch().addLine("\n" + (monitoredValue instanceof MonitoredGamepadValue ? "Value \tID: " + ((MonitoredGamepadValue) monitoredValue).identifier + "\n" + monitoredValue.currentValue : "") + "\tCurrent: " + monitoredValue.currentValue + "\n\tPrevious: " + monitoredValue.previousValue + "\n\tLatest: " + monitoredValue.value.fetch());
                        activeTelemetry.fetch().update();
                    }
                }
            }
            catch (Exception exception)
            {
                activeTelemetry.fetch().setAutoClear(false);
                activeTelemetry.fetch().addLine("[ERROR] An exception has occurred during the monitoring process.\n\t[INFO] Type of Exception: " + (exception.toString().indexOf(':') != -1 ? exception.toString().substring(0, exception.toString().indexOf(':')) : exception.toString()) + "\n\t[INFO] Message: " + exception.getMessage() + "\n\t[INFO] Stack Trace: ");
                for (StackTraceElement stackTraceElement : exception.getStackTrace()) activeTelemetry.fetch().addLine(stackTraceElement.toString());
                activeTelemetry.fetch().update();
            }
        }).start();
        if (loop != null) new Thread(() -> { while (opModeIsActive.fetch()) loop.run(); } ).start();
    }
}
