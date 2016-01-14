/*
 * Created on 29.07.2007
 */
package pro.sm.resource;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

import pro.java.swing.PRoEditorPane;
import pro.java.util.PRoArrayList;

/**
 * Die Klasse <code>ReaderSM</code> liest die <code>Resource-Dateien</code> ein.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 29.07.2007
 * @version 1.0
 */
public class ReaderSM {

    private final static String BACKFLASH = System.getProperty(
            "file.separator"
    );
    private final static String CLASSPATH = System.getProperty(
            "java.class.path"
    );
    private final static String HIGHSCORE = "highscore.txt";
    private final static String PATH = BACKFLASH + "pro" + BACKFLASH + "sm"
            + BACKFLASH + "resource";
    private final static String PATH_HIGHSCORE = PATH + BACKFLASH + HIGHSCORE;

    public static final JEditorPane readHTML(final String path) {

        final JEditorPane jep = new PRoEditorPane();
        jep.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        jep.setEditable(Boolean.FALSE);
        jep.setFocusable(Boolean.FALSE);
        jep.setPreferredSize(new Dimension(400, 235));

        final URL helpURL = ClassLoader.getSystemResource(path);
        try {

            jep.setPage(helpURL);
            jep.setSelectionStart(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jep;
    }

    public final PRoArrayList<String> readTXT(final String file) {

        final PRoArrayList<String> pal = new PRoArrayList<String>();

        BufferedReader br = null;
        String line = "";
        try {
            try {
                if (!file.equals(HIGHSCORE)) {

                    throw new FileNotFoundException();
                }

                checkResourceFolder();

                // Liest außerhalb des Jar-Archivs.
                br = new BufferedReader(new FileReader(new File(
                        CLASSPATH
                ).getParent() + PATH_HIGHSCORE
                ));
            } catch (FileNotFoundException e) {

                // Liest innerhalb des Jar-Archivs.
                br = new BufferedReader(new InputStreamReader(
                        getClass().getResourceAsStream(file)
                ));
            }

            while ((line = br.readLine()) != null) {

                if (!line.equals("") && !line.startsWith("#")) {

                    pal.add(line);
                }
            }

            br.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return pal;
    }

    public static void checkResourceFolder() {

        final File f = new File("pro/sm/resource");
        if (!f.exists()) {
            f.mkdirs();
        }
    }
}
