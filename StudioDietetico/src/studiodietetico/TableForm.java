package studiodietetico;

import hibernate.Paziente;

import java.lang.reflect.Method;
import java.lang.Integer;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.widgets.TableColumn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import command.AnamnesiDAO;
import command.DietaDAO;
import command.EsameClinicoDAO;
import command.FatturaDAO;
import command.MedicoDAO;
import command.ParametroAntropometricoDAO;
import command.PazienteDAO;
import command.VisitaDAO;
import common.ui.ListComposite;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;

import service.Utils;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;


public class TableForm extends ListComposite {

	public Composite top;
	private Label labelSelItem;
	private Table tableVisualizzazione = null;
	private Button buttonInsert;
	public Button getButtonInsert() {
		return buttonInsert;
	}

	public void setButtonInsert(Button buttonInsert) {
		this.buttonInsert = buttonInsert;
	}

	public Button getButtonElimina() {
		return buttonElimina;
	}

	public void setButtonElimina(Button buttonElimina) {
		this.buttonElimina = buttonElimina;
	}

	private Button buttonElimina;
	private TableItem rigaTableClick;
	private CCombo cComboColonne = null;
	private Label labelRicerca;
	private Text textRicerca = null;
	private String idShellVisualizzaDettagli;  //  @jve:decl-index=0:
	private String idShellInserimento;  //  @jve:decl-index=0:
	private Shell sShellMessElimina;
	private Object classeShell = null;
	private Object classeDAO = null;
	int numeroCol;
	private String classeChiamante = "";  //  @jve:decl-index=0:
	private ArrayList<Object> oggettiTabella;
	
	/**
	 * Costuttore della classe: crea una table con ricerca dell'item con scelta dell'attributo e ordinamento delle colonne. Permette di 
	 * effettuare l'inserimento e la cancellazione degli item con i pulsanti, e la visualizzazione dei dettagli con eventuale modifica
	 * attraverso il doppio click sull'item.
	 * @param parent
	 * @param style
	 * @param listaElementi lista degli item da caricare nella table
	 * @param methodCreateShellDettagli nome del metodo che crea la shell che si apre per i dettagli dell'item 
	 * @param methodCreateShellIns nome del metodo che crea la shell che si apre per l'insermento di un nuovo item
	 * @param classShell nome della classe che contiene le shell per inserimento e visualizzazione
	 * @param classDAO nome della classe DAO che contiene i metodi per l'accesso al db
	 * @param classe nome della classe View che richiama questa classe
	 * @see ATTENZIONE: il nome del metodo per la cancellazione di un item contenuto nella classe DAO, deve essere della forma 
	 * 		"cancella+classe View chiamante senza TableView finale. Ad esempio: 
	 * 		classe chiamante: InterventiView --> metodo cancellazione: cancellaInterventi(TableItem item)
	 */
	public TableForm(Composite parent, int style, ArrayList<Object> listaElementi, String methodCreateShellDettagli, 
			String methodCreateShellIns, Object classShell, Object classDAO, String classe) {
		super(parent, style);
		rigaTableClick = null;
		idShellVisualizzaDettagli = methodCreateShellDettagli;
		idShellInserimento = methodCreateShellIns;
		classeShell = classShell;
		classeDAO = classDAO;
		initialize(listaElementi, classe);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize(ArrayList<Object> listaElementi, String classe) {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = -1;
		gridData.widthHint = -1;
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.FILL;
		classeChiamante = classe;
		//final ArrayList<Object> listaElementiTable = listaElementi;
		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, true);
		this.setLayout(glForm);
		this.setBackground(common.Utils.getStandardWhiteColor());

		GridData gdFiller = new GridData(SWT.FILL);
		gdFiller.grabExcessHorizontalSpace = true;
		gdFiller.horizontalAlignment = SWT.FILL;
		gdFiller.grabExcessVerticalSpace = false;
		gdFiller.heightHint = 25;
		gdFiller.widthHint = -1;
		gdFiller.horizontalSpan = 4;

		GridData gdTbl = new GridData(SWT.FILL);
		gdTbl.grabExcessHorizontalSpace = true;
		gdTbl.horizontalAlignment = SWT.FILL;
		gdTbl.verticalAlignment = SWT.FILL;
		gdTbl.grabExcessVerticalSpace = true;
		gdTbl.horizontalSpan = 4;
		
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;
		
		top = new Composite(this, SWT.BORDER);
        top.setLayout(new GridLayout(4, true));
        top.setLayoutData(gdTop);
        
        labelSelItem = new Label(top, SWT.NONE);
		labelSelItem.setText("Selezionare un elemento e scegliere una opzione");
		labelSelItem.setLayoutData(gdFiller);
		
		labelRicerca = new Label(top, SWT.NONE);
		labelRicerca.setText("Ricerca sull'attributo:");
		
		cComboColonne = new CCombo(top, SWT.READ_ONLY);
		cComboColonne.setLayoutData(gridData);
		cComboColonne
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textRicerca.setEnabled(true);
						textRicerca.setText("");
					}
				});
		
		textRicerca = new Text(top, SWT.BORDER);
		textRicerca.setLayoutData(gdFiller);
		textRicerca.setEnabled(false);
		
		textRicerca.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				tableVisualizzazione.setVisible(false);
				tableVisualizzazione.removeAll(); //rimuove le righe
				
				//rimuove le colonne
				int k = 0;
				while (k<tableVisualizzazione.getColumnCount()) {
					tableVisualizzazione.getColumn(k).dispose();
				}
				
				//TODO riempie la tabella aggiornata in base alla classe chiamante
				if (classeChiamante.equalsIgnoreCase("VisitaTableView")) {
					oggettiTabella = VisitaDAO.getVisiteObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("PazienteTableView")) {
					oggettiTabella = PazienteDAO.getPazientiObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("MedicoTableView")) {
					oggettiTabella = MedicoDAO.getMediciObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("PrenotazioneTableView")) {
					oggettiTabella = VisitaDAO.getPrenotazioniObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("DietaTableView")) {
					oggettiTabella = DietaDAO.getDieteObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("PrescrizioneTableView")) {
					oggettiTabella = DietaDAO.getPrescrizioniObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("TurniTableView")) {
					oggettiTabella = MedicoDAO.getPrestazioniObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("FattureTableView")) {
					oggettiTabella = FatturaDAO.getFattureObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("InterventiTableView")) {
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getInterventiListByPaziente(pazSelHome);
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("AllergieTableView")) {
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getAllergieListByPaziente(pazSelHome);
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("SportTableView")) {
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getSportListByPaziente(pazSelHome);
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("FarmaciTableView")) {
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getFarmaciListByPaziente(pazSelHome);
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("MalattiaTableView")) {
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getMalattieListByPaziente(pazSelHome);
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("EsameClinicoTableView")) {
					oggettiTabella = EsameClinicoDAO.getEsameClinicoObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				else if (classeChiamante.equalsIgnoreCase("ParametroAntropometricoTableView")) {
					oggettiTabella = ParametroAntropometricoDAO.getParametroAntropometricoObject();
					riempiTabella(oggettiTabella, classeChiamante);
				}
				

				//TODO nasconde, aggiorna e ordina le colonne in base alla classe chiamante 
				if (classeChiamante.equalsIgnoreCase("VisitaTableView")) {
					nascondiColonne(new int[] {0,1,2,3,6,7,8});
					oggettiTabella = VisitaDAO.getVisiteObject();
					VisitaTableView.aggiungiColonne(TableForm.this, oggettiTabella);
					ordinamentoData(tableVisualizzazione, 4);
					ordinamentoData(tableVisualizzazione, 5);
					ordinamentoStringhe(tableVisualizzazione, 9);
					ordinamentoStringhe(tableVisualizzazione, 10);
					ordinamentoStringhe(tableVisualizzazione, 11);
				}
				else if (classeChiamante.equalsIgnoreCase("PazienteTableView")) {
					nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
					ordinamentoData(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 5);
					ordinamentoStringhe(tableVisualizzazione, 6);
					ordinamentoStringhe(tableVisualizzazione, 7);
				}
				else if (classeChiamante.equalsIgnoreCase("MedicoTableView")) {
					nascondiColonne(new int[] {0,1,4,5,8,9,13});
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 6);
					ordinamentoStringhe(tableVisualizzazione, 7);
					ordinamentoStringhe(tableVisualizzazione, 10);
					ordinamentoStringhe(tableVisualizzazione, 11);
					ordinamentoStringhe(tableVisualizzazione, 12);
				}
				else if (classeChiamante.equalsIgnoreCase("PrenotazioneTableView")) {
					nascondiColonne(new int[] {0,1,2,4});
					oggettiTabella = VisitaDAO.getPrenotazioniObject();
					PrenotazioneTableView.aggiungiColonne(TableForm.this, oggettiTabella);
					ordinamentoData(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 5);
					ordinamentoStringhe(tableVisualizzazione, 6);
				}
				else if (classeChiamante.equalsIgnoreCase("DietaTableView")) {
					nascondiColonne(new int[] {0,1,4,5});
					oggettiTabella = DietaDAO.getDieteObject();
					DietaTableView.aggiungiColonne(TableForm.this, oggettiTabella);
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoInteri(tableVisualizzazione, 3);
					ordinamentoInteri(tableVisualizzazione, 6);
					ordinamentoStringhe(tableVisualizzazione, 7);
					ordinamentoStringhe(tableVisualizzazione, 8);
					ordinamentoStringhe(tableVisualizzazione, 9);
					ordinamentoStringhe(tableVisualizzazione, 10);
				}
				else if (classeChiamante.equalsIgnoreCase("PrescrizioneTableView")) {
					nascondiColonne(new int[] {0,1,2,5});
					oggettiTabella = DietaDAO.getPrescrizioniObject();
					PrescrizioneTableView.aggiungiColonne(TableForm.this, oggettiTabella);
					ordinamentoData(tableVisualizzazione, 3);
					ordinamentoInteri(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 6);
					ordinamentoStringhe(tableVisualizzazione, 7);
				}
				else if (classeChiamante.equalsIgnoreCase("TurniTableView")) {
					nascondiColonne(new int[] {0,1,2});
					oggettiTabella = MedicoDAO.getPrestazioniObject();
					TurniTableView.aggiungiColonne(TableForm.this, oggettiTabella);
					ordinamentoData(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 6);
				}
				else if (classeChiamante.equalsIgnoreCase("FattureTableView")) {
					nascondiColonne(new int[] {0});
					oggettiTabella = FatturaDAO.getFattureObject();
					FattureTableView.modificaColonna(TableForm.this, oggettiTabella);
					ordinamentoInteri(tableVisualizzazione, 1);
					ordinamentoInteri(tableVisualizzazione, 2);
					ordinamentoInteri(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 5);
					ordinamentoData(tableVisualizzazione, 6);
				}
				else if (classeChiamante.equalsIgnoreCase("InterventiTableView")) {
					nascondiColonne(new int[]{0,1,2});
					Paziente pazSelHome = PazienteTableView.getPazienteSelezionato();
					AnamnesiDAO ad = new AnamnesiDAO();
					oggettiTabella = ad.getInterventiListByPaziente(pazSelHome);
					AnamnesiTTableView.aggiungiColonnaIntervento(TableForm.this, oggettiTabella);
					ordinamentoInteri(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 5);
					ordinamentoData(tableVisualizzazione, 3);
				}
				else if (classeChiamante.equalsIgnoreCase("AllergieTableView")) {
					nascondiColonne(new int[]{0,1,5,7});
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 6);
				}
				else if (classeChiamante.equalsIgnoreCase("SportTableView")) {
					nascondiColonne(new int[]{0,1});
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 3);
					ordinamentoStringhe(tableVisualizzazione, 4);
					ordinamentoInteri(tableVisualizzazione, 5);
				}
				else if (classeChiamante.equalsIgnoreCase("FarmaciTableView")) {
					nascondiColonne(new int[]{0,1,3,6});
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 4);
					ordinamentoStringhe(tableVisualizzazione, 5);
					
				}
				else if (classeChiamante.equalsIgnoreCase("EsameClinicoTableView")) {
					nascondiColonne(new int[]{0});
					ordinamentoStringhe(tableVisualizzazione, 1);
					ordinamentoStringhe(tableVisualizzazione, 2);
				}
				else if (classeChiamante.equalsIgnoreCase("ParametroAntropometricoTableView")) {
					nascondiColonne(new int[]{0});
					ordinamentoStringhe(tableVisualizzazione, 1);
					ordinamentoStringhe(tableVisualizzazione, 2);
				}
				else if (classeChiamante.equalsIgnoreCase("MalattiaTableView")) {
					nascondiColonne(new int[]{0,1});
					ordinamentoStringhe(tableVisualizzazione, 2);
					ordinamentoStringhe(tableVisualizzazione, 3);
				}
				
				
				
				//ricerca incrementale nella colonna selezionata
				int indiceColonnaSel = convertiComboToColonna(cComboColonne.getSelectionIndex());
				int i = 0;
				while (i<tableVisualizzazione.getItems().length) {
					String item = tableVisualizzazione.getItem(i).getText(indiceColonnaSel).toLowerCase();
					if (!(item.startsWith(textRicerca.getText().toLowerCase()))) {
						tableVisualizzazione.remove(i);
					}
					else {
						i++;
					}
				}
				tableVisualizzazione.setVisible(true);
			}
		});
	
		
		tableVisualizzazione = new Table(top, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tableVisualizzazione.setHeaderVisible(true);
		tableVisualizzazione.setLinesVisible(true);
		tableVisualizzazione.setLayout(new GridLayout(1, true));
		tableVisualizzazione.setLayoutData(gdTbl);
		tableVisualizzazione.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				if(tableVisualizzazione.getSelectionCount()>0)
					rigaTableClick = tableVisualizzazione.getSelection()[0];
				
				Class[] param = new Class[]{rigaTableClick.getClass()} ; 
				Method metodo = null;
				try {
					metodo = classeShell.getClass().getMethod(idShellVisualizzaDettagli, param);
					metodo.invoke(classeShell, rigaTableClick);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				tableVisualizzazione.deselectAll();
			}
		});
		
		buttonInsert = new Button(top, SWT.NONE);
		buttonInsert.setText("Inserisci nuovo");
		buttonInsert.setLayoutData(gridData1);
		buttonInsert.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Method metodo = null;
				if(classeShell!=null) {
					try {
						metodo = classeShell.getClass().getMethod(idShellInserimento, null);
						metodo.invoke(classeShell, null);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		if (classeChiamante=="FattureTableView" | classeChiamante=="PrenotazioneTableView"
			| classeChiamante=="PrescrizioneTableView") {
			buttonInsert.setVisible(false);
		}
		if (classeChiamante=="VisitaTableView") {
			buttonInsert.setText("Registra nuova visita");
		}
		else if (classeChiamante=="EsameClinicoTableView") {
			buttonInsert.setText("Inserisci nuovo esame clinico");
		}
		else if (classeChiamante=="ParametroAntropometricoTableView") {
			buttonInsert.setText("Inserisci nuovo parametro");
		}
		else if (classeChiamante=="DietaTableView") {
			buttonInsert.setText("Inserisci nuovo schema dietetico");
		}
		else if (classeChiamante=="PazienteTableView") {
			buttonInsert.setText("Registra nuovo paziente");
		}
		else if (classeChiamante=="MedicoTableView") {
			buttonInsert.setText("Registra nuovo medico");
		}
		else if (classeChiamante=="TurniTableView") {
			buttonInsert.setText("Inserisci nuovo turno");
		}
		
		buttonElimina = new Button(top, SWT.NONE);
		buttonElimina.setText("Elimina");
		buttonElimina.setLayoutData(gridData2);
		buttonElimina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(tableVisualizzazione.getSelectionCount()>0) {
					int indiceItemSel = tableVisualizzazione.getSelectionIndex();
					//MessageBox con conferma cancellazione
					createMessConfermaCanc(indiceItemSel);
				} else {
					//MessageBox con richiesta dell'elemento da cancellare
					createMessSelElemCanc(); 
				}
			}
		});
		
		if (classeChiamante=="TurniTableView"| classeChiamante=="MalattiaTableView") {
			buttonElimina.setVisible(false);
		}
		
		riempiTabella(listaElementi, classeChiamante);

		numeroCol = tableVisualizzazione.getColumnCount();
		//System.out.println("numero di colonne iniziali: " + numeroCol);
		
}
	
	private int convertiComboToColonna (int indiceCombo){
		ArrayList<Integer> colonneVisibili = new ArrayList<Integer>();
		for (int i = 0; i<tableVisualizzazione.getColumnCount(); i++) {
				if (tableVisualizzazione.getColumn(i).getWidth()!=0) {
				colonneVisibili.add(i);
			}
		}
		return colonneVisibili.get(indiceCombo);
	} 
	
	public void riempiTabella(ArrayList<Object> listaElementi, String classeChiamante){
		riempiTabellaEntita(tableVisualizzazione, listaElementi, classeChiamante);
		for (int i = 0; i < tableVisualizzazione.getColumnCount(); i++) {
			if (tableVisualizzazione.getColumn(i).getText().toLowerCase().contains("dataora")) {
				for (TableItem item : tableVisualizzazione.getItems()) {
					String testoitem = item.getText(i);
					int lunghezzaTestoItem = item.getText(i).length();
					item.setText(i, testoitem.substring(0, lunghezzaTestoItem-5));
				}
			}
		}

		for (TableColumn colonna : tableVisualizzazione.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		
	}
	
	private void createMessConfermaCanc(int indiceItemSel) {
		String classeCanc = "";
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.CANCEL| SWT.ICON_WARNING);
		messageBox.setMessage("Sei sicuro di voler eliminare questo elemento?");
		messageBox.setText("Conferma cancellazione");
		if (messageBox.open() == SWT.OK) {
			if (classeChiamante.endsWith("TableView")) {
				classeCanc = classeChiamante.split("TableView")[0];
			}
			
			rigaTableClick = tableVisualizzazione.getSelection()[0];
			Class[] param = new Class[]{rigaTableClick.getClass()} ;
			Method metodo = null;
			if(classeDAO!=null) {
				try {
					metodo = classeDAO.getClass().getMethod("cancella"+classeCanc, param);
					System.out.println("Metodo:"+metodo);
					metodo.invoke(classeDAO, rigaTableClick);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			tableVisualizzazione.remove(indiceItemSel);
			sShellMessElimina.close();
		}
	}
	
	private void createMessSelElemCanc() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare un elemento per cancellarlo!");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}
	
	/**
	 * Nasconde la colonna con indice dato in input
	 * @param indiceColonna
	 */
	public void nascondiColonne(int[]indiciColonne) {
		for (int i = 0; i < indiciColonne.length; i++) {
			tableVisualizzazione.getColumn(indiciColonne[i]).setWidth(0);
		}
	}
	
	/**
	 * Ordinamento crescente e decrescente delle colonne di tipo Integer
	 * @param tableVis
	 * @param indiceColonna
	 */
	public static void ordinamentoInteri(final Table tableVis, final int indiceColonna) {
		Listener sortListener = new Listener() {
	        public void handleEvent(Event e) {
	        	TableColumn sortColumn = tableVis.getSortColumn();
				int dir = tableVis.getSortDirection();
	        	TableItem[] items = tableVis.getItems();
	            TableColumn column = (TableColumn)e.widget; //colonna cliccata
	            
	            if(sortColumn==column){
					dir = dir==SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					tableVis.setSortColumn(column);
			          dir = SWT.UP;
				}
	            
	            int index = indiceColonna; //indice della colonna sulla quale fare l'ordinamento
	            int valueInt1 = 0, valueInt2 = 0;
	            for (int i = 1; i < items.length; i++) {
	            	if(items[i].getText(index).equals(""))
	            		valueInt1=Integer.MAX_VALUE;
	            	else
	            		valueInt1 = Integer.parseInt(items[i].getText(index));
	            	for (int j = 0; j < i; j++){
	            		if(items[j].getText(index).equals(""))
	            			valueInt2=Integer.MAX_VALUE;
	            		else
	            			valueInt2 = Integer.parseInt(items[j].getText(index));

	            		if(dir == SWT.DOWN) {

	            			if (valueInt1 < valueInt2) {
	            				String[] values = new String[tableVis.getColumnCount()];
	            				for (int k = 0; k < tableVis.getColumnCount(); k++) {
	            					values[k] = items[i].getText(k);
	            				}
	            				items[i].dispose();
	            				TableItem item = new TableItem(tableVis, SWT.NONE, j);
	            				item.setText(values);
	            				items = tableVis.getItems();
	            				break;
	            			}
	            		} else {
	            			if (valueInt1 > valueInt2) {
	            				String[] values = new String[tableVis.getColumnCount()];
	            				for (int k = 0; k < tableVis.getColumnCount(); k++) {
	            					values[k] = items[i].getText(k);
	            				}
	            				items[i].dispose();
	            				TableItem item = new TableItem(tableVis, SWT.NONE, j);
	            				item.setText(values);
	            				items = tableVis.getItems();
	            				break;
	            			}
	            		}
	            	}
	            }
	            tableVis.setSortDirection(dir);
	            tableVis.setSortColumn(column);
	        }
	    };
	    tableVis.getColumn(indiceColonna).addListener(SWT.Selection, sortListener);   
	}
	
	/**
	 * Ordinamento crescente e decrescente delle colonne di tipo String
	 * @param tableVis
	 * @param indiceColonna
	 */
	public static void ordinamentoStringhe(final Table tableVis, final int indiceColonna) {
		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableColumn sortColumn = tableVis.getSortColumn();
				int dir = tableVis.getSortDirection();
				TableItem[] items = tableVis.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				TableColumn column = (TableColumn)e.widget; //colonna cliccata
				
				if(sortColumn==column){
					dir = dir==SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					tableVis.setSortColumn(column);
			          dir = SWT.UP;
				}
				
				int index = indiceColonna; //indice della colonna sulla quale fare l'ordinamento
				String value1 = null, value2 = null;
				for (int i = 1; i < items.length; i++) {
					value1 = items[i].getText(index);
					for (int j = 0; j < i; j++){
						value2 = items[j].getText(index);
						if(dir == SWT.DOWN) {
							if (collator.compare(value1, value2) < 0) {
								String[] values = new String[tableVis.getColumnCount()];
								for (int k = 0; k < tableVis.getColumnCount(); k++) {
									values[k] = items[i].getText(k);
								}
								items[i].dispose();
								TableItem item = new TableItem(tableVis, SWT.NONE, j);
								item.setText(values);
								items = tableVis.getItems();
								break;
							}	
						} else {
							if (collator.compare(value1, value2) > 0) {
								String[] values = new String[tableVis.getColumnCount()];
								for (int k = 0; k < tableVis.getColumnCount(); k++) {
									values[k] = items[i].getText(k);
								}
								items[i].dispose();
								TableItem item = new TableItem(tableVis, SWT.NONE, j);
								item.setText(values);
								items = tableVis.getItems();
								break;
							}
						}
					}
				}
				tableVis.setSortDirection(dir);
				tableVis.setSortColumn(column);
			}
	    };
	    tableVis.getColumn(indiceColonna).addListener(SWT.Selection, sortListener);
	}
	
	/**
	 * Ordinamento crescente e decrescente delle colonne di tipo Data
	 * @param tableVis
	 * @param indiceColonna
	 */
	public static void ordinamentoData(final Table tableVis, final int indiceColonna) {
		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableColumn sortColumn = tableVis.getSortColumn();
				int dir = tableVis.getSortDirection();
				TableItem[] items = tableVis.getItems();
				TableColumn column = (TableColumn)e.widget; //colonna cliccata
				
				if(sortColumn==column){
					dir = dir==SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					tableVis.setSortColumn(column);
			          dir = SWT.UP;
				}
				
				int index = indiceColonna; //indice della colonna sulla quale fare l'ordinamento
				String value1, value2, formato="yyyy-MM-dd";
				Date valueDate1, valueDate2;
				for (int i = 1; i < items.length; i++) {
					value1 = items[i].getText(index);
					valueDate1 = Utils.convertStringToDate(value1, formato);
					for (int j = 0; j < i; j++){
						value2 = items[j].getText(index);
						valueDate2 = Utils.convertStringToDate(value2, formato);
						if(dir == SWT.DOWN) {
							if (valueDate1.compareTo(valueDate2) < 0) {
								String[] values = new String[tableVis.getColumnCount()];
								for (int k = 0; k < tableVis.getColumnCount(); k++) {
									values[k] = items[i].getText(k);
								}
								items[i].dispose();
								TableItem item = new TableItem(tableVis, SWT.NONE, j);
								item.setText(values);
								items = tableVis.getItems();
								break;
							}	
						} else {
							if (valueDate1.compareTo(valueDate2) > 0) {
								String[] values = new String[tableVis.getColumnCount()];
								for (int k = 0; k < tableVis.getColumnCount(); k++) {
									values[k] = items[i].getText(k);
								}
								items[i].dispose();
								TableItem item = new TableItem(tableVis, SWT.NONE, j);
								item.setText(values);
								items = tableVis.getItems();
								break;
							}
						}
					}
				}
				tableVis.setSortDirection(dir);
				tableVis.setSortColumn(column);
			}
	    };
	    tableVis.getColumn(indiceColonna).addListener(SWT.Selection, sortListener);
	}

	/**
	 * Aggiorna la combo per la ricerca con gli attributi visualizzati nella tabella
	 */
	public void aggiornaCombo() {
		for (TableColumn colonna : tableVisualizzazione.getColumns()) {
			if (colonna.getWidth()!=0 /*&& !setAttributi.contains(colonna.getText())*/) {
				cComboColonne.add(colonna.getText());
			}
		}
		cComboColonne.setText("Seleziona l'attributo");
		cComboColonne.pack();
	}

	/**
	 * Restituisce l'item sul quale viene cliccato
	 * @return
	 */
	public TableItem getRiga() {
		return rigaTableClick;
	}
	
	public Table getTableVisualizzazione() {
		return tableVisualizzazione;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"

