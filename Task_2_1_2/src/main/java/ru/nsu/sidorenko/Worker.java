package ru.nsu.sidorenko;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Вычислительный узел подсети.
 * Принимает задание с участком массива, проверяет наличие
 * непростого числа, отвечает контроллеру.
 * Каждое задание помещается в кэш по номеру, чтобы,
 * если участок обработан, не обрабатывать его снова.
 * После выполнения контроллеру отправляется результат
 * типа Result.
 */
public class Worker {
    private static final ConcurrentMap<UUID, Boolean> cache = new ConcurrentHashMap<>();

    /**
     * Метод для запуска вычислительного узла.
     * Работа с узлом происходит при помощи
     * ExecutorService, что позволяет управлять пулом
     * потоков.
     *
     * @param port - порт, на котором работает
     *             вычислительный узел.
     */
    public static void start(int port) {
        Thread t = new Thread(() -> {
            ExecutorService pool = Executors.newCachedThreadPool(r -> {
                Thread th = new Thread(r);
                th.setDaemon(true);
                return th;
            });
            try (ServerSocket server = new ServerSocket(port)) {
                while (true) {
                    Socket socket = server.accept();
                    pool.submit(() -> handle(socket, port));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * Метод для передачи результата выполнения
     * вычислительного узла контроллеру. Взаимодействие
     * сокета и вычислительного узла происходит при помощи
     * серверных сокетов.
     *
     * @param socket - сокет для взаимодействия
     *               контроллер и вычислительного узла.
     * @param port - порт, на котором работает
     *             вычислительный узел.
     */
    private static void handle(Socket socket, int port) {
        try (socket;
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream())) {

            Task task = (Task) in.readObject();
            UUID taskId = task.getTaskId();

            out.writeBoolean(true);
            out.flush();

            Boolean cached = cache.get(taskId);
            boolean hasComposite;
            if (cached != null) {
                hasComposite = cached;
                System.out.println("Worker " + port + ": cache hit for " + taskId);
            } else {
                hasComposite = task.getChunk().stream().anyMatch(x -> !IsPrime.isPrime(x));
                cache.put(taskId, hasComposite);
            }

            out.writeObject(new Result(taskId, hasComposite));
            out.flush();

        } catch (Exception e) {
            System.out.println("Worker " + port + " handle error: " + e.getMessage());
        }
    }
}