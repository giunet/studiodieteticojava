/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------------
 * PrenotazioniBarChart.java
 * ------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1 (DG);
 *
 */

package grafici;

import hibernate.Tipologiavisita;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import command.FatturaDAO;
import command.VisitaDAO;

/**
 * A simple demonstration application showing how to create a bar chart.
 */
public class StatisticheBarChart extends GraficiComposite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String titolo;

	/**
	 * Creates a new demo instance.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public StatisticheBarChart(Table risultati, Composite parent, int style, int variabile,int valore) {
		super(parent, style);
		try {
			this.titolo = risultati.getColumn(variabile).getText().toUpperCase() + " - " + risultati.getColumn(valore).getText().toUpperCase();
			GridData gdThis = new GridData(SWT.FILL);
			gdThis.horizontalAlignment = SWT.FILL;
			gdThis.verticalAlignment = SWT.FILL;
			gdThis.grabExcessHorizontalSpace = true;
			gdThis.grabExcessVerticalSpace = true;
			this.setLayoutData(gdThis);
			this.setLayout(new GridLayout(1, false));
			Composite cmp = new Composite(this, SWT.FILL | SWT.EMBEDDED);
			GridData gdCmp = new GridData(SWT.FILL);
			gdCmp.horizontalAlignment = SWT.FILL;
			gdCmp.verticalAlignment = SWT.FILL;
			gdCmp.grabExcessHorizontalSpace = true;
			gdCmp.grabExcessVerticalSpace = true;
			cmp.setLayoutData(gdCmp);
			cmp.setLayout(new GridLayout(1, false));

			CategoryDataset dataset = createDataset(risultati,variabile,valore);
			JFreeChart chart = createChart(dataset,risultati,variabile,valore);
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setFillZoomRectangle(true);
			// chartPanel.setMouseWheelEnabled(true);
			// chartPanel.setPreferredSize(new Dimension(1000, 700));
			Frame graphFrame = SWT_AWT.new_Frame(cmp);
			graphFrame.add(chartPanel);
			graphFrame.pack();

		} catch (Exception e) {
			alertGraficoNonDisp();
		}
	}

	/**
	 * Returns a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private static CategoryDataset createDataset(Table results, int variabile,int valore) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(TableItem item:results.getItems()){
			dataset.addValue(new Double(item.getText(valore)), item.getText(variabile),
			results.getColumn(variabile).getText().toUpperCase());
		}
			
		return dataset;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return The chart.
	 */
	private static JFreeChart createChart(CategoryDataset dataset, Table results, int variabile,int valore) {

		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(titolo, // chart
				// title
				"", // domain axis label
				results.getColumn(valore).getText().toUpperCase(), // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// ******************************************************************
		// More than 150 demo applications are included with the JFreeChart
		// Developer Guide...for more information, see:
		//
		// > http://www.object-refinery.com/jfreechart/guide.html
		//
		// ******************************************************************

		// set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
				0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
				0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
				0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 12.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

}