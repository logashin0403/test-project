package service;

import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class PrepareDataForOutput {

    public static String makeMapFromAllWords(String forWork) {

        String[] arrayOfAllWords = forWork.split(" ");
        Map<String, Integer> mapWithAllWordsAndTheirCounts = new TreeMap<>();

        for (int i = 0; i < arrayOfAllWords.length; i++) {
            if (mapWithAllWordsAndTheirCounts.get(arrayOfAllWords[i]) != null) {
                mapWithAllWordsAndTheirCounts.put(
                        arrayOfAllWords[i],
                        mapWithAllWordsAndTheirCounts.get(arrayOfAllWords[i]) + 1);
            } else {
                mapWithAllWordsAndTheirCounts.put(arrayOfAllWords[i], 1);
            }
        }

        Map<String, Integer> sortedMap = mapWithAllWordsAndTheirCounts.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue() * -1))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        sortedMap.entrySet().forEach(System.out::println);

        return createQueryForSendInformationToDB(sortedMap).toString();
    }

    private static StringBuilder createQueryForSendInformationToDB(Map<String, Integer> map) {
        StringBuilder query = new StringBuilder("INSERT INTO public.result VALUES");
        long index = 1;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            query
                    .append("\n(")
                    .append(index)
                    .append(", '")
                    .append(entry.getKey())
                    .append("', ")
                    .append(entry.getValue())
                    .append("),");

            index++;
        }

        query.deleteCharAt(query.length() - 1);
        query.append(";");

        return query;
    }
}
