package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Jones;
import org.firstinspires.ftc.teamcode.RobotController;
import org.firstinspires.ftc.teamcode.utils.GamepadValueMonitor;

/**
 * Created by Rashid on 11/18/2017.
 */

public class DriverPeriodTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        RobotController.setCurrentOpMode(this);
        Jones jones = new Jones();

        telemetry.addLine("Jones has been initialized.");
        telemetry.update();
        telemetry.setAutoClear(false);

        GamepadValueMonitor valueMonitorGamepad1 = new GamepadValueMonitor(gamepad1, () -> opModeIsActive(), () -> telemetry) {{
            leftStickY.active = leftStickX.active = dPadRight.active = dPadLeft.active = true;

            dPadRight.updateInformer = () ->
            {
                jones.turnRight();
            };
            dPadRight.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadRight.customUpdateCondition = () -> dPadRight.currentValue;

            dPadLeft.updateInformer = () ->
            {
                jones.turnLeft();
            };
            dPadLeft.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadLeft.customUpdateCondition = () -> dPadLeft.currentValue;


            loop = () ->
            {
                jones.setPowerLeftDrive((-leftStickY.currentValue + leftStickX.currentValue) * 0.6);
                jones.setPowerRightDrive((-leftStickY.currentValue - leftStickX.currentValue) * 0.6);
            };
        }};

        GamepadValueMonitor valueMonitorGamepad2 = new GamepadValueMonitor(gamepad2, () -> opModeIsActive(), () -> telemetry) {{
            dPadUp.updateInformer = () ->
            {
                jones.armMaxPosition();
            };
            dPadUp.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadUp.customUpdateCondition = () -> dPadUp.currentValue;
            dPadUp.active = true;

            dPadDown.updateInformer = () ->
            {
                jones.armMinPosition();
            };
            dPadDown.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadDown.customUpdateCondition = () -> dPadDown.currentValue;
            dPadDown.active = true;

            start.updateInformer = () ->
            {
                jones.armSetPosition(0.5);
                jones.setLiftPosition(0);
            };
            start.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            start.customUpdateCondition = () -> start.currentValue;
            start.active = true;

            y.updateInformer = () ->
            {
                if (y.currentValue)
                    jones.addToLiftPosition(0.005);
            };
            y.activeUpdateMode = MonitoredValue.UpdateMode.TICK;
            y.active = true;

            a.updateInformer = () ->
            {
                if (a.currentValue)
                    jones.addToLiftPosition(-0.005);
            };
            a.activeUpdateMode = MonitoredValue.UpdateMode.TICK;
            a.active = true;

            x.updateInformer = () -> jones.linearActuatorMin();
            x.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            x.customUpdateCondition = () -> x.currentValue;
            x.active = true;

            b.updateInformer = () -> jones.linearActuatorMax();
            b.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            b.customUpdateCondition = () -> b.currentValue;
            b.active = true;

        }};


        telemetry.addLine("The handlers have been initialized.");
        telemetry.addLine("Awaiting start...");
        telemetry.update();

        waitForStart();
        valueMonitorGamepad1.startMonitoring(true);
        valueMonitorGamepad2.startMonitoring(true);
        while (opModeIsActive()) ;


    }
}
