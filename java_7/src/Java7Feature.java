import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Java7Feature {
    // 一、Switch中添加对String类型的支持
    // Switch语句可以使用原始类型或枚举类型。Java引入了另一种类型，我们可以在switch语句中使用：字符串类型。
    // 说我们有一个根据其地位来处理贸易的要求。直到现在，我们使用if-其他语句来完成这个任务。
    private void processTrade(String status) {
        if (status.equalsIgnoreCase("NEW")) {
            // newTrade
        } else if (status.equalsIgnoreCase("EXECUTE")) {
            // executeTrade
        } else if (status.equalsIgnoreCase("PENDING")) {
            // pendingTrade
        }
    }

    // 这种处理字符串的方法是粗糙的。在Java中，我们可以使用增强的switch语句来改进程序，该语句以String类型作为参数。
    public String generate(String name, String gender) {
        String title = "";
        switch (gender) {
            case "Male":
                title = name + "先生";
                break;
            case "Female":
                title = name + "女士";
                break;
            default:
                title = name;
        }
        return title;
    }

    // 编译器在编译时先做处理
    // case仅仅有一种情况。直接转成if。
    // 假设仅仅有一个case和default，则直接转换为if…else…。
    // 有多个case。先将String转换为hashCode，然后相应的进行处理，JavaCode在底层兼容Java7曾经版本号。


    // 二、数字字面量的改进
    // Java7添加二进制表示（0B11110001、0b11110001）
    // Java7前支持十进制（123）、八进制（0123）、十六进制（0X12AB）
    // 数字中可加入分隔符
    // Java7中支持在数字量中间添加’_’作为分隔符。更直观，如（12_123_456）。下划线仅仅能在数字中间。编译时编译器自己主动删除数字中的下划线。
    int one_million = 1_000_000;


    // 三、异常处理（捕获多个异常） try-with-resources
    // 在异常处理区域有几处改进。Java引入了多个catch功能，以使用单个抓到块捕获多个异常类型。
    // catch子句能够同一时候捕获多个异常
    public void testSequence() {
        try {
            Integer.parseInt("Hello");
        } catch (NullPointerException | NumberFormatException e) {
            // 使用'|'切割，多个类型，一个对象e
        }
    }

    // 四、try-with-resources语句
    // Java7之前须要在finally中关闭socket、文件、数据库连接等资源；
    // Java7中在try语句中申请资源，实现资源的自己主动释放（资源类必须实现java.lang.AutoCloseable接口，一般的文件、数据库连接等均已实现该接口，close方法将被自己主动调用）。
    public String read(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(String.format("%n"));
            }
            return builder.toString();
        }
    }

    // 五、增强泛型推断
    Map<String, ArrayList<String>> map = new HashMap<>();
    List<String> list = new ArrayList<>();


    // 六、NIO2.0（AIO）新IO的支持
    // 那些使用Java的人可能还记得框架引起的头痛。在操作系统或多文件系统之间无缝地工作从来都不是一件容易的事情.。
    // 有些方法，例如删除或重命名，在大多数情况下都是出乎意料的。使用符号链接是另一个问题。实质上API需要大修。
    // 为了解决上述问题，Java引入了一个新的API，并在许多情况下引入了新的api。
    // 在NIO2.0提出了许多增强功能。在处理多个文件系统时，它还引入了新的类来简化开发人员的生活。
    // bytebuffer
    public void testBytebuffer() {
        ByteBufferUsage bbu = new ByteBufferUsage();
        bbu.useByteBuffer();
        bbu.byteOrder();
        bbu.compact();
        bbu.viewBuffer();
    }

    // filechannel
    public void testFileChannel() throws IOException {
        FileChannelUsage fcu = new FileChannelUsage();
        fcu.openAndWrite();
        fcu.readWriteAbsolute();
    }

    // 七、SR292与InvokeDynamic
    // JSR292: Supporting Dynamically Typed Languages on the JavaTM Platform，支持在JVM上运行动态类型语言。在字节码层面支持了InvokeDynamic。
    // 方法句柄MethodHandle
    // 见 class ThreadPoolManager

    // 调用invoke
    // 见 class ThreadPoolMain


    // 八、Path接口(重要接口更新)
    // 见 class PathUsage

    // DirectoryStream
    // 见 class ListFile

    // Files
    // 见 class FilesUtils

    // WatchService
    // 见 class WatchAndCalculate

    // 九、fork/join计算框架
    // Java7提供的一个用于并行执行任务的框架，是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。
    // 该框架为Java8的并行流打下了坚实的基础

    // Java中是如何实现此框架的。
    // - 分割任务，需要一个Fork类来分割任务，有可能任务很大，需要递归分割，直到分割的任务足够小；
    // - 执行任务，将分割的小任务分别放到双端队列中，然后启动线程从双端队列中获取任务并执行；
    // - 合并结果，小任务的执行结果会放到各自队列中，此时启动一个线程从各个队列中获取结果数据合并成最终结果。

    // Fork/Join使用以下两个类来完成以上三件事情：
    // ForkJoinTask: 我们要使用ForkJoin框架，必须首先创建一个ForkJoin任务。它提供在任务中执行fork()和join()操作的机制，
    // 通常情况下我们不需要直接继承ForkJoinTask类，而只需要继承它的子类，Fork/Join框架提供了以下两个子类：
    // - RecursiveAction: 用于没有返回结果的任务。
    // - RecursiveTask: 用于有返回结果的任务。
    // ForkJoinPool: ForkJoinTask需要通过ForkJoinPool来执行，
    // 任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。
    // 当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。

    // Fork/Join框架的使用
    // 计算 1 + 2 + ······ + 10 的计算结果。
    // - 任务分割: 每个子任务最多执行 5 个数的相加，那设置的廓值就是 5，那就是分割成 2 个子任务即可。
    // - 执行任务: 第一个任务执行 1-5 的和，第二个任务执行6-10的和
    // - 合并结果: 第一个任务和 + 第二个任务和
    // 见 class CountTask
}

class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 5;

    private int start;

    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            int sum = 0;
            // 任务小就直接计算
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
        // 如果任务大于廓值，则分割成2个子任务计算
        int middle = (start + end) / 2;
        CountTask countTask1 = new CountTask(start, middle);
        CountTask countTask2 = new CountTask(middle + 1, end);
        // 执行子任务
        countTask1.fork();
        countTask2.fork();
        // 等待子任务执行完，得到执行结果
        int leftResult = countTask1.join();
        int rightResult = countTask2.join();
        // 合并子任务结果
        return leftResult + rightResult;
    }

    /**
     * 说明：ForkJoinTask与一般任务的主要区别在于需要实现compute方法，
     * 此方法中首先需要判断任务是否足够小，如果足够小就直接执行任务。
     * 如果不是足够小（大于我们设置的廓值时），就必须分割成两个子任务，
     * 子任务在调用fork方法时，又会进入compute方法，看看当前子任务是否需要继续分割成子任务，
     * 如果不需要继续分割，则执行当前子任务并返回结果。使用join方法会等待子任务执行完并得到其结果。
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1, 10);
        Future<Integer> future = pool.submit(task);
        System.out.println(future.get());
    }
}

class WatchAndCalculate {
    void calculate() throws IOException, InterruptedException {
        WatchService service = FileSystems.getDefault()
                .newWatchService();
        Path path = Paths.get("")
                .toAbsolutePath();
        path.register(service, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            WatchKey key = service.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                Path createPath = (Path) event.context();
                createPath = path.resolve(createPath);
                long size = Files.size(createPath);
                System.out.println(createPath + "==>" + size);
            }
            key.reset();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchAndCalculate watchAndCalculate = new WatchAndCalculate();
        watchAndCalculate.calculate();
    }
}

class FilesUtils {
    void manipulateFiles() throws IOException {
        Path newFile = Files.createFile(Paths.get("new.txt")
                                                .toAbsolutePath());
        List<String> content = new ArrayList<>();
        content.add("Hello");
        content.add("World");
        Files.write(newFile, content, StandardCharsets.UTF_8);
        long size = Files.size(newFile);
        byte[] bytes = Files.readAllBytes(newFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.copy(newFile, outputStream);
        Files.delete(newFile);
    }

    public static void main(String[] args) throws IOException {
        FilesUtils fu = new FilesUtils();
        fu.manipulateFiles();
    }
}

class ListFile {
    public void listFiles() throws IOException {
        Path path = Paths.get("");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.*")) {
            for (Path entry : stream) {
                // 使用 entry
                System.out.println(entry);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ListFile listFile = new ListFile();
        listFile.listFiles();
    }
}

class PathUsage {
    void usePath() {
        Path path1 = Paths.get("folder1", "sub1");
        Path path2 = Paths.get("folder2", "sub1");
        path1.resolve(path2); // folder1\sub1\folder2\sub2
        path1.resolveSibling(path2); // folder1\folder2\sub2
        path1.relativize(path2); // ..\..\folder2\sub2
        path1.subpath(0, 1); // folder1
        path1.startsWith(path2); // false
        path2.endsWith(path2); // false
        Paths.get("folder1/./../folder2/my.text")
                .normalize(); // folder2\my.text
    }

    public static void main(String[] args) {
        PathUsage usage = new PathUsage();
        usage.usePath();
    }
}

class ThreadPoolMain {
    /**
     * 如果被继承，还能在静态上下文寻找正确的class
     */
    private ThreadPoolManager manager;

    private void cancelUsingMethodHandle(ScheduledFuture<?> hndl) {
        MethodHandle methodHandle = manager.makeMethodHandle();
        try {
            System.out.println("With Method Handle");
            methodHandle.invokeExact(manager, hndl);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void cancelUsingReflection(ScheduledFuture<?> hndl) {
        Method method = manager.makeReflective();
        try {
            System.out.println("With Reflection");
            method.invoke(hndl);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void cancelUsingProxy(ScheduledFuture<?> hndl) {
        ThreadPoolManager.CancelProxy proxy = manager.makeProxy();
        System.out.println("With Proxy");
        proxy.invoke(manager, hndl);
    }

    private void run() {
        BlockingQueue<String> lbq = new LinkedBlockingQueue<>();
        manager = new ThreadPoolManager(lbq);
        Runnable runnable = () -> System.out.println("run");
        ScheduledFuture<?> hndl = manager.run(runnable);
        cancelUsingMethodHandle(hndl);
    }

}

class ThreadPoolManager {

    private final ScheduledExecutorService stpe = Executors.newScheduledThreadPool(2);

    private final BlockingQueue<String> lbq;

    public ThreadPoolManager(BlockingQueue<String> lbq_) {
        lbq = lbq_;
    }

    public ScheduledFuture<?> run(Runnable runnable) {
        return stpe.scheduleAtFixedRate(runnable, 10, 10, TimeUnit.MILLISECONDS);
    }

    private void cancel(final ScheduledFuture<?> hndl) {
        stpe.schedule(new Runnable() {
            @Override
            public void run() {
                hndl.cancel(true);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * 使用Java7的新api，MethodHandle
     * invoke virtual 动态绑定后调用 obj.xxx
     * invoke special 静态绑定后调用 super.xxx
     */
    public MethodHandle makeMethodHandle() {
        MethodHandle methodHandle;
        try {
            methodHandle = MethodHandles.lookup()
                    .findVirtual(ThreadPoolManager.class,
                                 "cancel",
                                 MethodType.methodType(void.class, ScheduledFuture.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw (AssertionError) new AssertionError().initCause(e);
        }
        return methodHandle;
    }

    /**
     * 使用传统的反射api
     */
    public Method makeReflective() {
        Method method = null;
        try {
            Class<?>[] argsTypes = new Class[]{ScheduledFuture.class};
            method = ThreadPoolManager.class.getDeclaredMethod("cancel", argsTypes);
            method.setAccessible(true);
        } catch (IllegalArgumentException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 使用代理类
     */
    public CancelProxy makeProxy() {
        return new CancelProxy();
    }

    static class CancelProxy {
        public void invoke(ThreadPoolManager mae_, ScheduledFuture<?> hndl_) {
            mae_.cancel(hndl_);
        }
    }

}

class FileChannelUsage {
    void openAndWrite() throws IOException {
        FileChannel channel = FileChannel.open(Paths.get("my.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putChar('A')
                .flip();
        channel.write(buffer);
    }

    void readWriteAbsolute() throws IOException {
        FileChannel channel = FileChannel.open(Paths.get("absolute.txt"), StandardOpenOption.READ, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        ByteBuffer writeBuffer = ByteBuffer.allocate(4)
                .putChar('A')
                .putChar('B');
        writeBuffer.flip();
        channel.write(writeBuffer, 1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(2);
        channel.read(readBuffer, 1026);
        readBuffer.flip();
        char result = readBuffer.getChar(); //值为'B'
    }
}

class ByteBufferUsage {
    void useByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put((byte) 1);
        buffer.put(new byte[3]);
        buffer.putChar('A');
        buffer.putFloat(0.0f);
        buffer.putLong(10, 100L);
        System.out.println(buffer.getChar(4));
        System.out.println(buffer.remaining());
    }

    void byteOrder() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(buffer.getInt(0)); // 值为16777216
    }

    void compact() {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(new byte[16]);
        buffer.flip();
        buffer.getInt();
        buffer.compact();
        System.out.println(buffer.position());
    }

    void viewBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.putInt(1);
        IntBuffer intBuffer = buffer.asIntBuffer();
        intBuffer.put(2);
        int value = buffer.getInt();
    }

}