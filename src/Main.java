import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        test1();

    }

    public static void test1() {

        Random random = new Random();
        int number = random.nextInt(100);

        String url = "http://numbersapi.com/" + number + "/trivia";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String fact = response.body();

            System.out.println("Fact: " + fact);

            // Подсчет частоты символов(без пробела)
            int totalFrequency = 0;
            Map<Character, Integer> charCountMap = new HashMap<>();
            for (char c : fact.toCharArray()) {
                if(c == ' ' || c == '.'){
                    continue;
                }
                charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
                totalFrequency += 1;
            }

            int averageFrequency = totalFrequency / charCountMap.size();
            System.out.println("Average frequency: " + averageFrequency);

            // Поиск символов с частотой, наиболее близкой к среднему значению
            List<Character> closestChar = new ArrayList<>();
            int closestFrequency = Integer.MAX_VALUE;
            for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
                if(Math.abs(entry.getValue() - averageFrequency) < Math.abs(closestFrequency - averageFrequency)){
                    closestFrequency = entry.getValue();
                    closestChar.clear();
                    closestChar.add(entry.getKey());
                }
                else if(entry.getValue() == averageFrequency){
                    closestChar.add(entry.getKey());
                }
            }

            System.out.println("Character frequencies:");
            charCountMap.forEach((key, value) -> System.out.println(key + ": " + value));

            System.out.println("Character closest to average frequencies:");
            closestChar.forEach(key-> System.out.println(String.format("%s(%d)", key, (int)key)));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}