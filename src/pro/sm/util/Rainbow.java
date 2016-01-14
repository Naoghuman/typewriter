/*
 * Created on 01.08.2007
 */
package pro.sm.util;

import java.awt.Color;

/**
 * In der Klasse <code>Rainbow</code> wird die Funktionalität der
 * Farbgenerierung (Rot bis Violett) zur Verfügung gestellt.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 01.08.2007
 * @version 1.0
 */
public class Rainbow {

    private static Color rainbow = null;

    public final static Color getRainbow(final float hue, float saturation) {

        rainbow = new Color(hueToRGB(hue, saturation));

        return rainbow;
    }

    /**
     * Converts the <code>hue-component</code> of a color, as specified by the
     * <code>HSB-Model</code>, to an equivalent set of values for the default
     * <code>RGB-Model</code>.<p>
     *
     * The <code>saturation-</code> and <code>brightness-component</code> are
     * set to 1.0f. The <code>hue</code> component can be any floating-point
     * number. The floor of this number is subtracted from it to create a
     * fraction between 0 and 1. This fractional number is then multiplied by
     * 360 to produce the hue angle in the HSB color model.<p>
     *
     * The integer that is returned by <code>hueToRGB</code> encodes the value
     * of a color in bits 0-23 of an integer value that is the same format used
     * by the method {@link #getRGB() <code>getRGB</code>}. This integer can be
     * supplied as an argument to the <code>Color</code> constructor that takes
     * a single integer argument.<p>
     *
     * This methode returns a cyclically color from <code>red</code> to
     * <code>violet</code> etc., if <code>hue</code> cyclically form 0.0f to
     * 1.0f.<p>
     *
     * <b>Infomation:</b><br>
     * This method is double so fast then the methode
     * <code>Color.HSBtoRGB(float, float, float)</code>.
     *
     * @param hue the hue component of the color.
     * @return the RGB value of the color with the indicated hue.
     *
     * @see java.awt.Color#getRGB()
     * @see java.awt.Color#Color(int)
     * @see java.awt.image.ColorModel#getRGBdefault()
     */
    private static final int hueToRGB(
            final float hue, float saturation
    ) {
        final double h = (hue - Math.floor(hue)) * 6;
        final double f = h - Math.floor(h);

        final double p = 1 * (1.0f - saturation);
        final double q = 1 * (1 - saturation * f);
        final double t = 1 * (1 - (saturation * (1 - f)));

        int r = 0, g = 0, b = 0;
        switch ((int) h) {
            case 0: {
                r = 255;
                g = (int) (t * 255 + 0.5d);
                b = (int) (p * 255.0f + 0.5f);
                break;
            }
            case 1: {
                r = (int) (q * 255 + 0.5d);
                g = 255;
                b = (int) (p * 255.0f + 0.5f);
                break;
            }
            case 2: {
                r = (int) (p * 255.0f + 0.5f);
                g = 255;
                b = (int) (t * 255 + 0.5d);
                break;
            }
            case 3: {
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (q * 255 + 0.5d);
                b = 255;
                break;
            }
            case 4: {
                r = (int) (t * 255 + 0.5d);
                g = (int) (p * 255.0f + 0.5f);
                b = 255;
                break;
            }
            case 5: {
                r = 255;
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (q * 255 + 0.5d);
                break;
            }
        }

        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }
}
