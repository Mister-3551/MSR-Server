package eu.msr.server.service;

import eu.msr.server.entity.ChartStatistics;
import eu.msr.server.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Map<String, Object> weaponStatistics() {

        Map<String, Object> charts = new HashMap<>();
        ArrayList<ChartStatistics> weaponStatistics = adminRepository.weaponsStatistics();
        ArrayList<ChartStatistics> playTimeStatistics = adminRepository.playTimeStatistics();
        ArrayList<ChartStatistics> successfulMissions = adminRepository.successfulMissions();

        charts.put("pieChart", chartData(weaponStatistics, "% of Kills", null, null));
        charts.put("barChart", chartData(playTimeStatistics, "Number of Players", "Play time in hours", "Number of players"));
        charts.put("lineChart", chartData(successfulMissions, "Number of Players", "Completed missions per player in percent", "Number of players"));
        return charts;
    }

    private Map<String, Object> chartData(ArrayList<ChartStatistics> chartStatistics, String label, String xAxiosText, String yAxiosText) {
        List<String> strings = new ArrayList<>();
        List<Float> numbers = new ArrayList<>();

        for (ChartStatistics statistics : chartStatistics) {
            strings.add(statistics.getString());
            numbers.add(statistics.getNumber());
        }

        List<String> backgroundColor = generateRandomColors(strings.size(), false);
        List<String> borderColor = generateRandomColors(strings.size(), true);

        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", numbers);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("borderColor", borderColor);
        dataset.put("borderWidth", 1);

        List<Map<String, Object>> datasets = new ArrayList<>();
        datasets.add(dataset);

        Map<String, Object> options = getOptions(xAxiosText, yAxiosText);

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", strings);
        chartData.put("datasets", datasets);
        chartData.put("options", options);
        return chartData;
    }

    private static Map<String, Object> getOptions(String xAxiosText, String yAxiosText) {

        Map<String, Object> plugins = getPlugins(xAxiosText, yAxiosText);

        Map<String, Object> scales = getScales(xAxiosText, yAxiosText);

        Map<String, Object> options = new HashMap<>();
        options.put("plugins", plugins);

        if (xAxiosText != null && yAxiosText != null) {
            options.put("scales", scales);
        }

        options.put("maintainAspectRatio", false);
        options.put("responsive", true);
        return options;
    }

    private static Map<String, Object> getPlugins(String xAxiosText, String yAxiosText) {
        Map<String, Object> labels = new HashMap<>();
        labels.put("usePointStyle", true);

        Map<String, Object> legend = new HashMap<>();

        if (xAxiosText == null && yAxiosText == null) {
            legend.put("display", true);
            legend.put("position", "right");
            legend.put("labels", labels);
            legend.put("onClick", "(e) => e.stopPropagation()");
        } else {
            legend.put("display", false);
        }

        Map<String, Object> plugins = new HashMap<>();
        plugins.put("legend", legend);
        return plugins;
    }

    private static Map<String, Object> getScales(String xAxiosText, String yAxiosText) {

        Map<String, Object> ticks = new HashMap<>();
        ticks.put("suggestedMin", 0);
        ticks.put("stepSize", 1);

        Map<String, Object> xScaleTitle = new HashMap<>();
        xScaleTitle.put("display", true);
        xScaleTitle.put("text", xAxiosText);

        Map<String, Object> yScaleTitle = new HashMap<>();
        yScaleTitle.put("display", true);
        yScaleTitle.put("text", yAxiosText);

        Map<String, Object> xScale = new HashMap<>();
        xScale.put("beginAtZero", true);
        xScale.put("title", xScaleTitle);

        Map<String, Object> yScale = new HashMap<>();
        yScale.put("beginAtZero", true);
        yScale.put("title", yScaleTitle);
        yScale.put("ticks", ticks);

        Map<String, Object> scales = new HashMap<>();
        scales.put("x", xScale);
        scales.put("y", yScale);
        return scales;
    }

    private List<String> generateRandomColors(int count, boolean border) {
        List<String> colors = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String color = String.format("rgba(%d, %d, %d, %.1f)", random.nextInt(256), random.nextInt(256), random.nextInt(256), border ? 1.0f : 0.2f);

            int lastIndex = color.lastIndexOf(',');
            if (lastIndex >= 0) {
                colors.add(color.substring(0, lastIndex) + "." + color.substring(lastIndex + 1));
            }
        }
        return colors;
    }
}