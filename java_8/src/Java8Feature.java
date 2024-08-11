import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.*;

public class Java8Feature {

    // 一、收集器 – java.util.stream.Collectors
    // Java 8 流的新类 java.util.stream.Collectors 实现了 java.util.stream.Collector 接口，同时又提供了大量的方法对流 ( stream ) 的元素执行 map and reduce 操作，或者统计操作。

    List<Integer> list = Arrays.asList(1, 2, 3, 4);
    // 1、Collectors.averagingDouble()
    // Collectors.averagingDouble() 方法将流中的所有元素视为 double 类型并计算他们的平均值。
    // 该方法返回的是同一个 Collectors 实例，因此可以进行链式操作。

    // Collectors.averagingDouble() 接受一个参数，这个参数是一个 lambda 表达式，用于对所有的元素执行一个 map 操作。
    // Java 所有集合的 stream().collect() 可以接受一个收集器实例作为其参数并返回该收集器的计算结果

    // 例如下面的代码，collect() 方法会把所有的元素收集起来然后传递给 Collectors.averagingDouble(d->`d*2) 收集器，对每个元素执行 *2 操作后计算平均值
    public void testAveragingDouble() {
        Double result = list.stream()
                .collect(Collectors.averagingDouble(d -> d * 2));
        System.out.println(result); // 输出结果为 5.0
    }

    // 2、Collectors.averagingInt()
    // Collectors.averagingInt() 方法和 Collectors.averagingDouble() 一样，
    // 不同的是它把流中的所有元素看成是 int 类型，并返回一个浮点类型的平均值
    public void testAveragingInt() {
        Double result = list.stream()
                .collect(Collectors.averagingInt(v -> v * 2));
        System.out.println(result); // 输出结果为 5.0
    }

    // 3、Collectors.averagingLong()
    // Collectors.averagingLong() 方法也和 Collectors.averagingDouble() 类似，
    // 不同的是它把流中的所有元素看成是 long 类型，并返回一个 double 类型的平均值
    public void testAveragingLong() {
        Double result = list.stream()
                .collect(Collectors.averagingLong(v -> v * 2));
        System.out.println(result); // 输出结果为 5.0
    }

    // 4、Collectors.counting()
    // Collectors.counting() 用于统计流中元素的个数。
    public void testCounting() {
        long result = list.stream()
                .collect(Collectors.counting());
        System.out.println(result); // 输出结果为 4

        long count = list.stream()
                .count();
        System.out.println(count);

        int size = list.size();
        System.out.println(size);
    }

    // 5、Collectors.joining()
    // Collectors.joining() 方法用某个指定的拼接字符串把所有元素拼接成一个字符串，并添加可选的前缀和后缀
    public void testJoining() {
        List<String> list = Arrays.asList("A", "B", "C", "D");
        String result = list.stream()
                .collect(Collectors.joining(",", "(", ")"));
        System.out.println(result); // "(A,B,C,D)"
    }

    // 6、Collectors.maxBy() 和 Collectors.minBy()
    // Collectors.maxBy() 和 Collectors.minBy() 两个方法分别用于计算流中所有元素的最大值和最小值。
    // 两个方法都可以接受一个比较器作为参数，用于如何计算最大值或最小值
    public void testMaxByAndMinBy() {
        list.stream()
                .collect(Collectors.maxBy(new IntegerComp()))
                .ifPresent(i -> System.out.println(i));
        list.stream()
                .collect(Collectors.minBy(new IntegerComp()))
                .ifPresent(i -> System.out.println(i));

        list.stream()
                .max(new IntegerComp())
                .ifPresent(System.out::println);
        list.stream()
                .min(new IntegerComp())
                .ifPresent(System.out::println);
    }

    // 7、Collectors.summingInt()
    // Collectors.summingInt() 方法将流中的所有元素视为 int 类型，并计算所有元素的总和 ( sum )
    public void testSummingInt() {
        int result = list.stream()
                .collect(Collectors.summingInt(i -> i));
        System.out.println(result);

        int sum = list.stream()
                .mapToInt(i -> i)
                .sum();
        System.out.println(sum);
    }

    // 8、Collectors.summingLong()
    // Collectors.summingLong() 将流中的所有元素视为 long 类型，并计算所有元素的总和
    public void testSummingLong() {
        List<Long> list = Arrays.asList(1L, 2L, 3L, 4L);
        long result = list.stream()
                .collect(Collectors.summingLong(l -> l));
        System.out.println(result);

        long sum = list.stream()
                .mapToLong(l -> l)
                .sum();
        System.out.println(sum);
    }

    // 9、Collectors.summingDouble()
    // Collectors.summingDouble() 将流中的所有元素视为 double 类型，并计算所有元素的总和
    public void testSummingDouble() {
        List<Double> list = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        double result = list.stream()
                .collect(Collectors.summingDouble(l -> l));
        System.out.println(result);

        double sum = list.stream()
                .mapToDouble(l -> l)
                .sum();
        System.out.println(sum);
    }

    // 10、Collectors.toList()
    // Collectors.toList() 将流中的所有元素导出到一个列表 ( List ) 中
    public void testToList() {
        List<String> collect = Stream.of("A", "B")
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    // 11、Collectors.toSet()
    // Collectors.toSet() 把流中的所有元素导出到一个集合 ( Set ) 中，并排除重复的元素 ( Set 的特性 )
    public void testToSet() {
        Set<String> collect = Stream.of("A", "A")
                .collect(Collectors.toSet());
        System.out.println(collect);
    }

    // 12、Collectors.toMap()
    // Collectors.toMap() 将流中的所有元素导出到一个哈希表 ( Map ) 中。
    // 该方法接受两个参数，第一个参数用于生成键 ( key ) ，第二个参数用于生成值 ( value )。
    // 两个参数都是 Lambda 表达式。
    public void testToMap() {
        Map<String, String> map = Stream.of("A", "B", "C")
                .collect(Collectors.toMap(k -> k, v -> v + v));
        map.forEach((k, v) -> System.out.println("key: " + k + " value: " + v));
        // key:A  value:AA
        // key:B  value:BB
        // key:C  value:CC
    }

    // 13、Collectors.mapping()
    // Collectors.mapping() 一般用于多重 map and reduce 中。 Java 文档中描述的原型如下
    // 第一个参数用于 map ，第二个参数用于 reduce
    public void testMapping() {
        List<Person> personList = Person.getList();
        personList.stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.joining(","))));
    }

    // 14、IntStream,LongStream,DoubleStream
    // IntStream、LongStream 和 DoubleStream 分别表示原始 int 流、 原始 long 流 和 原始 double 流。
    // 这三个原始流类提供了大量的方法用于操作流中的数据，同时提供了相应的静态方法来初始化它们自己。
    // IntStream
    // 方法	说明
    // rangeClosed(a,b)	返回子序列 [a,b]，包含起始值，增长步值为 1
    // range(a,b)	返回子序列 [a,b)，左闭右开，意味着不包括 b
    // sum	计算所有元素的总和
    // sorted	排序元素
    public void testIntStream() {
        IntStream.rangeClosed(13, 15)
                .map(n -> n * n)
                .forEach(s -> System.out.println(s + " "));
        IntStream.range(13, 15)
                .map(n -> n * n)
                .forEach(s -> System.out.println(s + " "));
        int sum = IntStream.rangeClosed(1, 10)
                .sum();
        IntStream.of(13, 4, 15, 2, 8)
                .sorted()
                .forEach(s -> System.out.println(s + " "));
    }

    // LongStream
    // java.util.stream.LongStream 是一个原始长整型值序列 ( sequence ) 。该流提供了许多方法可以对该流中的元素顺序执行或并行执行一些聚合操作。其实，它的使用方式和 IntStream 一样，因为提供的方法也一样，我们就不做展开了，直接看范例
    public void testLongStream() {
        LongStream.rangeClosed(13, 15)
                .map(n -> n * n)
                .forEach(s -> System.out.println(s + " "));
        LongStream.range(13, 15)
                .map(n -> n * n)
                .forEach(s -> System.out.println(s + " "));
        long sum = LongStream.rangeClosed(1, 10)
                .sum();
        LongStream.of(13, 4, 15, 2, 8)
                .sorted()
                .forEach(s -> System.out.println(s + " "));
    }

    // DoubleStream
    // java.util.stream.LongStream 是一个原始双精度浮点型序列 ( sequence ) 。该流提供了许多方法可以对该流中的元素顺序执行或并行执行一些聚合操作。它的使用方式和 IntStream 一样，提供的方法也一样，除此之外，还额外提供了几个聚合方法
    // 方法	说明
    // average	计算平均值
    // max	查找最大值
    public void testDoubleStream() {
        DoubleStream.of(5.33, 2.34, 5.32, 2.31, 3.51)
                .map(d -> d * 1.5)
                .forEach(s -> System.out.println(s + " "));
        double val = DoubleStream.of(12.1, 11.2, 13.3)
                .average()
                .getAsDouble();

        double asDouble = DoubleStream.of(12.1, 11.2, 13.3)
                .max()
                .getAsDouble();

        DoubleStream.of(12.1, 11.2, 12.3)
                .filter(d -> d > 12.21)
                .forEach(System.out::println);
    }

    // 15、Collectors.joining() 详解
    // Collectors.joining() 方法以遭遇元素的顺序拼接元素。我们可以传递可选的拼接字符串、前缀和后缀
    // 假设我们的流中有四个元素 ["A","B","C","D"]，那么我们就可以按照以下方式来收集它们
    // joining() 无参数方法会返回一个 Collectors 实例，并且以空字符串 ( "" ) 来拼接收集到的所有元素
    List<String> stringList = Arrays.asList("A", "B", "C", "D");

    public void testJoining2() {
        String result = stringList.stream()
                .collect(Collectors.joining());
        System.out.println(result);

        String join = String.join("", stringList);
        System.out.println(join);
    }

    // joining(CharSequence delimiter)
    // joining(CharSequence delimiter) 接受一个参数字符串序列作为拼接符，并返回一个 Collectors 实例。假如我们传递的拼接符为 "-" 。那么输出结果为 A-B-C-D
    public void testJoiningDelimiter() {
        String collect = stringList.stream()
                .collect(Collectors.joining("-"));
        System.out.println(collect);

        String join = String.join("-", stringList);
        System.out.println(join);
    }

    // joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
    public void testJoiningDelimiterAndPrefixAndSuffix() {
        String collect = stringList.stream()
                .collect(Collectors.joining("-", "[", "]"));
        System.out.println(collect);
    }

    // 范例
    // 范例 1 ： 如果流中的数据是字符串
    // 下面的代码演示了如何使用 joinning() 的三种重载方法来拼接字符串
    public void testJoiningString() {
        System.out.println(stringList.stream()
                                   .collect(Collectors.joining()));

        System.out.println(stringList.stream()
                                   .collect(Collectors.joining(",")));

        System.out.println(stringList.stream()
                                   .collect(Collectors.joining(",", "[", "]")));
    }

    // 范例 2: 如果流中的数据是对象
    // 如果流中的数据是对象，下面的代码演示了如何拼接它们。
    // 首先，我们创建一个 Person 类
    public void testJoiningObject() {
        List<Person> personList = Person.getList();
        System.out.println(personList.stream()
                                   .map(Person::getName)
                                   .collect(Collectors.joining()));

        System.out.println(personList.stream()
                                   .map(Person::getName)
                                   .collect(Collectors.joining("|")));

        System.out.println(personList.stream()
                                   .map(Person::getName)
                                   .collect(Collectors.joining("-", "[", "]")));

        System.out.println(personList.stream()
                                   .map(p -> String.valueOf(p.getAge()))
                                   .collect(Collectors.joining()));

        System.out.println(personList.stream()
                                   .map(p -> String.valueOf(p.getAge()))
                                   .collect(Collectors.joining("|")));

        System.out.println(personList.stream()
                                   .map(p -> String.valueOf(p.getAge()))
                                   .collect(Collectors.joining("-", "[", "]")));
    }


    // 二、、Runnable Lambda 表达式
    // 众所周知，Java 8 中的 Runnable 和 Callable 两个接口都添加了 @FunctionalInterface 注解，
    // 因此我们可以直接使用 Lambda 表达式来代替它们的 run() 和 call() 方法
    // Runnable 表达式
    public void testRunnableLambda() {
        Runnable runnable = () -> System.out.println("Hello World");
        Thread thread = new Thread(runnable);
        thread.start();

        // 如果我们的 Lambda 表达式需要多行代码，可以用一对打括号 {} 扩起来，就像下面这样
        Runnable runnable2 = () -> {
            int a = 2;
            System.out.println(a);
        };

        // 如果Lambda 表达式需要使用到外部的参数，那么必须对参数添加 final 修饰符表示参数不可变更。
        final List<String> books = Arrays.asList("Xiyouji", "Hongloumeng");
        Runnable runnable3 = () -> {
            Consumer<String> style = (String book) -> System.out.println(book);
            books.forEach(style);
        };

        // 我们还可以把 Runnable 表达式作为参数传递给 Thread 相关的方法
        // 我们首先来定一个 Book.java 类
        // 然后在一个 Runnable 中输出书籍的一些信息
        final List<Book> bookList = Arrays.asList(new Book(1, "Xiyouji"), new Book(2, "Hongloumeng"));
        Runnable runnable4 = () -> bookList.forEach(Book::print);
        Thread thread1 = new Thread(runnable4);
        thread1.start();

        Runnable runnable5 = () -> {
            Consumer<Book> style = (Book b) -> System.out.println(b.name);
            bookList.forEach(style);
        };
        Thread thread2 = new Thread(runnable5);
        thread2.start();

        // 同样的，我们还可以在 ExecutorService 中运行 Runnable
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable6 = () -> bookList.forEach(Book::print);
        executorService.execute(runnable6);
        Runnable runnable7 = () -> {
            Consumer<Book> style = (Book book) -> System.out.println(book.name);
            bookList.forEach(style);
        };
        executorService.execute(runnable7);
    }

    // 三、java.util.Base64 编码解码
    // Base64 是一种常见的字符编码解码方式，一般用于将二进制数据编码为更具可读性的 Base64 进制格式。
    // 在Java 6 （ JDK 1.6 ) 之前， JDK 一直没有包含 Base64 的实现类。因此大部分人都使用 Sum/Orale JDK 里面的 sun.misc.BASE64Encode 和sun.misc.BASE64Decode。然后这也成为很多 Java 开发者的习惯。一直沿用到今天的 Java8 中还有人在用。
    // JDK1.6 虽然添加了 Base64 的实现。但是，非常隐秘，竟然是在 javax.xml.bind 包下的 DatastypeConvert 类中的两个静态方法 parseBase64Binary 和 printBase64Binary.
    // Java 8 终于把 Base64 扶正了，在 java.util 包下提供了 Base64 类用于编码和解码 Base64 数据。
    // Java 8 中的 java.util.Base64 类提供了三种类型的 Base64 编码解码格式：
    // 1、 简单类型( simple ) : 编码字符只包含 A-Za-z0-9+/ 等 64 个字符。且编码的时候不会包含任何换行符 ( \r 、 \n 、\r\n ）。解码的时候也只会解码 A-Za-z0-9+/ 内的字符，超出的则会被拒绝。
    // 2、 URL : 编码字符只包含 A-Za-z0-9+_ 等 64 个字符。和 简单 相比，就是把 / 换成了 _ 。因为没有 / 字符，因此这种编码方式非常适合 URL 和文件名等。
    // 3、 MIME : 编码会被映射为 MIME 友好格式：每一行输出不超过 76 个字符，而且每行以 \r\n 符结束。但末尾行并不会包含 \r\n。

    // 内部类
    // java.util.Base64 还包含了两个内部静态类，分别实现了 RFC 4648 和 RFC 2045 中规范的 Base64 编码和解码方式。
    // 内部类	                    说明
    // static class Base64.Decoder	该类实现使用 RFC 4648 和 RFC 2045 中规定的 Base64 解码方案解码数据
    // static class Base64.Encoder	该类实现使用 RFC 4648 和 RFC 2045 中规定的 Base64 编码方案编码数据

    // 静态方法
    // java.util.Base64 类提供的都是静态方法。下表列出了这些静态方法
    // 方法	                        说明
    // Base64.Decoder getDecoder()	返回一个 Base64.Decoder 类型的 简单 解码器
    // Base64.Encoder getEncoder()	返回一个 Base64.Encoder 类型的 简单 编码器
    // Base64.Decoder getMimeDecoder()	返回一个 Base64.Decoder 类型的 MIME 解码器
    // Base64.Encoder getMimeEncoder()	返回一个 Base64.Encoder 类型的 MIME 编码器
    // Base64.Encoder getMimeEncoder(
    // int lineLength, byte[] lineSeparator)	返回一个 Base64.Encoder 类型的使用特定长度和行分隔符的 MIME 编码器
    // Base64.Decoder getUrlDecoder()	返回一个 Base64.Decoder 类型的 URL 和文件名安全的解码器
    // Base64.Encoder getUrlEncoder()	返回一个 Base64.Encoder 类型的 URL 和文件名安全的编码器

    // 范例一： 基本的编码解码器
    public void testDecoderAndEncoder() {
        String base64EncodedString = Base64.getEncoder()
                .encodeToString("你好世界".getBytes(StandardCharsets.UTF_8));
        System.out.println(base64EncodedString);

        System.out.println();

        byte[] base64decodedBytes = Base64.getDecoder()
                .decode(base64EncodedString);
        System.out.println(new String(base64decodedBytes, StandardCharsets.UTF_8));
    }

    // 范例二： URL 和文件名安全的编码解码器
    public void testUrlDecoderAndUrlEncoder() {
        String base64encodedString = Base64.getUrlEncoder()
                .encodeToString("你好世界".getBytes(StandardCharsets.UTF_8));
        System.out.println(base64encodedString);

        System.out.println();

        byte[] base64decodedBytes = Base64.getUrlDecoder()
                .decode(base64encodedString);
        System.out.println(new String(base64decodedBytes, StandardCharsets.UTF_8));
    }

    // 范例三：MIME Base64 编码解码器
    public void testMimeDecoderAndMimeEncoder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(UUID.randomUUID()
                                         .toString());
        }

        byte[] bytes = stringBuilder.toString()
                .getBytes(StandardCharsets.UTF_8);
        String mimeEncodedString = Base64.getMimeEncoder()
                .encodeToString(bytes);
        System.out.println(mimeEncodedString);

        System.out.println();

        byte[] base64decodedBytes = Base64.getMimeDecoder()
                .decode(mimeEncodedString);
        System.out.println(new String(base64decodedBytes, StandardCharsets.UTF_8));

        // 是不是看不出来有 76 个字符分割 ？
        // 没关系，我们使用另一个 getMimeEncoder(int lineLength, byte[] lineSeparator) 重载方法试一下
        String mimeEncodedString32 = Base64.getMimeEncoder(32, "@~@\n\n".getBytes(StandardCharsets.UTF_8))
                .encodeToString(bytes);
        System.out.println(mimeEncodedString32);

        System.out.println();
        byte[] base64decodedBytes32 = Base64.getMimeDecoder()
                .decode(mimeEncodedString);
        System.out.println(new String(base64decodedBytes32, StandardCharsets.UTF_8));
    }

    // 四、Lambda 表达式 （ 上 ）- 简介
    // Lambda 表达式在 Java 8 中引入，并且被吹捧为 Java 8 最大的特性。
    // Lambda 表达式是函数式编程的的一个重要特性，标志者 Java 向函数式编程迈出了重要的第一步。

    // Java Lambda 表达式语法
    // parameter -> expression body
    // 有参数且只有一条语句时
    // (a,b) -> a + b
    // 只有一个参数时
    // a -> a
    // 没有参数时
    // ()  -> System.out.println("Hello World")
    // 有多条语句时
    // (a,b) -> {
    //    int c = a + b;
    //    System.out.println("Hello World")
    //}

    // 可选的参数类型声明 ： 无需声明参数的类型。编译器可以从参数的值推断出相同的值。
    // 可选的参数周围的小括号 () ： 如果只有一个参数，可以忽略参数周围的小括号。但如果有多个参数，则必须添加小括号。
    // 可选的大括号 {} : 如果 Lambda 表达式只包含一条语句，那么可以省略大括号。但如果有多条语句，则必须添加大括号。
    // 可选的 return 关键字 ： 如果 Lambda 表达式只有一条语句，那么编译器会自动 return 该语句最后的结果。但如果显式使用了 return 语句，则必须添加大括号 {} ，哪怕只有一条语句。

    // Java Lambda 表达式的原理
    // Java 8 中的 Lambda 表达式其实是一个特殊的只有一个方法的类的实例。
    // 这些类是 Java 8 内部已经定义好的，而且实现了 java.lang.FunctionalInterface 这个接口。
    // 这个java.lang.FunctionalInterface 接口是一种信息性注解类型，用于标识一个接口类型声明为函数接口（ functional interface ）。
    // 从某些方面说，Java 8 的 Lambda 表达式是使用匿名内部类的语法创建了 java.util.function 包下相应签名的接口的或者其它自定义的只有一个方法的接口实例。
    // 但是，实际上，Java 8 中的 Lambda 不仅仅是使用匿名内部类，还使用了 Java 8 接口的默认方法和一些其它的功能。

    interface MathOperation {
        int operation(int a, int b);
    }

    public void testLambdaMathOperation() {
        // 范例一： Java Lambda 表达式
        Runnable runnable = () -> System.out.println("Hello");
        Thread thread = new Thread(runnable);
        thread.start();

        // 范例二
        // 有声明参数类型
        MathOperation addition = (int a, int b) -> a + b;
        // 没有声明参数类型
        MathOperation subtraction = (a, b) -> a - b;
        // 使用 return 语句显式返回值需要添加大括号
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        // 如果只有一条语句，那么可以省略大括号，Java 会返回表达式的值
        MathOperation division = (int a, int b) -> a / b;
        System.out.println(operate(10, 5, addition));
        System.out.println(operate(10, 5, subtraction));
        System.out.println(operate(10, 5, multiplication));
        System.out.println(operate(10, 5, division));
    }

    static int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    // Java Lambda 表达式的缺点
    // Java Lambda 表达式最大的缺点，就是不能像其它语言的 Lambda 表达式一样凭空出现。
    // Java 中的 Lambda 表达式需要有一个函数接口声明作为模板。这个模板定义了 Lambda 表达式的参数类型和返回值类型。
    // 例如下面的代码，我们先要声明一个函数接口类型，然后才能定义一个参数和返回值都一样的表达式
    // 先声明一个函数接口
    interface GreetingService {
        void sayMessage(String message);
    }

    public void testLambdaGreetingService() {
        GreetingService greetingService = message -> System.out.println("你好:" + message);
        greetingService.sayMessage("小华");
    }

    // 五、Lambda 表达式 （ 中 ）- 外部参数
    // 在Java 8 Lambda 表达式 （ 上 ）- 简介 章节中我们讲解了 Java 8 Lambda 表达式的一些基础知识。我们也了解 Java 8 Lambda 表达式的一些使用场景：
    // - Java Lambda 表达式主要用于定义函数接口的内联实现。而函数接口，就是只包含了一个方法的接口。在前一章节中，我们使用了各种类型的 lambda 表达式来定义 MathOperation 接口的 operation 方法。
    // - Java Lambda 表达式消除了对 匿名类 的需求，并为 Java 提供了非常简单但功能强大的函数编程功能。

    // 1、Java 8 Lambda 表达式作用域 ( scope )
    // 因为Java 8 的 lambda 表达式其实是函数接口的内联实现，也就是匿名内部类，因此，可以引用任何外部的变量或者常量。
    // 但是，lambda 对这些外部的变量是有要求的： 它们必须使用 final 修饰符修饰。
    // 如果一个变量允许被第二次赋值，则 Lambda 表达式会抛出编译错误。
    static final String message = "Hello";

    // lambda 引用的普通的变量也是可以的，只要这个变量没有第二次被赋值，不管是任何地方。
    static String name = "小华";

    // 如果lambda 表达式引用的变量并不是当前作用域下声明的，也可以随意赋值，并不会报错
    static String time = "12:00";

    public void testLambda2() {
        GreetingService greetingService = x -> System.out.println(message + name + x);
        greetingService.sayMessage("你好");

        // 如果lambda 表达式引用的是当前作用域下的普通的变量，而该变量又在某个地方第二次被赋值，则会抛出一个编译错误
        String id = "1";
        GreetingService greetingService1 = x -> {
            //id = "2";
            System.out.println(id + x);
            return;
        };
        //id = "2";
        greetingService1.sayMessage("你好");

        GreetingService greetingService2 = x -> {
            time = "13:00";
            System.out.println(time + x);
            return;
        };
        greetingService2.sayMessage("你好");

    }

    // 2、总结
    // Java lambda 表达式可以随意引用外部变量，但如果外部变量是在当前作用域声明的，则一定不可以进行第二次赋值，哪怕是在 lambda 语句之后。

    // 六、Lambda 表达式 （ 下 ）范例
    // lambda 表达式是 java 8 引入的最重要的功能之一。
    // lambda 表达式为那些只包含一个方法的接口提供了简洁代码，例如函数接口 ( function interface )。
    // lambda 表达式还为集合的迭代提供了一种更为简单的方式。
    // 对于那些使用匿名类或内部类实现的单个方法接口，在 Java 8 中，可以使用 lambda 表达式来实现。不仅功能相同，而且代码更简洁。

    // lambda 表达式真正的原理，是 「 为那些函数接口定义了它们包含的唯一方法，而且返回函数接口的实例 」
    // 1、在 Runnable 中使用 lambda 表达式
    public void testRunnable() {
        Runnable runnable = () -> System.out.println("Hello");
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // 2、在用户自定义的函数接口中使用 lambda 表达式
    public void testCalculator() {
        Calculator calculator = (n1, n2) -> n1 + n2;
        System.out.println(calculator.add(5, 8));

        Calculator calculator1 = Integer::sum;
        System.out.println(calculator.add(5, 8));
    }

    interface Calculator {
        int add(int n1, int n2);
    }

    // 3、在 Comparator 中使用 lambda 表达式
    public void testComparator() {
        List<String> list = Arrays.asList("A", "B", "C", "D");
        for (String s : list) {
            System.out.println(s);
        }
        Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
        list.sort(comparator);
        for (String s : list) {
            System.out.println(s);
        }

        Comparator<String> comparator2 = String::compareTo;
        list.sort(comparator2);
    }

    // 4、使用 Lambda 表达式实现函数接口来迭代集合
    // Java 8 中引入了 java.util.function 包。这个包提供了 java.util.function.Function 接口，我们可以在这个接口的帮助下，使用 Lambda 表达式来迭代集合。
    // java.util.function.Function 只有一个方法 apply() ，这个方法可以在用户定义的函数中调用。我们可以使用 Function 接口实现一个自定义的打印方法
    public void testFunction() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("小华", 18));
        list.add(new Student("小红", 19));
        list.add(new Student("小龙", 19));
        for (Student student : list) {
            System.out.println(student.customShow(s -> s.getName()));
            System.out.println(student.customShow(Student::getName));
        }
    }

    // 七、方法引用
    // 使用 Java 8 新增的 lambda 表达式和 forEach 改造for循环
    public void testForEach() {
        List<String> list = Arrays.asList("Alibaba", "Tencent", "Baidu");
        list.forEach(item -> System.out.println(item));
        list.forEach(item -> upperAndPrint(item));

        // item ->` System.out.println(item) 这个表达式，
        // 其实就是调用 System.out.println() 方法，并把 forEach 迭代列表生成的唯一参数 item 传给它而已
        // 既然这样，我们为什么不能直接传递方法名给 forEach 呢？
        // 自 Java 8 开始，还真是可以直接传递方法名的，改成下面这样
        list.forEach(System.out::println);
        list.forEach(Java8Feature::upperAndPrint);

        // 在这个正确的方法中，有两个重点：
        // 1、 方法名和类名之间不是使用点号(.)而是使用两个冒号::；
        // 2、 提供的方法名必须包含类名，如果没有引入该类名，则需要使用全限定类名，也就是需要添加包名作为前缀；
        // 这种使用方法名做参数的做法，在 Java 8 中称之为 「 方法引用 」

        // Java 8 中的方法引用通过 :: 符号引用方法，而且支持一下类型的方法引用
        // 1、 静态方法；
        // 2、 实例方法；
        // 3、 使用new运算符的构造函数例如TreeSet::new；

        // 我们重写一下上面的范例，演示下如何引用静态方法和实例方法
        list.forEach(System.out::println);
        list.forEach(Java8Feature::upperAndPrint);
        list.forEach(this::lowerAndPrint);
    }

    public void lowerAndPrint(String s) {
        System.out.println(s.toLowerCase());
    }

    public static void upperAndPrint(String s) {
        System.out.println(s.toUpperCase());
    }


    // 七、接口 ( interface ) 默认方法
    // 在Java 7 及以前的版本，对于一个接口有多个实现的时候，我们通常的做法就是让所有的实现继承另一个基础类，然后在这个基础类中实现这个方法。
    // 这就是，为什么 Java 中的 I/O 那么多类的原因，一个庞大的家族体系。
    // Java 8 为 接口 ( interface ) 中引入了 「 默认方法 」( default method ) 实现这个新的概念。
    // 但是，引入的初衷竟然是不是为了解救一个接口多个实现的痛苦，而是为了向后兼容，以便旧接口也可以使用 Java 8 的 [lambda 表达式][lambda] 功能。
    // 例如，Java 8 新引入的 forEach 这个功能，其实，List 或 Collection 接口没有声明和实现 forEach 方法。因为，添加此类方法将简单地破坏集合框架实现。
    // 既然不能每个类都改过去，那怎么办呢？
    // 当然是从它们都实现了的共同的祖先处想办法啦。
    // 跳来跳去，最后选中了接口 interface 。

    // 新增加一个关键字，比如 default ，用于标识这个方法是可以有具体的实现。
    // 有了default 关键字，我们就可以在所有集合都实现的接口 Collection<E> 中添加一个 forEach 方法啦。
    // 这是一个非常重要的新功能，它的出现，Java 8 及以后的版本，添加新功能的速度明显加快了很多
    // Java 8 接口默认方法语法
    interface Greeter {
        default void greet(String name) {
            System.out.println("你好, " + name);
        }

        default void greetEn(String name) {
            System.out.println("Hello, " + name);
        }

    }
    // 从语法中可以看到，一个接口默认方法和普通的接口方法声明有两个不同点：
    // 1、 接口默认方法可以有具体实现；
    // 2、 接口默认方法需要使用default关键字修饰；

    // Java 8 接口默认方法特征
    // 1、 一个接口可以有任意数量的默认方法，也可以没有默认方法；
    // 2、 如果一个类实现的两个接口都有一个同名的默认方法，那么该类必须自己实现同样的方法，然后在实现内部可以调用相应接口的方法；

    // 接口默认方法
    // 我们写一个范例简单演示下接口默认方法的使用
    public void testDefaultMethod() {
        Greeter greeter = new Greeter() {
        };
        greeter.greet("小华");
        // 一个接口可以有多个默认方法
        greeter.greetEn("XiaoHua");

        // 一个类实现了多个具有同名的默认方法接口
        // 如果一个类实现了两个或两个以上的接口，而这些接口有两个或两个以上实现了相同的方法名的默认方法，结果会怎么样呢？ 比如下面这个范例
        // 类必须自己实现一个相同的方法，否则调用会报错
        Hello hello = new Hello();
        hello.greet("小华");

    }

    class Hello implements Greeter, GreeterEn {
        public void greet(String name) {
            // 当然了，还可以调用相关接口的默认方法
            Greeter.super.greet(name);
            GreeterEn.super.greet(name);
            System.out.println("你好，" + name);
        }
    }

    interface GreeterEn {
        default public void greet(String name) {
            System.out.println("Hello，" + name);
        }
    }

    // 八、接口静态方法
    // CountDownLatch 类的作用？ 简单来说，我们可以使用它来阻塞线程，直到其他线程完成给定任务。
    // 1、并发编程中使用 CountDownLatch
    // 简而言之，CountDownLatch 有一个计数器字段，我们可以根据需要减少它，因此，我们可以使用它来阻止调用线程，直到它被计数到零。
    // 如果我们正在进行一些并行处理，我们可以使用与计数器相同的值来实例化 CountDownLatch，因为我们想要处理多个线程。然后，我们可以在每个线程完成后调用 countdown()，保证调用 await() 的依赖线程将阻塞，直到工作线程完成。

    // 2、使用 CountDownLatch 等待线程池完成
    // 我们通过创建一个 Worker 来尝试这个模式，并使用 CountDownLatch 字段来指示它何时完成
    class Worker implements Runnable {

        private List<String> outputScraper;

        private CountDownLatch countDownLatch;

        public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            // Do something
            countDownLatch.countDown();
            outputScraper.add("count down");
        }
    }

    public void testCountDown() throws InterruptedException {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await();
        outputScraper.add("Latch released");
        // 上面这个示例中，"Latch release" 将始终是最后一个输出 – 因为它取决于 CountDownLatch 的释放。
        // 注意，如果我们没有调用 await() 方法，我们将无法保证线程执行的顺序，因此测试会随机失败。
    }

    // 3、在等待开始的线程池中使用 CountDownLatch
    // 我们重用前面的示例，但是这次开启了了数千个线程而不是 5 个线程，很可能许多早期的线程在后面的线程上调用 start() 之前已经完成了处理。这可能会使尝试重现并发问题变得困难，因为我们无法让所有线程并行运行。
    // 为了解决这个问题，我们让 CountdownLatch 的工作方式与上一个示例有所不同。在某些子线程完成之前，我们可以阻止每个子线程直到所有其他子线程都启动，而不是阻塞父线程。
    // 我们把上一个示例的 run() 方法修改下，使其在处理之前阻塞
    public class WaitingWorker implements Runnable {

        private List<String> outputScraper;

        private CountDownLatch readyThreadCounter;

        private CountDownLatch callingThreadBlocker;

        private CountDownLatch completedThreadCounter;

        public WaitingWorker(List<String> outputScraper, CountDownLatch readyThreadCounter, CountDownLatch callingThreadBlocker, CountDownLatch completedThreadCounter) {
            this.outputScraper = outputScraper;
            this.readyThreadCounter = readyThreadCounter;
            this.callingThreadBlocker = callingThreadBlocker;
            this.completedThreadCounter = completedThreadCounter;
        }

        @Override
        public void run() {
            // do something
            readyThreadCounter.countDown();
            outputScraper.add("readyThreadCounter count down");
            try {
                callingThreadBlocker.await();
                outputScraper.add("callingThreadBlocker calls");
                // Do something
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // do something
                completedThreadCounter.countDown();
                outputScraper.add("completedThreadCounter count down");
            }
        }

        // 接下来，我们修改下测试，直到所有工人都已启动，解锁工人，然后阻止，直到工人完成
        public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
            List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
            CountDownLatch readyThreadCounter = new CountDownLatch(5);
            CountDownLatch callingThreadBlocker = new CountDownLatch(1);
            CountDownLatch completedThreadCounter = new CountDownLatch(1);
            List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                    .limit(5)
                    .collect(Collectors.toList());
            workers.forEach(Thread::start);
            readyThreadCounter.await();
            outputScraper.add("Workers ready");
            callingThreadBlocker.countDown();
            outputScraper.add("callingThreadBlocker count down");
            completedThreadCounter.await();
            outputScraper.add("Workers complete");
        }
    }
    // 这种模式对于尝试重现并发错误非常有用，可以用来强制数千个线程尝试并行执行某些逻辑。

    // 4、让 CountdownLatch 尽早结束
    // 有时，我们可能会遇到一个情况，即在 CountdownLatch 倒计时之前，Workers 已经终止了错误。这可能导致它永远不会达到零并且 await() 永远不会终止。
    // 为了解决这个问题，我们在调用 await() 时添加一个超时参数。
    public void whenFailingToParallelProcess_thenMainThreadShouldGetNotGetStuck() throws InterruptedException {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(Collectors.toList());
        workers.forEach(Thread::start);
        countDownLatch.await(3L, TimeUnit.SECONDS);
    }

    class BrokenWorker implements Runnable {

        private List<String> outputScraper;

        private CountDownLatch countDownLatch;

        public BrokenWorker(List<String> outputScraper, CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            if (true) {
                throw new RuntimeException("Oh dear, I'm a BrokenWorker");
            }
            // Do something
            countDownLatch.countDown();
            outputScraper.add("count down");
        }
    }

    // 九、集合遍历 forEach() 方法
    // BlockingQueue 的队列类型
    // java.util.concurrent 提供了两种类型的 BlockingQueue：
    // 1、 无限队列（unboundedqueue）–几乎可以无限增长；
    // 2、 有限队列（boundedqueue）–定义了最大容量；

    // 1、无限队列
    BlockingQueue<String> unboundedQueue = new LinkedBlockingQueue<>();
    // 上面这段代码中，blockingQueue 的容量将设置为 Integer.MAX_VALUE 。
    // 使用无限 BlockingQueue 设计生产者 – 消费者模型时最重要的是
    // 消费者应该能够像生产者向队列添加消息一样快地消费消息 。
    // 否则，内存可能会填满，然后就会得到一个 OutOfMemory 异常。

    // 3、有限队列
    BlockingQueue<String> boundedQueue = new LinkedBlockingQueue<>(10);
    // 上面这句代码中，我们设置了 blockingQueue 的容量为 10 。这意味着当消费者尝试将元素添加到已经满了的队列时，结果取决于添加元素的方法（ offer() 、add() 、put() ) ，它将阻塞，直到有足够的空间可以插入元素。否则，添加操作将会失败。
    // 使用有限队列是设计并发程序的好方法，因为当我们将元素插入到已经满了的队列时，这些操作需要等到消费者赶上并在队列中提供一些空间。这种机制可以让那个我们不做任何其它更改就可以实现节流。

    // 4、BlockingQueue API
    // BlockingQueue 接口的所有方法可以分为两大类：
    // 负责向队列添加元素的方法和检索这些元素的方法。
    // 在队列满/空的情况下，来自这两个组的每个方法的行为都不同。

    // 添加元素
    // BlockingQueue 提供了以下方法用于添加元素
    // 方法	    说明
    // add()	如果插入成功则返回 true，否则抛出 IllegalStateException 异常
    // put()	将指定的元素插入队列，如果队列满了，那么会阻塞直到有空间插入
    // offer()	如果插入成功则返回 true，否则返回 false
    // offer(E e, long timeout, TimeUnit unit)	尝试将元素插入队列，如果队列已满，那么会阻塞直到有空间插入

    // 检索元素
    // BlockingQueue 提供了以下方法用于检索元素
    // 方法	    说明
    // take()	获取队列的头部元素并将其删除，如果队列为空，则阻塞并等待元素变为可用
    // poll(long timeout, TimeUnit unit)	检索并删除队列的头部，如有必要，等待指定的等待时间以使元素可用，如果超时，则返回 null
    // 在构建生产者 – 消费者程序时，这些方法是 BlockingQueue 接口中最重要的构建块。

    // 5、多线程生产者 – 消费者示例
    // 接下来我们创建一个由两部分组成的程序 – 生产者 ( Producer ) 和消费者 ( Consumer ) 。
    class NumbersProducer implements Runnable {

        private BlockingQueue<Integer> numbersQueue;
        private final int poisonPill;
        private final int poisonPillPerProducer;

        public NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer) {
            this.numbersQueue = numbersQueue;
            this.poisonPill = poisonPill;
            this.poisonPillPerProducer = poisonPillPerProducer;
        }

        @Override
        public void run() {
            try {
                generateNumbers();
            } catch (InterruptedException e) {
                Thread.currentThread()
                        .interrupt();
            }
        }


        private void generateNumbers() throws InterruptedException {
            for (int i = 0; i < 100; i++) {
                numbersQueue.put(ThreadLocalRandom.current()
                                         .nextInt(100));
            }
            for (int j = 0; j < poisonPillPerProducer; j++) {
                numbersQueue.put(poisonPill);
            }
        }
    }
    // 我们的生成器构造函数将 BlockingQueue 作为参数，用于协调生产者和使用者之间的处理。
    // 我们看到方法 generateNumbers() 将 100 个元素放入队列中。
    // 它还需要有毒 （ poison ） 丸 （ pill ） 消息，以便知道在执行完成时放入队列的消息类型。
    // 该消息需要将 poisonPillPerProducer 次放入队列中。

    // 每个消费者将使用 take() 方法从 BlockingQueue 获取一个元素，因此它将阻塞，直到队列中有一个元素。
    // 从队列中取出一个 Integer 后，它会检查该消息是否是毒 （ poison ） 丸 （ pill ） ，
    // 如果是，则完成一个线程的执行。否则，它将在标准输出上打印出结果以及当前线程的名称。

    class NumbersConsumer implements Runnable {

        private BlockingQueue<Integer> queue;
        private final int poisonPill;

        public NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
            this.queue = queue;
            this.poisonPill = poisonPill;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    if (number.equals(poisonPill)) {
                        return;
                    }
                    System.out.println(Thread.currentThread()
                                               .getName() + " " + number);
                }
            } catch (InterruptedException e) {
                Thread.currentThread()
                        .interrupt();
            }
        }
    }
    // 需要注意的重要事项是队列的使用。与生成器构造函数中的相同，队列作为参数传递。
    // 我们可以这样做，是因为 BlockingQueue 可以在线程之间共享而无需任何显式同步。
    // 既然我们有生产者和消费者，我们就可以开始我们的计划。我们需要定义队列的容量，并将其设置为 100 个元素。

    // 我们希望有 4 个生产者线程，并且有许多消费者线程将等于可用处理器的数量
    public void testBlockingQueue() {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime()
                .availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
        int mod = N_CONSUMERS % N_PRODUCERS;

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);
        for (int i = 0; i < N_PRODUCERS - 1; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
        }
        new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer + mod)).start();

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }
    }
    // BlockingQueue 是使用具有容量的构造创建的。我们正在创造 4 个生产者和 N 个消费者。
    // 我们将我们的毒 （ poison ） 丸 （ pill ）消息指定为 Integer.MAX_VALUE，因为我们的生产者在正常工作条件下永远不会发送这样的值。
    // 这里要注意的最重要的事情是 BlockingQueue 用于协调它们之间的工作。

    // 当我们运行程序时，4 个生产者线程将随机整数放入 BlockingQueue 中，
    // 消费者将从队列中获取这些元素。每个线程将打印到标准输出线程的名称和结果。


    // 十、可选值 java.util.Optional 类
    // 在不考虑竖起来的情况下，抛一个硬币，落地时，显示正面的情况只有两种：是正面和不是正面。
    // 很多时候，这是一个 「 谓词 」，也就是返回布尔类型 ( bool )。但有时候，我们需要返回另一种类型：存在 和 空。
    // - 存在 就是硬币落地时显示为正面
    // - 空 就是硬币落地式显示的不是正面。

    // 从另一方面说，结果就是 有值 和 空 。
    // 一个类，如果可以同时表示 有值 和 空 ，我们称这种类为 可选类 ( Optional )
    // 从某些方面说，Optional 类型就是 「那里有一个值，它等于 x，或者那里没有那个值」

    // 1、java.util.Optional 类
    // Optional 类是一个容器，用于表示可能包含也可能不包含非 null 值。如果存在值，isPresent() 方法将返回 true，get() 将返回该值。
    // Optional 类提供了许多方法用于处理 「 可用 」 或 「 不可用 」 ，而不是简单的检查空值情况。
    // public final class Optional<T> extends Object
    // 注意：该类是一个最终类，不能被继承和扩展。

    // 2、Optional 类提供了以下静态方法来创建 Optional 类的实例
    // Optional 类提供了三个静态方法用于创建 Optional 类的实例，这三个方法的返回值都是 Optional<T>
    // 方法	    说明
    // empty()	创建一个空 ( empty ) 的 Optional 类的实例
    // of(T value)	创建一个包含了指定 T 类型的 value 值的 Optional 实例
    // ofNullable(T value)	如果 value 非 null ，则创建一个包含了指定 T 类型的 value 值的 Optional 实例，否则创建一个空的 Optional 实例

    // 3、Optional 类提供的方法
    // 方法                          说明
    //boolean equals(Object obj)    判断某个其它的对象是否 「 等于 」 此 Optional
    //Optional<T> filter(Predicate<? super T> predicate)    如果存在值，并且值与给定谓词匹配，则返回描述值的 Optional，否则返回空 Optional
    //<U> Optional<U> flatMap(Function<? super T>, Optional<U>> mapper)   如果值存在，则将 map 应用到该值上并返回应用后的结果，如果值不存在，则返回一个空的 Optional
    //T get()   如果此 Optional 中存在值，则返回该值，否则抛出NoSuchElementException 异常
    //int hashCode()    如果值存在，则返回当前值的哈希值，如果不存在值，则返回 0
    //void ifPresent(Consumer<? super T> consumer)  如果值存在，则使用该值作为参数调用方法 consumer 。如果值不存在，则什么事情都不做
    //boolean isPresent()   如果值存在则返回 true ，否则返回 false
    //<U> Optional<U> map(Function<? super T,? extends U> mapper)   如果存在值，则将传递的 map 函数应用于该值，如果结果为非 null，则返回描述结果的 Optionals
    //T orElse(T other)     如果值存在则返回值，否则返回 other
    //T orElseGet(Supplier<? extends T> other)  如果值存在则返回值，否则调用 other 并返回该调用的结果
    //<X extends Throwable> T orElseThrow(Supplier<? extends X>>exceptionSupplier)  如果值存在，则返回包含的值，否则抛出由开发者提供的异常
    //String toString()     返回此 Optional 的非空字符串表示形式，一般用于调试

    public void testOptional() {
        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);
        Optional<Integer> a = Optional.ofNullable(value1);
        Optional<Integer> b = Optional.of(value2);

        Integer v1 = a.orElse(Integer.valueOf(0));
        Integer v2 = b.get();
        System.out.println(v1 + v2);
    }


    // 十一、Nashorn JavaScript
    // 对于Java 中的 JavaScript 引擎， Java 8 引入了 Nashorn 来代替原先的 Rhino。
    // Nashorn 使用 Java 7 中引入的调用动态特性，且直接编译内存中的代码并将字节码传递给 JVM。这两项改进，直接给 Nashorn 带了至少 2 到 10 倍的性能提升。

    // jjs
    // 在Nashorn JavaScript 引擎中。JAVA 8 引入了一个新的命令行工具 jjs，用于在控制台执行 javascript 代码。
    // jjs hello.js
    // 在启动jjs 交互式解释器时，我们还可以使用两个中划线 ( -- ) 来传递一些参数。
    // 参数之间使用空格分隔 ( 注意，多个空额会视为单个空格 )。
    // jjs -- 你好世界 HelloWorld
    // jjs> print('你输入的是: ' +arguments.join(", "))

    // 在 Java 中调用 JavaScript
    // 通过ScriptEngineManager 类，Java 可以解析和调用 JavaScript 代码。
    // 1、 创建JavaScript引擎的管理器，也就是创建一个ScriptEngineManager类的实例；
    // 2、 通过JavaScript引擎的管理器获取一个JavaScript引擎，比如Java8中引入的Nashorn；
    // 3、 调用获取到的引擎的eval()方法执行一些代码；
    public void testNashorn() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

        Integer result = null;
        try {
            nashorn.eval("print('你好世界')");
            result = (Integer) nashorn.eval("1 + 1");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }


    // 十二、本地日期时间
    // 作为开发者，经常需要处理日期时间。如果你跟随者 Java 5 一路走来，
    // 那么一定会对 java.util.Date 、java.util.Calendar 、java.util.GregorianCalendar 和 java.text.SimpleDateFormat 四大类非常熟悉，
    // 它们分别用于处理日期、日历、日历表示、日期时间格式化。

    // 这四个类，对于编程老人来讲，应该是习惯了，但对于编程新人来讲，就有好多疑问，有好多陷阱和坑等着它们跳，比如
    // 1、 非线程安全：java.util.Date 并不是线程安全的。开发者在使用这个类时必须自己处理多线程并发问题。
    // 2、 设计不佳 ：一方面日期和日期格式化分布在多个包中。另一方面，java.util.Date 的默认日期，年竟然是从 1900 开始，月从 1 开始，日从 0 开始，没有统一性。而且 Date 类也缺少直接操作日期的相关方法。
    // 3、 时区处理困难：因为设计不佳，开发人员不得不编写大量代码来处理时区问题。
    // 4、 还有其它一些问题；

    // 面对种种问题，Java 8 终于重新设计了所有日期时间、日历及时区相关的 API。并把它们都统一放置在 java.time 包和子包下。并作出了以下改进
    // 1、 新的日期时间API是线程安全的不仅没有setter方法，而且任何对实例的变更都会返回一个新的实例而保证原来的实例不变；
    // 2、 新的日期时间API提供了大量的方法，用于修改日期时间的各个部分，并返回一个新的实例；
    // 3、 在时区方面，新的日期时间API引入了域(domain)这个概念；
    // 同时Java 8 还针对原来复杂的 API 进行重新组合和拆分，分成了好多个类。本章接下来的章节，我们就来详细介绍其中几个最重要的。

    // 1、本地日期时间 API
    // Java 8 为处理本地的日期时间提供了三个类 LocalDate 、LocalTime 和 LocalDateTime。
    // 分别用于处理 本地日期、本地时间 和 本地日期时间。
    // 当使用这三个类时，开发者并不需要关心时区是什么。因为它默认使用的是操作系统的时区。
    public void testLocalDate() {
        // 比如，可以使用 LocalDateTime.now() 方法返回当前的日期时间。
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);

        LocalDate date = dateTime.toLocalDate();
        System.out.println(date);

        LocalTime time = dateTime.toLocalTime();
        System.out.println(time);

        // 比如我们可以调用 LocalDateTime 对象的 getMonth() 方法返回当前的月份，调用 getDayOfMonth() 返回当前的日期，调用 getSecond() 返回当前时间的秒数
        Month month = dateTime.getMonth();
        System.out.println(month);

        int day = dateTime.getDayOfMonth();
        System.out.println(day);

        int second = dateTime.getSecond();
        System.out.println(second);

        // 比如我们可以调用 LocalDateTime 对象的 withDayOfMonth() 修改日并返回一个新的实例，调用 withYear() 修改年，调用其它 with* 方法修改其它属性。
        // 这些 with 方法都是返回新的实例，而原来的实例并不会改变。
        LocalDateTime newDateTime = dateTime.withDayOfMonth(10)
                .withYear(2024); // 没有变更的话，不会创建新示例
        System.out.println(newDateTime);
        System.out.println(dateTime);

        // 同时，新的日期时间 API 还大量引入了 of() 方法，比如我们可以调用 LocalDate.of() 方法创建一个日期实例，调用 LocalTime.of() 方法创建一个时间实例。
        LocalDate newDate = LocalDate.of(2019, Month.OCTOBER, 01);
        System.out.println(newDate);

        LocalTime newTime = LocalTime.of(12, 30);
        System.out.println(newTime);

        // 我们还可以调用 LocalDateTime.parse() 、LocalDate.parse() 和 LocalTime.parse() 方法解析字符串格式的日期时间、日期和时间。
        LocalDateTime parseDateTime = LocalDateTime.parse("2024=02-15T22:00:00");
        System.out.println(parseDateTime);

        LocalDate parseDate = LocalDate.parse("2024-10-01");
        System.out.println(parseDate);

        LocalTime parseTime = LocalTime.parse("22:00:00");
        System.out.println(parseTime);
    }


    // 十三、新日期时间 API ( 中 ) – 时区日期时间
    // 以此同时，Java 在 java.time 包中也提供了几个类用于处理需要关注时区的日期时间 API。它们是 java.time.ZonedDateTime 和 java.time.ZoneId。前者用于处理需要时区的日期时间，后者用于处理时区。
    // ZonedDateTime 和 LocalDateTime 类似，几乎有着相同的 API。从某些方面说，ZonedLocalTime 如果不传递时区信息，那么它会默认使用操作系统的时区，这样，结果其实和 LocalDateTime 是类似的。
    public void testZoneLocalTime() {
        // 比如，我们可以使用 ZonedDateTime 的 now() 方法返回当前时区 ( 操作系统时区 ) 的日期时间，
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);

        // 调用 parse() 方法可以将一个包含了时区信息的字符串格式的日期时间转化为一个 ZonedDateTime 实例。
        ZonedDateTime dateTime = ZonedDateTime.parse("2024-10-10T21:58:00+08:00");
        System.out.println(dateTime);

        // 我们还可以调用 ZonedDateTime 对象的 toLocalDate() 和 toLocalTime() 方法将获取该实例的转换为本地的日期和时间
        LocalDate localDate = now.toLocalDate();
        System.out.println(localDate);

        LocalTime localTime = now.toLocalTime();
        System.out.println(localTime);

        // 处理时区
        // 时区相关的信息，我们可以使用 ZoneId 类来处理。
        // 比如可以调用 ZoneId 类的静态方法 systemDefault() 返回当前的时区。
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
        // 我们还可以调用 ZonedDateTime 实例的 getZone() 方法获取实例所在的时区
        ZoneId zone = now.getZone();
        System.out.println(zone);
    }

    // 十四、Java 8 - 新日期时间 API ( 下 ) – 格式化
    // Java 8 似乎也对 java.text.SimpleDateFormat 也不太满意，竟然重新创建了一个 java.time.format 包，该包下包含了几个类和枚举用于格式化日期时间。
    // java.time.format 包提供了以下几个类用于格式化日期时间
    // 类	                说明
    // DateTimeFormatter	用于打印和解析日期时间对象的格式化程序
    // DateTimeFormatterBuilder	创建日期时间格式化样式的构建器
    // DecimalStyle	日期和时间格式中使用的本地化十进制样式

    // java.time.format 包还提供了以下几个枚举，包含了常见的几种日期时间格式。
    // 枚举	        说明
    // FormatStyle	包含了本地化日期，时间或日期时间格式器的样式的枚举
    // ResolverStyle	包含了解决日期和时间的不同方法的枚举
    // SignStyle	包含了如何处理正/负号的方法的枚举
    // TextStyle	包含了文本格式和解析的样式的枚举

    // 1、DateTimeFormatter 类
    // DateTimeFormatter 类格式化日期时间的最重要的类，该类是一个最终类，只能实例化，不能被扩展和继承。
    // DateTimeFormatter 类用于打印和解析日期时间对象的格式化器。此类提供打印和解析的主要应用程序入口点，并提供 DateTimeFormatter 的常见模式
    // - 使用预定义的常量，比如 ISO_LOCAL_DATE
    // - 使用模式字母，例如 yyyy-MM-dd
    // - 使用本地化样式，例如 long 或 medium

    // 所有的日期时间类，包括本地日期时间和包含时区的日期时间类，都提供了两个重要的方法
    // 1、 一个用于格式化，format(DateTimeFormatter formatter)；
    // 2、 另一个用于解析，parse(CharSequence text, DateTimeFormatter formatter)；
    public void testDateTimeFormatter() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);

        String format1 = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        System.out.println(format1);

        // 我们还可以调用 DateTimeFormatter.ofPattern() 方法创建自己的日期时间格式，例如
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String format2 = now.format(dateTimeFormatter);
        System.out.println(format2);

        // 当然了，我们可以调用 LocalDateTime 类的静态方法 parse() 将我们刚刚自定义的日期时间格式给解析回来
        LocalDateTime parse = LocalDateTime.parse(format2, dateTimeFormatter);
        System.out.println(parse);
    }

    // 十五、流 Stream ( 上 )
    // 流(Stream ) 是 Java 8 新增加的一个重磅级的功能。流是一个抽象层。有了流，我们就可以使用类似于 SQL 语句的声明方式来处理数据。
    // 在流（ Stream ) 出现之前，对于Java 中的集合框架的使用。开发人员不得不一次次的写一个循环，一次次的重复检查。当然了，这也什么，毕竟大家都是这样过来的。
    // 更大的问题在于开发效率。面对当前的多核 CPU 计算机，面对并发编程。我们开发者常常会写出非常容易出错的并发执行的代码。
    // 为了解决这些问题，Java 8 引入了流 ( Stream ) 这个概念，允许开发人员以声明的方式处理数据的同时，还能利用多核构架，而无需编写任何特殊的代码。

    // 流是什么 ?
    // Java 中的 流 ( Stream ) 表示来自 源 ( source ) 的一系列对象，它支持统计、求和、求平均值等聚合操作。
    // 流具有以下特征：
    // - 元素序列 : 流以顺序方式提供特定类型的一组元素。流只会按需获取/计算元素。但它从不存储元素。
    // - 源 ( Source )：流可以将集合，数组或 I/O 资源作为输入源。
    // - 聚合操作： 流支持聚合操作，如 filter、map、limit、reduce、find、match 等
    // - 管道 ( pipelining )：大多数流操作都返回流本身，以便可以对其结果进行流水线操作。这些操作称为 中间 操作，它们的功能是获取输入，处理它们并将输出返回到目标。collect() 方法是一个终端操作，通常在流水线操作结束时出现，以标记流的结尾。
    // - 原子性迭代 ( Automatic iterations ) ： 与需要显式迭代的集合相比，流操作在内部对所提供的源元素进行迭代。

    // 流的创建
    // Java 8 在推出流的同时，对集合框架也进行了一些比较大变更。主要是在 Collection 接口上提供了两种生成 Stream 的方法:
    // - stream() 方法，该方法以集合作为源，返回集合中的所有元素以在集合中出现的顺序组成的流。
    // - parallelStream() 方法，该方法以集合作为源，返回一个支持并发操作的流。

    // 流支持的聚合操作
    public void testStream() {
        // forEach() 方法
        // Java 8 为 Stream 提供了一种新方法 forEach()，用于迭代流的每个元素。
        Random random = new Random();
        random.ints()
                .limit(10)
                .forEach(System.out::println);
        // 上面这个代码片段中，Random 对象的 ints() 方法会返回一个整数流。
        // 而 limit() 方法则限制了流中的元素个数。从某些方面说，可以理解为当源产生了 10 个随机数之后就关闭源。

        // map() 方法
        // map() 方法会迭代流中的元素，并为每个元素应用一个方法，然后返回应用后的流。
        // 例如下面的代码，使用 map() 方法把求出每个元素的平方，然后过滤掉重复的元素，最后在转换为列表集合
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream()
                .map(i -> i * i)
                .distinct()
                .collect(Collectors.toList());
        // map( i ->` i*i) 操作求取流中每个元素的平方，并返回一个新的流。distinct() 方法则用于过滤流中的重复元素。

        // filter() 方法
        // filter() 方法根据一个谓词来过滤元素。这个谓词是一个方法，以流中的每一个元素作为参数，如果返回 true 则会被过滤掉。
        // 例如下面的代码，使用 filter() 方法过滤那些空字符串。
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.stream()
                .filter(String::isEmpty)
                .count();

        // limit() 方法
        // limit() 方法用于减少( 限制 ) 流中的元素数量。
        // 例如下面的代码段演示了如何使用 limit() 方法只输出 10 个随机数
        random.ints()
                .limit(10)
                .forEach(System.out::println);

        // sorted() 方法
        // sorted() 方法用于给流中的元素进行排序。
        // 下面的范例演示了如何按照排序顺序打印 10 个随机数
        random.ints()
                .limit(10)
                .sorted()
                .forEach(System.out::println);
    }

    // 并发处理
    // parallelStream() 是需要并发处理的流的替代方案。stream() 方法产生的流只能是串行处理，可以理解为只在一个线程中，按照流中元素的顺序一个接一个的处理。
    // 而并发处理，就是传说中的 map-reduce 方法，可以充分利用多核优势。
    // 需要注意的是，parallelStream() 会打乱流的顺序，也就是返回的序列顺序不一定是输入的序列顺序。
    // 例如下面的代码用于打印序列中的空字符串的数量
    public void testParallelStream() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.parallelStream()
                .filter(String::isEmpty)
                .count();
    }
    // 因为stream() 返回是串行流，而 parallelStream() 返回的是并行流。因此在串行和并行之间切换是非常简单的。

    // 收集器 （ Collectors ）
    // 收集器（ Collectors ）用于将已经处理的流中的元素组合到一起。
    // Collectors 类提供了大量方法用于指示如何收集元素。
    public void testCollector() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 比如Collectors.toList() 方法可以将流中的元素收集起来，并转换为列表
        List<String> filtered = strings.stream()
                .filter(String::isEmpty)
                .collect(Collectors.toList());

        // 比如Collectors.joining() 方法可以将流中的元素收集起来，并使用指定的字符串拼接符拼接成一个字符串。
        String joined = strings.stream()
                .filter(s -> s.isEmpty())
                .collect(Collectors.joining(","));
    }

    // 统计 ( Statistics )
    // Java 8 同时新增了大量的统计收集器来来获取流中的元素的一些统计信息。
    // 前提是我们先要在流上调用 summaryStatistics() 方法返回统计信息概要，然后在调用相应的方法来获取具体的统计信息。
    public void testStatistics() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 例如下面的代码，先调用 summaryStatistics() 方法返回统计概要，然后调用 getMax() 方法获取最大值
        IntSummaryStatistics intSummaryStatistics = numbers.stream()
                .mapToInt((x) -> x)
                .summaryStatistics();

        // 例如下面的代码，先调用 summaryStatistics() 方法返回统计概要，然后调用 getMin() 和 getSum() 方法获取最小值和所有数字之和
        int min = intSummaryStatistics.getMin();
        int max = intSummaryStatistics.getMax();

        // 例如下面的代码，先调用 summaryStatistics() 方法返回统计概要，然后调用 getAverage() 方法获取平均值
        double average = intSummaryStatistics.getAverage();
    }

}

class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String customShow(Function<Student, String> fun) {
        return fun.apply(this);
    }
}

class Book {
    public int id;
    public String name;

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("id:" + id + ", Name:" + name);
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static List<Person> getList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Ram", 30));
        list.add(new Person("Shyam", 20));
        list.add(new Person("Shiv", 20));
        list.add(new Person("Mahesh", 30));
        return list;
    }

}

class IntegerComp implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 >= o2 ? 1 : -1;
    }
}

