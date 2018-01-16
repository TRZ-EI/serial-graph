package trz.gui;

import javafx.animation.Interpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 16/01/18
 * Time: 15.11
 */
public class BestFitSplineInterpolator extends Interpolator {

    final PolynomialSplineFunction f;

    BestFitSplineInterpolator(double[] x, double[] y) {
        f = new SplineInterpolator().interpolate(x, y);
    }

    @Override protected double curve(double t) {
        return f.value(t);
    }
}
