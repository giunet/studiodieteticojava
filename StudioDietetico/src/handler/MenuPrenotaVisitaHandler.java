package handler;

import org.eclipse.core.commands.IHandler;

public class MenuPrenotaVisitaHandler extends MenuStudioDieteticoHandler
		implements IHandler {

	//private static final String VIEW = "StudioDietetico.PrenotaVisitaView";
	private static final String VIEW = "StudioDietetico.PazienteTableView";
	private static final String MY_FUNCTION = "MenuPrenotaVisita";

	String getMyFunction() {
		return MY_FUNCTION;
	}

	String getMyView() {
		return VIEW;
	}

}