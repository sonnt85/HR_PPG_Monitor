package electria.hr_ppg_monitor.utils;

/*******************************************************************************
 * Copyright (c) 2013 Nordic Semiconductor. All Rights Reserved.
 *
 * The information contained herein is property of Nordic Semiconductor ASA.
 * Terms and conditions of usage are described in detail in NORDIC SEMICONDUCTOR STANDARD SOFTWARE LICENSE AGREEMENT.
 * Licensees are granted free, non-transferable use of the information. NO WARRANTY of ANY KIND is provided.
 * This heading must NOT be removed from the file.
 ******************************************************************************/

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Point;

/**
 * This class uses external library AChartEngine to show dynamic real time line graph for ECG values
 */
public class LineGraphView {
    //TimeSeries will hold the data in x,y format for single chart
    private TimeSeries mSeries = new TimeSeries("");
    //XYSeriesRenderer is used to set the properties like chart color, style of each point, etc. of single chart
    private XYSeriesRenderer mRenderer = new XYSeriesRenderer();
    //XYMultipleSeriesDataset will contain all the TimeSeries
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    //XYMultipleSeriesRenderer will contain all XYSeriesRenderer and it can be used to set the properties of whole Graph
    private XYMultipleSeriesRenderer mMultiRenderer = new XYMultipleSeriesRenderer();

    private static LineGraphView mInstance = null;

    /**
     * singleton implementation of LineGraphView class
     */
    public static synchronized LineGraphView getLineGraphView() {
        if (mInstance == null) {
            mInstance = new LineGraphView();
        }
        return mInstance;
    }

    /**
     * This constructor will set some properties of single chart and some properties of whole graph
     */
    public LineGraphView() {
        //add single line chart mSeries
        mDataset.addSeries(mSeries);
        //set line chart color to blue
        mRenderer.setColor(Color.BLUE);

        //fill inside line graph
        FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ALL);
        fill.setColor(Color.BLUE);
        mRenderer.addFillOutsideLine(fill);
        mRenderer.setFillPoints(true);

        final XYMultipleSeriesRenderer renderer = mMultiRenderer;
        //set whole graph background color to transparent color
        renderer.setBackgroundColor(Color.GRAY);
        renderer.setMargins(new int[]{50, 65, 40, 5}); // top, left, bottom, right
        renderer.setMarginsColor(Color.WHITE);
        renderer.setAxesColor(Color.WHITE);
        renderer.setShowGrid(false);
        renderer.setXLabels(0);
        renderer.setYLabels(0);
        renderer.setPanEnabled(false, false);
        renderer.setZoomEnabled(false, false);
        renderer.addSeriesRenderer(mRenderer);
    }

    /**
     * return graph view to activity
     */
    public GraphicalView getView(Context context) {
        final GraphicalView graphView = ChartFactory.getLineChartView(context, mDataset, mMultiRenderer);
        return graphView;
    }

    /**
     * add new x,y value to chart
     */
    public void addValue(Point p) {
        mSeries.add(p.x, p.y);
    }

    /**
     * clear all previous values of chart
     */
    public void clearGraph() {
        mSeries.clear();
    }

    public void setXRange(double minX, double maxX){
        mMultiRenderer.setXAxisMin(minX);
        mMultiRenderer.setXAxisMax(maxX);
    }

    public void setYRange(double minY, double maxY){
        mMultiRenderer.setXAxisMin(minY);
        mMultiRenderer.setXAxisMax(maxY);
    }

    public void setPan(double minX, double maxX, double minY, double maxY){
        mMultiRenderer.setPanEnabled(true, true);
        mMultiRenderer.setPanLimits(new double[]{minX, maxX, minY, maxY});
    }

}