package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.controls.ControlSmoothing;
import org.firstinspires.ftc.teamcode.subsystems.trackporter.TrackPorter;

@TeleOp(name = "TrackPorter TeleOp", group = "TeleOp")
public class TrackPorterTeleOp extends LinearOpMode {

    TrackPorter trackPorter;
    public ControlSmoothing smoother;

    double adjustedGamepad1LeftY;
    double adjustedGamepad1RightY;

    double leftMotorPower;
    double rightMotorPower;

    double speedModifier = 1;

    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        trackPorter = new TrackPorter(hardwareMap, telemetry);
        smoother = new ControlSmoothing();

        waitForStart();

        while(!isStopRequested()) {

            /*if (gamepad1.left_trigger <= .3){
                adjustedGamepad1LeftY = smoother.SmoothGamepad1LeftY(gamepad1.left_stick_y, 99.1);
                adjustedGamepad1RightY = smoother.SmoothGamepad1RightY(gamepad1.right_stick_y, 99.1);
            } else {
                adjustedGamepad1LeftY = gamepad1.left_stick_y;
                adjustedGamepad1RightY = gamepad1.right_stick_y;
            }

            if (gamepad1.right_trigger <= .3) {
                speedModifier = 1;
            }
            else {
                speedModifier = .5;
            }*/

            double denominator = Math.max(Math.abs(gamepad1.left_stick_y)+ Math.abs(gamepad1.right_stick_x), 1);

            double leftMotorPower = (gamepad1.left_stick_y - gamepad1.right_stick_x) / denominator;
            double rightMotorPower = (gamepad1.left_stick_y + gamepad1.right_stick_x) / denominator;

            trackPorter.drive.drive(
                    (leftMotorPower * speedModifier),
                    (rightMotorPower * speedModifier)
            );
        }
    }
}
