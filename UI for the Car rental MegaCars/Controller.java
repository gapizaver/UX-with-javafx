package dn3;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField ime;
    public TextField priimek;
    public TextField naslov;
    public TextField posta;
    public TextField kraj;
    public Spinner danRojstva;
    public ChoiceBox mesecRojstva;
    public Spinner letoRojstva;
    public CheckBox mladiVoznik;
    public ChoiceBox vrstaVozila;
    public Spinner stSedezev;
    public TextField prostornina;
    public TextField moc;
    public RadioButton gorivoElektrika;
    public RadioButton gorivoDiesel;
    public ToggleGroup gorivo;
    public RadioButton gorivoBencin;
    public TextField oznaka;
    public TextField znamka;
    public Label status;
    // spremenjeno ko uporabnik naredi prvo spremembo
    private static boolean spremenjeno = false;
    public TextField regPrvaKraj;
    public TextField reg;
    public DatePicker regPrvaDatum;
    public ComboBox dodatnoZavarovanje;
    public RadioButton ao;
    public RadioButton aoPlus;
    public RadioButton kaskoPolno;
    public RadioButton kaskoOdbitna;
    public RadioButton kaskoBrez;
    public MenuButton menuButton;
    public CheckMenuItem zavarovanjeStekel;
    public CheckMenuItem zavarovanjeParkirisce;
    public CheckMenuItem zavarovanjeTretjeOsebe;
    public CheckMenuItem zavarovanjeGum;
    public CheckMenuItem zavarovanjeProtiKraji;
    public CheckMenuItem zavarovanjeProtiToci;
    public MenuButton menuZavarovanje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        danRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1));
        letoRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2003, 1990));
        stSedezev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 99, 5));
        mesecRojstva.getItems().addAll("Januar", "Februar", "Marec", "April", "Maj", "Junij", "Julij", "Avgust", "September", "Oktober", "November", "December");
        mesecRojstva.setValue("Januar");
        vrstaVozila.getItems().addAll("Avtobus", "Motor", "Osebni avto", "Traktor", "Tovornjak");
        vrstaVozila.setValue("Osebni avto");
        regPrvaDatum.setValue(java.time.LocalDate.now());
    }

    public void onSpremembaHandler() {
        if (!spremenjeno) {
            spremenjeno = true;
        }
    }

    // dodatno preveri če uporabnik dejansko želi storiti to akcijo
    private boolean checkWithUser(String tekstZaPrikaz) {
        if (spremenjeno) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText(tekstZaPrikaz);
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = a.showAndWait();
            if (!result.isPresent())
                return false;
            else return result.get() == ButtonType.OK;
        } else
            return true;

    }

    public void odpri(ActionEvent actionEvent) {
        if (checkWithUser("Resnično želiš odpreti novo datoteko? Vnešeni podatki, ki niso shranjeni, bodo izgubljeni.")) {
            // izberi file
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(null);

            if (f == null)
                return;

            // load SaveFile objekt
            try {
                // naloadaj Savefile
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                SaveFile saveFile = (SaveFile) ois.readObject();
                status.setText("Status: odprl si datoteko "+ f.getAbsolutePath());

                // vstavi vrednosti v UV
                ime.setText(saveFile.ime);
                priimek.setText(saveFile.priimek);
                naslov.setText(saveFile.naslov);
                posta.setText(saveFile.posta);
                kraj.setText(saveFile.kraj);
                mesecRojstva.setValue(saveFile.mesecRojstva);
                danRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, saveFile.danRojstva));
                letoRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2003, saveFile.letoRojstva));
                mladiVoznik.setSelected(saveFile.mladiVoznik);
                vrstaVozila.setValue(saveFile.vrstaVozila);
                stSedezev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 99, saveFile.stSedezev));
                prostornina.setText(saveFile.prostornina);
                moc.setText(saveFile.moc);
                gorivoElektrika.setSelected(saveFile.gorivoElektrika);
                gorivoBencin.setSelected(saveFile.gorivoBencin);
                gorivoDiesel.setSelected(saveFile.gorivoDiesel);
                oznaka.setText(saveFile.oznaka);
                znamka.setText(saveFile.znamka);
                regPrvaKraj.setText(saveFile.regPrvaKraj);
                reg.setText(saveFile.reg);
                ao.setSelected(saveFile.ao);
                aoPlus.setSelected(saveFile.aoPlus);
                kaskoPolno.setSelected(saveFile.kaskoPolno);
                kaskoBrez.setSelected(saveFile.kaskoBrez);
                kaskoOdbitna.setSelected(saveFile.kaskoOdbitna);
                zavarovanjeStekel.setSelected(saveFile.zavarovanjeStekel);
                zavarovanjeParkirisce.setSelected(saveFile.zavarovanjeParkirisce);
                zavarovanjeTretjeOsebe.setSelected(saveFile.zavarovanjeTretjeOsebe);
                zavarovanjeGum.setSelected(saveFile.zavarovanjeGum);
                zavarovanjeProtiKraji.setSelected(saveFile.zavarovanjeProtiKraji);
                zavarovanjeProtiToci.setSelected(saveFile.zavarovanjeProtiToci);
                regPrvaDatum.setValue(saveFile.datumPrveRegistracije);

                status.setText("Status: datoteka ["+ f.getAbsolutePath() + "] uspešno prebrana.");
                spremenjeno = false;
            } catch (IOException e){
                e.printStackTrace();
                status.setText("Status: Prišlo je do napake pri branju datoteke.");
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                status.setText("Status: Prišlo je do napake pri branju datoteke.");
            }

        }
    }

    private boolean checkInput(){
        boolean manjka = false;
        String opozorilo = "Vpisali niste";

        if (ime.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " imena";
            manjka = true;
        }
        if (priimek.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " priimka";
            manjka = true;
        }
        if (moc.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " moči";
            manjka = true;
        }
        if (prostornina.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " prostornine";
            manjka = true;
        }
        if (!gorivoBencin.isSelected() && !gorivoDiesel.isSelected() && !gorivoElektrika.isSelected()){
            if (manjka)
                opozorilo += ",";
            opozorilo += " vrste goriva";
            manjka = true;
        }
        if (znamka.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " znamke";
            manjka = true;
        }
        if (oznaka.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " oznake";
            manjka = true;
        }
        if (naslov.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " naslova";
            manjka = true;
        }
        if (posta.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " pošte";
            manjka = true;
        }
        if (kraj.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " kraja";
            manjka = true;
        }
        if (!ao.isSelected() && !aoPlus.isSelected()){
            if (manjka)
                opozorilo += ",";
            opozorilo += " zavarovanja (AO, AO+)";
            manjka = true;
        }
        if (!kaskoPolno.isSelected() && !kaskoBrez.isSelected() && !kaskoOdbitna.isSelected()){
            if (manjka)
                opozorilo += ",";
            opozorilo += " kaska";
            manjka = true;
        }
        if (reg.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " registracijske označbe";
            manjka = true;
        }
        if (regPrvaKraj.getText().equals("")){
            if (manjka)
                opozorilo += ",";
            opozorilo += " kraja prve registracije";
            manjka = true;
        }

        if (!manjka){
            return true;
        } else {
            spremenjeno = true;
            return checkWithUser(opozorilo + ". Vseeno želiš nadaljevati?");
        }
    }

    private boolean checkDate(){
        if (regPrvaDatum.getValue().isAfter(java.time.LocalDate.now())
                || regPrvaDatum.getValue().isBefore(LocalDate.parse("1900-01-01"))){
            spremenjeno = true;
            return checkWithUser("Datum prve registracije se ne zdi korekten. Želiš vseeno nadaljevati?");
        } else if ((int) letoRojstva.getValue() < 1900 || (int) letoRojstva.getValue() > 2021){
            return checkWithUser("Datum rojstva se ne zdi korekten. Želiš vseeno nadaljevati?");
        } else
            return true;
    }

    private boolean checkPosta(){
        if (posta.getText().length() != 4
                || posta.getText().length() != posta.getText().replaceAll("[^0-9]", "").length()){
            return checkWithUser("Vnešena pošta \""+ posta.getText() +"\" verjetno ne obstaja. Želiš vseeno nadaljevati?");
        } else
            return true;
    }

    public void shrani(ActionEvent actionEvent) {
        if (!checkInput()) {
            return;
        }
        if (!checkDate())
            return;
        if (!checkPosta())
            return;

        SaveFile sf = new SaveFile();
        sf.ime = ime.getText();
        sf.priimek = priimek.getText();
        sf.posta = posta.getText();
        sf.naslov = naslov.getText();
        sf.kraj = kraj.getText();
        sf.danRojstva = (int) danRojstva.getValue();
        sf.mesecRojstva = mesecRojstva.valueProperty().getValue().toString();
        sf.letoRojstva = (int) letoRojstva.getValue();
        sf.mladiVoznik = mladiVoznik.isSelected();
        sf.vrstaVozila = vrstaVozila.valueProperty().getValue().toString();
        sf.stSedezev = (int) stSedezev.getValue();
        sf.prostornina = prostornina.getText();
        sf.moc = moc.getText();
        sf.gorivoElektrika = gorivoElektrika.isSelected();
        sf.gorivoBencin = gorivoBencin.isSelected();
        sf.gorivoDiesel = gorivoDiesel.isSelected();
        sf.oznaka = oznaka.getText();
        sf.znamka = znamka.getText();
        sf.regPrvaKraj = regPrvaKraj.getText();
        sf.reg = reg.getText();
        sf.ao = ao.isSelected();
        sf.aoPlus = aoPlus.isSelected();
        sf.kaskoPolno = kaskoPolno.isSelected();
        sf.kaskoOdbitna = kaskoOdbitna.isSelected();
        sf.kaskoBrez = kaskoBrez.isSelected();
        sf.zavarovanjeStekel = zavarovanjeStekel.isSelected();
        sf.zavarovanjeParkirisce = zavarovanjeParkirisce.isSelected();
        sf.zavarovanjeTretjeOsebe = zavarovanjeTretjeOsebe.isSelected();
        sf.zavarovanjeGum = zavarovanjeGum.isSelected();
        sf.zavarovanjeProtiKraji = zavarovanjeProtiKraji.isSelected();
        sf.zavarovanjeProtiToci = zavarovanjeProtiToci.isSelected();
        sf.datumPrveRegistracije = regPrvaDatum.getValue();

        try {
            // izberi file
            FileChooser fc = new FileChooser();
            File f = fc.showOpenDialog(null);

            if (f == null)
                return;

            // zasejvi object
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ois.writeObject(sf);

            ois.close();
            fos.close();

            status.setText("Status: datoteka ["+ f.getAbsolutePath() + "] uspešno shranjena.");
            spremenjeno = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zapri(ActionEvent actionEvent) {
        if (checkWithUser("Resnično želiš zapustiti aplikacijo? Vnešeni podatki, ki niso shranjeni, bodo izgubljeni.")){
            System.exit(0);
        }
    }

    public void oAvtorju(ActionEvent actionEvent) {
        status.setText("Status: Avtor te mega aplikacije sem Gašper Levačič, študent FRI.");
    }

    public void brisi(ActionEvent actionEvent) {
        if (checkWithUser("Resnično želiš vstaviti privzete vrednosti? Vnešeni podatki, ki niso shranjeni, bodo izgubljeni.")){
            znamka.setText("");
            oznaka.setText("");
            gorivoBencin.setSelected(false);
            gorivoElektrika.setSelected(false);
            gorivoDiesel.setSelected(false);
            moc.setText("");
            prostornina.setText("");
            danRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1));
            letoRojstva.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2003, 1991));
            stSedezev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 99, 5));
            vrstaVozila.getSelectionModel().select(2);
            mesecRojstva.getSelectionModel().select(0);
            ime.setText("");
            priimek.setText("");
            naslov.setText("");
            posta.setText("");
            kraj.setText("");
            mladiVoznik.setSelected(false);
            ao.setSelected(false);
            aoPlus.setSelected(false);
            kaskoOdbitna.setSelected(false);
            kaskoBrez.setSelected(false);
            kaskoPolno.setSelected(false);
            zavarovanjeProtiToci.setSelected(false);
            zavarovanjeProtiKraji.setSelected(false);
            zavarovanjeGum.setSelected(false);
            zavarovanjeParkirisce.setSelected(false);
            zavarovanjeTretjeOsebe.setSelected(false);
            zavarovanjeStekel.setSelected(false);
            reg.setText("");
            regPrvaKraj.setText("");
            regPrvaDatum.setValue(java.time.LocalDate.now());

            spremenjeno = false;
            status.setText("Status: Vstavljene so bile privzete vrednosti");
        }
    }
}
