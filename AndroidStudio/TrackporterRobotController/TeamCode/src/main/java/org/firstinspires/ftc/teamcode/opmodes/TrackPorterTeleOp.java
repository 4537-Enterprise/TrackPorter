package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.trackporter.TrackPorter;

@TeleOp(name = "TrackPorter TeleOp", group = "TeleOp")
public class TrackPorterTeleOp extends LinearOpMode {

    TrackPorter trackPorter;

    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        trackPorter = new TrackPorter(hardwareMap, telemetry);

        waitForStart();

        while(!isStopRequested()) {
            trackPorter.drive.drive(gamepad1.left_stick_y, gamepad1.right_stick_y);
        }
    }
}
