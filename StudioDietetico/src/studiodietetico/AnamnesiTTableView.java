package studiodietetico;

import java.util.ArrayList;

import forms.TableUtil;
import hibernate.Intervento;
import hibernate.Paziente;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import service.Utils;

import command.AnamnesiDAO;
import command.PazienteDAO;


public class AnamnesiTTableView extends ViewPart {
	//Generale
	private Composite top = null;
	private TabFolder tabFolder = null;
	private Label labelPaziente;
	private Text textPaziente;
	private static Paziente pazSelHome;
	//Interventi
	private Composite compositeInterventi = null;
	private ArrayList<Object> interventiPazList;
	private TableForm classVis;
	//Allergie
	private Composite compositeAllergie = null;
	private ArrayList<Object> allergiePazList;
	//Attivit�Fisica
	private Composite compositeAttivitaFisica = null;
	private ArrayList<Object> sportPazList;

	
	//Costruttore
	public AnamnesiTTableView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        createTabFolder();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	public static Paziente getPazienteSel() {
		return pazSelHome;
	}

	/**
	 * This method initializes tabFolder	
	 *
	 */
	private void createTabFolder() {
		tabFolder = new TabFolder(top, SWT.TOP);
		tabFolder.setBounds(new Rectangle(0, 0, 854, 416));
		
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(tabFolder, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 30, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(tabFolder, SWT.BORDER | SWT.BOLD);
		textPaziente.setBounds(new Rectangle(106, 30, 263, 31));
		textPaziente.setEnabled(false);
		pazSelHome = PazienteDAO.getPazienti().get(2);
		//pazSelHome = HomePazienteForm.getPazienteSelezionato();
		String dataNascPazSel = pazSelHome.getDataNascita().getDay()+"/"+pazSelHome.getDataNascita().getMonth()+"/"+pazSelHome.getDataNascita().getYear();
		textPaziente.setText(pazSelHome.getCognome()+"   "+pazSelHome.getNome()+"   "+dataNascPazSel);
		
		createCompositeInterventi(tabFolder);
		
		createCompositeAllergie(tabFolder);
		
		createCompositeAttivitaFisica(tabFolder);
		
	}

	//INTERVENTI
	/**
	 * This method initializes compositeInterventi	
	 *
	 */
	public void createCompositeInterventi(TabFolder tabFolder) {
		compositeInterventi = new Composite(tabFolder, SWT.TRANSPARENT);
		TabItem tabItemInterventi = new TabItem(tabFolder, SWT.NONE);
		tabItemInterventi.setText("Interventi");
		tabItemInterventi.setControl(compositeInterventi);
		interventiPazList = AnamnesiDAO.getInterventiListByPaziente(pazSelHome);
				
		//Richiama il costruttore della classe Form per gli interventi
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeInterventi, SWT.BORDER, interventiPazList,"createSShellDettagliInterventi", "createSShellInserimentoInterventi", aw, "InterventiTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1,2});
		
		//Aggiunge la colonna che visualizza il nome dell'intervento
		aggiungiColonnaIntervento(/*classVis, interventiPazList*/);
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 3);
	}
	
	public static void aggiungiColonnaIntervento(TableForm classVis, ArrayList<Object> interventiPazList) {
		//Aggiunge la colonna che visualizza il nome dell'intervento
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Tipo Intervento");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < interventiPazList.size(); j++) {
			nome = ((Intervento)interventiPazList.get(j)).getTipologiaintervento().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		}
		colonna.pack();
		colonna.setResizable(false);
	}
	
	public void aggiungiColonnaIntervento() {
		//Aggiunge la colonna che visualizza il nome dell'intervento
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Tipo Intervento");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < interventiPazList.size(); j++) {
			nome = ((Intervento)interventiPazList.get(j)).getTipologiaintervento().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		}
		colonna.pack();
		colonna.setResizable(false);
	}
	
	
	//ALLERGIE
	private void createCompositeAllergie(TabFolder tabFolder) {
		compositeAllergie = new Composite(tabFolder, SWT.TRANSPARENT);
		TabItem tabItemAllergie = new TabItem(tabFolder, SWT.NONE);
		tabItemAllergie.setText("Allergie/Intolleranze");
		tabItemAllergie.setControl(compositeAllergie);
		allergiePazList = AnamnesiDAO.getAllergieListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per le allergie
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeAllergie, SWT.BORDER, allergiePazList,"createSShellDettagliAllergie", "createSShellInserimentoAllergie", aw, "AllergieTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1,5,7});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
	}
	
	
	
	//ATTIVITA' FISICA
	private void createCompositeAttivitaFisica(TabFolder tabFolder) {
		compositeAttivitaFisica = new Composite(tabFolder, SWT.TRANSPARENT);
		TabItem tabItemAttivitaFisica = new TabItem(tabFolder, SWT.NONE);
		tabItemAttivitaFisica.setText("Attivit� Fisica");
		tabItemAttivitaFisica.setControl(compositeAttivitaFisica);
		sportPazList = AnamnesiDAO.getSportListByPaziente(pazSelHome);
		
		//Richiama il costruttore della classe Form per gli sport
		AnamnesiShell aw = new AnamnesiShell();
		classVis = new TableForm(compositeAttivitaFisica, SWT.BORDER, sportPazList,"createSShellDettagliSport", "createSShellInserimentoSport", aw, "SportTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		//Nasconde le colonne che visualizzano gli id
		classVis.nascondiColonne(new int[]{0,1});
		
		//Aggiorna la combo con l'attributo aggiunto
		classVis.aggiornaCombo();
		
		//Applica l'ordinamento alle colonne visualizzate
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoInteri(classVis.getTableVisualizzazione(), 5);
		
	}
	
	
	
	
	
	
	/*public void aggiornaTable() { //listaElementi � interventiPazList
	AnamnesiDAO an = new AnamnesiDAO();
	ArrayList<Object> listaElementi = an.getListInterventi();
	//Aggiorna la tabella quando � modificato qualche item
	classVis.getTableVisualizzazione().removeAll(); //rimuove gli item
	//rimuove le colonne
	int k = 0;
	while (k<classVis.getTableVisualizzazione().getColumnCount()) {
		classVis.getTableVisualizzazione().getColumn(k).dispose();
	}
	TableUtil.riempiTabellaEntita(classVis.getTableVisualizzazione(), listaElementi);
	
	for (TableColumn colonna : classVis.getTableVisualizzazione().getColumns()) {
		colonna.pack();
		colonna.setResizable(false);
	}
	
	for (int i = 0; i < 3; i++) {
		classVis.getTableVisualizzazione().getColumn(i).setWidth(0);
	}
}*/

}


