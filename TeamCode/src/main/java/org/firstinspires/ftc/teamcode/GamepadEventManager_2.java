package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by alex.fanat on 9/26/2017.
 */
// NOTE: See this useful link for more information: https://stackoverflow.com/questions/13604703/how-do-i-define-a-method-which-takes-a-lambda-as-a-parameter-in-java-8.

public class GamepadEventManager_2
{
    public interface ChangeInformer { void inform(); }
    public interface ValueFetcher<T> { T fetch(); }
    public static class MonitoredValue<T> { public void updateValue() { previousValue = currentValue; currentValue = fetcher.fetch(); } ValueFetcher<T> fetcher; ChangeInformer informer; T currentValue, previousValue; }

    MonitoredValue<Boolean> leftStickPressed, rightStickPressed, leftBumperPressed, rightBumperPressed, aPressed, bPressed, xPressed, yPressed, dPadUpPressed, dPadDownPressed, dPadLeftPressed, dPadRightPressed;
}
