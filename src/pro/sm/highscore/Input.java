/*
 * Created on 27.07.2007
 */
package pro.sm.highscore;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pro.java.awt.PRoBorderLayout;
import pro.java.awt.PRoGridLayout;
import pro.java.swing.PRoDialog;
import pro.java.swing.PRoPanel;
import pro.sm.SM;

/**
 * @Info -
 * @WhatToDo -
 *
 * @author PRo (Peter Rogge) | Copyright (c) 27.07.2007
 * @version 1.0
 */
public class Input extends PRoDialog implements ActionListener {

    private static final long serialVersionUID = 1208130082512655858L;

    private JTextField jtfInput = null;
    private String name = null;

    private SM sm = null;

    public Input(SM sm) {

        super((JFrame) sm.getContainer(), "Neuer Highscore!!", Boolean.TRUE);

        this.sm = sm;

        this.init();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(
     * java.awt.event.ActionEvent
     * )
     */
    public final void actionPerformed(final ActionEvent e) {

        final JTextField tf = (JTextField) e.getSource();
        name = tf.getText();

        final int length = name.length();
        if (length <= 10) {
            this.validateName(tf, name);
        }
        if (length > 10) {
            this.checkName(tf);
        }
    }

    private final void checkName(final JTextField tf) {

        final int answer = JOptionPane.showConfirmDialog(
                this,
                "Die Eingabe ist länger als 10 Buchstaben,\n"
                + "nur die ersten 10 Buchstaben werden übernommen.\n"
                + "Fortfahren?",
                "Eingabe",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (answer == JOptionPane.YES_OPTION) {

            this.validateName(tf, name.substring(0, 10));
        }

        if (answer == JOptionPane.NO_OPTION) {

            tf.selectAll();
        }
    }

    /* (non-Javadoc)
     * @see pro.java.swing.PRoDialog#init()
     */
    @Override
    protected final void init() {

        name = sm.getName();

        super.init();
        super.setBackground(Color.WHITE);

        try {

            Thread.sleep(500);
        } catch (Exception e) {
        }

        jtfInput.addActionListener(this);
        jtfInput.selectAll();
        jtfInput.requestFocusInWindow();
    }

    /* (non-Javadoc)
     * @see pro.java.swing.PRoDialog#initCenter()
     */
    @Override
    protected final JComponent initCenter() {

        final PRoPanel pp = new PRoPanel(new PRoGridLayout(2, 1));
        pp.setBackground(new Color(230, 230, 230));
        pp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel jl = new JLabel("Ihr Name?", SwingConstants.CENTER);
        jl.setFont(new Font("Dialog", Font.PLAIN, 16));
        pp.add(jl);

        jl = new JLabel(
                "(Maximal 10 Buchstaben)", SwingConstants.CENTER
        );
        jl.setFont(new Font("Dialog", Font.PLAIN, 12));
        pp.add(jl, PRoBorderLayout.CENTER);

        return pp;
    }

    /* (non-Javadoc)
     * @see pro.java.swing.PRoDialog#initSouth()
     */
    @Override
    protected final JComponent initSouth() {

        final PRoPanel pp = new PRoPanel();
        pp.setBackground(Color.WHITE);

        jtfInput = new JTextField(name, 12);
        jtfInput.setBackground(new Color(210, 210, 210));
        jtfInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jtfInput.setFont(new Font("Dialog", Font.PLAIN, 16));
        jtfInput.setHorizontalAlignment(SwingConstants.CENTER);
        jtfInput.setOpaque(Boolean.TRUE);
        pp.add(jtfInput, PRoBorderLayout.CENTER);

        return pp;
    }

    private final void validateName(final JTextField tf, final String name) {

        if (!name.contains(";")) {

            sm.setUserName(name);
            this.closePRoDialog();
        } else {

            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(
                    this,
                    "Das Zeichen ';' wird für die Administration benötigt."
                    + "\nBitte ein anderes Zeichen eingeben.",
                    "Sonderzeichen!",
                    JOptionPane.WARNING_MESSAGE
            );

            tf.selectAll();
        }
    }
}
