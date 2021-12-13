package org.firstinspires.ftc.teamcode.subsystems.controls;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ControlSmoothing {

    double Gamepad1LeftYPreviousInput;
    double Gamepad1RightYPreviousInput;

    public ControlSmoothing() {
        Gamepad1LeftYPreviousInput = 0;
        Gamepad1RightYPreviousInput = 0;
    }

    public double SmoothGamepad1LeftY(double input, double percentage) {
        double decimalPercentage = percentage/100; //Convert percentage to a decimal percentage
        double invertPercentage = 1 - decimalPercentage; //Inverts the percentage to get the remainder of the percentage

        double smoothedInput = (input * invertPercentage) + (Gamepad1LeftYPreviousInput * decimalPercentage);

        Gamepad1LeftYPreviousInput = smoothedInput;

        return smoothedInput;
    }

    public double SmoothGamepad1RightY(double input, double percentage) {
        double decimalPercentage = percentage/100; //Convert percentage to a decimal percentage
        double invertPercentage = 1 - decimalPercentage; //Inverts the percentage to get the remainder of the percentage

        double smoothedInput = (input * invertPercentage) + (Gamepad1RightYPreviousInput * decimalPercentage);

        Gamepad1RightYPreviousInput = smoothedInput;

        return smoothedInput;
    }
}
