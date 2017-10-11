package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadValueMonitor
{
    public interface ChangeInformer { void inform(); }
    public interface ValueFetcher<T> { T fetch(); }
    public static class MonitoredValue<T> { public void updateValue() { previousValue = currentValue; currentValue = fetcher.fetch(); if (tickBased || previousValue != currentValue) informer.inform(); } public boolean tickBased = true; public ValueFetcher<T> fetcher; public ChangeInformer informer; T currentValue, previousValue; }

    MonitoredValue<Boolean> leftStickButton, rightStickButton, leftBumper, rightBumper, a, b, x, y, start, back, dPadUp, dPadDown, dPadLeft, dPadRight;
    MonitoredValue<Float> leftStickX, leftStickY, rightStickX, rightStickY, leftTrigger;

    public List<MonitoredValue> monitoredValues = new ArrayList<MonitoredValue>();

    public GamepadValueMonitor(List<MonitoredValue> valuesToMonitor) { monitoredValues = new ArrayList<>(valuesToMonitor); }

    public void initialize()
    {

    }
}
