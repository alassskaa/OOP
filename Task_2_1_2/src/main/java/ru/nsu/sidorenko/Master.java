package ru.nsu.sidorenko;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

/**
 * Класс для распределения рабочих узлов подсети.
 * Топология сети - Звезда. От главного контроллера
 * запросы посылаются вычислительным узлам.
 */
public class Master {
    private final List<List<Integer>> chunks = new ArrayList<>();
    private static final List<WorkerInfo> workers = List.of(
            new WorkerInfo("localhost", 5001),
            new WorkerInfo("localhost", 5002),
            new WorkerInfo("localhost", 5003),
            new WorkerInfo("localhost", 5004)
    );

    /**
     * Метод, распределяющий части массива между узлами подсети.
     * Каждый рабочий узел подсети получает свой участок
     * входного массива. Распределение происходит при
     * помощи ExecutorService и CompletionService.
     *
     * @param list - входной массив.
     * @return false или true.
     * @throws Exception - исключение при ошибке.
     */
    public boolean distribute(List<Integer> list) throws Exception {
        chunks.clear();
        splitArray(list);

        if (chunks.isEmpty()) {
            return false;
        }

        ExecutorService executor = Executors.newFixedThreadPool(chunks.size());
        CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);

        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            final int idx = i;
            final List<Integer> chunk = chunks.get(i);
            futures.add(completionService.submit(() -> sendToWorker(chunk, idx)));
        }

        try {
            for (int i = 0; i < chunks.size(); i++) {
                Future<Boolean> completed = completionService.take();
                try {
                    if (completed.get()) {
                        return true;
                    }
                } catch (ExecutionException e) {
                    System.out.println("Chunk failed: " + e.getCause().getMessage());
                }
            }
            return false;
        } finally {
            for (Future<Boolean> f : futures) {
                f.cancel(true);
            }
            executor.shutdownNow();
        }
    }

    /**
     * Метод для отправки части массива вычислительному узлу.
     * Связь между контроллером и вычислительным узлом
     * осуществляется при помощи сокетов.
     *
     * @param chunk - часть входного массива.
     * @param workerInd - индекс вычислительного узла.
     * @return результат работы вычислительного узла.
     */
    private boolean sendToWorker(List<Integer> chunk, int workerInd) {
        UUID taskId = UUID.randomUUID();
        Task task = new Task(taskId, chunk);

        for (int attempt = 0; attempt < workers.size(); attempt++) {

            int idx = (workerInd + attempt) % workers.size();
            WorkerInfo worker = workers.get(idx);

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(worker.getHost(), worker.getPort()), 2000);

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                out.writeObject(task);
                out.flush();

                socket.setSoTimeout(2000);
                if (!in.readBoolean()) {
                    continue;
                }

                socket.setSoTimeout(30000);
                Result result = (Result) in.readObject();

                if (!result.getTaskId().equals(taskId)) {
                    System.out.println("Worker " + worker.getPort() + " returned wrong taskId");
                    continue;
                }

                return result.getResult();

            } catch (Exception e) {
                System.out.println("Worker " + worker.getPort() + " failed: " + e.getMessage());
            }
        }

        throw new RuntimeException("No available workers");
    }

    /**
     * Метод для разбиения массива на участки.
     * Массив разбивается на участки в зависимости от
     * количества вычислительных узлов.
     *
     * @param list - входной массив.
     */
    private void splitArray(List<Integer> list) {
        int n = workers.size();
        int chunkSize = (int) Math.ceil((double) list.size() / n);

        for (int i = 0; i < n; i++) {

            int start = i * chunkSize;
            if (start >= list.size()) {
                break;
            }

            int end = Math.min(start + chunkSize, list.size());

            chunks.add(new ArrayList<>(list.subList(start, end)));
        }
    }
}