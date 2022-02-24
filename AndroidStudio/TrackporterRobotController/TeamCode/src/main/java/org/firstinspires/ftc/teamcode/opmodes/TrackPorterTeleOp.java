package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.subsystems.realsense.T265;
import org.firstinspires.ftc.teamcode.subsystems.trackporter.TrackPorter;

@TeleOp(name = "TrackPorter TeleOp", group = "TeleOp")
public class TrackPorterTeleOp extends LinearOpMode {

    TrackPorter trackPorter;

    double speedModifier = 1;

    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        trackPorter = new TrackPorter(hardwareMap, telemetry);

        waitForStart();

        while(!isStopRequested()) {

            double denominator = Math.max(Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.right_stick_x), 1);

            double leftMotorPower = (gamepad1.left_stick_y - gamepad1.right_stick_x) / denominator;
            double rightMotorPower = (gamepad1.left_stick_y + gamepad1.right_stick_x) / denominator;

            trackPorter.drive.drive(
                    (leftMotorPower * speedModifier),
                    (rightMotorPower * speedModifier)
            );

            trackPorter.t265.updatePose();

            telemetry.addData("x", trackPorter.t265.location.getX());
            telemetry.addData("y", trackPorter.t265.location.getY());
            telemetry.addData("Rotation", trackPorter.t265.rotation.getDegrees());
        }

        trackPorter.t265.stop();
    }
}
