/*
 * Created on 01.08.2007
 */
package pro.sm.resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import pro.java.util.PRoArrayList;
import pro.sm.highscore.Entry;

/**
 * Die Klasse <code>WriterSM</code> schreibt die <code>Resourcen-Dateien</code>
 * des Programm.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 01.08.2007
 * @version 1.0
 */
public class WriterSM {

    private final static String NEWLINIE = System.getProperty("line.separator");

    public static final void write(final PRoArrayList<Entry> entrys) {

        try {

            /*final File f = new File("pro/sm/resource");
             if (!f.exists()) { f.mkdirs();	}*/
            ReaderSM.checkResourceFolder();

            final BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File("pro/sm/resource/highscore.txt")
            ));

            for (final Iterator<Entry> iter = entrys.iterator(); iter.hasNext();) {

                final Entry e = iter.next();
                bw.write(e.toString());

                if (iter.hasNext()) {
                    bw.write(NEWLINIE);
                }
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
