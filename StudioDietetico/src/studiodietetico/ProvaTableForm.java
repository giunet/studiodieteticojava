package studiodietetico;

import java.lang.reflect.Method;
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

import common.ui.ListComposite;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;

import service.Utils;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;


public class ProvaTableForm extends ListComposite {

	private Composite top;
	private Label labelSelItem;
	private Table tableVisualizzazione = null;
	private Button buttonInsert;
	private Button buttonElimina;
	private TableItem rigaTableClick;
	private CCombo cComboColonne = null;
	private Label labelRicerca;
	private Text textRicerca = null;
	private String idShellVisualizzaDettagli;  //  @jve:decl-index=0:
	private String idShellInserimento;  //  @jve:decl-index=0:
	private Shell sShellMessElimina;
	private Object classeShell = null;
	
	
	public ProvaTableForm(Composite parent, int style, ArrayList<Object> listaElementi, String methodCreateShellDettagli, String methodCreateShellIns, Object classShell) {
		super(parent, style);
		rigaTableClick = null;
		idShellVisualizzaDettagli = methodCreateShellDettagli;
		idShellInserimento = methodCreateShellIns;
		classeShell = classShell;
		
		initialize(listaElementi);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize(ArrayList<Object> listaElementi) {
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
		labelRicerca.setText("Ricerca");
		
		cComboColonne = new CCombo(top, SWT.READ_ONLY);
		
		textRicerca = new Text(top, SWT.BORDER);
		textRicerca.setLayoutData(gdFiller);
		
		
		
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
		buttonInsert.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Method metodo = null;
				System.out.println("ClasseShell: "+classeShell);
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
		
		buttonElimina = new Button(top, SWT.NONE);
		buttonElimina.setText("Elimina");
		buttonElimina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(tableVisualizzazione.getSelectionCount()>0) {
					int indiceItemSel = tableVisualizzazione.getSelectionIndex();
					//MessageBox con conferma cancellazione
					//elimina solo dalla tabella
					createMessConfermaCanc(indiceItemSel);
				} else {
					//MessageBox con richiesta dell'elemento da cancellare
					createMessSelElemCanc(); 
				}
			}
		});
		
		riempiTabellaEntita(tableVisualizzazione, listaElementi);
		
		for (TableColumn colonna : tableVisualizzazione.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		
}
	
	private void createMessConfermaCanc(int indiceItemSel) {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.CANCEL| SWT.ICON_WARNING);
		messageBox.setMessage("Sei sicuro di voler eliminare questo elemento?");
		messageBox.setText("Conferma cancellazione");
		if (messageBox.open() == SWT.OK) {
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
	public void nascondiColonna(int indiceColonna) {
		tableVisualizzazione.getColumn(indiceColonna).setWidth(0);
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