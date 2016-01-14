/*
 * Created on 26.07.2007
 */
package pro.sm;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import pro.java.awt.PRoBorderLayout;
import pro.java.awt.PRoFlowLayout;
import pro.java.swing.PRoLabel;
import pro.java.swing.PRoPanel;
import pro.java.util.PRoArrayList;
import pro.sm.exercise.Exercise;
import pro.sm.exercise.IExercise;
import pro.sm.highscore.Entry;
import pro.sm.highscore.HighScore;
import pro.sm.highscore.ITotal;
import pro.sm.util.Listener;
import pro.sm.util.Menue;
import pro.sm.util.Rainbow;

/**
 * Die Klasse <code>SM</code> enthält die Implementierung der GUI des Programms
 * und deren Zugriffs-Methoden.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 26.07.2007
 * @version 1.0
 */
public class SM extends PRoPanel implements IExercise, ITotal {

    private static final float VELOCITY_COLOR = 0.85f / 251;

    private static final Color BACKGROUND_BUTTONS = new Color(225, 225, 225);
    private static final Color BACKGROUND_INFO = new Color(240, 240, 240);

    private static final long serialVersionUID = 4356439939920041504L;

    // Jedes Zeichen ist gleich breit.
    private static final Font FONT = new Font("Monospaced", Font.BOLD, 42);

    private static final PRoArrayList<JLabel> LABELS
            = new PRoArrayList<JLabel>();

    private static float fHue = 0.0f;

    /**
     * Liefert den aktuellen <code>hue-Wert</code>. Der Wert ist f�r die
     * Darstellung der Farben bei der Geschwindigkeitsanzeige und im Panel der
     * Buchstaben verantwortlich.
     *
     * @return float.
     */
    public static float getHue() {
        return fHue;
    }

    private Container parent = null;

    private JButton jbStart = null;
    private JButton jbStop = null;

    private JLabel jlLetters = null;
    private JLabel jlMalus = null;
    private JLabel jlPercent = null;
    private JLabel jlPoints = null;
    private JLabel jlTipped = null;
    private JLabel jlWrong = null;
    private JLabel jlVeloCity = null;

    private Entry entry = null;
    private HighScore highscore = null;
    private Listener listener = null;

    /**
     * Konstruktor der Klasse <code>SM</code>.
     *
     * @param parent Der <code>Container</code>, der diese Klasse enth�lt.
     */
    public SM(final Container parent) {

        this.parent = parent;

        this.init();
    }

    private final void addButtons() {

        final PRoPanel pp = new PRoPanel(new PRoFlowLayout(
                PRoFlowLayout.CENTER, 10, 0, 10, 0, 10, 0));
        pp.setBackground(BACKGROUND_BUTTONS);
        pp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        jbStart = new JButton("Start");
        jbStart.setFocusable(Boolean.FALSE);
        jbStart.setToolTipText("Drücke 'Start' zum Starten der Übung.");
        pp.add(jbStart);

        jbStop = new JButton("Stop");
        jbStop.setFocusable(Boolean.FALSE);
        jbStop.setToolTipText("Drücke 'Stop' zum Beenden der Übung.");
        pp.add(jbStop);

        super.add(pp, PRoBorderLayout.SOUTH);
    }

    private final void addComponents() {

        this.addInfo();
        this.addLetters();
        this.addButtons();
    }

    private final void addInfo() {

        final PRoPanel ppInfo = new PRoPanel(new PRoBorderLayout(0, 5));
        ppInfo.setBackground(BACKGROUND_INFO);
        ppInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final PRoPanel ppModus = new PRoPanel();
        ppModus.setBackground(BACKGROUND_INFO);
        final JLabel jl = new PRoLabel(
                "Schnell schreiben", SwingConstants.CENTER
        );
        jl.setFont(new Font("Dialog", Font.BOLD, 46));
        ppModus.add(jl, PRoBorderLayout.CENTER);
        ppInfo.add(ppModus, PRoBorderLayout.NORTH);

        final PRoPanel ppValues = new PRoPanel();

        // Geschwindigkeit.
        PRoPanel pp = new PRoPanel(new PRoFlowLayout(PRoFlowLayout.CENTER, 10,
                0, 0, 0, 5, 0));
        pp.setBackground(BACKGROUND_INFO);
        jlVeloCity = new PRoLabel(
                entry.getVeloCity().toString(), SwingConstants.LEFT
        );
        jlVeloCity.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        jlVeloCity.setBackground(Rainbow.getRainbow(fHue, 0.25f));
        jlVeloCity.setFont(new Font("Dialog", Font.BOLD, 22));
        jlVeloCity.setOpaque(Boolean.TRUE);
        pp.add(jlVeloCity);
        ppValues.add(pp, PRoBorderLayout.NORTH);

        // Statistik.
        pp = new PRoPanel(new PRoFlowLayout(PRoFlowLayout.CENTER, 10, 0));
        pp.setBackground(BACKGROUND_INFO);
        jlTipped = new PRoLabel(
                entry.getTipped().toString(), SwingConstants.LEFT
        );
        jlTipped.setFont(new Font("Dialog", Font.BOLD, 16));
        pp.add(jlTipped);

        jlWrong = new PRoLabel(
                entry.getWrong().toString(), SwingConstants.LEFT
        );
        jlWrong.setFont(new Font("Dialog", Font.BOLD, 16));
        pp.add(jlWrong);

        jlPercent = new PRoLabel(
                entry.getPercent().toString(), SwingConstants.LEFT
        );
        jlPercent.setFont(new Font("Dialog", Font.BOLD, 16));
        pp.add(jlPercent);
        ppValues.add(pp, PRoBorderLayout.CENTER);

        // Punkte.
        pp = new PRoPanel(new PRoFlowLayout(
                PRoFlowLayout.CENTER, 10, 0, 0, 0, 10, 0
        ));
        pp.setBackground(BACKGROUND_INFO);
        jlPoints = new PRoLabel(
                entry.getPoints().toString(), SwingConstants.LEFT
        );
        jlPoints.setFont(new Font("Dialog", Font.BOLD, 16));
        pp.add(jlPoints);

        jlMalus = new PRoLabel(
                entry.getMalus().toString(), SwingConstants.LEFT
        );
        jlMalus.setFont(new Font("Dialog", Font.BOLD, 16));
        pp.add(jlMalus);
        ppValues.add(pp, PRoBorderLayout.SOUTH);
        ppInfo.add(ppValues, PRoBorderLayout.CENTER);

        super.add(ppInfo, PRoBorderLayout.NORTH);
    }

    private final void addLetters() {

        final PRoPanel pp = new PRoPanel();
        pp.setBackground(Color.WHITE);
        pp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        jlLetters = new PRoLabel("", SwingConstants.RIGHT);
        jlLetters.setBackground(Rainbow.getRainbow(0.0f, 0.35f));
        jlLetters.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));
        jlLetters.setFont(FONT);
        jlLetters.setOpaque(Boolean.TRUE);
        pp.add(jlLetters, PRoBorderLayout.CENTER);

        super.add(pp, PRoBorderLayout.CENTER);
    }

    private final void addListener() {

        final Exercise exercise = new Exercise(this);
        listener = new Listener(exercise);

        jbStart.addActionListener(listener);
        jbStop.addActionListener(listener);

        parent.addKeyListener(listener);
    }

    /**
     * Liefert den ActionListener des Programms.
     *
     * @return ActionListener.
     */
    public final ActionListener getActionListener() {
        return listener;
    }

    /**
     * Liefert den Container der Klasse <code>StartSM</code>.
     *
     * @return Container.
     */
    public final Container getContainer() {
        return parent;
    }

    /**
     * Liefert eine Reference auf die Klasse <code>Entry</code>.
     *
     * @return Entry.
     */
    public final Entry getEntry() {
        return entry;
    }

    /*
     * (non-Javadoc)
     * @see pro.sm.highscore.ITotal#getTotal()
     */
    public final int getTotal() {
        return entry.getTotal();
    }

    private final void init() {

        super.setName(System.getProperty("user.name"));

        entry = new Entry();

        super.setBackground(new Color(195, 195, 195));
        super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        super.setLayout(new PRoBorderLayout(10, 10));

        this.addComponents();

        LABELS.add(jlLetters);
        LABELS.add(jlMalus);
        LABELS.add(jlPercent);
        LABELS.add(jlPoints);
        LABELS.add(jlTipped);
        LABELS.add(jlVeloCity);
        LABELS.add(jlWrong);

        this.addListener();

        ((JFrame) parent).setJMenuBar(new Menue(this));
        parent.add(SM.this, PRoBorderLayout.CENTER);
    }

    /**
     * Berechnet an Hand des Wertes <code>vc</code> die Farbentsprechung
     * (Geschwindigkeit - Anschl�ge/Minute).
     *
     * @param vc aktuelle Geschwindigkeit.
     */
    public final void setColorRainbow(final int vc) {

        fHue = (vc - 49) * VELOCITY_COLOR;

        jlLetters.setBackground(Rainbow.getRainbow(fHue, 0.45f));
        jlVeloCity.setBackground(Rainbow.getRainbow(fHue, 0.40f));
    }

    /*
     * (non-Javadoc)
     * @see pro.sm.highscore.ITotal#setTotal(int)
     */
    public final void setTotal(final int total) {

        entry.setTotal(total);
    }

    /**
     * Setzt den Username. Generiert gleichzeitig das aktuelle Datum und ruft
     * die Highscore-Tabelle auf.
     *
     * @param name der neue Username.
     */
    public final void setUserName(final String name) {

        super.setName(name);
        entry.setName(name);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",
                Locale.getDefault());
        final String date = formatter.format(new Date(), new StringBuffer(),
                new FieldPosition(0)).toString();
        entry.setDate(date);

        highscore = new HighScore(entry);
        highscore.showTable();
    }

    /*
     * (non-Javadoc)
     * 
     * @see pro.sm.IUpdate#update(byte)
     */
    public final void update(final byte b) {
        this.update(b, entry.toString(b));
    }

    /*
     * (non-Javadoc)
     * 
     * @see pro.sm.exercise.IExercise#update(byte, java.lang.String)
     */
    public final void update(final byte b, final String text) {

        // b kann bei -1 beginnen.
        LABELS.get(b + 1).setText(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see pro.sm.IUpdate#updateLetters(java.lang.StringBuffer)
     */
    public final void updateLetters(final StringBuffer sb) {

        jlLetters.setText(sb.toString());
    }
}
