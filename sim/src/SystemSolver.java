package src;

import java.util.HashMap;
import java.util.Map;

/*
    # This program calculates the desired velocity and angle for an object to land inside the target
    # during the FRC 2024 season!

    The program uses the Gauss-Jordan elimination algorithm to solve the system of equations.

    ## Equations:
    v0 * cos(θ) + v * cos(α) = 2Δx
    v0 * sin(θ) + v * sin(α) = 2Δy

    ## Constants:
    - Δx: Representing the distance difference from the shooter to the target:
    - Δy: Representing the height difference from the shooter to the target:
    - α:  Representing the angle in which the object should land at inside the target: 110deg
    - v0: Representing the initial velocity of the object:

    ## Variables:
    - v: Representing the desired velocity of the object:
    - θ: Representing the angle of the shooter:
*/



public class SystemSolver {

    private static final int MAX_EQUATIONS = 2;
    private static final double PI = 3.14159265358979323846;
    private static final double TO_DEGREES = 180.0 / PI;
    private static final int VELOCITY_INDEX = 0;
    private static final int THETA_INDEX = 1;

    private double dx;
    private double dy;
    private double v0;
    private double alpha;

    private double[][] coefficients = new double[MAX_EQUATIONS][MAX_EQUATIONS + 1];
    private double[] x = new double[MAX_EQUATIONS];

    public SystemSolver(double dx, double dy, double v0, double alpha) {
        this.dx = dx;
        this.dy = dy;
        this.v0 = v0;
        this.alpha = alpha;

        coefficients[0][0] = v0;
        coefficients[0][1] = Math.cos(alpha * TO_DEGREES);

        coefficients[1][0] = v0;
        coefficients[1][1] = Math.sin(alpha * TO_DEGREES);
        coefficients[1][2] = 2 * dy;
    }

    private void partialPivot() {
        for (int i = 0; i < MAX_EQUATIONS; i++) {
            int pivotRow = i;

            for (int j = i + 1; j < MAX_EQUATIONS; j++) {
                if (Math.abs(coefficients[j][i]) > Math.abs(coefficients[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            if (pivotRow != i) {
                for (int j = i; j <= MAX_EQUATIONS; j++) {
                    double temp = coefficients[i][j];
                    coefficients[i][j] = coefficients[pivotRow][j];
                    coefficients[pivotRow][j] = temp;
                }
            }

            for (int j = i + 1; j < MAX_EQUATIONS; j++) {
                double factor = coefficients[j][i] / coefficients[i][i];

                for (int k = i; k <= MAX_EQUATIONS; k++) {
                    coefficients[j][k] -= factor * coefficients[i][k];
                }
            }
        }
    }

    private void backSubstitute() {
        for (int i = MAX_EQUATIONS - 1; i >= 0; i--) {
            double sum = 0;

            for (int j = i + 1; j < MAX_EQUATIONS; j++) {
                sum += coefficients[i][j] * x[j];
            }

            x[i] = (coefficients[i][MAX_EQUATIONS] - sum) / coefficients[i][i];
        }
    }

    public Map<String, Double> solveSystem() {
        partialPivot();
        backSubstitute();

        double velocity = x[VELOCITY_INDEX];
        double theta = Math.cos(x[THETA_INDEX]) * TO_DEGREES;

        System.out.println("Solution for the system:");
        System.out.println("v = " + velocity);
        System.out.println("θ = " + theta);
        System.out.println("---------------------------------");

        Map<String, Double> results = new HashMap<>();
        results.put("v", velocity);
        results.put("theta", theta);

        return results;
    }

    public static void main(String[] args) {
        SystemSolver solver1 = new SystemSolver(3.5, 3.0, 15.0, 130.0);
        Map<String, Double> result1 = solver1.solveSystem();

        System.out.println(result1.get("v"));
        System.out.println(result1.get("theta"));
    }
}
