package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadValueMonitor
{
    public interface Fetchable<T> { T fetch(); }

    public static class MonitoredValue<T>
    {
        public enum UpdateMode { TICK, CHANGE, CUSTOM }

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
        
        boolean showTelemetryInformation = false;
    }

    public static class MonitoredGamepadValue<T> extends MonitoredValue<T>
    {
        public boolean active = false;
        public String identifier = "MonitoredGamepadValue";
    }

    private static int instanceCount = 0;

    private static List<Boolean> telemetryLoopStatuses = new ArrayList<>();

    public static boolean HALT_AND_CATCH_FIRE = false;

    public int requestedMonitorThreadCount = 4;
    public final int instanceNumber = instanceCount++;

    @NonNull public String monitorID;
    @NonNull public Telemetry activeTelemetry;
    @NonNull public Fetchable<Boolean> opModeIsActive;
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

    public GamepadValueMonitor(Gamepad targetGamepad, @NonNull Fetchable<Boolean> opModeIsActive, @NonNull Telemetry activeTelemetry, @NonNull String monitorID)
    {
        this.targetGamepad = targetGamepad;
        this.opModeIsActive = opModeIsActive;
        this.activeTelemetry = activeTelemetry;
        this.monitorID = monitorID;

        telemetryLoopStatuses.add(instanceNumber, false);
    }

    public GamepadValueMonitor(Gamepad targetGamepad, @NonNull Fetchable<Boolean> opModeIsActive, @NonNull Telemetry activeTelemetry, @NonNull String monitorID, int requestedMonitorThreadCount)
    {
        this(targetGamepad, opModeIsActive, activeTelemetry, monitorID);
        this.requestedMonitorThreadCount = requestedMonitorThreadCount;
    }

    public GamepadValueMonitor(@NonNull GamepadValueMonitor other)
    {
        this(other.targetGamepad, other.opModeIsActive, other.activeTelemetry, other.monitorID + "(Copy)", other.requestedMonitorThreadCount);
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
        activeTelemetry.addLine("The value monitoring will soon commence for the \"" + monitorID + "\" monitor.");
        for (int i = 0; i < requestedMonitorThreadCount; i++)
            new Thread(() ->
            {
                try
                {
                    while (opModeIsActive.fetch())
                    {
                        setTelemetryLoopStatus(false);
                        for (MonitoredValue monitoredValue : monitoredValues)
                        {
                            monitoredValue.updateValue();
                            if (monitoredValue.showTelemetryInformation)
                            {
                                activeTelemetry.addLine("\n" + (monitoredValue instanceof MonitoredGamepadValue ? "Value \tID: " + ((MonitoredGamepadValue) monitoredValue).identifier + "\n" + monitoredValue.currentValue : "") + "\tCurrent: " + monitoredValue.currentValue + "\n\tPrevious: " + monitoredValue.previousValue + "\n\tLatest: " + monitoredValue.value.fetch() + "\n\tMonitor ID: " + monitorID);
                            }
                        }
                        if (HALT_AND_CATCH_FIRE)
                        {
                            halt();
                            return;
                        }
                        setTelemetryLoopStatus(true);
                        if (areTelemetryLoopsComplete())
                            activeTelemetry.update();
                    }
                }
                catch (Exception exception) { processException(exception); }
            }).start();
        if (loop != null)
            new Thread(() ->
            {
                try
                {
                    while (opModeIsActive.fetch())
                    {
                        loop.run();
                        if (HALT_AND_CATCH_FIRE)
                        {
                            halt();
                            return;
                        }
                    }
                }
                catch (Exception exception) { processException(exception); }
            }).start();
    }

    public void halt()
    {
        HALT_AND_CATCH_FIRE = true;
        setTelemetryLoopStatus(false);
        activeTelemetry.addLine("[WARNING] Full operations stop requested.");
        activeTelemetry.update();
    }

    private void setTelemetryLoopStatus(boolean value)
    {
        telemetryLoopStatuses.set(instanceNumber, value);
    }

    private boolean areTelemetryLoopsComplete()
    {
        boolean areTelemetryLoopsComplete = true;
        for (boolean status: telemetryLoopStatuses)
            areTelemetryLoopsComplete = areTelemetryLoopsComplete && status;
        return areTelemetryLoopsComplete;
    }

    private void processException(Exception exception)
    {
        HALT_AND_CATCH_FIRE = true;
        activeTelemetry.addLine("[ERROR] An exception has occurred during the monitoring process on the \"" + monitorID + "\" monitor.\n\t[INFO] Type of Exception: " + (exception.toString().indexOf(':') != -1 ? exception.toString().substring(0, exception.toString().indexOf(':')) : exception.toString()) + "\n\t[INFO] Message: " + exception.getMessage() + "\n\t[INFO] Stack Trace: ");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) activeTelemetry.addLine(stackTraceElement.toString());
        activeTelemetry.update();
    }
}
