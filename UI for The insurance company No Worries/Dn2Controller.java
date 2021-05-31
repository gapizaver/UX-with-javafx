package dn2;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Dn2Controller implements Initializable {
    public TextField zamenjava;
    public TextField iskana;
    public TextArea log;
    public TextArea textEditor;
    public HTMLEditor htmlEditor;
    public Label status;
    public TitledPane htmlEditorPane;
    public TitledPane textEditorPane;
    public Accordion accordion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accordion.setExpandedPane(htmlEditorPane);
    }

    private void addLog(String s){
        log.appendText(s + "\n");
        status.setText("Status: " + s);
    }

    public void odpriCB(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi HTML datoteko");

        File f = fc.showOpenDialog(null);
        if (f != null) {
            StringBuffer sb = new StringBuffer();
            try (BufferedReader br = new BufferedReader((new FileReader(f)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                htmlEditor.setHtmlText(sb.toString());
                textEditor.setText(sb.toString());
                addLog("Prebral si datoteko: " + f.getAbsolutePath() + ", velikost: "+ f.length() +"B");
            } catch (Exception e) {
                System.out.println(e.toString());
                addLog("Neuspešno branje datoteke.");
            }
        }
    }

    public void shraniCB(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi datoteko za shranjevanje");
        File f = fc.showOpenDialog(null);

        if (f != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(htmlEditor.getHtmlText());
                bw.close();
                addLog("Shranil si v datoteko: " + f.getAbsolutePath());
            } catch (Exception e) {
                System.out.println(e.toString());
                addLog("Neuspešno shranjevanje datoteke.");
            }
        }
    }

    public void izhodCB(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void najdiCB(ActionEvent actionEvent) {
        if (textEditorPane.isExpanded()) {
            String searched = iskana.getText();
            if (searched.equals("")) {
                addLog("Za iskanje niza vpiši niz v polje \"Iskana beseda\"");
                return;
            }
            int index = textEditor.getText().indexOf(searched);
            textEditor.requestFocus();
            textEditor.positionCaret(index);
        } else {
            addLog("Za iskanje odpri besedilni urejevalnik HTML");
        }

    }

    public void najdiVseCB(ActionEvent actionEvent) {
        String searched = iskana.getText();
        String replacement = zamenjava.getText();

        if (searched.equals("") || replacement.equals("")) {
            addLog("Za zamenjavo vpiši niza v polji \"Iskana beseda\" in \"Zamenjaj z\"");
            return;
        }

        String newText = htmlEditor.getHtmlText().replaceAll(searched, replacement);

        htmlEditor.setHtmlText(newText);
        textEditor.setText(newText);

        addLog("Nize "+ searched +" si zamenjal z nizom "+ replacement);
    }

    public void avtorCB(ActionEvent actionEvent) {
        log.setText("Izbral si izpis avtorja te mega aplikacije\n");
        status.setText("Jaz, Gašper Levačič, sem avtor te mega aplikacije.");
    }

    public void test(KeyEvent keyEvent) {
        System.out.println("test");
    }

    public void updateText(KeyEvent keyEvent) {
        textEditor.setText(htmlEditor.getHtmlText());
    }
}
