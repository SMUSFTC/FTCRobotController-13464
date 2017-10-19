package org.firstinspires.ftc.teamcode;

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
        public enum UpdateMode { TICK, CHANGE, UNIMPLEMENTED_CUSTOM }

        public void updateValue()
        {
            previousValue = currentValue;
            currentValue = value.fetch();
            if (activeUpdateMode == UpdateMode.TICK || previousValue != null && !currentValue.equals(previousValue)) updateInformer.run();
        }

        @NonNull public Fetchable<T> value;
        @NonNull public Runnable updateInformer;

        T currentValue, previousValue;
        UpdateMode activeUpdateMode = UpdateMode.TICK;
    }

    public static class MonitoredGamepadValue<T> extends MonitoredValue<T> { public boolean active = false; }

    @NonNull public Fetchable<Boolean> opModeIsActive;
    @NonNull public Fetchable<Telemetry> activeTelemetry;
    @NonNull public List<MonitoredValue> monitoredValues;
    public final MonitoredGamepadValue<Boolean>
            leftStickButton = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.left_stick_button; } }; }},
            rightStickButton = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.right_stick_button; } }; }},
            leftBumper = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.left_bumper; } }; }},
            rightBumper = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.right_bumper; } }; }},
            a = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.a; } }; }},
            b = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.b; } }; }},
            x = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.x; } }; }},
            y = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.y; } }; }},
            start = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.start; } }; }},
            back = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.back; } }; }},
            dPadUp = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.dpad_up; } }; }},
            dPadDown = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.dpad_down; } }; }},
            dPadLeft = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.dpad_left; } }; }},
            dPadRight = new MonitoredGamepadValue<Boolean>() {{ value = new Fetchable<Boolean>() { @Override public Boolean fetch() { return targetGamepad.dpad_right; } }; }};
    public final MonitoredGamepadValue<Float>
            leftStickX = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.left_stick_x; } }; }},
            leftStickY = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.left_stick_y; } }; }},
            rightStickX = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.right_stick_x; } }; }},
            rightStickY = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.right_stick_y; } }; }},
            leftTrigger = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.left_trigger; } }; }},
            rightTrigger = new MonitoredGamepadValue<Float>() {{ value = new Fetchable<Float>() { @Override public Float fetch() { return targetGamepad.right_trigger; } }; }};

    Gamepad targetGamepad;

    public GamepadValueMonitor(Gamepad targetGamepad, @NonNull Fetchable<Boolean> opModeIsActive, @NonNull Fetchable<Telemetry> activeTelemetry) { this.targetGamepad = targetGamepad; this.opModeIsActive = opModeIsActive; this.activeTelemetry = activeTelemetry; }

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
        new Thread(new Runnable() { @Override public void run()
        {
            try
            {
                while (opModeIsActive.fetch())
                {
                    for (MonitoredValue value : monitoredValues)
                    {
                        value.updateValue();
                        activeTelemetry.fetch().addLine("The most recent value is: [" + value.value.fetch() + "],\r\nThe current value is: [" + value.currentValue + "],\r\nThe previous value is: [" + value.previousValue + "]");
                        activeTelemetry.fetch().update();
                    }
                    activeTelemetry.fetch().addLine("A monitoring loop has completed. The current value of ");
                    activeTelemetry.fetch().update();
                }
            }
            catch (Exception exception)
            {
                activeTelemetry.fetch().addLine("[ERROR] An exception has occurred during the monitoring process.\n\t[INFO] Type of Exception: " + exception.getClass() + "\n\t[INFO] Message: " + exception.getMessage() + "\n\t[INFO] Stack Trace: ");
                for (StackTraceElement stackTraceElement : exception.getStackTrace()) activeTelemetry.fetch().addLine(stackTraceElement.toString());
                activeTelemetry.fetch().update();
            }
        } }).start();
    }
}
