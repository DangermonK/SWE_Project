import datentypen.Classtype;
import datentypen.Dateiformat;
import datentypen.SuchkriteriumExponat;
import de.dhbwka.swe.utils.gui.TextComponent;
import model.Exponat;
import model.Historie;
import model.Kuenstler;
import util.EntityAdapter;
import util.Statics;
import util.StorageAdapter;
import view.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MuseumsController {

    private EntityAdapter entityAdapter;

    private StorageAdapter storageAdapter;

    public MuseumsController() {
        entityAdapter = new EntityAdapter();
        storageAdapter = new StorageAdapter();

        List<String[]> data = storageAdapter.importData("src/assets/database/data.json", Dateiformat.JSON);
        entityAdapter.createAll(data);

        Object[][] tabellenArr = new Object[entityAdapter.getExponatList().size()][6];
        for(int i = 0; i < tabellenArr.length; i++) {
            tabellenArr[i][0] = entityAdapter.getExponatList().get(i).getInventarnummer();
            tabellenArr[i][1] = entityAdapter.getExponatList().get(i).getName();
            tabellenArr[i][2] = entityAdapter.getExponatList().get(i).getRaum().getNummer();
            tabellenArr[i][3] = entityAdapter.getExponatList().get(i).getKuenstler().getName();
            tabellenArr[i][4] = entityAdapter.getExponatList().get(i).getKategorie();
            Aenderung date = entityAdapter.getExponatList().get(i).getHistorie().getLetzteAenderung();
            tabellenArr[i][5] = (date != null ? Statics.dateFormat.format(date.getAenderungsDatum()) : null);
        }

        String[] pathsArr = new String[((Exponat)entityAdapter.getElement(Classtype.EXPONAT, tabellenArr[0][0])).getBildList().size()];
        for(int i = 0; i < pathsArr.length; i++) {
            pathsArr[i] = ((Exponat)entityAdapter.getElement(Classtype.EXPONAT,  tabellenArr[0][0])).getBildList().get(i).getPfad();
        }

        new MainGUI(pathsArr, new String[] {
                SuchkriteriumExponat.RAUM.toString(),
                SuchkriteriumExponat.NAME.toString(),
                SuchkriteriumExponat.KUENSTLERNAME.toString(),
                SuchkriteriumExponat.KATEGORIE.toString(),
                SuchkriteriumExponat.INVENTARNUMMER.toString(),
                SuchkriteriumExponat.AENDERUNGSDATUM.toString()
        }, tabellenArr);
    }

    public static void main(String[] args) {

        MuseumsController controller = new MuseumsController();

        controller.storageAdapter.exportData(controller.entityAdapter.getAllData(), "src/assets/database/data.json");

    }

}
