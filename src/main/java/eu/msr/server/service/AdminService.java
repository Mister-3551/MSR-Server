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

        //TODO last two of first three lists not working correctly
        Map<String, Object> charts = new HashMap<>();
        ArrayList<ChartStatistics> weaponStatistics = adminRepository.weaponsStatistics();
        ArrayList<ChartStatistics> playTimeStatistics = adminRepository.playTimeStatistics();
        ArrayList<ChartStatistics> successfulMissions = adminRepository.successfulMissions();

        charts.put("pieChart", chartData(weaponStatistics, "% of Kills"));
        charts.put("barChart", chartData(playTimeStatistics, "# of Players"));
        charts.put("lineChart", chartData(successfulMissions, "# of Players"));

        return charts;
    }

    private Map<String, Object> chartData(ArrayList<ChartStatistics> chartStatistics, String label) {
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

        Map<String, Object> legendOptions = new HashMap<>();
        legendOptions.put("display", false);

        Map<String, Object> plugins = new HashMap<>();
        plugins.put("legend", legendOptions);

        Map<String, Object> options = new HashMap<>();
        options.put("plugins", plugins);
        options.put("maintainAspectRatio", false);
        options.put("responsive", true);

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", strings);
        chartData.put("datasets", datasets);
        chartData.put("options", options);

        return chartData;
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