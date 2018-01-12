package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Rashid on 1/11/2018.
 */

@Autonomous(name = "VuMark Read Test")
public class VuMarkReadTest extends LinearOpMode {

    VuforiaLocalizer vuforia;

    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AcTvLN3/////AAAAGeubf7NV7EeMqnpJ9vg/MD49t+FAEYVyIWlCfs04wEfNWVdY9GH8kQP+Q6mp/+1hMLEfBu8aFsNPMVOZTHIziNniPzX19W/QeBqUL4fn/F/+/DgqHnxIh5H/XTdsh7VbTi32XWtB44k5fxRK4ya+mAqDlkbZDSM/0/+P9cl6WZ1M9s8NJ6y0wAO39GpkLdWqHXS/XgmSSg82iCozVSwUzKn8/7ITiXxjlXCTNJwFS1BMkehgC7u3IT5HuR4l5bvYF/87pNnVnQ/azQEB0jzTv1Yww4VaVJ7KOC25MhWB5u8KqTNAwJyvY24mav+Jg5Kdg3ibRp0zybRQuP0WEnktTHk+ajU5jSR9uGC77d9zU586";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

    }
}
