/*
 * Created on 28.07.2007
 */
package pro.sm.highscore;

import java.text.DecimalFormat;

import pro.java.util.PRoArrayList;

/**
 * Die Klasse <code>Entry</code> enthält die Daten für einen Highscore-Eintrag.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public class Entry implements Comparable<Object>, IEntry, ITotal {

    private static final String GREEN = "00C000";
    private static final String RED = "FF0000";

    private static final String HTML_PREFIX = "<html>";
    private static final String HTML_SUFFIX = "</html>";

    private static final String FONT_COLOR_PREFIX = "<font color=\"#";
    private static final String FONT_COLOR_SUFFIX = "\">";
    private static final String FONT_SUFFIX = "</font>";

    private int total = 0;

    private String date = null;
    private String name = null;

    private Data<Integer> malus = null;
    private Data<Double> percent = null;
    private Data<Integer> points = null;
    private Data<Integer> tipped = null;
    private Data<Integer> velocity = null;
    private Data<Integer> wrong = null;

    private PRoArrayList<Data<?>> data = null;

    public Entry() {
        this.init();
    }

    private final int compareDate(final String eDate) {

        final int year = Integer.parseInt(date.substring(6, 10));
        final int eYear = Integer.parseInt(eDate.substring(6, 10));
        int cY = 0;
        if (year < eYear) {
            cY = -500;
        } // -100
        if (year > eYear) {
            cY = 500;
        } // 100

        final int month = Integer.parseInt(date.substring(3, 5));
        final int eMonth = Integer.parseInt(eDate.substring(3, 5));
        int cM = 0;
        if (month < eMonth) {
            cM = -125;
        }
        if (month > eMonth) {
            cM = 125;
        }

        final int day = Integer.parseInt(date.substring(0, 2));
        final int eDay = Integer.parseInt(eDate.substring(0, 2));
        int cD = 0;
        if (day < eDay) {
            cD = -25;
        } // -100
        if (day > eDay) {
            cD = 25;
        } // 100

        // XXX
        System.out.println(
                "this: " + this.getDate().toString()
                + " - eDate: " + eDate.toString()
                + " = " + (cY + cM + cD)
        );

        return cY + cM + cD;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public final int compareTo(final Object o) {

        final Entry e = (Entry) o;
        final int cPoints = points.compareTo(e.getPoints());
        final int cVeloCity = velocity.compareTo(e.getVeloCity());
        final int cName = name.compareTo(e.getName()) * -1000;
        final int cDate = this.compareDate(e.getDate());
        final int cTipped = tipped.compareTo(e.getTipped());
        final int cWrong = wrong.compareTo(e.getWrong());
        final int cPercent = percent.compareTo(e.getPercent());
        final int cMalus = malus.compareTo(e.getMalus());

        final int compareTo = cPoints + cVeloCity + cName + cDate
                + cTipped + cWrong + cPercent + cMalus;

        return compareTo * -1;
    }

    public void createEntry(String[] data) {

        points.set(Integer.parseInt(data[0]));
        velocity.set(Integer.parseInt(data[1]));
        name = data[2];
        date = data[3];
        tipped.set(Integer.parseInt(data[4]));
        wrong.set(Integer.parseInt(data[5]));
        percent.set(Double.parseDouble(data[6]));
        malus.set(Integer.parseInt(data[7]));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object obj) {

        final Entry e = (Entry) obj;
        final boolean equals
                = points.equals(e.getPoints())
                && velocity.equals(e.getVeloCity())
                && name.equals(e.getName())
                && date.equals(e.getDate())
                && tipped.equals(e.getTipped())
                && wrong.equals(e.getWrong())
                && percent.equals(e.getPercent())
                && malus.equals(e.getMalus());

        return equals;
    }

    public final String format(final byte b) {

        return data.get(b).format();
    }

    public final String getDate() {
        return date;
    }

    public PRoArrayList<Object> getFormat(Entry e) {

        PRoArrayList<Object> o = new PRoArrayList<Object>();

        o.add(e.format(POINTS));
        o.add(e.format(VELOCITY));
        o.add(e.getName());
        o.add(e.getDate());
        o.add(e.format(TIPPED));
        o.add(e.format(WRONG));
        o.add(e.format(PERCENT) + "%");
        o.add(e.format(MALUS));

        return o;
    }

    public final Data<Integer> getMalus() {
        return this.malus;
    }

    public final String getName() {
        return name;
    }

    public final Data<Double> getPercent() {
        return this.percent;
    }

    public final Data<Integer> getPoints() {
        return this.points;
    }

    public final Data<Integer> getTipped() {
        return this.tipped;
    }

    /* (non-Javadoc)
     * @see pro.sm.highscore.ITotal#getTotal()
     */
    public final int getTotal() {
        return this.total;
    }

    public final Data<Integer> getVeloCity() {
        return velocity;
    }

    public final Data<Integer> getWrong() {
        return this.wrong;
    }

    private final void init() {

        name = System.getProperty("user.name");

        data = new PRoArrayList<Data<?>>();

        malus = this.initMalus();
        data.add(malus);

        percent = this.initPercent();
        data.add(percent);

        points = this.initPoints();
        data.add(points);

        tipped = this.initTipped();
        data.add(tipped);

        velocity = this.initVelocity();
        data.add(velocity);

        wrong = this.initWrong();
        data.add(wrong);

        this.reset();
    }

    public final Data<Integer> initMalus() {

        if (malus == null) {

            malus = new Data<Integer>(
                    new DecimalFormat("0000"), 1, -1
            ) {
                @Override
                public String toString() {

                    String color = RED;
                    if (this.get() <= 0) {
                        color = GREEN;
                    }

                    final String sMalus = HTML_PREFIX
                            + "Malus: "
                            + FONT_COLOR_PREFIX
                            + color
                            + FONT_COLOR_SUFFIX
                            + this.format()
                            + FONT_SUFFIX
                            + HTML_SUFFIX;

                    return sMalus;
                }
            };
        }

        return malus;
    }

    public final Data<Double> initPercent() {

        if (percent == null) {

            percent = new Data<Double>(
                    new DecimalFormat("000.00"), 1, -1
            ) {
                @Override
                public String format() {

                    return super.format().replace(',', '.');
                }

                @Override
                public String toString() {

                    final String sPercent = "Prozent: "
                            + this.format()
                            + "%";

                    return sPercent;
                }
            };
        }

        return percent;
    }

    public final Data<Integer> initPoints() {

        if (points == null) {

            points = new Data<Integer>(
                    new DecimalFormat("00000"), -1000000, 1000000
            ) {
                @Override
                public String toString() {

                    String color = GREEN;
                    if (this.get() < 0) {
                        color = RED;
                    }

                    final String sPoint = HTML_PREFIX
                            + "Punkte: "
                            + FONT_COLOR_PREFIX
                            + color
                            + FONT_COLOR_SUFFIX
                            + this.format()
                            + FONT_SUFFIX
                            + HTML_SUFFIX;

                    return sPoint;
                }
            };
        }

        return points;
    }

    public final Data<Integer> initTipped() {

        if (tipped == null) {

            tipped = new Data<Integer>(
                    new DecimalFormat("0000"), -10, 10
            ) {
                @Override
                public String toString() {

                    final String sTipped = "Zeichen: " + this.format();

                    return sTipped;
                }
            };
        }

        return tipped;
    }

    public final Data<Integer> initVelocity() {

        if (velocity == null) {

            velocity = new Data<Integer>(
                    new DecimalFormat("000"), -100000, 100000
            ) {
                @Override
                public String toString() {

                    return this.format() + " an/min";
                }
            };
        }

        return velocity;
    }

    public final Data<Integer> initWrong() {

        if (wrong == null) {

            wrong = new Data<Integer>(
                    new DecimalFormat("000"), 1, -1
            ) {
                @Override
                public String toString() {

                    String color = RED;
                    if (this.get() <= 0) {
                        color = GREEN;
                    }

                    final String sWrong = HTML_PREFIX
                            + "Fehler: "
                            + FONT_COLOR_PREFIX
                            + color
                            + FONT_COLOR_SUFFIX
                            + this.format()
                            + FONT_SUFFIX
                            + HTML_SUFFIX;

                    return sWrong;
                }
            };
        }

        return wrong;
    }

    public final void reset() {

        malus.set(0);
        percent.set(0.0d);
        points.set(0);
        tipped.set(0);
        velocity.set(50);
        wrong.set(0);

        total = 0;
    }

    public final void setDate(final String date) {
        this.date = date;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see pro.sm.highscore.ITotal#setTotal(int)
     */
    public final void setTotal(final int total) {
        this.total = total;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {

        final String entry = points.format() + ";"
                + velocity.format() + ";"
                + name + ";"
                + date + ";"
                + tipped.format() + ";"
                + wrong.format() + ";"
                + percent.format() + ";"
                + malus.format();

        return entry;
    }

    public final String toString(final byte b) {

        if (b == PERCENT) {

            if (tipped.get() != 0 && wrong.get() != 0) {

                final double dTippedChars = tipped.get();
                final double dWrongChars = wrong.get();
                percent.set(100.0d / dTippedChars * dWrongChars);
            }

            return percent.toString();
        }

        return data.get(b).toString();
    }
}
