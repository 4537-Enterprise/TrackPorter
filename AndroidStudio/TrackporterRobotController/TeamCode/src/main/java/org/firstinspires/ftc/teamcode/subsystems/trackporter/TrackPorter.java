package org.firstinspires.ftc.teamcode.subsystems.trackporter;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.realsense.T265;

public class TrackPorter {

    public DriveTrain drive;
    public T265 t265;

    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    public TrackPorter(HardwareMap map, Telemetry telemetry) {
        drive = new DriveTrain(map, telemetry);
        t265 = new T265(map);

        telemetry.addData("Robot", "Initialized");
        telemetry.update();

        packet.put("TrackPorter", "Initialized");
        dashboard.sendTelemetryPacket(packet);
    }
}
