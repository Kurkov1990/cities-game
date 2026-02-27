
package app.cities.model;

import app.cities.util.CitiesDataLoadException;
import app.cities.util.TextNormalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CityRepository {

    private final Map<Character, List<String>> byFirstLetter;
    private final Set<String> allCities;
    private final Random random;

    public CityRepository(List<String> cities) {
        this(cities, new Random());
    }

    public CityRepository(List<String> cities, Random random) {
        Objects.requireNonNull(cities, "cities must not be null");
        this.random = Objects.requireNonNull(random, "random must not be null");

        Map<Character, Set<String>> tmp = new HashMap<>();
        Set<String> all = new HashSet<>();

        for (String raw : cities) {
            String n = TextNormalizer.normalize(raw);

            if (!n.isBlank() && Character.isLetter(n.charAt(0))) {
                char first = n.charAt(0);
                tmp.computeIfAbsent(first, k -> new HashSet<>()).add(n);
                all.add(n);
            }
        }

        Map<Character, List<String>> map = new HashMap<>();
        for (var e : tmp.entrySet()) {
            ArrayList<String> list = new ArrayList<>(e.getValue());
            Collections.sort(list);
            map.put(e.getKey(), Collections.unmodifiableList(list));
        }

        this.byFirstLetter = Collections.unmodifiableMap(map);
        this.allCities = Collections.unmodifiableSet(all);
    }

    public static CityRepository fromResource(String resourcePath) {
        try (var in = CityRepository.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new CitiesDataLoadException("Resource not found: " + resourcePath);
            }

            List<String> cities = getCities(resourcePath, in);

            return new CityRepository(cities);
        } catch (CitiesDataLoadException e) {
            throw e;
        } catch (Exception e) {
            throw new CitiesDataLoadException("Failed to read resource: " + resourcePath, e);
        }
    }

    private static List<String> getCities(String resourcePath, InputStream in) throws IOException {
        List<String> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    cities.add(trimmed);
                }
            }
        }

        if (cities.isEmpty()) {
            throw new CitiesDataLoadException("Cities list is empty: " + resourcePath);
        }
        return cities;
    }

    public boolean containsCity(String normalizedCity) {
        if (normalizedCity == null || normalizedCity.isBlank()) {
            return false;
        }
        return allCities.contains(normalizedCity);
    }

    public Optional<String> pickAnswer(char requiredFirstLetter, Set<String> usedNormalized) {
        requiredFirstLetter = Character.toLowerCase(requiredFirstLetter);

        List<String> candidates = byFirstLetter.get(requiredFirstLetter);
        if (candidates == null || candidates.isEmpty()) {
            return Optional.empty();
        }

        String chosen = null;
        int seen = 0;

        for (String c : candidates) {
            if (usedNormalized.contains(c)) {
                continue;
            }
            seen++;

            if (random.nextInt(seen) == 0) {
                chosen = c;
            }
        }

        return Optional.ofNullable(chosen);
    }
}