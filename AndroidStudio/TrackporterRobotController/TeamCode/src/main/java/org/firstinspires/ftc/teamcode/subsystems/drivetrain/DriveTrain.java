package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {

    CRServo leftMotor;
    CRServo rightMotor;

    Telemetry telemetry;
    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    public DriveTrain(HardwareMap map, Telemetry telemetry) {
        this.telemetry = telemetry;

        leftMotor = map.get(CRServo.class, "leftMotor");
        rightMotor = map.get(CRServo.class, "rightMotor");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setLeftMotor(double power) {
        leftMotor.setPower(power);

        return;
    }

    public void setRightMotor(double power) {
        rightMotor.setPower(power);

        return;
    }

    public void drive(double leftMotorPower, double rightMotorPower) {
        setLeftMotor(leftMotorPower);
        setRightMotor(rightMotorPower);

        telemetry.addData("Left Motor Power", leftMotorPower);
        telemetry.addData("Right Motor Power", rightMotorPower);
        telemetry.update();

        packet.put("Left Motor Power", leftMotorPower);
        packet.put("Right Motor Power", rightMotorPower);
        dashboard.sendTelemetryPacket(packet);

        return;
    }
}
