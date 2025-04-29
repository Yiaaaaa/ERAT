package org.example.erat.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChartGenerator {

    public static void generateLineChart(Map<String, Double> submissionRates) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : submissionRates.entrySet()) {
            dataset.addValue(entry.getValue(), "提交率(%)", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "实验提交率趋势",
                "实验编号",
                "提交率(%)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // 自定义样式
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6)
        );
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setDrawOutlines(true);
        renderer.setDefaultToolTipGenerator((datasetObj, row, column) -> {
            String exp = dataset.getColumnKey(column).toString();
            double rate = (Double) dataset.getValue(row, column);
            return exp + ": " + String.format("%.1f%%", rate);
        });

        // 显示图表窗口
        ChartFrame frame = new ChartFrame("实验提交率分析", chart);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // 居中显示
        frame.setVisible(true);
    }
}