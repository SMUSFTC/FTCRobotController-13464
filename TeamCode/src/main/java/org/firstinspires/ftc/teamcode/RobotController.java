package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.teamcode.opmodes.DriverPeriod;
import org.firstinspires.ftc.teamcode.tests.DriverPeriodTest;
import org.firstinspires.ftc.teamcode.tests.VuMarkReadTest;

/**
 * Created by Rashid on 1/12/2018.
 */

public class RobotController
{

    public static final String VUFORIA_LICENSE_KEY = "AcTvLN3/////AAAAGeubf7NV7EeMqnpJ9vg/MD49t+FAEYVyIWlCfs04wEfNWVdY9GH8kQP+Q6mp/+1hMLEfBu8aFsNPMVOZTHIziNniPzX19W/QeBqUL4fn/F/+/DgqHnxIh5H/XTdsh7VbTi32XWtB44k5fxRK4ya+mAqDlkbZDSM/0/+P9cl6WZ1M9s8NJ6y0wAO39GpkLdWqHXS/XgmSSg82iCozVSwUzKn8/7ITiXxjlXCTNJwFS1BMkehgC7u3IT5HuR4l5bvYF/87pNnVnQ/azQEB0jzTv1Yww4VaVJ7KOC25MhWB5u8KqTNAwJyvY24mav+Jg5Kdg3ibRp0zybRQuP0WEnktTHk+ajU5jSR9uGC77d9zU586";

    public static LinearOpMode currentOpMode;
    public static HardwareMap currentHardwareMap;


    @OpModeRegistrar
    public static void registerOpModes(OpModeManager manager) {

        /*
         * Register main OpModes
         */

        manager.register(new OpModeMeta("Driver Period", OpModeMeta.Flavor.TELEOP), DriverPeriod.class);

        /*
         * Register test OpModes
         */
        manager.register(new OpModeMeta("Driver Period Test", OpModeMeta.Flavor.TELEOP), DriverPeriodTest.class);
        manager.register(new OpModeMeta("VuMark Read Test", OpModeMeta.Flavor.AUTONOMOUS), VuMarkReadTest.class);

    }

    public static void setCurrentOpMode(LinearOpMode opMode) {
        currentOpMode = opMode;
        currentHardwareMap = opMode.hardwareMap;
    }
}
